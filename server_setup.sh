#!/bin/bash
set -e

# 1. Actualizar e instalar dependencias
echo ">>> Actualizando sistema e instalando dependencias..."
sudo apt update && sudo apt upgrade -y
sudo apt install openjdk-21-jdk git authbind postgresql build-essential automake autoconf -y

# 2. Configurar PostgreSQL (Academic Requirement)
echo ">>> Configurando PostgreSQL..."
sudo -u postgres psql -c "ALTER USER postgres WITH PASSWORD 'emico3110';"
sudo -u postgres psql -c "ALTER USER postgres WITH SUPERUSER;"
sudo -u postgres psql -c "CREATE DATABASE perritos_web;" || echo "La base de datos ya existe"

# Habilitar acceso MD5
PG_CONF=$(ls /etc/postgresql/*/main/pg_hba.conf | head -n 1)
sudo sed -i 's/local   all             postgres                                peer/local   all             postgres                                md5/' $PG_CONF
sudo sed -i 's/local   all             all                                     peer/local   all             all                                     md5/' $PG_CONF
sudo systemctl restart postgresql

# 3. Configurar Authbind para puerto 80
echo ">>> Configurando Authbind..."
sudo touch /etc/authbind/byport/80
sudo chmod 500 /etc/authbind/byport/80
sudo chown git_deploy /etc/authbind/byport/80 || echo "El usuario git_deploy aún no existe, se asignará más tarde."

sudo touch /etc/authbind/byport/8080
sudo chmod 500 /etc/authbind/byport/8080
sudo chown git_deploy /etc/authbind/byport/8080 || echo "El usuario git_deploy aún no existe, se asignará más tarde."

# 3. Crear Grupo y Usuario
echo ">>> Creando usuario y grupo..."
if ! getent group deployers > /dev/null; then
  sudo groupadd deployers
fi

if ! id "git_deploy" &>/dev/null; then
  sudo useradd -m -g deployers git_deploy
  echo "Usuario git_deploy creado."
else
  echo "Usuario git_deploy ya existe."
fi

# Asignar authbind al usuario ahora que existe
sudo chown git_deploy /etc/authbind/byport/80

# 5. Crear Directorios de la Aplicación
echo ">>> Creando directorios..."
sudo mkdir -p /opt/apps/backend/scripts
sudo chown -R git_deploy:deployers /opt/apps/backend
sudo chmod -R 770 /opt/apps/backend

# Carpeta temporal (Requerimiento Académico)
sudo mkdir -p /tmp/deploy
sudo chown -R git_deploy:deployers /tmp/deploy
sudo chmod -R 770 /tmp/deploy

# 6. Configurar Sudoers (Requerimiento Académico)
echo ">>> Configurando permisos sudo..."
SUDO_FILE="/etc/sudoers.d/deployers"
echo "git_deploy ALL=(root) NOPASSWD: /usr/bin/systemctl start myapp.service" | sudo tee $SUDO_FILE
echo "git_deploy ALL=(root) NOPASSWD: /usr/bin/systemctl stop myapp.service" | sudo tee -a $SUDO_FILE
echo "git_deploy ALL=(root) NOPASSWD: /usr/bin/systemctl restart myapp.service" | sudo tee -a $SUDO_FILE
echo "git_deploy ALL=(root) NOPASSWD: /usr/bin/systemctl daemon-reload" | sudo tee -a $SUDO_FILE

# 7. Instalar SHC y Ejercicio Extra (hola.sh)
echo ">>> Instalando SHC y ejecutando ejercicio extra..."
cd /tmp
rm -rf shc
git clone https://github.com/neurobin/shc.git
cd shc
# Fix: Generar archivos de configuración faltantes
autoreconf -vfi
./configure && make && sudo make install
cd ~

# Ejercicio Extra: hola.sh
# Fix: Usar cat con EOF entre comillas para evitar error de history expansion (!)
cat << 'EOF_HOLA' > hola.sh
#!/bin/bash
echo "Este es un archivo ejecutable de prueba"
EOF_HOLA

shc -f hola.sh -o ejecutable
chmod +x ejecutable
echo ">>> Probando ejecutable SHC:"
./ejecutable

# 8. Crear Servicio Systemd (myapp.service)
echo ">>> Creando servicio myapp.service..."
sudo bash -c 'cat << EOF > /etc/systemd/system/myapp.service
[Unit]
Description=My Java APP
After=network.target

[Service]
User=git_deploy
Group=deployers
WorkingDirectory=/opt/apps/backend
ExecStart=/bin/bash -c "/usr/local/bin/authbind --deep /usr/bin/java -jar \$(ls /opt/apps/backend/app*.jar | head -n 1)"
SuccessExitStatus=143
StandardOutput=journal
StandardError=journal
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
EOF'

sudo systemctl daemon-reload

# 9. Crear Scripts de Despliegue (pre-deploy.sh y post-deploy.sh)
echo ">>> Creando scripts de despliegue..."
SCRIPTS_DIR="/opt/apps/backend/scripts"

# pre-deploy.sh
sudo bash -c "cat << 'EOF' > $SCRIPTS_DIR/pre-deploy.sh
#!/bin/bash
SERVICE_NAME=\"myapp.service\"
APP_DIR=\"/opt/apps/backend/\"

echo \">>> [PRE-DEPLOY] INICIANDO...\"

if systemctl is-active --quiet \$SERVICE_NAME; then
    echo \"Deteniendo servicio \$SERVICE_NAME...\"
    sudo systemctl stop \$SERVICE_NAME
else
    echo \"El servicio ya se encuentra detenido\"
fi

echo \"Eliminando jars antiguos en \$APP_DIR...\"
find \"\$APP_DIR\" -name \"app*.jar\" -type f -delete || true
echo \">>> [PRE-DEPLOY] COMPLETADO\"
EOF"
sudo chmod +x $SCRIPTS_DIR/pre-deploy.sh
sudo chown git_deploy:deployers $SCRIPTS_DIR/pre-deploy.sh

# post-deploy.sh
sudo bash -c "cat << 'EOF' > $SCRIPTS_DIR/post-deploy.sh
#!/bin/bash
SERVICE_NAME=\"myapp.service\"

echo \">>> [POST-DEPLOY] INICIANDO\"

sudo systemctl daemon-reload
echo \"Reiniciando \$SERVICE_NAME...\"
sudo systemctl start \$SERVICE_NAME

echo \"VERIFICANDO ESTADO\"

if systemctl is-active --quiet \$SERVICE_NAME; then
        echo \"SUCCESS: EL SERVICIO ESTÁ EN LINEA\"
        exit 0
else
        echo \"ERROR: El servicio falló al iniciar\"
        sudo journalctl -u \$SERVICE_NAME -n 10 --no-pager
        exit 1
fi
EOF"
sudo chmod +x $SCRIPTS_DIR/post-deploy.sh
sudo chown git_deploy:deployers $SCRIPTS_DIR/post-deploy.sh

echo ">>> ¡Configuración del servidor completada!"
echo "Ahora debes configurar las llaves SSH para git_deploy en GitHub."

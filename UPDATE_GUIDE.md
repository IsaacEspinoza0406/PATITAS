# Guía de Actualización de Instancia EC2

Esta guía explica cómo actualizar tu aplicación en AWS EC2 después de hacer cambios en tu código local.

## 1. Subir Cambios a GitHub (Desde tu PC Local)

Primero, asegúrate de que tus cambios locales estén confirmados y subidos a tu repositorio.

```bash
# 1. Ver estado de archivos
git status

# 2. Agregar archivos (incluyendo los nuevos scripts)
git add .

# 3. Confirmar cambios
git commit -m "Agregando scripts de despliegue y correcciones de rutas"

# 4. Subir a GitHub
git push origin main
```

## 2. Actualizar en EC2

Conéctate a tu instancia y ejecuta los siguientes pasos.

### 2.1 Conexión SSH
```bash
ssh -i "patitas-key.pem" ec2-user@44.210.168.90
```

### 2.2 Cambiar al usuario de despliegue
```bash
sudo su - git_deploy
```

### 2.3 Descargar Cambios
```bash
cd /opt/apps/backend
# Si ya clonaste el repo, solo haz pull. Si no, clónalo primero.
git pull origin main
```

### 2.4 Re-compilar (Si es necesario)
Si hiciste cambios en código Java/Kotlin:
```bash
./gradlew build -x test
```

### 2.5 Ejecutar Despliegue
Usa los scripts que creamos para reiniciar el servicio de forma segura.

```bash
# Dar permisos de ejecución (solo la primera vez o si cambian)
chmod +x pre-deploy.sh post-deploy.sh

# 1. Detener servicio y limpiar
./pre-deploy.sh

# 2. Iniciar servicio y verificar
./post-deploy.sh
```

## 3. Verificación Rápida
Puedes ver los logs en tiempo real para asegurarte de que todo va bien:

```bash
journalctl -u myapp.service -f
```

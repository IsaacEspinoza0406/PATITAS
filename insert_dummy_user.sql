-- Script para insertar un usuario dummy con id=1 y un rol básico
-- Esto permite que created_by=1 sea válido al crear perros

-- Primero insertar un rol básico si no existe
INSERT INTO public.roles (id, name, description)
VALUES (1, 'Admin', 'Usuario administrador por defecto')
ON CONFLICT (id) DO NOTHING;

-- Insertar usuario dummy con id=1 (referencia al rol creado)
INSERT INTO public.users (id, role_id, name, email, password, telefono)
VALUES (1, 1, 'Usuario Sistema', 'sistema@patitas.com', 'dummy_password_hash', '000-0000')
ON CONFLICT (id) DO NOTHING;

-- Confirmar que se insertó correctamente
SELECT id, name, email FROM public.users WHERE id = 1;

import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-user-auth',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './user-auth.html',
  styleUrls: ['./user-auth.css']
})
export class UserAuthComponent {

  private authService = inject(AuthService);
  private router = inject(Router);

  isRegisterMode = false;

  loginData = {
    email: '',
    password: ''
  };

  registerData = {
    userName: '', // En el formulario usamos este nombre
    email: '',
    password: '',
    confirmPassword: ''
  };

  toggleMode() {
    this.isRegisterMode = !this.isRegisterMode;
  }

  // --- LOGIN ---
  onLogin() {
    console.log('Enviando login...', this.loginData);

    this.authService.login(this.loginData).subscribe({
      next: (response) => {
        console.log('Login exitoso:', response);
        // Guardamos el token si viene en la respuesta
        // (El servicio ya lo hace, pero por si acaso)
        alert('¡Bienvenido!');
        this.router.navigate(['/inicio']);
      },
      error: (error) => {
        console.error('Error login:', error);
        // Checa si el status es 0 (CORS) o 401 (Credenciales)
        if (error.status === 0) {
          alert('Error de conexión (Posiblemente CORS). Revisa que el servidor permita localhost:4200');
        } else {
          alert('Usuario o contraseña incorrectos.');
        }
      }
    });
  }

  // --- REGISTRO ---
  onRegister() {
    if (this.registerData.password !== this.registerData.confirmPassword) {
      alert('Las contraseñas no coinciden.');
      return;
    }

    // --- CORRECCIÓN AQUÍ ---
    // Tu API (Postman) espera "name", no "username".
    const newUser = {
      name: this.registerData.userName,
      email: this.registerData.email,
      password: this.registerData.password,
      roleId: 2 // Opcional: Si tu API pide rol por defecto
    };

    console.log('Enviando registro...', newUser);

    this.authService.register(newUser).subscribe({
      next: (response) => {
        console.log('Registro exitoso:', response);
        alert('¡Cuenta creada con éxito! Ahora inicia sesión.');
        this.isRegisterMode = false;
      },
      error: (error) => {
        console.error('Error registro:', error);
        if (error.status === 0) {
          alert('Error de conexión con el servidor (CORS).');
        } else {
          alert('No se pudo crear la cuenta. Intenta con otro correo.');
        }
      }
    });
  }
}
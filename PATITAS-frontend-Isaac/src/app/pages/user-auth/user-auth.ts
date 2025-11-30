import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-user-auth',
  standalone: true,
  imports: [CommonModule, FormsModule],
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
    userName: '',
    email: '',
    password: '',
    confirmPassword: ''
  };

  toggleMode() {
    this.isRegisterMode = !this.isRegisterMode;
  }

  // Login.
  onLogin() {
    console.log('Enviando login...', this.loginData);

    this.authService.login(this.loginData).subscribe({
      next: (response: any) => {
        console.log('Login exitoso:', response);
        alert('¡Bienvenido!');
        this.router.navigate(['/perros']);
      },
      error: (error: any) => {
        console.error('Error login:', error);
        alert('Credenciales incorrectas o error en el servidor.');
      }
    });
  }

  // Registro.
  onRegister() {
    if (this.registerData.password !== this.registerData.confirmPassword) {
      alert('Las contraseñas no coinciden.');
      return;
    }

    const newUser = {
      name: this.registerData.userName,
      email: this.registerData.email,
      password: this.registerData.password,
      roleId: 2
    };

    console.log('Enviando registro...', newUser);

    this.authService.register(newUser).subscribe({
      next: (response: any) => {
        console.log('Registro exitoso:', response);
        alert('¡Cuenta creada con éxito! Ahora inicia sesión.');
        this.isRegisterMode = false;
      },
      error: (error: any) => {
        console.error('Error registro:', error);
        alert('No se pudo crear la cuenta. Intenta con otro correo.');
      }
    });
  }
}
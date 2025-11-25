import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {

  private authService = inject(AuthService);
  private router = inject(Router);

  // Datos del formulario.
  loginData = {
    email: '',
    password: ''
  };

  // Iniciar sesión.
  onLogin() {
    console.log('Intentando iniciar sesión como Admin:', this.loginData);

    this.authService.login(this.loginData).subscribe({
      next: (response: any) => {
        console.log('Login exitoso:', response);

        if (response.user && response.user.roleId === 1) {
          alert('¡Bienvenido Administrador!');
          this.router.navigate(['/admin/adoption-request']);
        } else {
          alert('Acceso denegado. No tienes permisos de administrador.');
          this.authService.logout();
        }
      },
      error: (error: any) => {
        console.error('Error login:', error);
        alert('Credenciales incorrectas o error en el servidor.');
      }
    });
  }
}

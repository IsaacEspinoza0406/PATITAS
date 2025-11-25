import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule,], 
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {

  // Con esta variable controlamos si estamos en modo Login o Registro.
  isRegisterMode = false;

  // Datos del formulario
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

  // Función para cambiar entre modo Login y Registro.
  toggleMode() {
    this.isRegisterMode = !this.isRegisterMode;
  }

  // Iniciar sesión.
  onLogin() {
    console.log('Intentando iniciar sesión con:', this.loginData);
    alert('¡Iniciando sesión!');
  }

  // Registrarse.
  onRegister() {
    console.log('Intentando registrar con:', this.registerData);
    if (this.registerData.password !== this.registerData.confirmPassword) {
      alert('Las contraseñas no coinciden.');
      return;
    }
    alert('¡Registrado con éxito!');
  }
}


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

  

  // Iniciar sesión.
  onLogin() {
    console.log('Intentando iniciar sesión con:', this.loginData);
    alert('¡Iniciando sesión!');
  }
}


import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';  

//para standalone
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-login-user',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './login-user.html',
  styleUrls: ['./login-user.css']
})
export class LoginUser {
constructor() { }
  iniciarSesion(form: NgForm) { 
    if (form.invalid) {
      console.log('Formulario no válido');
      return;
    }

    console.log('Datos del formulario:', form.value);
  }
}

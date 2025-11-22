import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-registration',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './user-registration.html',
  styleUrls: ['./user-registration.css']
})
export class UserRegistration {
Registrarse(forms: any) {
    if (forms.invalid) {
      console.log('Formulario no válido');
      return;
    }else{
      console.log('Datos del formulario:', forms.value);
    }
}
}

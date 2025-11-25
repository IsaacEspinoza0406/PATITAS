import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 

@Component({
  selector: 'app-forms',
  standalone: true,
  imports: [CommonModule, FormsModule], 
  templateUrl: './forms.html',
  styleUrls: ['./forms.css']
})
export class FormsComponent {
  step = 1;

  // Cree este objeto para almacenar los datos del formulario.
  public formData: any = {
    // Campos del contenedor 1.
    nombre: '',
    telefono: '',
    edad: null,
    ocupacion: '',
    ingreso: null,
    horas: null,

    // Campos del contenedor 2.
    espacio: '',
    habitantes: '',
    vivienda: '',
    personas: null,

    // Campos del contenedor 3.
    mascotas: '',
    cuidado: '',
    responsabilidades: '',
    esterilizacion: ''
  };

  nextStep() {
    if (this.step < 3) this.step++;
  }

  prevStep() {
    if (this.step > 1) this.step--;
  }

  onSubmit() {
    console.log('Datos del formulario:', this.formData);
    
    alert('¡Respuestas enviadas con éxito!');
    // Aquí se limpia el formulario después de darle enviar y te regresa al primer paso.
    this.formData = {
      nombre: '',
      telefono: '',
      edad: null,
      ocupacion: '',
      ingreso: null,
      horas: null,
      espacio: '',
      habitantes: '',
      vivienda: '',
      personas: null,
      mascotas: '',
      cuidado: '',
      responsabilidades: '',
      esterilizacion: ''
    };
    this.step = 1;
  }

  }

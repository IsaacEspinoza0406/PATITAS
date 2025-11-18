import { Component, inject } from '@angular/core'; 
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { HttpClient } from '@angular/common/http'; 

@Component({
  selector: 'app-forms',
  standalone: true,
  imports: [CommonModule, FormsModule], 
  templateUrl: './forms.html',
  styleUrls: ['./forms.css']
})
export class FormsComponent {
  step = 1;

  private http = inject(HttpClient);
  private apiUrl = 'http://127.0.0.1:8080/adoptantes';

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
    edades: '', 
    vivienda: '',
    personas: null,

    // Campos del contenedor 3.
    mascotas: '',
    cuidado: '',
    sigueCuidando: '', 
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
    console.log('Datos del formulario (Frontend):', this.formData);

    const apiPayload = {
      nombreCompleto: this.formData.nombre,
      telefono: this.formData.telefono,
      edad: String(this.formData.edad), 
      ocupacion: this.formData.ocupacion,
      ingresoMensual: String(this.formData.ingreso), 
      horasDeTrabajo: String(this.formData.horas), 
      tienePatio: this.formData.espacio,
      ninosEnCasa: this.formData.habitantes + " (Edades: " + this.formData.edades + ")", 
      tipoVivienda: this.formData.vivienda,
      convivientes: String(this.formData.personas), 
      mascotasAnteriores: this.formData.mascotas,
      aunConservaMascotas: this.formData.cuidado + " (Sigue cuidando: " + this.formData.sigueCuidando + ")", 
      responsabilidadesMascota: this.formData.responsabilidades,
      opinionEsterilizacion: this.formData.esterilizacion
    };
    
    console.log('Datos a enviar a la API (Backend):', apiPayload);

    this.http.post(this.apiUrl, apiPayload).subscribe({
      next: (respuesta) => {
        console.log('¡Solicitud enviada con éxito!', respuesta);
        alert('¡Tu solicitud de adopción ha sido enviada con éxito!');
        
        this.formData = {
          nombre: '', telefono: '', edad: null, ocupacion: '', 
          ingreso: null, horas: null, espacio: '', habitantes: '', 
          edades: '', vivienda: '', personas: null, mascotas: '', 
          cuidado: '', sigueCuidando: '', responsabilidades: '', esterilizacion: ''
        };
        this.step = 1;
      },

      error: (error) => {
        console.error('Error al enviar la solicitud:', error);
        alert('Hubo un error al enviar tu solicitud. Por favor, inténtalo de nuevo.');
      }
    });
  }
  }
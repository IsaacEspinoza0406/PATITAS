import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-adoption-request-form',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './adoption-request-form.html',
  styleUrl: './adoption-request-form.css'
})
export class AdoptionRequestForm {
  // Datos de ejemplo de la solicitud
  solicitud = {
    nombre: 'Juan Pérez',
    telefono: '5551234567',
    ocupacion: 'Ingeniero',
    ingresosMensuales: '$15,000'
  };
}

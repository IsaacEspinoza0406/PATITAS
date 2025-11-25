import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Importante para *ngFor
import { Router } from '@angular/router';

interface AdoptionRequest {
  id: number;
  name: string;
}

@Component({
  selector: 'app-adoption-request',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './AdoptionRequest.html',
  styleUrl: './AdoptionRequest.css'
})
export class AdoptionRequestComponent implements OnInit {

  requests: AdoptionRequest[] = [];

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.loadMockData();
  }

  loadMockData(): void {
    this.requests = [
      { id: 101, name: 'Paula Jiménez' },
      { id: 102, name: 'Paulina Mendoza' },
      { id: 103, name: 'Roberto Pinto' },
      { id: 104, name: 'Octavio Cruz' },
      { id: 105, name: 'David Molina' },
      { id: 106, name: 'Daniela Vázquez' },
    ];
  }

  // --- LÓGICA DE LOS BOTONES ---

  /**
   * Punto 1: Redirige a la vista de detalle de la solicitud.
   */
  viewRequest(id: number): void {
    console.log('Navegando al detalle de la solicitud:', id);

    // Usamos el router para navegar a una nueva ruta.
    this.router.navigate(['/solicitud', id]);

  }

  deleteRequest(id: number): void {
    console.log('Eliminando solicitud:', id);
    this.requests = this.requests.filter(request => request.id !== id);

  }
}

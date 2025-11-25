import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AdoptanteService } from '../../../services/adoptante';
import { AdoptanteFullResponse } from '../../../interfaces/models';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-adoption-request',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './AdoptionRequest.html',
  styleUrls: ['./AdoptionRequest.css']
})
export class AdoptionRequestComponent implements OnInit {

  private adoptanteService = inject(AdoptanteService);
  public requests: AdoptanteFullResponse[] = [];
  public selectedRequest: AdoptanteFullResponse | null = null;
  public isLoading = true;
  public error: string | null = null;

  ngOnInit(): void {
    this.loadRequests();
  }

  loadRequests(): void {
    this.isLoading = true;
    this.error = null;
    this.adoptanteService.getAdoptantes()
      .pipe(
        catchError((err: any) => {
          console.error('Error al jalar solicitudes:', err);
          this.error = 'No se pudieron cargar las solicitudes. Revisa la API.';
          this.isLoading = false;
          return of([]);
        })
      )
      .subscribe((data: AdoptanteFullResponse[]) => {
        this.requests = data;
        this.isLoading = false;
      });
  }

  viewRequest(request: AdoptanteFullResponse): void {
    this.selectedRequest = request;
  }

  deleteRequest(id: number): void {
    if (confirm('¿Estás seguro de que quieres eliminar esta solicitud?')) {
      this.adoptanteService.deleteAdoptante(id).subscribe({
        next: () => {
          alert('Solicitud eliminada.');
          this.requests = this.requests.filter(req => req.id !== id);
          if (this.selectedRequest?.id === id) {
            this.selectedRequest = null;
          }
        },
        error: (err: any) => {
          console.error('Error al eliminar:', err);
          alert('No se pudo eliminar la solicitud.');
        }
      });
    }
  }

  acceptRequest(id: number): void {
    alert('Función "Aceptar" todavía no implementada.');
  }
}
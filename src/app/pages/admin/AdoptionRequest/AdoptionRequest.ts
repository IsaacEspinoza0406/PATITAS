import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AdoptionService } from '../../../services/adoption.service';
import { AdoptionResponse } from '../../../interfaces/models';
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

  private adoptionService = inject(AdoptionService);
  public requests: AdoptionResponse[] = [];
  public selectedRequest: AdoptionResponse | null = null;
  public isLoading = true;
  public error: string | null = null;

  ngOnInit(): void {
    this.loadRequests();
  }

  loadRequests(): void {
    this.isLoading = true;
    this.error = null;
    this.adoptionService.getAdoptions()
      .pipe(
        catchError((err: any) => {
          console.error('Error al jalar solicitudes:', err);
          this.error = 'No se pudieron cargar las solicitudes. Revisa la API.';
          this.isLoading = false;
          return of([]);
        })
      )
      .subscribe((data: AdoptionResponse[]) => {
        this.requests = data;
        this.isLoading = false;
      });
  }

  viewRequest(request: AdoptionResponse): void {
    this.selectedRequest = request;
  }

  deleteRequest(id: number): void {
    if (confirm('¿Estás seguro de que quieres eliminar esta solicitud?')) {
      this.adoptionService.deleteAdoption(id).subscribe({
        next: () => {
          alert('Solicitud eliminada.');
          console.log('Eliminando solicitud con ID:', id);
          this.requests = this.requests.filter(req => req.id != id);
          if (this.selectedRequest?.id == id) {
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
    if (confirm('¿Aceptar solicitud? Esto marcará al perro como adoptado.')) {
      this.adoptionService.acceptAdoption(id).subscribe({
        next: () => {
          alert('Solicitud aceptada. El perro ha sido marcado como adoptado.');
          console.log('Aceptando solicitud con ID:', id);
          this.requests = this.requests.filter(req => req.id != id);
          if (this.selectedRequest?.id == id) {
            this.selectedRequest = null;
          }
        },
        error: (err: any) => {
          console.error('Error al aceptar:', err);
          alert('No se pudo aceptar la solicitud.');
        }
      });
    }
  }
}
import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AdoptionService } from '../../../services/adoption.service';
import { DogService } from '../../../services/dog.service';
import { AdoptionResponse, DogResponse } from '../../../interfaces/models';
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
  private dogService = inject(DogService);
  public requests: AdoptionResponse[] = [];
  public dogs: DogResponse[] = [];
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
        this.loadDogs();
      });
  }

  loadDogs(): void {
    this.dogService.getDogs().subscribe({
      next: (data) => {
        this.dogs = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching dogs', err);
        this.isLoading = false;
      }
    });
  }

  getDogImage(dogId: number): string {
    const dog = this.dogs.find(d => d.id === dogId);
    if (dog && dog.photos && dog.photos.length > 0) {
      return dog.photos[0].photoUrl;
    }
    return 'https://placehold.co/600x400/E0E0E0/B0B0B0?text=Sin+Foto';
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
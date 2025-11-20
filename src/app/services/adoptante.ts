import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


export interface Adoptante {
  id: number;
  nombreCompleto: string;
  telefono: string;
  edad: string;
  ocupacion: string;
  ingresoMensual: string;
  horasDeTrabajo: string;
  tienePatio: string;
  ninosEnCasa: string;
  tipoVivienda: string;
  convivientes: string;
  mascotasAnteriores: string;
  aunConservaMascotas: string;
  responsabilidadesMascota: string;
  opinionEsterilizacion: string;
}

@Injectable({
  providedIn: 'root'
})
export class AdoptanteService {

  private http = inject(HttpClient);
  private apiUrl = 'http://127.0.0.1:8080/adoptantes'; 

  constructor() {}

  // --- 1. Jala TODAS las solicitudes ---
  getAdoptantes(): Observable<Adoptante[]> {
    return this.http.get<Adoptante[]>(this.apiUrl);
  }

  // --- 2. Jala UNA solicitud por ID ---
  getAdoptanteById(id: number): Observable<Adoptante> {
    return this.http.get<Adoptante>(`${this.apiUrl}/${id}`);
  }

  // ---3. Borra una solicitud.---
  deleteAdoptante(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { AdoptanteRequest, AdoptanteFullResponse } from '../interfaces/models';

@Injectable({
  providedIn: 'root'
})
export class AdoptanteService {

  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/adoptantes`;

  constructor() { }

  // --- 1. Jala TODAS las solicitudes ---
  getAdoptantes(): Observable<AdoptanteFullResponse[]> {
    return this.http.get<AdoptanteFullResponse[]>(this.apiUrl);
  }

  // --- 2. Jala UNA solicitud por ID ---
  getAdoptanteById(id: number): Observable<AdoptanteFullResponse> {
    return this.http.get<AdoptanteFullResponse>(`${this.apiUrl}/${id}`);
  }

  // --- 3. Borra una solicitud.---
  deleteAdoptante(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { responseType: 'text' });
  }

  // --- 4. Crea una solicitud ---
  createAdoptante(adoptante: AdoptanteRequest): Observable<AdoptanteFullResponse> {
    return this.http.post<AdoptanteFullResponse>(this.apiUrl, adoptante);
  }

  // --- 5. Actualiza una solicitud ---
  updateAdoptante(id: number, adoptante: AdoptanteRequest): Observable<AdoptanteFullResponse> {
    return this.http.put<AdoptanteFullResponse>(`${this.apiUrl}/${id}`, adoptante);
  }

}
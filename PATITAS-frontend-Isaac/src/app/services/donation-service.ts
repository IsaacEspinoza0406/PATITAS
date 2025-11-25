import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Donation {
  id: number;
  amount: number;
  donorName: string;
  date: string;
}

@Injectable({
  providedIn: 'root'
})
export class DonationService {
  private http = inject(HttpClient);
  private apiUrl = 'http://127.0.0.1:8080/donations';

  // Crear donación
  createDonation(donation: Donation): Observable<Donation> {
    return this.http.post<Donation>(this.apiUrl, donation);
  }

  // Listar donaciones
  getDonations(): Observable<Donation[]> {
    return this.http.get<Donation[]>(this.apiUrl);
  }
}
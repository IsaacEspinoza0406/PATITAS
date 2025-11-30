import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface DonationRequest {
  userId: number;
  amount: number;
  methodName: string;
  transactionId: string;
  payerEmail: string;
}

export interface DonationResponse {
  id: number;
  userId: number | null;
  amount: number;
  methodName: string;
  transactionId: string;
  payerEmail: string;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class DonationService {
  private http = inject(HttpClient);
  private apiUrl = 'http://44.210.168.90:8080/donations';

  // Crear donación
  createDonation(donation: DonationRequest): Observable<DonationResponse> {
    return this.http.post<DonationResponse>(this.apiUrl, donation);
  }

  // Listar donaciones
  getDonations(): Observable<DonationResponse[]> {
    return this.http.get<DonationResponse[]>(this.apiUrl);
  }
}
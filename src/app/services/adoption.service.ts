import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { AdoptionRequest, AdoptionResponse } from '../interfaces/models';

@Injectable({
    providedIn: 'root'
})
export class AdoptionService {
    private apiUrl = `${environment.apiUrl}/adoptions`;

    constructor(private http: HttpClient) { }

    getAdoptions(): Observable<AdoptionResponse[]> {
        return this.http.get<AdoptionResponse[]>(this.apiUrl);
    }

    createAdoption(adoption: AdoptionRequest): Observable<AdoptionResponse> {
        return this.http.post<AdoptionResponse>(this.apiUrl, adoption);
    }

    deleteAdoption(id: number): Observable<any> {
        return this.http.delete(`${this.apiUrl}/${id}`, { responseType: 'text' });
    }

    acceptAdoption(id: number): Observable<any> {
        return this.http.put(`${this.apiUrl}/${id}/accept`, {}, { responseType: 'text' });
    }
}

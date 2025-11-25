import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Adoption {
    id?: number;
    userId: number;
    dogId: number;
    status?: string; // Asumiendo que podría haber un estado
    requestDate?: string;
}

@Injectable({
    providedIn: 'root'
})
export class AdoptionsService {

    private http = inject(HttpClient);
    private apiUrl = 'http://127.0.0.1:8080/adoptions';

    constructor() { }

    getAdoptions(): Observable<Adoption[]> {
        return this.http.get<Adoption[]>(this.apiUrl);
    }

    createAdoption(adoption: Adoption): Observable<Adoption> {
        return this.http.post<Adoption>(this.apiUrl, adoption);
    }

    deleteAdoption(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}

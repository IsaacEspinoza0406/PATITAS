import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { DogRequest, DogResponse } from '../interfaces/models';

@Injectable({
    providedIn: 'root'
})
export class DogService {
    private apiUrl = 'http://44.210.168.90:8080/dogs';

    constructor(private http: HttpClient) { }

    getDogs(): Observable<DogResponse[]> {
        return this.http.get<DogResponse[]>(this.apiUrl);
    }

    getDogById(id: number): Observable<DogResponse> {
        return this.http.get<DogResponse>(`${this.apiUrl}/${id}`);
    }

    addDog(dog: DogRequest): Observable<DogResponse> {
        return this.http.post<DogResponse>(this.apiUrl, dog);
    }

    updateDog(id: number, dog: DogRequest): Observable<DogResponse> {
        return this.http.put<DogResponse>(`${this.apiUrl}/${id}`, dog);
    }

    deleteDog(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }

    uploadPhoto(dogId: number, file: File): Observable<any> {
        const formData = new FormData();
        formData.append('file', file);
        return this.http.post(`${this.apiUrl}/${dogId}/photos/upload-photo`, formData);
    }
}

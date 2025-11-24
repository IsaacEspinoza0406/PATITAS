import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Photo {
  id: number;
  dogId: number;
  photoUrl: string;
  description: string;
}

export interface Dog {
  id: number;
  name: string;
  age: number;
  breed: string;
  history: string;
  sterilized: string;
  adopted: string;
  photos: Photo[];
}

@Injectable({
  providedIn: 'root'
})
export class DogService {

  private http = inject(HttpClient);
  
  private baseUrl = 'http://127.0.0.1:8080'; 

  constructor() {}

  getDogs(): Observable<Dog[]> {
    return this.http.get<Dog[]>(`${this.baseUrl}/dogs`);
  }

  getDogById(id: number): Observable<Dog> {
    return this.http.get<Dog>(`${this.baseUrl}/dogs/${id}`);
  }

  addDog(dogData: any): Observable<Dog> {
    return this.http.post<Dog>(`${this.baseUrl}/dogs`, dogData);
  }

  addPhoto(dogId: number, photoData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/dogs/${dogId}/photos`, photoData);
  }

  deleteDog(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/dogs/${id}`);
  }
}
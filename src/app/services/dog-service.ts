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
  history: string ;
  sterilized: string;
  adopted: string;
  photos: Photo[];
}

@Injectable({
  providedIn: 'root'
})
export class DogService {

  private http = inject(HttpClient);
  private apiUrl = 'http://127.0.0.1:5000/dogs'; //puerto original: 8080

  constructor() {}

  getDogs(): Observable<Dog[]> {
    return this.http.get<Dog[]>(this.apiUrl);
  }

  getDogById(id: number): Observable<Dog> {
    return this.http.get<Dog>(`${this.apiUrl}/${id}`);
  }
}

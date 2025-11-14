import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Dog {
  id: number;
  name: string;
  age: number;
  breed: string;
  history: string ;
  sterilized: boolean | null;
  vaccinated: string;
  imageUrl: string;
}

@Injectable({
  providedIn: 'root'
})
export class DogService {

  private http = inject(HttpClient);

  private apiUrl = 'http://127.0.0.1:8080/dogs'; 

  constructor() {}

  getDogs(): Observable<Dog[]> {
    return this.http.get<Dog[]>(this.apiUrl);
  }

  getDogById(id: number): Observable<Dog> {
    return this.http.get<Dog>(`${this.apiUrl}/${id}`);
  }
}
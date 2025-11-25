import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { DogPhotoResponse } from '../interfaces/models';

@Injectable({
    providedIn: 'root'
})
export class PhotosService {

    private http = inject(HttpClient);
    private apiUrl = `${environment.apiUrl}/photos`;

    constructor() { }

    getPhoto(id: number): Observable<DogPhotoResponse> {
        return this.http.get<DogPhotoResponse>(`${this.apiUrl}/${id}`);
    }

    deletePhoto(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}

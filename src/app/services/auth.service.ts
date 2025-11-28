import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { LoginRequest, AuthResponse, RegisterRequest, UserResponse } from '../interfaces/models';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl = `${environment.apiUrl}/api/auth`;
    private tokenKey = 'auth_token';
    private userKey = 'auth_user';

    constructor(private http: HttpClient) { }

    login(credentials: LoginRequest): Observable<AuthResponse> {
        return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials).pipe(
            tap(response => this.saveSession(response))
        );
    }

    register(data: RegisterRequest): Observable<UserResponse> {
        return this.http.post<UserResponse>(`${this.apiUrl}/register`, data);
    }

    logout(): void {
        sessionStorage.removeItem(this.tokenKey);
        sessionStorage.removeItem(this.userKey);
    }

    isAuthenticated(): boolean {
        return !!sessionStorage.getItem(this.tokenKey);
    }

    getToken(): string | null {
        return sessionStorage.getItem(this.tokenKey);
    }

    getUser(): UserResponse | null {
        const userStr = sessionStorage.getItem(this.userKey);
        return userStr ? JSON.parse(userStr) : null;
    }

    private saveSession(response: AuthResponse): void {
        sessionStorage.setItem(this.tokenKey, response.token);
        sessionStorage.setItem(this.userKey, JSON.stringify(response.user));
    }
}

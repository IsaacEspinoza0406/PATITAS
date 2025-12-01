import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

export interface LoginRequest {
    email: string;
    password?: string;
}

export interface RegisterRequest {
    name: string;
    email: string;
    password?: string;
    roleId?: number;
}

export interface UserResponse {
    id: number;
    name: string;
    email: string;
    roleId: number;
    roleName: string;
}

export interface AuthResponse {
    token: string;
    user: UserResponse;
}

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private http = inject(HttpClient);

    // URL de tu API desplegada
    private apiUrl = 'http://44.210.168.90:8080/api/auth';

    private tokenKey = 'auth_token';
    private userKey = 'auth_user';

    constructor() { }

    // --- LOGIN ---
    login(credentials: LoginRequest): Observable<AuthResponse> {
        return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials).pipe(
            tap(response => this.saveSession(response))
        );
    }

    // --- REGISTRO ---
    register(data: RegisterRequest): Observable<UserResponse> {
        // Según tu Postman, el registro devuelve el usuario creado (con ID), no el token directo.
        // Si quieres login automático tras registro, tendrás que llamar a login() después.
        return this.http.post<UserResponse>(`${this.apiUrl}/register`, data);
    }

    // --- LOGOUT ---
    logout(): void {
        sessionStorage.removeItem(this.tokenKey);
        sessionStorage.removeItem(this.userKey);
    }

    // --- HELPERS ---
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
        if (response.token && response.user) {
            sessionStorage.setItem(this.tokenKey, response.token);
            sessionStorage.setItem(this.userKey, JSON.stringify(response.user));
        }
    }
}
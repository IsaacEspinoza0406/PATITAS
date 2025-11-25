import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DogService } from '../../services/dog.service';
import { AuthService } from '../../services/auth.service';
import { DogResponse } from '../../interfaces/models';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home implements OnInit {

  dogs: DogResponse[] = [];
  private dogService = inject(DogService);
  private authService = inject(AuthService);
  private router = inject(Router);

  ngOnInit() {
    this.loadDogs();
  }

  loadDogs() {
    this.dogService.getDogs().subscribe({
      next: (data) => {
        // Mostrar solo los primeros 6 perros
        this.dogs = data.slice(0, 6);
      },
      error: (err) => {
        console.error('Error al cargar perros:', err);
      }
    });
  }

  get isLoggedIn(): boolean {
    return this.authService.isAuthenticated();
  }

  get userName(): string {
    const user = this.authService.getUser();
    return user ? user.name : '';
  }

  onAdopt() {
    if (this.isLoggedIn) {
      // Si ya inició sesión, ir al cuestionario
      this.router.navigate(['/forms']);
    } else {
      // Si no, ir a iniciar sesión
      this.router.navigate(['/inicia-sesion']);
    }
  }

  logout() {
    this.authService.logout();
    window.location.reload(); // Recargar para actualizar estado
  }

}

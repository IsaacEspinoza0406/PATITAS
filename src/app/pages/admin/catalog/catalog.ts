import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DogService } from '../../../services/dog.service';
import { DogResponse } from '../../../interfaces/models';

@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './catalog.html',
  styleUrls: ['./catalog.css']
})
export class CatalogComponent implements OnInit {
  // Datos de ejemplo para las mascotas
  pets: DogResponse[] = [];
  private dogService = inject(DogService);
  private router = inject(Router);

  constructor() { }

  editPet(pet: DogResponse) {
    this.router.navigate(['/admin/add-dog'], { queryParams: { id: pet.id } });
  }

  // Función para eliminar una mascota
  deletePet(pet: any) {
    if (confirm(`¿Estás seguro de que deseas eliminar a ${pet.name}?`)) {
      this.dogService.deleteDog(pet.id).subscribe({
        next: () => {
          this.pets = this.pets.filter(p => p.id !== pet.id);
          alert('Mascota eliminada correctamente.');
        },
        error: (err: any) => {
          console.error('Error al eliminar mascota:', err);
          alert('No se pudo eliminar la mascota.');
        }
      });
    }
  }

  // Función para cargar las mascotas (será conectada a la API)
  loadPets() {
    this.dogService.getDogs().subscribe({
      next: (data) => {
        this.pets = data;
      },
      error: (err: any) => {
        console.error('Error al cargar mascotas:', err);
        alert('No se pudieron cargar los perros.');
      }
    });
  }

  ngOnInit() {
    this.loadPets();
  }
}


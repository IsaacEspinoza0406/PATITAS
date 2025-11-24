import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { DogService, Dog } from '../../../services/dog-service';

@Component({
  selector: 'app-catalog-management',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './catalog-management.html',
  styleUrls: ['./catalog-management.css']
})
export class CatalogManagementComponent implements OnInit {

  private dogService = inject(DogService); 

  dogs: Dog[] = [];
  isLoading = true;
  adminName: string = "Admin"; 

  ngOnInit(): void {
    this.loadDogs();
  }

  loadDogs() {
    this.isLoading = true;
    this.dogService.getDogs().subscribe({
      next: (data) => {
        this.dogs = data;
        this.isLoading = false;
      },
      error: (err: any) => {
        console.error('Error cargando perros:', err);
        this.isLoading = false;
      }
    });
  }

  onDelete(id: number) {
    if (confirm('¿Estás seguro de eliminar este perro?')) {
      this.dogService.deleteDog(id).subscribe({
        next: () => {
          alert('Perro eliminado.');
          this.loadDogs(); 
        },
        error: (err: any) => {
          console.error('Error al eliminar:', err);
          alert('No se pudo eliminar.');
        }
      });
    }
  }

  onEdit(id: number) {
    alert('Editar perro ID: ' + id);
  }
}
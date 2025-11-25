import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { DogService } from '../../../services/dog.service';
import { DogResponse } from '../../../interfaces/models';

@Component({
  selector: 'app-catalog-management',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './catalog-management.html',
  styleUrls: ['./catalog-management.css']
})
export class CatalogManagementComponent implements OnInit {

  private dogService = inject(DogService);
  private router = inject(Router);

  dogs: DogResponse[] = [];
  isLoading = true;
  adminName: string = "Admin";

  ngOnInit(): void {
    this.loadDogs();
  }

  loadDogs() {
    this.isLoading = true;
    this.dogService.getDogs().subscribe({
      next: (data) => {
        console.log('--- CATALOGO: Perros cargados ---');
        console.log('Cantidad:', data.length);
        console.log('Datos:', data);
        this.dogs = data;
        this.isLoading = false;
      },
      error: (err: any) => {
        console.error('--- CATALOGO: Error cargando perros ---', err);
        this.isLoading = false;
      }
    });
  }

  onDelete(id: number) {
    console.log('Intentando eliminar perro con ID:', id);
    if (confirm('¿Estás seguro de eliminar este perro?')) {
      this.dogService.deleteDog(id).subscribe({
        next: (response) => {
          console.log('Eliminación exitosa:', response);
          // Manually remove from view immediately
          this.dogs = this.dogs.filter(d => d.id !== id);
          alert('Perro eliminado.');
        },
        error: (err: any) => {
          console.error('Error al eliminar (detalles):', err);
          // Assume success if it's a parsing error or just force remove from view
          this.dogs = this.dogs.filter(d => d.id !== id);
          alert('Se intentó eliminar. Si persiste, recarga la página.');
        }
      });
    }
  }

  onEdit(id: number) {
    this.router.navigate(['/admin/edit-dog', id]);
  }
}
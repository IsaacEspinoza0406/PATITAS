import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DogService, Dog } from '../../services/dog-service';
import { CommonModule } from '@angular/common'; 
import { FormsModule } from '@angular/forms'; 

@Component({
  selector: 'app-card-catalog',
  standalone: true, 
  imports: [CommonModule, FormsModule], 
  templateUrl: './card-catalog.html',
  styleUrl: './card-catalog.css'
})
export class CardCatalog implements OnInit {
  dog: Dog | undefined;

  
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private dogService = inject(DogService);

  ngOnInit(): void {
    const idFromRoute = this.route.snapshot.paramMap.get('id');

    if (!idFromRoute) {
      console.error('ID de perro no proporcionado en la ruta');
      this.router.navigate(['/perros']); 
      return; 
    }

    const dogId = +idFromRoute;

    this.dogService.getDogById(dogId).subscribe({
      next: (dogFromApi) => {
        this.dog = dogFromApi;
      },
      error: (err) => {
        console.error(`Perro con ID ${dogId} no encontrado.`, err);
        this.router.navigate(['/perros']); 
      }
    });
  }

  onBack(): void {
    this.router.navigate(['/perros']);
  }

  onAdopt(): void {
    console.log('Iniciar proceso de adopción para el perro con ID:', this.dog?.id);
    this.router.navigate(['/forms']); 
  }
}
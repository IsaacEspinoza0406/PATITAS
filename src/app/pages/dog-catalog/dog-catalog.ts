import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DogService, Dog } from '../../services/dog-service'; 

@Component({
  selector: 'app-dog-catalog',
  standalone: true,
  imports: [CommonModule], 
  templateUrl: './dog-catalog.html',
  styleUrl: './dog-catalog.css'
})
export class DogCatalog implements OnInit {
  

  private router = inject(Router);
  private dogService = inject(DogService);


  public dogs: Dog[] = [];

  ngOnInit(): void {
    this.dogService.getDogs().subscribe({ 
      next: (dogsFromApi) => {
        this.dogs = dogsFromApi;
      },
      error: (err) => {
        console.error('Error al jalar los perros de la API:', err);
      }
    });
  }

  verDetalles(dogId: number) {
    this.router.navigate(['/dog-details', dogId]); 
  }

  irAAdoptar(dogId: number) {
    this.router.navigate(['/forms']); 
  }
}
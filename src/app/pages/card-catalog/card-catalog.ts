import { Component, OnInit } from '@angular/core';
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

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private dogService: DogService,
  ) { }

  ngOnInit(): void {
    const idFromRoute = this.route.snapshot.paramMap.get('id');

    if (!idFromRoute) {
      console.error('ID de perro no proporcionado en la ruta');
      this.router.navigate(['/catalog']);
      return; 
    }

    const dogId = +idFromRoute;
    this.dog = this.dogService.getDogById(dogId);

    if (!this.dog) {
      console.error(`Perro con ID ${dogId} no encontrado.`);
      this.router.navigate(['/catalog']);
    }
  }

  onBack(): void {
    this.router.navigate(['/catalog']);
  }

  onAdopt(): void {
    console.log('Iniciar proceso de adopción para el perro con ID:', this.dog?.id);
  }
}
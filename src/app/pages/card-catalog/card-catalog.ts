import { Component, OnInit } from '@angular/core';
import { ActivatedRoute,Router } from '@angular/router';
import { DogService, Dog } from '../../services/dog-service';


@Component({
  selector: 'app-card-catalog',
  imports: [],
  templateUrl: './card-catalog.html',
  styleUrl: './card-catalog.css'
})
export class CardCatalog implements OnInit {
dog: Dog | undefined;

constructor(
  private route: ActivatedRoute,
  private router: Router,
  private dogService: DogService,
){}

ngOnInit(): void {
  const idFromRoute = this.route.snapshot.paramMap.get('id');
  if (idFromRoute) {
    const dogId=+idFromRoute;
    this.dog=this.dogService.getDogById(dogId);
    if (!this.dog) {
      // Si no se encuentra el perro, redirigir al catálogo
      this.router.navigate(['/catalog']);
    }else{
      console.error('ID de perro no proporcionado en la ruta');
      this.router.navigate(['/catalog']);
    }
  }
}
//Lógica para el botón "Volver"
onBack(): void {
  this.router.navigate(['/catalog']);
}
ondopt(): void {
  console.log('Iniciar proceso de adopción para el perro con ID:', this.dog?.id);
}
}

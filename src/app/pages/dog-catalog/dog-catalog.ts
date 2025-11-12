import { Component } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-dog-catalog',
  imports: [],
  templateUrl: './dog-catalog.html',
  styleUrl: './dog-catalog.css'
})
export class DogCatalog {
//  Inyecta el Router en el constructor
  constructor(private router: Router) { }

  verDetalles(dogId: number) {
    this.router.navigate(['/dog-details', dogId]);
  }
}

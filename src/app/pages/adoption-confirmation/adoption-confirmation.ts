import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-adoption-confirmation',
  standalone: true,
  imports: [],
  templateUrl: './adoption-confirmation.html',
  styleUrls: ['./adoption-confirmation.css']
})
export class AdoptionConfirmationComponent {
  constructor(private router: Router) {}

  goToHome() {
    this.router.navigate(['/inicio']);
  }

  goToDogCatalog() {
    this.router.navigate(['/dog-catalog']);
  }
}

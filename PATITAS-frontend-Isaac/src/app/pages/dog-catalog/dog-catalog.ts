import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DogService } from '../../services/dog.service';
import { DogResponse } from '../../interfaces/models';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dog-catalog',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dog-catalog.html',
  styleUrls: ['./dog-catalog.css']
})
export class DogCatalogComponent implements OnInit {
  private dogService = inject(DogService);
  private router = inject(Router);
  
  dogs: DogResponse[] = []; // Template uses 'dogs' array

  ngOnInit(): void {
    this.dogService.getDogs().subscribe({
      next: (data) => {
        this.dogs = data;
      },
      error: (err) => console.error('Error fetching dogs', err)
    });
  }

  irAAdoptar(id: number) {
    // Navigate to adoption form or details
    console.log('Adoptar', id);
    // For now, maybe navigate to a contact form or details
    this.router.navigate(['/forms']); 
  }

  verDetalles(id: number) {
    this.router.navigate(['/dog-details', id]);
  }
}
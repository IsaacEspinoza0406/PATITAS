import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { DogService } from '../../../services/dog-service';

@Component({
  selector: 'app-add-dog',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-dog.html',
  styleUrls: ['./add-dog.css']
})
export class AddDogComponent {
  
  private dogService = inject(DogService);
  private router = inject(Router);

  newDog = {
    name: '',
    age: null,
    breed: '',
    history: '',
    sterilized: 'no', 
    health: '',
    vaccinated: {
      rabies: false,
      distemper: false,
      parvo: false
    },
    image: null as File | null 
  };

  imagePreview: string | ArrayBuffer | null = null;

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.newDog.image = file;
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(file);
    }
  }

  onSubmit() {
    console.log('Paso 1: Enviando datos del perro...');
    
    const dogJson = {
      name: this.newDog.name,
      age: Number(this.newDog.age),
      breed: this.newDog.breed,
      history: this.newDog.history,
      sterilized: this.newDog.sterilized === 'si' ? 'sí' : 'no', 
      adopted: 'no'
    };

    this.dogService.addDog(dogJson).subscribe({
      next: (dogCreado: any) => {
        console.log('¡Perro creado! ID:', dogCreado.id);
        
        if (this.imagePreview) {
          this.agregarFotoAlPerro(dogCreado.id);
        } else {
          alert('¡Perro agregado con éxito!');
          this.router.navigate(['/admin/catalog']);
        }
      },
      error: (err: any) => {
        console.error('Error al crear perro:', err);
        const msg = err.error?.message || err.statusText;
        alert('Error al guardar: ' + msg);
      }
    });
  }

  agregarFotoAlPerro(dogId: number) {
    const photoJson = {
      photoUrl: this.imagePreview?.toString() || 'https://placehold.co/600x400?text=Perro',
      description: 'Foto de ' + this.newDog.name
    };

    this.dogService.addPhoto(dogId, photoJson).subscribe({
      next: () => {
        alert('¡Perro y foto guardados!');
        this.router.navigate(['/admin/catalog']);
      },
      error: (err: any) => {
        console.error('Error foto:', err);
        alert('Perro guardado, pero falló la foto.');
        this.router.navigate(['/admin/catalog']);
      }
    });
  }

  onCancel() {
    this.router.navigate(['/admin/catalog']);
  }
}
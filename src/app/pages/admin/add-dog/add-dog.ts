import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DogService } from '../../../services/dog.service';
import { Router, ActivatedRoute } from '@angular/router';
import { DogRequest } from '../../../interfaces/models';

@Component({
  selector: 'app-add-dog',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-dog.html',
  styleUrls: ['./add-dog.css']
})
export class AddDogComponent implements OnInit {
  private dogService = inject(DogService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  isEditMode = false;
  dogId: number | null = null;

  newDog: any = {
    name: '',
    age: null,
    breed: '',
    history: '',
    sterilized: 'No',
    adopted: 'No',
    health: '',
    vaccinated: {
      rabies: false,
      distemper: false,
      parvo: false
    }
  };

  imagePreview: string | ArrayBuffer | null = null;
  selectedFile: File | null = null;

  ngOnInit() {
    console.log('AddDogComponent: Initialized');
    this.route.params.subscribe(params => {
      console.log('AddDogComponent: Route params:', params);
      if (params['id']) {
        this.isEditMode = true;
        this.dogId = +params['id'];
        console.log('AddDogComponent: Edit mode detected, ID:', this.dogId);
        this.loadDogData(this.dogId);
      } else {
        console.log('AddDogComponent: No ID in params, Create mode');
      }
    });
  }

  loadDogData(id: number) {
    console.log('AddDogComponent: Loading dog data for ID:', id);
    this.dogService.getDogById(id).subscribe({
      next: (dog) => {
        console.log('AddDogComponent: Dog data received:', dog);
        this.newDog = {
          name: dog.name,
          age: dog.age,
          breed: dog.breed,
          history: dog.history,
          sterilized: dog.sterilized || 'No',
          adopted: dog.adopted || 'No',
          health: '',
          vaccinated: {
            rabies: dog.vaccines?.includes('Rabia'),
            distemper: dog.vaccines?.includes('Moquillo'),
            parvo: dog.vaccines?.includes('Parvovirus')
          }
        };
        if (dog.photos && dog.photos.length > 0) {
          this.imagePreview = dog.photos[0].photoUrl;
        }
      },
      error: (err) => console.error('AddDogComponent: Error loading dog', err)
    });
  }

  onFileSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(file);
    }
  }

  onSubmit() {
    const dogRequest: DogRequest = {
      name: this.newDog.name,
      age: this.newDog.age || 0,
      breed: this.newDog.breed,
      history: this.newDog.history,
      sterilized: this.newDog.sterilized,
      adopted: this.newDog.adopted,
      vaccines: this.formatVaccines(this.newDog.vaccinated)
    };

    if (this.isEditMode && this.dogId) {
      this.dogService.updateDog(this.dogId, dogRequest).subscribe({
        next: (res) => {
          if (this.selectedFile) {
            this.uploadPhotoAndNavigate(this.dogId!, this.selectedFile);
          } else {
            alert('Perro actualizado exitosamente!');
            this.router.navigate(['/admin/catalog']);
          }
        },
        error: (err) => {
          console.error('Error updating dog', err);
          alert('Error al actualizar.');
        }
      });
    } else {
      this.dogService.addDog(dogRequest).subscribe({
        next: (res) => {
          if (this.selectedFile) {
            this.uploadPhotoAndNavigate(res.id, this.selectedFile);
          } else {
            alert('Perro agregado exitosamente!');
            this.router.navigate(['/admin/catalog']);
          }
        },
        error: (err) => {
          console.error('Error adding dog', err);
          alert('Error al guardar.');
        }
      });
    }
  }

  private uploadPhotoAndNavigate(dogId: number, file: File) {
    this.dogService.uploadPhoto(dogId, file).subscribe({
      next: () => {
        alert('Perro y foto guardados exitosamente!');
        this.router.navigate(['/admin/catalog']);
      },
      error: (err) => {
        console.error('Error uploading photo', err);
        alert('Perro guardado, pero falló la subida de la foto.');
        this.router.navigate(['/admin/catalog']);
      }
    });
  }

  private formatVaccines(vaccines: any): string {
    const vaxList = [];
    if (vaccines.rabies) vaxList.push('Rabia');
    if (vaccines.distemper) vaxList.push('Moquillo');
    if (vaccines.parvo) vaxList.push('Parvovirus');
    return vaxList.join(', ');
  }
}
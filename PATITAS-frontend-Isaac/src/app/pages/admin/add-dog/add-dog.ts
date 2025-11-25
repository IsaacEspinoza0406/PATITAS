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

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.dogId = +params['id'];
        this.loadDogData(this.dogId);
      }
    });
  }

  loadDogData(id: number) {
    this.dogService.getDogById(id).subscribe({
      next: (dog) => {
        this.newDog = {
          name: dog.name,
          age: dog.age,
          breed: dog.breed,
          history: dog.history,
          sterilized: dog.sterilized || 'No',
          adopted: dog.adopted || 'No',
          health: '', // Not in response?
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
      error: (err) => console.error('Error loading dog', err)
    });
  }

  imagePreview: string | ArrayBuffer | null = null;
  selectedFile: File | null = null;

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
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-add-publications-img',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-publications-img.html',
  styleUrl: './add-publications-img.css'
})

export class AddPublicationsImg {
  imagenUrl: string | ArrayBuffer | null = null;
  nombre: string = '';
  edad: string = '';
  raza: string = '';
  historia: string = '';
  esterilizacion: string = '';
  salud: string = '';
  vacunas: { [key: string]: boolean } = {
    vacuna1: false,
    vacuna2: false,
    vacuna3: false,
    vacuna4: false
  }

  onImgSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const file = input.files[0];
      const reader = new FileReader();
      reader.onload = e =>{
        this.imagenUrl = e.target?.result || null;
      };
      reader.readAsDataURL(file);
    }
      }
  
    triggerFileInput(): void {
    const fileInput = document.getElementById('fileInput') as HTMLInputElement;
    fileInput?.click();
  }
  
  publicar(): void {
    console.log('FORMULARIO ENVIADO:',{
      imagen: this.imagenUrl,
      nombre: this.nombre,
      edad: this.edad,
      raza: this.raza,
      historia: this.historia,
      esterilizacion: this.esterilizacion,
      salud: this.salud,
      vacunas: this.vacunas
    });
    }
  }

    
  


  

  




import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './catalog.html',
  styleUrls: ['./catalog.css']
})
export class CatalogComponent implements OnInit {
  // Datos de ejemplo para las mascotas
  pets = [
    {
      id: 1,
      nombre: 'Maylo y Coronel',
      edad: ''
    },
    {
      id: 2,
      nombre: 'Bella',
      edad: '2 años'
    },
    {
      id: 3,
      nombre: 'Max',
      edad: '4 años'
    },
    {
      id: 4,
      nombre: 'Luna',
      edad: '1 año'
    },
    {
      id: 5,
      nombre: 'Thor',
      edad: '3 años'
    },
    {
      id: 6,
      nombre: 'Chloe',
      edad: '5 años'
    }
  ];

  constructor() {}

  // Función para eliminar una mascota
  deletePet(pet: any) {
    if (confirm(`¿Estás seguro de que deseas eliminar a ${pet.nombre}?`)) {
      const index = this.pets.findIndex(p => p.id === pet.id);
      if (index > -1) {
        this.pets.splice(index, 1);
        console.log('Mascota eliminada:', pet);
        // Aquí iría la llamada a la API para eliminar
        // this.http.delete(`/api/pets/${pet.id}`).subscribe(...)
      }
    }
  }

  // Función para cargar las mascotas (será conectada a la API)
  loadPets() {
    // TODO: Conectar con la API cuando esté disponible
    // this.http.get('/api/pets').subscribe((pets: any) => {
    //   this.pets = pets;
    // });
  }

  ngOnInit() {
    this.loadPets();
  }
}


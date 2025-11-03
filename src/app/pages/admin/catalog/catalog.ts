import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './catalog.html',
  styleUrls: ['./catalog.css']
})
export class CatalogComponent implements OnInit {
  // Datos de ejemplo para las mascotas
  pets = [
    {
      id: 1,
      nombre: 'Maylo y Coronel',
      edad: '',
      imagen: 'assets/img/perros-ejemplo.jpg'
    },
    {
      id: 2,
      nombre: 'Bella',
      edad: '2 años',
      imagen: 'assets/img/perros-ejemplo.jpg'
    },
    {
      id: 3,
      nombre: 'Max',
      edad: '4 años',
      imagen: 'assets/img/perros-ejemplo.jpg'
    },
    {
      id: 4,
      nombre: 'Luna',
      edad: '1 año',
      imagen: 'assets/img/perros-ejemplo.jpg'
    },
    {
      id: 5,
      nombre: 'Thor',
      edad: '3 años',
      imagen: 'assets/img/perros-ejemplo.jpg'
    },
    {
      id: 6,
      nombre: 'Chloe',
      edad: '5 años',
      imagen: 'assets/img/perros-ejemplo.jpg'
    }
  ];

  // Estado de edición
  editingPet: any = null;
  
  // Formulario de edición
  editForm = {
    nombre: '',
    edad: ''
  };

  constructor() {}

  // Función para editar una mascota
  editPet(pet: any) {
    this.editingPet = pet.id;
    this.editForm.nombre = pet.nombre;
    this.editForm.edad = pet.edad;
  }

  // Función para guardar los cambios
  saveChanges(pet: any) {
    if (this.editForm.nombre.trim()) {
      pet.nombre = this.editForm.nombre;
      pet.edad = this.editForm.edad;
      console.log('Mascota actualizada:', pet);
      // Aquí iría la llamada a la API para actualizar
      // this.http.put(`/api/pets/${pet.id}`, pet).subscribe(...)
    }
    this.cancelEdit();
  }

  // Función para cancelar la edición
  cancelEdit() {
    this.editingPet = null;
    this.editForm.nombre = '';
    this.editForm.edad = '';
  }

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


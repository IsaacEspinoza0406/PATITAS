import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdoptanteService } from '../../services/adoptante';
import { AdoptionService } from '../../services/adoption.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-forms',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './forms.html',
  styleUrls: ['./forms.css']
})
export class FormsComponent implements OnInit {
  step = 1;
  dogId: number | null = null;

  private adoptanteService = inject(AdoptanteService);
  private adoptionService = inject(AdoptionService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  public formData: any = {
    // Campos del contenedor 1.
    nombre: '',
    telefono: '',
    edad: null,
    ocupacion: '',
    ingreso: null,
    horas: null,

    // Campos del contenedor 2.
    espacio: '',
    habitantes: '',
    edades: '',
    vivienda: '',
    personas: null,

    // Campos del contenedor 3.
    mascotas: '',
    cuidado: '',
    sigueCuidando: '',
    responsabilidades: '',
    esterilizacion: ''
  };

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      if (params['dogId']) {
        this.dogId = +params['dogId'];
      }
    });
  }

  nextStep() {
    if (this.step < 3) this.step++;
  }

  prevStep() {
    if (this.step > 1) this.step--;
  }

  onSubmit() {
    console.log('Datos del formulario (Frontend):', this.formData);

    const apiPayload = {
      nombreCompleto: this.formData.nombre,
      telefono: this.formData.telefono,
      edad: String(this.formData.edad),
      ocupacion: this.formData.ocupacion,
      ingresoMensual: String(this.formData.ingreso),
      horasDeTrabajo: String(this.formData.horas),
      tienePatio: this.formData.espacio,
      ninosEnCasa: this.formData.habitantes + " (Edades: " + this.formData.edades + ")",
      tipoVivienda: this.formData.vivienda,
      convivientes: String(this.formData.personas),
      mascotasAnteriores: this.formData.mascotas,
      aunConservaMascotas: this.formData.cuidado + " (Sigue cuidando: " + this.formData.sigueCuidando + ")",
      responsabilidadesMascota: this.formData.responsabilidades,
      opinionEsterilizacion: this.formData.esterilizacion
    };

    console.log('Datos a enviar a la API (Backend):', apiPayload);

    this.adoptanteService.createAdoptante(apiPayload).subscribe({
      next: (adoptanteRes) => {
        console.log('Adoptante creado:', adoptanteRes);

        if (this.dogId) {
          // Create the link (Adoption Request)
          this.adoptionService.createAdoption({
            adoptanteId: adoptanteRes.id, // Using adoptante ID
            dogId: this.dogId
          }).subscribe({
            next: (adoptionRes) => {
              console.log('Solicitud de adopción creada:', adoptionRes);
              alert('¡Tu solicitud de adopción ha sido enviada con éxito!');
              this.resetForm();
            },
            error: (err) => {
              console.error('Error creando solicitud de adopción:', err);
              alert('Se guardaron tus datos, pero hubo un error al vincular con el perro.');
            }
          });
        } else {
          alert('¡Datos guardados! (No se vinculó a ningún perro específico)');
          this.resetForm();
        }
      },
      error: (error) => {
        console.error('Error al enviar la solicitud:', error);
        alert('Hubo un error al enviar tu solicitud. Por favor, inténtalo de nuevo.');
      }
    });
  }

  resetForm() {
    this.formData = {
      nombre: '', telefono: '', edad: null, ocupacion: '',
      ingreso: null, horas: null, espacio: '', habitantes: '',
      edades: '', vivienda: '', personas: null, mascotas: '',
      cuidado: '', sigueCuidando: '', responsabilidades: '', esterilizacion: ''
    };
    this.step = 1;
    this.router.navigate(['/perros']); // Go back to catalog
  }
}
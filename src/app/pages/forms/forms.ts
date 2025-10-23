import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; //importaciones para ngSwitch, ngIf, class.active
import { FormsModule } from '@angular/forms'; 

@Component({
  selector: 'app-forms',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './forms.html',
  styleUrls: ['./forms.css']
})

export class FormsComponent {
  step = 1; 

  nextStep() {
    if (this.step < 3) this.step++;
  }

  prevStep() {
    if (this.step > 1) this.step--;
  }

  onSubmit() {
    alert('Formulario enviado ');
  }
}
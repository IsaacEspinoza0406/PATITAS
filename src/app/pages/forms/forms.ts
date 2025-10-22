import { Component } from '@angular/core';

@Component({
  selector: 'app-forms',
  standalone: true,
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
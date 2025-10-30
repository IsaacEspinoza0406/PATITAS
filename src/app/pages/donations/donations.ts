import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-donations', 
  standalone: true,
  imports: [CommonModule, FormsModule], 
  templateUrl: './donations.html',
  styleUrls: ['./donations.css']
})
export class DonationsComponent {
  step: string = 'landing';
  donationAmount: number | null = 5; 

  // Guarda los datos de la tarjeta, este es para la "vista 2".
  paymentData = {
    cardNumber: '',
    cardName: '',
    cvv: '',
    expiry: '',
    email: ''
  };

  // HELPERS.
  // Estado de carga para simular el pago
  isProcessing: boolean = false;
  
  // Aunque solo sea una simulacion, inyectamos esto para decir que vamos a consumir la api de paypal.
  private http = inject(HttpClient);

  constructor() {}
  setPresetAmount(amount: number) {
    this.donationAmount = amount;
  }

  goToPayment() {
    if (!this.donationAmount || this.donationAmount <= 0) {
      alert("Por favor, ingresa un monto válido para donar.");
      return;
    }
    this.step = 'payment';
  }

  // Simula el proceso de pago, con paypal.
  simulatePayment() {
    this.isProcessing = true; 

    // Simula una llamada a la API.
    setTimeout(() => {
      console.log('--- PROCESANDO DONACIÓN ---');
      console.log('Monto:', this.donationAmount);
      console.log('Datos de pago (simulados):', this.paymentData);
      
      // Simulación exitosa.
      this.isProcessing = false;
      this.step = 'thanks';
    }, 2000);
  }

  goToStart() {
    this.step = 'landing';
    
    // Resetea todos los formularios.
    this.donationAmount = 5;
    this.paymentData = {
      cardNumber: '',
      cardName: '',
      cvv: '',
      expiry: '',
      email: ''
    };
  }
}

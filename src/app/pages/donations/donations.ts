import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-donations',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './donations.html',
  styleUrls: ['./donations.css']
})
export class DonationsComponent {
  step: 'landing' | 'payment' | 'thanks' = 'landing';
  donationAmount: number | null = null;
  isProcessing = false;

  paymentData = {
    cardNumber: '',
    cardName: '',
    expiry: '',
    cvv: '',
    email: ''
  };

  setPresetAmount(amount: number) {
    this.donationAmount = amount;
  }

  goToPayment() {
    if (this.donationAmount && this.donationAmount > 0) {
      this.step = 'payment';
    } else {
      alert('Por favor selecciona un monto válido.');
    }
  }

  simulatePayment() {
    this.isProcessing = true;
    console.log(`Procesando donación de $${this.donationAmount}`, this.paymentData);

    setTimeout(() => {
      this.isProcessing = false;
      this.step = 'thanks';
    }, 2000);
  }

  goToStart() {
    this.step = 'landing';
    this.donationAmount = null;
    this.paymentData = {
      cardNumber: '',
      cardName: '',
      expiry: '',
      cvv: '',
      email: ''
    };
  }
}

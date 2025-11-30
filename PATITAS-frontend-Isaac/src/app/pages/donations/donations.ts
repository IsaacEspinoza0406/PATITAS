import { Component, inject } from '@angular/core';
import { DonationService, DonationRequest, DonationResponse } from '../../services/donation-service';
import { AuthService } from '../../services/auth.service';
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

  private donationService = inject(DonationService);
  private authService = inject(AuthService);

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

  processPayment() {
    if (!this.donationAmount) return;

    const user = this.authService.getUser();
    // User login is optional now

    this.isProcessing = true;

    const donationRequest: DonationRequest = {
      userId: user ? user.id : 1, // Use dummy user ID 1 for anonymous donations
      amount: this.donationAmount,
      methodName: 'Credit Card', // Hardcoded for now as per UI
      transactionId: `TXN-${Date.now()}`,
      payerEmail: this.paymentData.email || (user ? user.email : 'anonymous@patitas.com') // Fallback to user email or anonymous
    };

    this.donationService.createDonation(donationRequest).subscribe({
      next: (response: DonationResponse) => {
        console.log('Donación exitosa:', response);
        this.isProcessing = false;
        this.step = 'thanks';
      },
      error: (err: any) => {
        console.error('Error al donar:', err);
        this.isProcessing = false;
        alert('Hubo un error al procesar tu donación. Inténtalo de nuevo.');
      }
    });
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

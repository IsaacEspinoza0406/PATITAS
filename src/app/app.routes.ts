import { Routes } from '@angular/router';
//Hasta 
import { FormsComponent } from './pages/forms/forms';
import { DonationsComponent } from './pages/donations/donations'; 

export const routes: Routes = [
  { path: 'forms', component: FormsComponent },
  { path: 'donations', component: DonationsComponent },
  { path: '', redirectTo: '/donations', pathMatch: 'full' },
];


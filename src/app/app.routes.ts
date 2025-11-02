import { Routes } from '@angular/router';
//Hasta 
import { FormsComponent } from './pages/forms/forms';
import { DonationsComponent } from './pages/donations/donations'; 
import{ Home }  from './pages/home/home';

export const routes: Routes = [
  { path: 'forms', component: FormsComponent },
  { path: 'donations', component: DonationsComponent },
  {path: 'home', component: Home},
  { path: '', redirectTo: '/donations', pathMatch: 'full' },
];


import { Routes } from '@angular/router';
//Hasta
import { FormsComponent } from './pages/forms/forms';
import { DonationsComponent } from './pages/donations/donations';
import{ Home }  from './pages/home/home';
import {AdoptionRequestComponent} from './pages/admin/AdoptionRequest/AdoptionRequest';

export const routes: Routes = [
  { path: 'forms', component: FormsComponent },
  { path: 'donations', component: DonationsComponent },
  {path: 'home', component: Home},
  { path: '', redirectTo: '/donations', pathMatch: 'full' },
  { path: 'AdoptionRequest', component: AdoptionRequestComponent },
];


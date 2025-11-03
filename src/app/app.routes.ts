import { Routes } from '@angular/router';
//Hasta
import { FormsComponent } from './pages/forms/forms';
import { DonationsComponent } from './pages/donations/donations';
import{ Home }  from './pages/home/home';
<<<<<<< HEAD
import {AdoptionRequestComponent} from './pages/admin/AdoptionRequest/AdoptionRequest';
=======
import { CatalogComponent } from './pages/admin/catalog/catalog';
>>>>>>> fc97406bf47d060a117fc2ad232aa1ef5da7079d

export const routes: Routes = [
  { path: 'forms', component: FormsComponent },
  { path: 'donations', component: DonationsComponent },
  {path: 'home', component: Home},
  { path: 'admin/catalog', component: CatalogComponent },
  { path: '', redirectTo: '/donations', pathMatch: 'full' },
  { path: 'AdoptionRequest', component: AdoptionRequestComponent },
];


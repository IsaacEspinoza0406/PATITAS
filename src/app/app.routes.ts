import { Routes } from '@angular/router';
//Hasta 
import { FormsComponent } from './pages/forms/forms';
import { DonationsComponent } from './pages/donations/donations'; 
import{ Home }  from './pages/home/home';
import { CatalogComponent } from './pages/admin/catalog/catalog';

export const routes: Routes = [
  { path: 'forms', component: FormsComponent },
  { path: 'donations', component: DonationsComponent },
  {path: 'home', component: Home},
  { path: 'admin/catalog', component: CatalogComponent },
  { path: '', redirectTo: '/donations', pathMatch: 'full' },
];


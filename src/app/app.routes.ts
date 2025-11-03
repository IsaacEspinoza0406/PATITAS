import { Routes } from '@angular/router';
import { FormsComponent } from './pages/forms/forms';
import { DonationsComponent } from './pages/donations/donations'; 
import { Home } from './pages/home/home'; 
// import { CatalogComponent } from './pages/catalog/catalog'; 
// import { AdoptionRequestComponent } from './pages/admin/AdoptionRequest/AdoptionRequest'; 

export const routes: Routes = [
  { path: 'inicio', component: Home }, 
  
  // { path: 'perros', component: CatalogComponent }, 
  
  { path: 'donations', component: DonationsComponent }, 

  { path: 'forms', component: FormsComponent },

  { path: 'inicia-sesion', component: Home}, 
  
  // { path: 'admin', component: AdoptionRequestComponent },
  
  { path: '', redirectTo: '/inicio', pathMatch: 'full' },
  
  { path: '**', redirectTo: '/inicio' } 
];


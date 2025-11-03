import { Routes } from '@angular/router';
//Hasta
import { FormsComponent } from './pages/forms/forms';
import { DonationsComponent } from './pages/donations/donations';
import{ Home }  from './pages/home/home';
 
import {AdoptionRequestComponent} from './pages/admin/AdoptionRequest/AdoptionRequest';
import { CatalogComponent } from './pages/admin/catalog/catalog';
import { FormsComponent } from './pages/forms/forms';
import { DonationsComponent } from './pages/donations/donations'; 
import { Home } from './pages/home/home'; 
import { LoginComponent } from './pages/admin/login/login'; 
// Si vas a mover el navbar, asegurate de importar el componente correcto.

export const routes: Routes = [
  { path: 'inicio', component: Home }, 
    
  { path: 'donations', component: DonationsComponent }, 

  { path: 'forms', component: FormsComponent },
  { path: 'donations', component: DonationsComponent },
  {path: 'home', component: Home},
  { path: 'admin/catalog', component: CatalogComponent },
  { path: '', redirectTo: '/donations', pathMatch: 'full' },
  { path: 'AdoptionRequest', component: AdoptionRequestComponent },
  { path: 'inicia-sesion', component: LoginComponent }, 
    
  { path: '', redirectTo: '/inicio', pathMatch: 'full' },
  
  // Esto es solo para redirigir rutas no encontradas a inicio.
  { path: '**', redirectTo: '/inicio' } 
];


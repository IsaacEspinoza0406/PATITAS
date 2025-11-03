import { Routes } from '@angular/router';
import { FormsComponent } from './pages/forms/forms';
import { DonationsComponent } from './pages/donations/donations'; 
import { Home } from './pages/home/home'; 
import { LoginComponent } from './pages/admin/login/login'; 
// Si vas a mover el navbar, asegurate de importar el componente correcto.

export const routes: Routes = [
  { path: 'inicio', component: Home }, 
    
  { path: 'donations', component: DonationsComponent }, 

  { path: 'forms', component: FormsComponent },

  { path: 'inicia-sesion', component: LoginComponent }, 
    
  { path: '', redirectTo: '/inicio', pathMatch: 'full' },
  
  // Esto es solo para redirigir rutas no encontradas a inicio.
  { path: '**', redirectTo: '/inicio' } 
];


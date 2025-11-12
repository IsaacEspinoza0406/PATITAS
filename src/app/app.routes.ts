import { Routes } from '@angular/router';

import { FormsComponent } from './pages/forms/forms';
import { DonationsComponent } from './pages/donations/donations';
import { Home } from './pages/home/home';
import { DogCatalog } from './pages/dog-catalog/dog-catalog';
import { CardCatalog } from './pages/card-catalog/card-catalog';

import { AdoptionRequestComponent } from './pages/admin/AdoptionRequest/AdoptionRequest';
import { CatalogComponent } from './pages/admin/catalog/catalog';
import { LoginComponent } from './pages/admin/login/login';

// Rutas de la aplicación
export const routes: Routes = [
  // Rutas públicas
  { path: 'inicio', component: Home },
  { path: 'home', component: Home },
  { path: 'donations', component: DonationsComponent },
  { path: 'forms', component: FormsComponent },
  {path: 'catalog', component: DogCatalog },
  {path: 'card-catalog', component: CardCatalog },

 
  // Rutas de administración agrupadas bajo /admin
  {
    path: 'admin',
    children: [
      { path: 'catalog', component: CatalogComponent },
      { path: 'adoption-request', component: AdoptionRequestComponent },
      { path: 'login', component: LoginComponent },
      
      // Por defecto dentro de /admin redirige al catálogo
      { path: '', redirectTo: 'catalog', pathMatch: 'full' }
    ]
  },

  // Redirección por defecto y fallback
  { path: '', redirectTo: '/inicio', pathMatch: 'full' },
  { path: '**', redirectTo: '/inicio' }
  
];


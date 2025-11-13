import { Routes } from '@angular/router';

// --- Componentes Públicos ---
import { FormsComponent } from './pages/forms/forms';
import { DonationsComponent } from './pages/donations/donations';
import { Home } from './pages/home/home';
import { DogCatalog } from './pages/dog-catalog/dog-catalog';
import { CardCatalog } from './pages/card-catalog/card-catalog';

// --- Componentes de Admin ---
import { AdoptionRequestComponent } from './pages/admin/AdoptionRequest/AdoptionRequest';
import { CatalogComponent } from './pages/admin/catalog/catalog';
import { LoginComponent } from './pages/admin/login/login';

// Rutas de la aplicación
export const routes: Routes = [
  { path: 'inicio', component: Home },
  { path: 'donations', component: DonationsComponent },
  { path: 'forms', component: FormsComponent },
  {path: 'dog-catalog', component: DogCatalog },
  {path: 'card-catalog', component: CardCatalog },

  { path: 'inicia-sesion', component: LoginComponent },

  
  // --- Rutas de Admin (agrupadas) ---
  {
    path: 'admin',
    children: [
      { path: 'catalog', component: CatalogComponent },
      { path: 'adoption-request', component: AdoptionRequestComponent },
      { path: 'inicia-sesion', component: LoginComponent },
      
      // Por defecto dentro de /admin redirige al catálogo
      { path: '', redirectTo: 'catalog', pathMatch: 'full' }
    ]
  },

  { path: '', redirectTo: '/inicio', pathMatch: 'full' }, 
  { path: '**', redirectTo: '/inicio' }, 
];
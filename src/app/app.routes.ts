import { Routes } from '@angular/router';
import { FormsComponent } from './pages/forms/forms';
import { DonationsComponent } from './pages/donations/donations';
import { Home } from './pages/home/home';
import { DogCatalog } from './pages/dog-catalog/dog-catalog';
import { CardCatalog } from './pages/card-catalog/card-catalog';
import { LoginUser } from './pages/login-user/login-user';
import { AdoptionConfirmationComponent } from './pages/adoption-confirmation/adoption-confirmation';

// --- Componentes de Admin. ---
import { AdoptionRequestComponent } from './pages/admin/AdoptionRequest/AdoptionRequest';
import { CatalogComponent } from './pages/admin/catalog/catalog';
import { LoginComponent } from './pages/admin/login/login';
import { AdoptionRequestForm } from './pages/admin/adoption-request-form/adoption-request-form';  
import { AddPublications } from './pages/admin/add-publications/add-publications';
import { AddPublicationsImg } from './pages/admin/add-publications-img/add-publications-img';

// Rutas de la aplicación.
export const routes: Routes = [
  // --- Rutas Públicas.---
  { path: 'inicio', component: Home },
  { path: 'donations', component: DonationsComponent },
  { path: 'forms', component: FormsComponent },
  { path: 'adoption-confirmation', component: AdoptionConfirmationComponent },
  {path: 'dog-catalog', component: DogCatalog },
  {path: 'card-catalog/:id', component: CardCatalog },

  { path: 'inicia-sesion', component: LoginComponent },

  
  // --- Rutas de Admin (agrupadas) ---
  {
    path: 'admin',
    children: [
      { path: 'catalog', component: CatalogComponent },
      { path: 'adoption-request', component: AdoptionRequestComponent },
      { path: 'inicia-sesion', component: LoginComponent },
      {path: 'add-publications', component: AddPublications },
      {path: 'add-publications-img', component: AddPublicationsImg },
      // Por defecto dentro de /admin redirige al catálogo
      { path: '', redirectTo: 'catalog', pathMatch: 'full' }
    ]
  },

  { path: '', redirectTo: '/inicio', pathMatch: 'full' }, 
  { path: '**', redirectTo: '/inicio' },
];
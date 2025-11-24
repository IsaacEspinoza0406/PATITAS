import { Routes } from '@angular/router';

//Componentes Públicos.
import { FormsComponent } from './pages/forms/forms';
import { DonationsComponent } from './pages/donations/donations';
import { Home } from './pages/home/home';
import { DogCatalog } from './pages/dog-catalog/dog-catalog';
import { CardCatalog } from './pages/card-catalog/card-catalog';

//Componentes de Auth.
import { AuthSelectionComponent } from './pages/auth-selection/auth-selection';
import { UserAuthComponent } from './pages/user-auth/user-auth';

// Componentes de Admin.
import { AdoptionRequestComponent } from './pages/admin/AdoptionRequest/AdoptionRequest';
import { LoginComponent } from './pages/admin/login/login'; 
import { CatalogManagementComponent } from './pages/admin/catalog-management/catalog-management'; 
import { AddDogComponent } from './pages/admin/add-dog/add-dog';

export const routes: Routes = [
  //  Rutas Públicas.
  { path: 'inicio', component: Home },
  { path: 'donations', component: DonationsComponent },
  { path: 'forms', component: FormsComponent },
  { path: 'perros', component: DogCatalog }, 
  { path: 'dog-details/:id', component: CardCatalog},

  { path: 'inicia-sesion', component: AuthSelectionComponent },

  { path: 'auth/user', component: UserAuthComponent },
  
  {
    path: 'admin',
    children: [
      { path: 'login', component: LoginComponent },

      { path: 'catalog', component: CatalogManagementComponent }, 
      { path: 'add-dog', component: AddDogComponent },      
      { path: 'adoption-request', component: AdoptionRequestComponent },
      
      { path: '', redirectTo: 'login', pathMatch: 'full' }
    ]
  },

  { path: '', redirectTo: '/inicio', pathMatch: 'full' }, 
  { path: '**', redirectTo: '/inicio' }, 
];
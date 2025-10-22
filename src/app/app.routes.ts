import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'forms',
    loadComponent: () =>
      import('./pages/forms/forms').then(m => m.FormsComponent)
  }
];
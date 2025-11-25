import { ApplicationConfig, importProvidersFrom } from '@angular/core'; 
import { provideRouter } from '@angular/router';
import { FormsModule } from '@angular/forms';

// ESTO ES MUY IMPORTANTE PORQUE SI NO TE PUEDE DAR ERROR COMO A MÍ JAJAJA, NO LO TOQUES.!
import { provideHttpClient } from '@angular/common/http'; 

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    importProvidersFrom(FormsModule)
 ]
};


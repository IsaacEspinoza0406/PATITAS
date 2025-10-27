import { ApplicationConfig } from '@angular/core'; 
import { provideRouter } from '@angular/router';

// ESTO ES MUY IMPORTANTE PORQUE SI NO TE PUEDE DAR ERROR COMO A MÍ JAJAJA, NO LO TOQUES.!
import { provideHttpClient } from '@angular/common/http'; 

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient()
 ]
};


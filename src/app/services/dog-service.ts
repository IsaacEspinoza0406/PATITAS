import { Injectable } from '@angular/core';

export interface Dog {
  id: number;
  name: string;
  age: number;
  breed: string;
  history: string ;
  sterilized: boolean| null;
  vaccinated: string;
  imageUrl: string;
}

@Injectable({
  providedIn: 'root'
})
export class DogService {
constructor() {}

getDogById(id: number): Dog | undefined {
  switch (id) {
    case 1:
      return {
        id: 1,
        name: 'Fido',
        age: 3,
        breed: 'Labrador',
        history: 'Rescued from the streets, very friendly.',
        sterilized: true,
        vaccinated: 'Rabies, Distemper',
        imageUrl: 'assets/img/perro1.jpg'
};
default:
      return undefined;
}
}
}

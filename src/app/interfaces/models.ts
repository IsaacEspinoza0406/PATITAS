export interface DogRequest {
    name: string;
    age?: number;
    breed?: string;
    history?: string;
    sterilized?: string;
    adopted?: string;
    vaccines?: string;
}

export interface DogResponse {
    id: number;
    name: string;
    age?: number;
    breed?: string;
    history?: string;
    sterilized?: string;
    adopted?: string;
    vaccines?: string;
    photos: DogPhotoResponse[];
}

export interface DogPhotoRequest {
    photoUrl: string;
    description?: string;
}

export interface DogPhotoResponse {
    id: number;
    dogId: number;
    photoUrl: string;
    description?: string;
}

export interface AdoptanteRequest {
    nombreCompleto: string;
    telefono: string;
    edad: string;
    ocupacion: string;
    ingresoMensual: string;
    horasDeTrabajo: string;
    tienePatio: string;
    ninosEnCasa: string;
    tipoVivienda: string;
    convivientes: string;
    mascotasAnteriores: string;
    aunConservaMascotas: string;
    responsabilidadesMascota: string;
    opinionEsterilizacion: string;
}

export interface AdoptanteFullResponse {
    id: number;
    nombreCompleto: string;
    telefono: string;
    edad: string;
    ocupacion: string;
    ingresoMensual: string;
    horasDeTrabajo: string;
    tienePatio: string;
    ninosEnCasa: string;
    tipoVivienda: string;
    convivientes: string;
    mascotasAnteriores: string;
    aunConservaMascotas: string;
    responsabilidadesMascota: string;
    opinionEsterilizacion: string;
}

export interface LoginRequest {
    email: string;
    password: string;
}

export interface RegisterRequest {
    name: string;
    email: string;
    password: string;
    roleId: number;
}

export interface UserResponse {
    id: number;
    name: string;
    email: string;
    roleId: number;
    roleName: string;
}

export interface AuthResponse {
    token: string;
    user: UserResponse;
}

export interface AdoptionRequest {
    adoptanteId: number;
    dogId: number;
}

export interface AdoptionResponse {
    id: number;
    adoptanteId: number;
    dogId: number;
    dogName?: string;
    nombreCompleto?: string;
    telefono?: string;
    edad?: string;
    ocupacion?: string;
    ingresoMensual?: string;
    horasDeTrabajo?: string;
    tienePatio?: string;
    ninosEnCasa?: string;
    tipoVivienda?: string;
    convivientes?: string;
    mascotasAnteriores?: string;
    aunConservaMascotas?: string;
    responsabilidadesMascota?: string;
    opinionEsterilizacion?: string;
}

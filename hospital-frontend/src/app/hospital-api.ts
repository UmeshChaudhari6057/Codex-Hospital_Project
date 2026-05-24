import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

export type UserRole = 'ADMIN' | 'DOCTOR' | 'PATIENT';
export type Gender = 'MALE' | 'FEMALE' | 'OTHER';
export type BloodGroup = 'A_POSITIVE' | 'A_NEGATIVE' | 'B_POSITIVE' | 'B_NEGATIVE' | 'AB_POSITIVE' | 'AB_NEGATIVE' | 'O_POSITIVE' | 'O_NEGATIVE';

export interface RegisterRequest {
  username: string;
  password: string;
  email: string;
  role: UserRole;
}

export interface PatientRequest {
  name: string;
  birthDate: string;
  email: string;
  gender: Gender;
  bloodGroup: BloodGroup;
  userId: number;
}

export interface DoctorRequest {
  name: string;
  specialization: string;
  email: string;
  departments: string[];
  userId: number;
}

export interface AppointmentRequest {
  appointmentTime: string;
  reason: string;
  patientId: number;
  doctorId: number;
}

@Injectable({ providedIn: 'root' })
export class HospitalApi {
  private readonly baseUrl = `${this.resolveGatewayUrl()}/api/v1`;

  constructor(private readonly http: HttpClient) {}

  register(payload: RegisterRequest) {
    return this.http.post(`${this.baseUrl}/auth/register`, payload);
  }

  createPatient(payload: PatientRequest) {
    return this.http.post(`${this.baseUrl}/patients`, payload);
  }

  findPatient(id: number) {
    return this.http.get(`${this.baseUrl}/patients/${id}`);
  }

  createDoctor(payload: DoctorRequest) {
    return this.http.post(`${this.baseUrl}/doctors`, payload);
  }

  listDoctors() {
    return this.http.get(`${this.baseUrl}/doctors`);
  }

  createAppointment(payload: AppointmentRequest) {
    return this.http.post(`${this.baseUrl}/appointments`, payload);
  }

  findAppointment(id: number) {
    return this.http.get(`${this.baseUrl}/appointments/${id}`);
  }

  cancelAppointment(id: number) {
    return this.http.put(`${this.baseUrl}/appointments/${id}/cancel`, {});
  }

  completeAppointment(id: number) {
    return this.http.put(`${this.baseUrl}/appointments/${id}/complete`, {});
  }

  private resolveGatewayUrl() {
    const { protocol, hostname } = window.location;

    if (hostname.includes('.app.github.dev')) {
      return `${protocol}//${hostname.replace('-4200.', '-8080.')}`;
    }

    return 'http://localhost:8080';
  }
}

import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { JsonPipe } from '@angular/common';
import { Observable } from 'rxjs';
import { BloodGroup, Gender, HospitalApi, UserRole } from './hospital-api';

@Component({
  selector: 'app-root',
  imports: [FormsModule, JsonPipe],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly activeSection = signal('overview');
  protected readonly busy = signal(false);
  protected readonly message = signal('Frontend ready. Start backend services, then use the forms below.');
  protected readonly lastResponse = signal<unknown>(null);

  protected readonly roles: UserRole[] = ['PATIENT', 'DOCTOR', 'ADMIN'];
  protected readonly genders: Gender[] = ['MALE', 'FEMALE', 'OTHER'];
  protected readonly bloodGroups: BloodGroup[] = [
    'A_POSITIVE',
    'A_NEGATIVE',
    'B_POSITIVE',
    'B_NEGATIVE',
    'AB_POSITIVE',
    'AB_NEGATIVE',
    'O_POSITIVE',
    'O_NEGATIVE'
  ];

  protected registerForm = {
    username: 'john_doe',
    password: 'password123',
    email: 'john@example.com',
    role: 'PATIENT' as UserRole
  };

  protected patientForm = {
    name: 'John Doe',
    birthDate: '1990-01-01',
    email: 'john@example.com',
    gender: 'MALE' as Gender,
    bloodGroup: 'A_POSITIVE' as BloodGroup,
    userId: 1
  };

  protected doctorForm = {
    name: 'Dr. Smith',
    specialization: 'Cardiology',
    email: 'smith@example.com',
    departments: 'Cardiology, Internal Medicine',
    userId: 2
  };

  protected appointmentForm = {
    appointmentTime: '2026-06-01 10:00:00',
    reason: 'Regular checkup',
    patientId: 1,
    doctorId: 1
  };

  protected lookup = {
    patientId: 1,
    appointmentId: 1
  };

  protected readonly stats = [
    { label: 'Services', value: '6', detail: 'Gateway, discovery, user, patient, doctor, appointment' },
    { label: 'Gateway', value: '8080', detail: 'All frontend API calls route through one entry point' },
    { label: 'Frontend', value: '20', detail: 'Angular version aligned with the updated 50-day plan' }
  ];

  protected readonly sections = [
    { id: 'overview', label: 'Overview' },
    { id: 'patients', label: 'Patients' },
    { id: 'doctors', label: 'Doctors' },
    { id: 'appointments', label: 'Appointments' }
  ];

  constructor(private readonly api: HospitalApi) {}

  protected selectSection(section: string) {
    this.activeSection.set(section);
  }

  protected registerUser() {
    this.run('User registration request sent', this.api.register(this.registerForm));
  }

  protected createPatient() {
    this.run('Patient creation request sent', this.api.createPatient(this.patientForm));
  }

  protected findPatient() {
    this.run('Patient lookup request sent', this.api.findPatient(Number(this.lookup.patientId)));
  }

  protected createDoctor() {
    const payload = {
      ...this.doctorForm,
      departments: this.doctorForm.departments.split(',').map((department) => department.trim()).filter(Boolean)
    };

    this.run('Doctor creation request sent', this.api.createDoctor(payload));
  }

  protected listDoctors() {
    this.run('Doctor list request sent', this.api.listDoctors());
  }

  protected createAppointment() {
    this.run('Appointment booking request sent', this.api.createAppointment(this.appointmentForm));
  }

  protected findAppointment() {
    this.run('Appointment lookup request sent', this.api.findAppointment(Number(this.lookup.appointmentId)));
  }

  protected cancelAppointment() {
    this.run('Appointment cancellation request sent', this.api.cancelAppointment(Number(this.lookup.appointmentId)));
  }

  protected completeAppointment() {
    this.run('Appointment completion request sent', this.api.completeAppointment(Number(this.lookup.appointmentId)));
  }

  private run(label: string, request: Observable<unknown>) {
    this.busy.set(true);
    this.message.set(label);
    request.subscribe({
      next: (response) => {
        this.lastResponse.set(response);
        this.message.set('Request completed successfully.');
        this.busy.set(false);
      },
      error: (error) => {
        this.lastResponse.set(error);
        this.message.set('Request failed. Check backend services and gateway CORS configuration.');
        this.busy.set(false);
      }
    });
  }
}

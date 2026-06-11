import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { SessionStateService } from '../../core/security/session-state.service';
import { UserRole } from '../../hospital-api';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  password: string;
  email: string;
  role: UserRole;
}

export interface AuthResponse {
  token?: string;
  accessToken?: string;
  username?: string;
  role?: UserRole;
  message?: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly baseUrl = `${this.resolveGatewayUrl()}/api/v1/auth`;

  constructor(
    private readonly http: HttpClient,
    private readonly session: SessionStateService
  ) {}

  login(payload: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/login`, payload).pipe(
      tap(() => this.session.activate())
    );
  }

  register(payload: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/register`, payload).pipe(
      tap(() => this.session.activate())
    );
  }

  logout() {
    this.session.clear();
  }

  private resolveGatewayUrl() {
    const { protocol, hostname } = window.location;

    if (hostname.includes('.app.github.dev')) {
      return `${protocol}//${hostname.replace('-4200.', '-8080.')}`;
    }

    return 'http://localhost:8080';
  }
}

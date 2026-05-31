import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class SessionStateService {
  private readonly active = signal(false);
  readonly isActive = this.active.asReadonly();

  activate() {
    this.active.set(true);
  }

  clear() {
    this.active.set(false);
  }
}

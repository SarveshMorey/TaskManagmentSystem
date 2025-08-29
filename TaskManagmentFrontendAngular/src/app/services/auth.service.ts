import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { ApiService } from './api.service';
import { User } from '../models/task.model';

const STORAGE_KEY = 'tms_user';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private api: ApiService) {}

  login(email: string, password: string): Observable<User> {
    return this.api.login(email, password).pipe(
      tap(user => localStorage.setItem(STORAGE_KEY, JSON.stringify(user)))
    );
  }

  signup(data: Partial<User>): Observable<User> {
    return this.api.signup(data).pipe(
      tap(user => localStorage.setItem(STORAGE_KEY, JSON.stringify(user)))
    );
  }

  logout(): void {
    localStorage.removeItem(STORAGE_KEY);
  }

  currentUser(): User | null {
    const raw = localStorage.getItem(STORAGE_KEY);
    return raw ? JSON.parse(raw) as User : null;
  }

  isLoggedIn(): boolean {
    return !!this.currentUser();
  }
}

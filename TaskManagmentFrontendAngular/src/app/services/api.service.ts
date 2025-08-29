import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Task, User } from '../models/task.model';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private baseUrl = 'http://localhost:8084/api';

  constructor(private http: HttpClient) {}

  // Auth
  login(email: string, password: string): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/auth/login`, { email, password });
  }

  signup(payload: Partial<User>): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/auth/signup`, payload);
  }

  // Users
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/users`);
  }

  getUser(id: number): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/users/${id}`);
  }

  addUser(payload: Partial<User>): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/users`, payload);
  }

  updateUser(id: number, payload: Partial<User>): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/users/${id}`, payload);
  }

  deleteUser(userId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/users/${userId}`);
  }


  // Tasks
  getTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.baseUrl}/tasks`);
  }

  getTasksByUser(userId: number): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.baseUrl}/tasks/user/${userId}`)
      .pipe(tap(res => console.log('Tasks by user:', res)));
  }

  getTask(id: number): Observable<Task> {
    return this.http.get<Task>(`${this.baseUrl}/tasks/${id}`);
  }

  createTask(payload: Partial<Task>): Observable<Task> {
    return this.http.post<Task>(`${this.baseUrl}/tasks`, payload);
  }

  updateTask(id: number, payload: Partial<Task>): Observable<Task> {
    return this.http.put<Task>(`${this.baseUrl}/tasks/${id}`, payload);
  }

  deleteTask(taskId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/tasks/${taskId}`);
  }

  
  markComplete(taskId: number): Observable<Task> {
    return this.http.put<Task>(`${this.baseUrl}/tasks/${taskId}`, { status: 'COMPLETED' });
  }
}

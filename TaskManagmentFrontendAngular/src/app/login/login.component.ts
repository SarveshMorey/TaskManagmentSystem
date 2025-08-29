import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  error: string | null = null;

  form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(3)]],
  });

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {}

  submit() {
    if (this.form.invalid) return;

    const { email, password } = this.form.value;
    this.error = null; // reset error 

    this.auth.login(email!, password!).subscribe({
      next: (user) => {
        // Login successful
        this.router.navigate(['/tasks']);
      },
      error: (err) => {
        console.error('Login failed:', err);
        this.error = err.error?.message || 'Invalid credentials';
        alert(this.error); 
      }
    });
  }
}

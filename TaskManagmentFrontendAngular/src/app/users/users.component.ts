import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service';
import { User } from '../models/task.model';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html'
})
export class UsersComponent implements OnInit {
  users: User[] = [];

  // Use nonNullable for strict forms, no nulls
  form = this.fb.nonNullable.group({
    username: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  });

  constructor(private api: ApiService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.api.getUsers().subscribe(u => (this.users = u));
  }

  add(): void {
    if (this.form.invalid) return;

    const payload: User = {
      username: this.form.get('username')!.value,
      email: this.form.get('email')!.value,
      password: this.form.get('password')!.value
    };

    this.api.addUser(payload).subscribe(() => {
      this.form.reset();
      this.load();
    });
  }

  delete(u: User): void {
    if (!u.userId) return;
    if (confirm('Delete user?')) {
      this.api.deleteUser(u.userId).subscribe(() => this.load());
    }
  }
}

import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service';
import { Task } from '../models/task.model';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {
  tasks: Task[] = [];
  loading = false;

  constructor(
    private api: ApiService,
    private router: Router,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    this.load();
  }

  private load(): void {
    const user = this.auth.currentUser();
    if (!user || !user.userId) {
      this.router.navigate(['/login']);
      return;
    }
    this.loading = true;
    this.api.getTasksByUser(user.userId).subscribe({
      next: (res) => {
        this.tasks = res || [];
        this.loading = false;
      },
      error: () => {
        this.tasks = [];
        this.loading = false;
      }
    });
  }

  add() {
    this.router.navigate(['/tasks/new']);
  }

  edit(t: Task) {
    if (!t.taskId) return;
    this.router.navigate(['/tasks', t.taskId, 'edit']);
  }

  markComplete(t: Task) {
    if (!t.taskId) return;
    this.api.updateTask(t.taskId, { status: 'COMPLETED' })
      .subscribe(() => this.load());
  }

  delete(t: Task) {
    if (!t.taskId) return;
    if (confirm('Delete this task?')) {
      this.api.deleteTask(t.taskId).subscribe(() => this.load());
    }
  }

  /* HDOM nodes correctly and avoids any weird visual artifacts */
  trackByTaskId = (_: number, t: Task) => t.taskId ?? t.title;
}

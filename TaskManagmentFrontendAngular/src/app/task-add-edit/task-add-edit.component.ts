import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../services/api.service';
import { Task } from '../models/task.model';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-task-add-edit',
  templateUrl: './task-add-edit.component.html'
})
export class TaskAddEditComponent implements OnInit {
  id: number | null = null;
  loading = false;

  form = this.fb.group({
    title: ['', Validators.required],
    description: [''],
    status: ['PENDING', Validators.required],
    dueDate: ['']
  });

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private api: ApiService,
    private router: Router,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.id = idParam ? +idParam : null;

    if (this.id) {
      this.loading = true;
      this.api.getTask(this.id).subscribe(t => {
        this.form.patchValue({
          title: t.title,
          description: t.description || '',
          status: t.status,
          dueDate: t.dueDate || ''
        });
        this.loading = false;
      });
    }
  }

  save() {
    if (this.form.invalid) return;

    const payload = this.form.value as Partial<Task>;

    if (this.id) {
      // Update existing task
      this.api.updateTask(this.id, payload).subscribe(() => 
        this.router.navigate(['/tasks'])
      );
    } else {
      // Create new task
      const u = this.auth.currentUser();
      if (u) {
        (payload as any).userId = u.userId!;
      }
      this.api.createTask(payload).subscribe(() => 
        this.router.navigate(['/tasks'])
      );
    }
  }
}

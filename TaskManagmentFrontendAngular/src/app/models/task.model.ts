export interface User {
  userId?: number;
  username: string;
  email: string;
  password?: string;
  createdAt?: string;
}

export type TaskStatus = 'PENDING' | 'IN_PROGRESS' | 'COMPLETED';

export interface Task {
  taskId?: number;
  title: string;
  description?: string;
  status?: TaskStatus;
  dueDate?: string; 
  userId?: number;
  createdAt?: string;
  updatedAt?: string;
}

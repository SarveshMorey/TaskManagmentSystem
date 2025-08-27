package com.wipro.springboot_tms.repository;

import com.wipro.springboot_tms.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByUserUserId(Integer userId);
}

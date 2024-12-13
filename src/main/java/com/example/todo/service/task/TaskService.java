package com.example.todo.service.task;

import com.example.todo.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class TaskService {

    private final TaskRepository taskRepository;


    public List<TaskEntity> find() {
        return taskRepository.select();
    }

    public Optional<TaskEntity> findById(@PathVariable("id") long id) {
        return taskRepository.selectById(id);
    }

    @Transactional
    public void create(TaskEntity newEntity) {
        taskRepository.insert(newEntity);
    }

    @Transactional
    public void update(TaskEntity entity) {
        taskRepository.update(entity);
    }
}

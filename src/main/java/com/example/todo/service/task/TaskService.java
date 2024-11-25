package com.example.todo.service.task;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskService {

    public List<TaskEntity> find() {
        var task1 = new TaskEntity(1L, "フェザー級①", "クレベル・コイケ", TaskStatus.TODO);
        var task2 = new TaskEntity(2L, "フェザー級②", "ラジャブアリ・シェイドラフ", TaskStatus.DOING);

        return List.of(task1, task2);
    }
}

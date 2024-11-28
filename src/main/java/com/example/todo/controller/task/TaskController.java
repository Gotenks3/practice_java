package com.example.todo.controller.task;

import com.example.todo.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/tasks")
    public String list(Model model) {
        var taskList = taskService.find()
                .stream()
                .map(TaskDTO::toDTO)
                .toList();

        model.addAttribute("taskList", taskList);

        return "tasks/list";
    }

    @GetMapping("/tasks/{id}")
    public String showDetail(@PathVariable("id") long id, Model model) {

        var taskEntity = taskService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("存在しないIdです: id = " + id));
        model.addAttribute("task", TaskDTO.toDTO(taskEntity));
        return "tasks/detail";
    }
}

package com.example.todo.controller.task;

import com.example.todo.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    // 一覧はDBとやり取りしたデータ(Entity)を
    // クライアントに返す形式(DTO)に変換して返す必要がある
    // なので、.mapでEntityをDTOに変換。
    // EntityはDBとやり取りしたデータを返すため、createやupdateなどを返す
    @GetMapping
    public String list(Model model) {
        var taskList = taskService.find()
                .stream()
                .map(TaskDTO::toDTO)
                .toList();

        model.addAttribute("taskList", taskList);

        return "tasks/list";
    }

    // 詳細ははDBとやり取りしたデータ(Entity)を
    // クライアントに返す形式(DTO)に変換して返す必要がある
    // なので、.mapでEntityをDTOに変換。
    // EntityはDBとやり取りしたデータを返すため、createやupdateなどを返す
    @GetMapping("/{id}")
    public String showDetail(@PathVariable("id") long id, Model model) {

        var taskDTO = taskService.findById(id)
                .map(TaskDTO::toDTO)
                .orElseThrow(TaskNotFoundException::new);
        model.addAttribute("task", taskDTO);
        return "tasks/detail";
    }

    // GET /tasks/creationForm
    @GetMapping("/creationForm")
    public String showCreationForm(@ModelAttribute TaskForm form, Model model) {
        model.addAttribute("mode", "CREATE");
        return "tasks/form";
    }


    // 作成処理は受け取ったFormをDBとやり取りするEntityに変換する必要がある
    // TaskFormで受け取った値を「form.toEntity()」に変換
    // Form型はリクエストを受け取ったデータなので、DBとやり取りに使わない。
    // POST /tasks
    @PostMapping
    public String create(@Validated  TaskForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return showCreationForm(form, model);
        }
        taskService.create(form.toEntity());
        return "redirect:/tasks";
    }

    //GET /tasks/{taskId}/editForm
    @GetMapping("/{id}/editForm")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        var form = taskService.findById(id)
                .map(TaskForm::formEntity)
                .orElseThrow(TaskNotFoundException::new); // 引数のないメソッドは::が使える
        model.addAttribute("taskForm", form);
        model.addAttribute("mode", "EDIT");
        return "tasks/form";
    }

    @PutMapping("/{id}") //PUT /tasks/{id}
    public String update(@PathVariable("id") long id,
                         @Validated @ModelAttribute TaskForm form,
                         BindingResult bindingResult,
                         Model model)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "EDIT");
            return "tasks/form";
        }

        var entity = form.toEntity(id);
        taskService.update(entity);
        return "redirect:/tasks/{id}";
    }
}

package com.example.todo.repository.task;

import com.example.todo.service.task.TaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

// SpringのDIにBeanとして登録される
// @Mapperを使用する際は「public class」ではなく「public interface」を使用する
@Mapper
public interface TaskRepository {

    @Select("SELECT id, summary, description, status FROM tasks;")
    List<TaskEntity> select();

    @Select("SELECT id, summary, description, status FROM tasks WHERE id = #{id}")
    Optional<TaskEntity> selectById(@Param("id") long id);

}

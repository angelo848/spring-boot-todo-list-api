package com.todo.todolist.model.mapper;

import com.todo.todolist.domain.TaskEntity;
import com.todo.todolist.model.response.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

  TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

  TaskResponse toResponse(TaskEntity task);
}

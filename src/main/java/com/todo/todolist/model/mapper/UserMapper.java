package com.todo.todolist.model.mapper;

import com.todo.todolist.domain.UserEntity;
import com.todo.todolist.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserResponse toResponse(UserEntity user);
}

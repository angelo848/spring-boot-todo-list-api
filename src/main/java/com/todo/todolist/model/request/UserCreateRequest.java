package com.todo.todolist.model.request;

public record UserCreateRequest(
    String name,
    String email,
    String password
) {
}

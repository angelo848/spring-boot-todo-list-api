package com.todo.todolist.domain;

import org.hibernate.annotations.IdGeneratorType;
import org.hibernate.id.IncrementGenerator;

@IdGeneratorType(IncrementGenerator.class)
public @interface PrimaryIDGenerator {
}

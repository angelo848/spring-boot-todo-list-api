package com.todo.todolist.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Audited
@MappedSuperclass
public abstract class Auditable implements Serializable {

  @CreatedDate
  @Column(name = "created_date", updatable = false)
  protected LocalDateTime createdDate;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  protected LocalDateTime updatedDate;

  @CreatedBy
  @Column(name = "created_by", updatable = false)
  protected String createdBy;

  @LastModifiedBy
  @Column(name = "last_modified_by")
  protected String updatedBy;
}

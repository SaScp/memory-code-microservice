package ru.memorycode.userservice.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {

    private LocalDateTime created_at;

    private LocalDateTime last_activity;

}

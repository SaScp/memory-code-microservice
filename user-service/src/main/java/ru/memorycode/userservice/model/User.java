package ru.memorycode.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(schema = "user_microservice", name = "t_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "lang_code", nullable = false)
    private String langCode;

    @Column(name = "first_lang_set", nullable = false)
    private Boolean firstLangSet;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_activity", nullable = false)
    private LocalDateTime lastActivity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "auth_id",
            referencedColumnName = "id")
    private UserAuthentication userAuth;



}

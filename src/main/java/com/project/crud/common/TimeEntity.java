package com.project.crud.common;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeEntity {

    @Column(name = "CREATED_DATE", nullable = false)
    private String createdDate;

    @Column(name = "MODIFIED_DATE")
    private String modifiedDate;

    @PrePersist
    void onPrePersist() {
        createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @PreUpdate
    void onPreUpdate() {
        modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}

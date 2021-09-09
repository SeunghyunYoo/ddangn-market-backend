package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Data
public abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

//    @PrePersist
//    public void prePersist(){
//        createdAt = LocalDateTime.now();
//        updatedAt = LocalDateTime.now();
//    }

//
//    @PreUpdate
//    public void preUpdate(){
//        updatedAt = LocalDateTime.now();
//    }
}

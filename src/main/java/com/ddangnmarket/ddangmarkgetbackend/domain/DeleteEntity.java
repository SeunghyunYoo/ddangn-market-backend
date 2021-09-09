package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class DeleteEntity extends BaseEntity{

    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @PrePersist
    public void prePersist(){
//        createdAt = LocalDateTime.now();
//        updatedAt = LocalDateTime.now();
        isDeleted = false;
    }

//
//    @PreUpdate
//    public void preUpdate(){
//        updatedAt = LocalDateTime.now();
//    }

    public void delete(){
        this.isDeleted = true;
    }

    public void cancelDelete(){
        this.isDeleted = false;
    }
}

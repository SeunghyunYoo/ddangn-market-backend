package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UploadFile {
    private String uploadFileName;
    private String storeFileName;
}

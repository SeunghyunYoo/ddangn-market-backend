package com.ddangnmarket.ddangmarkgetbackend.domain.file.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author SeunghyunYoo
 */
@NoArgsConstructor
@Data
public class UploadImagesResponseDto {
    private List<Long> imageIds;

    public UploadImagesResponseDto(List<Long> imageIds){
        this.imageIds = imageIds;
    }
}

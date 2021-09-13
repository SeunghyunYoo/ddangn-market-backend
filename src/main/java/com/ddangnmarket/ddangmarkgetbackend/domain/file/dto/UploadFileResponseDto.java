package com.ddangnmarket.ddangmarkgetbackend.domain.file.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author SeunghyunYoo
 */
@NoArgsConstructor
@Data
public class UploadFileResponseDto {
    private List<Long> fileIds;

    public UploadFileResponseDto(List<Long> fileIds){
        this.fileIds = fileIds;
    }
}

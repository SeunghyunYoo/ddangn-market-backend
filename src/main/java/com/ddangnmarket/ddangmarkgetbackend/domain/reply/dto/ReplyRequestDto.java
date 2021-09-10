package com.ddangnmarket.ddangmarkgetbackend.domain.reply.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class ReplyRequestDto {
    @NotEmpty
    private String content;
}

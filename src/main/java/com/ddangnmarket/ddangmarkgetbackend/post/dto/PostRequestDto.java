package com.ddangnmarket.ddangmarkgetbackend.post.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.Status;
import lombok.Data;

import javax.persistence.*;
import java.util.Locale;

@Data
public class PostRequestDto {
    private String title;

    private String desc;

    private int price;

    private CategoryTag categoryTag;

}

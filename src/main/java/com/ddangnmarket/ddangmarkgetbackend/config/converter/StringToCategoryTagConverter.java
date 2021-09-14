package com.ddangnmarket.ddangmarkgetbackend.config.converter;

import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import org.springframework.core.convert.converter.Converter;

public class StringToCategoryTagConverter implements Converter<String, CategoryTag> {
    @Override
    public CategoryTag convert(String source) {
        return CategoryTag.valueOf(source.toUpperCase());
    }
}

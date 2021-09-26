package com.ddangnmarket.ddangmarkgetbackend.config.converter;

import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostStatus;
import org.springframework.core.convert.converter.Converter;

/**
 * @author SeunghyunYoo
 */
public class StringToPostStatusConverter implements Converter<String, PostStatus> {
    @Override
    public PostStatus convert(String source) {
        return PostStatus.valueOf(source.toUpperCase());
    }
}

package com.ddangnmarket.ddangmarkgetbackend.config;

import com.ddangnmarket.ddangmarkgetbackend.config.converter.StringToCategoryTagConverter;
import com.ddangnmarket.ddangmarkgetbackend.interceptor.LogInterceptor;
import com.ddangnmarket.ddangmarkgetbackend.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    public static final String API_VERSION = "/api/v1";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        API_VERSION + "/accounts/new",
                        API_VERSION + "/login",
                        API_VERSION + "/logout",
                        "/error");
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToCategoryTagConverter());
    }
}

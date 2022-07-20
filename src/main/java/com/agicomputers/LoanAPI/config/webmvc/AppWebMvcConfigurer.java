package com.agicomputers.LoanAPI.config.webmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

@Configuration
@EnableWebMvc
public class AppWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new KryoHttpMessageConverter());
        converters.add(new BufferedImageHttpMessageConverter());

        WebMvcConfigurer.super.configureMessageConverters(converters);
    }
}

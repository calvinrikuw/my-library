package com.example.library.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${app.upload.root:uploads}")
    private String uploadRoot;

    @Value("${app.upload.public-prefix:/static}")
    private String publicPrefix;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 把 /static/** 映射到本地磁盘 uploads 目录
        String location = Path.of(uploadRoot).toAbsolutePath().toUri().toString();
        String pattern = (publicPrefix.endsWith("/")) ? (publicPrefix + "**") : (publicPrefix + "/**");
        registry.addResourceHandler(pattern).addResourceLocations(location);
    }
}

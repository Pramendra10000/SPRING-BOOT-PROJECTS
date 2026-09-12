package com.parammart.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StorageWebConfig implements WebMvcConfigurer {

    private final String rootLocation;

    public StorageWebConfig(
            @Value("${app.storage.local.root:uploads}")
            String rootLocation) {

        this.rootLocation = Paths.get(rootLocation)
                .toAbsolutePath()
                .normalize()
                .toUri()
                .toString();
    }

    @Override
    public void addResourceHandlers(
            ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/media/**")
                .addResourceLocations(rootLocation);
    }
}
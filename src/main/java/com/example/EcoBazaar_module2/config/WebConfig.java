package com.example.EcoBazaar_module2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // @Value("${file.upload-dir}") // No longer needed
    // private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // We no longer need to serve files from the file system
        // as they are now stored in the database.

        // registry.addResourceHandler("/uploads/**")
        //         .addResourceLocations("file:" + uploadDir + "/");
    }
}
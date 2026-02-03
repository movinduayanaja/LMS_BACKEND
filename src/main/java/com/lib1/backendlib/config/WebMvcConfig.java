package com.lib1.backendlib.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.time.Duration;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.upload.books-subdir:book-covers}")
    private String bookCoversDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Absolute path to <uploads>/<book-covers> directory
        Path absBooksPath = Path.of(uploadDir).toAbsolutePath().normalize().resolve(bookCoversDir);

        // IMPORTANT: prefix with "file:" and ensure trailing slash
        String booksLocation = "file:" + absBooksPath.toString() + "/";

        registry.addResourceHandler("/images/book-covers/**")
                .addResourceLocations(booksLocation)
                .setCacheControl(CacheControl.maxAge(Duration.ofHours(1)).cachePublic());
    }
}

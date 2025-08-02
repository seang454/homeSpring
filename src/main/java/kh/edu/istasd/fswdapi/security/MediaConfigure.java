package kh.edu.istasd.fswdapi.security;

import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class MediaConfigure implements WebMvcConfigurer {

    @Value("${media.server-path}")
    private String serverPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/v1/media/**")
                .addResourceLocations("file:/"+serverPath);
    }
}

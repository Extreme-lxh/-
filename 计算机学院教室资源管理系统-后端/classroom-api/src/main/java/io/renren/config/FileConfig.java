package io.renren.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther zxl
 * @Date 2019/12/23 17:01
 * @Description
 **/
@Slf4j
@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Value("${files.resources}")
    private String imageResource;

    @Value("${files.path}")
    private String imagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(imageResource).addResourceLocations(imagePath);

    }
}

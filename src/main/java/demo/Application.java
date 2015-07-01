package demo;

import demo.configurations.FileUploadConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by nesterenko_d on 01.07.15.
 */

@Configuration
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

    @Autowired
    FileUploadConfiguration fileUploaderConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = "file:" + fileUploaderConfiguration.getPathToUploadFolder();
        registry.addResourceHandler("/images/**").addResourceLocations(location);
    }

    @Bean
    @ConfigurationProperties("fileUpload")
    public FileUploadConfiguration uploaderConfiguration() {
        return new FileUploadConfiguration();
    }
}

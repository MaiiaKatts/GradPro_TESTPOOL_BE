package de.ait.tp.config;


import de.ait.tp.dto.StandardResponseDto;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

import static de.ait.tp.documentation.OpenApiDocumentation.*;

@ org.springframework.context.annotation.Configuration
public class AppConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OpenAPI openAPI(@Value("${base.url}") String baseUrl) {
        ResolvedSchema resolvedSchema = ModelConverters.getInstance()
                .resolveAsResolvedSchema(
                        new AnnotatedType(StandardResponseDto.class).resolveAsRef(false));

        return new OpenAPI()
                .servers(Arrays.asList(
                        new Server().url("http://localhost:8080"),
                        new Server().url(baseUrl)
                ))
                .components(new Components()
                        .addSchemas("EmailAndPassword", emailAndPassword())
                        .addSecuritySchemes("cookieAuth", securityScheme())
                        .addSchemas("StandardResponseDto", resolvedSchema.schema.description("StandardResponseDto")))
                .addSecurityItem(buildSecurity())
                .paths(buildAuthenticationPath())
                .info(new Info().title("TestPool API").version("0.1"));
    }
    @Bean
    public WebMvcConfigurer cors(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                WebMvcConfigurer.super.addCorsMappings(registry);
                registry.addMapping("/**").allowedOrigins("*");
            }
        };

    }
    @Bean
    public Configuration freemarkerConfiguration(){
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateLoader(new ClassTemplateLoader(AppConfig.class,"/mails/"));
        return configuration;

    }
}

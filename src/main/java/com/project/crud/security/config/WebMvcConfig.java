package com.project.crud.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /*
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/static/", "classpath:/public/", "classpath:/",
            "classpath:/resources/", "classpath:/META-INF/resources/", "classpath:/META-INF/resources/webjars/" };

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        registry.addInterceptor(jwtTokenInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/v1/account")
                .excludePathPatterns("/api/v1/login")
                .excludePathPatterns("/swagger-ui/**");



        registry.addInterceptor(sessionInterceptor())
                .addPathPatterns("/api/**");

        registry.addInterceptor(afterLoginInterceptor())
                .addPathPatterns("/api/v1/login");
    }

    @Bean
    SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor();
    }

    @Bean
    AfterLoginInterceptor afterLoginInterceptor() {
        return new AfterLoginInterceptor();
    }

    @Bean
    public JwtTokenInterceptor jwtTokenInterceptor() {
        return new JwtTokenInterceptor();
    }
    */
}

package com.project.crud.login.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.crud.account.repository.AccountRepository;
import com.project.crud.login.custom.CustomAuthenticationFilter;
import com.project.crud.login.custom.CustomAuthenticationProvider;
import com.project.crud.login.custom.CustomSuccessHandler;
import com.project.crud.login.custom.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private static final String PROCESS_URL = "/api/v1/login";

    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper;

    public SecurityConfig(AccountRepository accountRepository, ObjectMapper objectMapper) {
        this.accountRepository = accountRepository;
        this.objectMapper = objectMapper;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(accountRepository);
    }

    @Bean
    UsernamePasswordAuthenticationFilter customAuthenticationFilter() {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(authenticationManager());

        filter.setFilterProcessesUrl(PROCESS_URL);
        filter.setAuthenticationSuccessHandler(customSuccessHandler());
        return filter;
    }

    @Bean
    AuthenticationSuccessHandler customSuccessHandler() {
        return new CustomSuccessHandler(objectMapper);
    }

    @Bean
    AuthenticationManager authenticationManager() {
        return new ProviderManager(customAuthenticationProvider());
    }

    @Bean
    AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService());
    }
}

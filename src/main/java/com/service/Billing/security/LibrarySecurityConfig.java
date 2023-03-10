package com.service.Billing.security;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class LibrarySecurityConfig{
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        return http.csrf().disable()
            .authorizeHttpRequests().requestMatchers("/isexpire","/bill/**","/swagger-ui/**", "/javainuse-openapi/**","/v3/api-docs/**").permitAll().and()
            .authorizeHttpRequests().requestMatchers("/admin/**").authenticated().and().httpBasic().and().build();
    }
   


}

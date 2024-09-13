package com.bussinesdomain.training.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


import lombok.RequiredArgsConstructor;




@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig  {

    //private final AuthenticationProvider authProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
     private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception{

        
            http
                .csrf( scrf -> scrf.disable())
                .authorizeHttpRequests( 
                    authorize -> 
                        authorize
                        .requestMatchers("/auth/**",
                        "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                );
    
            http.sessionManagement(sessionManager ->  sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            http.addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            //http.authenticationProvider(authProvider).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class );
    
            return http.build();
            
        
                
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public OpenAPI openAPI() {
    return new OpenAPI().addSecurityItem(new SecurityRequirement().
            addList("Bearer Authentication"))
        .components(new Components().addSecuritySchemes
            ("Bearer Authentication", createAPIKeyScheme()))
        .info(new Info().title("My REST API")
            .description("Some custom description of API.")
            .version("1.0").contact(new Contact().name("Sallo Szrajbman")
                .email( "www.baeldung.com").url("salloszraj@gmail.com"))
            .license(new License().name("License of API")
                .url("API license URL")));
    }
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("bearer");
    }
    
}

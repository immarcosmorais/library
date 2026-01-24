package com.mm.library.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/login").permitAll();
                    req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();

                    req.requestMatchers("/reservations/**").hasAnyRole("LIBRARIAN", "READER", "ADMIN");

                    req.requestMatchers(HttpMethod.GET,"/readers/**").hasAnyRole("LIBRARIAN", "READER", "ADMIN");
                    req.requestMatchers("/readers/**").hasAnyRole("LIBRARIAN", "ADMIN");

                    req.requestMatchers(HttpMethod.GET,"/authors/**").hasAnyRole("LIBRARIAN", "READER", "ADMIN");
                    req.requestMatchers("/authors/**").hasAnyRole("LIBRARIAN", "ADMIN");

                    req.requestMatchers(HttpMethod.GET,"/books/**").hasAnyRole("LIBRARIAN", "READER", "ADMIN");
                    req.requestMatchers("/books/**").hasAnyRole("LIBRARIAN", "ADMIN");

                    req.requestMatchers(HttpMethod.GET,"/borrowins/**").hasAnyRole("LIBRARIAN", "READER", "ADMIN");
                    req.requestMatchers("/borrowins/**").hasAnyRole("LIBRARIAN", "ADMIN");

                    req.requestMatchers(HttpMethod.GET,"/publishers/**").hasAnyRole("LIBRARIAN", "READER", "ADMIN");
                    req.requestMatchers("/publishers/**").hasAnyRole("LIBRARIAN", "ADMIN");
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

package com.example.reactivespringvault.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class KeyValueVaultSecurityConfig {

    @Value("${userpass}")
    private String userPassword;

    @Value("${adminpass}")
    private String adminPassword;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.equals(encodedPassword);
            }
        };
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsRepository(PasswordEncoder passwordEncoder) {
        UserDetails standardUser = User.withUsername("user")
                .password(userPassword).passwordEncoder(passwordEncoder::encode)
                .roles("USER")
                .build();
        UserDetails adminUser = User.withUsername("admin")
                .password(adminPassword).passwordEncoder(passwordEncoder::encode)
                .roles("USER", "ADMIN")
                .build();
        return new MapReactiveUserDetailsService(standardUser, adminUser);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .authorizeExchange()
                .anyExchange().authenticated()
                .and()
                .httpBasic();
        return http.build();
    }

}

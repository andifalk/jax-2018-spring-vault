package com.example.springvaultsecurity.config;

import com.example.springvaultsecurity.crypto.VaultPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.vault.core.VaultOperations;

@Configuration
public class SecurityConfiguration {

    @Bean
    PasswordEncoder passwordEncoder(@Autowired VaultOperations vaultOperations) {
        return new VaultPasswordEncoder(vaultOperations.opsForTransit());
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                    User
                        .withUsername("user")
                        .passwordEncoder(passwordEncoder::encode)
                        .password("password")
                        .roles("USER")
                        .build());
        return manager;
    }
}

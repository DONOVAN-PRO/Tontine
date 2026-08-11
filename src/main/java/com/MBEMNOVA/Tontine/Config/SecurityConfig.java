package com.MBEMNOVA.Tontine.Config;

import com.MBEMNOVA.Tontine.Entity.Membre; // CORRIGÉ
import com.MBEMNOVA.Tontine.Repository.MembreRepository; // CORRIGÉ
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(MembreRepository membreRepository) {
        return mail -> {
            Membre membre = membreRepository.findByMail(mail)
                    .orElseThrow(() -> new UsernameNotFoundException("Membre non trouve: " + mail));

            return User.builder()
                    .username(membre.getMail())
                    .password(membre.getPassword())
                    .roles("USER")
                    .build();
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers("/api/tontines/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/membres/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/membres/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/membres/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/membres/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
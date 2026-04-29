package com.salonSpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService usuarios() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();
        UserDetails recepcion = User.builder()
                .username("recepcion")
                .password("{noop}recep123")
                .roles("RECEPCION")
                .build();
        UserDetails cliente = User.builder()
                .username("cliente")
                .password("{noop}cliente123")
                .roles("CLIENTE")
                .build();
        return new InMemoryUserDetailsManager(admin, recepcion, cliente);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index",
                        "/servicio/catalogo",
                        "/cita/reservar",
                        "/cita/horarios",
                        "/cita/confirmar",
                        "/cita/mis-citas",
                        "/cita/cancelar",
                        "/css/**", "/js/**",
                        "/webjars/**").permitAll()
                .requestMatchers("/servicio/listado",
                        "/servicio/guardar",
                        "/servicio/eliminar",
                        "/servicio/modificar/**",
                        "/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/admin/agenda", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );
        return http.build();
    }
}
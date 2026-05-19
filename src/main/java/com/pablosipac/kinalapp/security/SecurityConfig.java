package com.pablosipac.kinalapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final KinalUserDetailsService kinalUserDetailsService;

    public SecurityConfig(KinalUserDetailsService kinalUserDetailsService) {
        this.kinalUserDetailsService = kinalUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(kinalUserDetailsService)
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/css/**", "/js/**",
                                "/vista/login", "/vista/registro").permitAll()
                        .requestMatchers("/vista/clientes/nuevo").permitAll()
                        .requestMatchers("/vista/clientes/guardar").permitAll()
                        .requestMatchers("/vista/clientes/editar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/clientes/actualizar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/clientes/eliminar/**").hasRole("ADMIN")

                        .requestMatchers("/vista/productos/nuevo").permitAll()
                        .requestMatchers("/vista/productos/guardar").permitAll()
                        .requestMatchers("/vista/productos/editar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/productos/actualizar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/productos/eliminar/**").hasRole("ADMIN")

                        .requestMatchers("/vista/ventas/nuevo").permitAll()
                        .requestMatchers("/vista/ventas/guardar").permitAll()
                        .requestMatchers("/vista/ventas/editar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/ventas/actualizar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/ventas/eliminar/**").hasRole("ADMIN")

                        .requestMatchers("/vista/detalles/nuevo").permitAll()
                        .requestMatchers("/vista/detalles/guardar").permitAll()
                        .requestMatchers("/vista/detalles/editar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/detalles/actualizar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/detalles/eliminar/**").hasRole("ADMIN")

                        .requestMatchers("/vista/usuarios/nuevo").permitAll()
                        .requestMatchers("/vista/usuarios/guardar").permitAll()
                        .requestMatchers("/vista/usuarios/editar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/usuarios/actualizar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/usuarios/eliminar/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/vista/login")
                        .loginProcessingUrl("/vista/login")
                        .defaultSuccessUrl("/vista/menu", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/vista/logout")
                        .logoutSuccessUrl("/vista/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
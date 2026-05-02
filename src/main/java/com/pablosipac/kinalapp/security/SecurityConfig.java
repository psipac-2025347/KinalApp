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

                        .requestMatchers("/vista/clientes/nuevo").hasRole("ADMIN")
                        .requestMatchers("/vista/clientes/guardar").hasRole("ADMIN")
                        .requestMatchers("/vista/clientes/editar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/clientes/actualizar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/clientes/eliminar/**").hasRole("ADMIN")

                        .requestMatchers("/vista/productos/nuevo").hasRole("ADMIN")
                        .requestMatchers("/vista/productos/guardar").hasRole("ADMIN")
                        .requestMatchers("/vista/productos/editar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/productos/actualizar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/productos/eliminar/**").hasRole("ADMIN")

                        .requestMatchers("/vista/ventas/nuevo").hasRole("ADMIN")
                        .requestMatchers("/vista/ventas/guardar").hasRole("ADMIN")
                        .requestMatchers("/vista/ventas/editar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/ventas/actualizar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/ventas/eliminar/**").hasRole("ADMIN")

                        .requestMatchers("/vista/detalles/nuevo").hasRole("ADMIN")
                        .requestMatchers("/vista/detalles/guardar").hasRole("ADMIN")
                        .requestMatchers("/vista/detalles/editar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/detalles/actualizar/**").hasRole("ADMIN")
                        .requestMatchers("/vista/detalles/eliminar/**").hasRole("ADMIN")

                        .requestMatchers("/vista/usuarios/**").hasRole("ADMIN")

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
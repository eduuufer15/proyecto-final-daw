package com.miempresa.Proyecto_1.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.miempresa.Proyecto_1.Service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Recursos estáticos públicos
            		
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                // API REST pública (React)
                
                .requestMatchers("/api/**").permitAll()

                // Login público
                
                .requestMatchers("/login").permitAll()

                // Rutas solo para ADMIN
                
                .requestMatchers(
                    "/preguntas/form",
                    "/preguntas/editar/**",
                    "/preguntas/borrar/**",
                    "/preguntas/guardar",
                    "/preguntas/importar",
                    "/preguntas/csv-ejemplo",
                    "/categorias/form",        
                    "/categorias/editar/**",   
                    "/categorias/borrar/**",   
                    "/categorias/guardar"      
                ).hasRole("ADMIN")

                // El resto requiere estar autenticado
                
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**", "/preguntas/importar")
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
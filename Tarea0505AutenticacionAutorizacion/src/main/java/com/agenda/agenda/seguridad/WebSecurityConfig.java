package com.agenda.agenda.seguridad;

import com.agenda.agenda.entidades.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        // Permitir entrar al login a todo el mundo
                        .requestMatchers(HttpMethod.POST, Constans.LOGIN_URL).permitAll()

                        // REGLA PARA DELETE: Solo el ADMIN puede borrar contactos
                        .requestMatchers(HttpMethod.DELETE, "/contactos/**").hasAuthority("ROLE_" + Rol.ADMIN)

                        // NUEVAS REGLAS: POST y PUT solo para ADMIN y USER (GUEST se queda fuera)
                        .requestMatchers(HttpMethod.POST, "/contactos/**").hasAnyAuthority("ROLE_" + Rol.ADMIN, "ROLE_" + Rol.USER)
                        .requestMatchers(HttpMethod.PUT, "/contactos/**").hasAnyAuthority("ROLE_" + Rol.ADMIN, "ROLE_" + Rol.USER)

                        // REGLA PARA GET: Permitido para cualquier usuario autenticado (ADMIN, USER y GUEST)
                        .requestMatchers(HttpMethod.GET, "/contactos/**").authenticated()

                        // El resto de peticiones requieren estar autenticado (tener token)
                        .anyRequest().authenticated())
                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
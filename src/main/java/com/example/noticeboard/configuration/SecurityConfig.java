package com.example.noticeboard.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        RedirectServerLogoutSuccessHandler logoutSuccessHandler = new RedirectServerLogoutSuccessHandler();
        logoutSuccessHandler.setLogoutSuccessUrl(URI.create("/auth/showLogin"));

        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges ->
                        exchanges.pathMatchers("/board/showBoard", "/auth/**").permitAll()
                                .anyExchange().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin.loginPage("/auth/showLogin")
                                .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/board/showBoard"))
                )
                .logout(logout ->
                        logout.logoutUrl("/auth/logout")
                                .logoutSuccessHandler(logoutSuccessHandler)
                );

        return http.build();
    }
}

package com.sadikov.gtwapp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SecurityFilter{

    private final AccountAuthenticationProvider provider;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http){
        return http.httpBasic(Customizer.withDefaults())
                .headers(headerSpec ->
                        headerSpec.contentSecurityPolicy(contentSecurityPolicySpec ->
                                contentSecurityPolicySpec.policyDirectives("upgrade-insecure-request")))
                .cors(Customizer.withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(requests -> {
                    requests.pathMatchers("/openid-connect/**").permitAll();
                    requests.pathMatchers("/login/users/auth").permitAll();
                    requests.pathMatchers("/login/users/create").permitAll();
                    requests.pathMatchers("/login/users/create/new").permitAll();
//                    requests.pathMatchers("/portal/company/create").hasAnyAuthority("REGISTRATOR");
                })
                .oauth2ResourceServer(ServerConfig ->
                        ServerConfig.authenticationManagerResolver(context -> Mono.just(provider))
                ).build();
    }

}

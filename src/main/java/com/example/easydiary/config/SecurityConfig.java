package com.example.easydiary.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OAuth2UserService oAuth2UserService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeRequests(
                        registry -> registry.requestMatchers("/private/**")
                                .authenticated()
                                .anyRequest()
                                .permitAll()
                )
                .logout((logoutConfig) ->
                        logoutConfig.logoutSuccessUrl("/")

                )
                .oauth2Login((oauth2) -> oauth2
                .loginPage("/login")
                .defaultSuccessUrl("/")
                        .failureUrl("/oauth2/authorization/google")
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                .userService(oAuth2UserService)));
        return httpSecurity.build();
    }
}

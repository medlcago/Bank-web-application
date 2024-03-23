package org.backend.bankwebapplication.config;

import org.backend.bankwebapplication.security.auth.CustomAuthenticationFailureHandler;
import org.backend.bankwebapplication.security.auth.CustomAuthenticationProvider;
import org.backend.bankwebapplication.security.user.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {
    UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/registration").permitAll()
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                ).formLogin(form ->
                        form.loginPage("/login")
                                .defaultSuccessUrl("/profile", true)
                                .failureHandler(customAuthenticationFailureHandler())
                                .permitAll()
                )
                .rememberMe(remember ->
                        remember.tokenValiditySeconds(7 * 24 * 60 * 60) // 7 дней в секундах
                )
                .logout(logout ->
                        logout.permitAll()
                                .logoutSuccessUrl("/login?logout")
                                .invalidateHttpSession(true)
                )
                .build();
    }


    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        CustomAuthenticationProvider provider = new CustomAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
package org.backend.bankwebapplication.config;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.security.auth.CustomAuthenticationFailureHandler;
import org.backend.bankwebapplication.security.auth.CustomAuthenticationProvider;
import org.backend.bankwebapplication.security.user.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtRequestsFilter jwtRequestFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final ApiRequestMatcher apiRequestMatcher;
    private final AnyRequestMatcherExceptApi anyRequestMatcherExceptApi;
    private final ApiAuthenticationEntryPoint apiAuthenticationEntryPoint;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers(
                                "/api/v1/**"
                        ))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth").permitAll()
                        .requestMatchers("/api/v1/daily-curs", "/api/v1/transfer-funds", "/api/v1/transactions", "/api/v1/feedback", "/api/v1/create-account").authenticated()
                        .requestMatchers("/registration").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/forgot-password", "api/v1/forgot-password").permitAll()
                        .requestMatchers("/reset-password/**", "/reset-password").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers(apiRequestMatcher).authenticated()
                        .requestMatchers(anyRequestMatcherExceptApi).authenticated()
                ).
                exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(accessDeniedHandler)
                                .authenticationEntryPoint(apiAuthenticationEntryPoint)
                )
                .formLogin(form ->
                        form.loginPage("/login")
                                .defaultSuccessUrl("/profile", true)
                                .failureHandler(customAuthenticationFailureHandler)
                                .permitAll()
                )
                .rememberMe(remember ->
                        remember.tokenValiditySeconds(7 * 24 * 60 * 60) // 7 дней в секундах
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl("/login?expired")
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/login?expired")
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.permitAll()
                                .logoutSuccessUrl("/login?logout")
                                .invalidateHttpSession(true))
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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
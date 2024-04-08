package org.backend.bankwebapplication.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.dto.response.ErrorResponse;
import org.backend.bankwebapplication.security.user.UserDetailsImpl;
import org.backend.bankwebapplication.security.user.UserDetailsServiceImpl;
import org.backend.bankwebapplication.utils.JwtTokenUtils;
import org.backend.bankwebapplication.utils.WebUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestsFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsServiceImpl userDetailsService;
    private final WebUtils webUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            boolean isValid = jwtTokenUtils.validateToken(jwt);
            if (!isValid) {
                webUtils.sendJsonResponse(response, 401, new ErrorResponse(401, "Invalid token"));
                return;
            }
            username = jwtTokenUtils.getUsername(jwt);
        }

        if (username != null) {
            UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request, response);
    }
}

package org.backend.bankwebapplication.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dto.response.ErrorResponse;
import org.backend.bankwebapplication.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final WebUtils webUtils;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (webUtils.isApiRequest(request)) {
            webUtils.sendJsonResponse(response, 401, new ErrorResponse(401, "Unauthorized"));
        } else {
            response.sendRedirect("/login");
        }
    }
}

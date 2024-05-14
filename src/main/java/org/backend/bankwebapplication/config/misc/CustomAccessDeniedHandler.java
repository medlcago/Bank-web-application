package org.backend.bankwebapplication.config.misc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dto.responses.ErrorResponse;
import org.backend.bankwebapplication.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final WebUtils webUtils;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorResponse errorResponse;
        int statusCode;
        if (webUtils.isApiRequest(request)) {
            statusCode = 401;
            errorResponse = new ErrorResponse(statusCode, "Unauthorized");
        } else {
            statusCode = 403;
            errorResponse = new ErrorResponse(statusCode, "Access Denied");
        }
        webUtils.sendJsonResponse(response, statusCode, errorResponse);
    }
}

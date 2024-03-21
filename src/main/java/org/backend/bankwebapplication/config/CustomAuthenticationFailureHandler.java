package org.backend.bankwebapplication.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;


class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String authErrorMessage = exception.getMessage();

        addErrorMessageToSession(request, authErrorMessage);
        logger.error(authErrorMessage);
        super.onAuthenticationFailure(request, response, exception);
    }

    private void addErrorMessageToSession(HttpServletRequest request, String authErrorMessage) {
        HttpSession session = request.getSession();
        session.setAttribute("authErrorMessage", authErrorMessage);
    }
}
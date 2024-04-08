package org.backend.bankwebapplication.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.utils.WebUtils;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiRequestMatcher implements RequestMatcher {
    private final WebUtils webUtils;

    @Override
    public boolean matches(HttpServletRequest request) {
        return webUtils.apiRequestMatcher(request);
    }
}

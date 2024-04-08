package org.backend.bankwebapplication.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class WebUtils {
    @Value("${api.v1.prefix}")
    private String apiV1Prefix;

    /**
     * Возвращает базовый URL сайта на основе запроса.
     *
     * @param request объект HttpServletRequest
     * @return базовый URL сайта
     */
    public String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    /**
     * Отправляет JSON-ответ на основе указанных параметров.
     *
     * @param response       объект HttpServletResponse, через который будет отправлен ответ
     * @param statusCode     статусный код HTTP для ответа
     * @param responseObject объект, который будет преобразован в JSON и отправлен в ответе
     * @throws IOException если произошла ошибка ввода-вывода при отправке ответа
     */
    public void sendJsonResponse(HttpServletResponse response, int statusCode, Object responseObject) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(responseObject);
        response.getWriter().write(json);
    }

    /**
     * Проверяет, является ли запрос API-запросом.
     *
     * @param request объект HttpServletRequest, представляющий входящий запрос
     * @return true, если запрос является API-запросом, false в противном случае
     */
    public boolean isApiRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith(apiV1Prefix);
    }

    /**
     * Проверяет, соответствует ли запрос условиям API-запросчика.
     *
     * @param request объект HttpServletRequest, представляющий входящий запрос
     * @return true, если запрос является API-запросом и содержит заголовок авторизации, начинающийся с "Bearer ", false в противном случае
     */
    public boolean apiRequestMatcher(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return isApiRequest(request) && authHeader != null && authHeader.startsWith("Bearer ");
    }

    /**
     * Проверяет, соответствует ли запрос условиям запросчика, отличного от API-запросчика.
     *
     * @param request объект HttpServletRequest, представляющий входящий запрос
     * @return true, если запрос не является API-запросом и не содержит заголовок авторизации, false в противном случае
     */
    public boolean anyRequestMatcherExceptApi(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return !isApiRequest(request) && authHeader == null;
    }
}
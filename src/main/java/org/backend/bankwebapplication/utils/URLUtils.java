package org.backend.bankwebapplication.utils;

import jakarta.servlet.http.HttpServletRequest;


public class URLUtils {
    /**
     * Возвращает базовый URL сайта на основе запроса.
     *
     * @param request объект HttpServletRequest
     * @return базовый URL сайта
     */
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
package org.backend.bankwebapplication.controllers;

import org.backend.bankwebapplication.security.user.UserDetailsImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AdviceController {
    /**
     * Проверяет, аутентифицирован ли текущий пользователь, и добавляет значение этой проверки в модель с атрибутом "authenticated".
     *
     * @param authentication Объект Authentication, содержащий информацию о текущей аутентификации пользователя.
     * @return true, если пользователь аутентифицирован, в противном случае false.
     */
    @ModelAttribute("authenticated")
    public boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }

    /**
     * Получает данные пользователя из объекта Authentication и добавляет их в модель с атрибутом "user".
     *
     * @param authentication Объект Authentication, содержащий информацию о текущей аутентификации пользователя.
     * @return Объект UserDetailsImpl, содержащий информацию о пользователе, или null, если аутентификация не выполнена.
     */
    @ModelAttribute("user")
    public UserDetailsImpl getPrincipal(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsImpl) {
                return (UserDetailsImpl) principal;
            }
        }
        return null;
    }

    /**
     * Обрабатывает исключение MethodArgumentNotValidException, возникающее при невалидных аргументах метода, и возвращает ответ с ошибками в формате JSON.
     *
     * @param ex Исключение MethodArgumentNotValidException.
     * @return Ответ с ошибками в формате JSON.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        Map<String, Map<String, String>> response = new HashMap<>();
        response.put("errors", errors);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .headers(headers)
                .body(response);
    }

    /**
     * Обрабатывает ошибку 404 (Ресурс не найден) и возвращает шаблон "errors/404".
     *
     * @return Модель и представление для отображения страницы ошибки 404.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView pageNotFound() {
        return new ModelAndView("errors/404");
    }
}
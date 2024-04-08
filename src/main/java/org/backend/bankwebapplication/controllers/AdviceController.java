package org.backend.bankwebapplication.controllers;

import org.backend.bankwebapplication.security.user.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * @param userDetails Объект UserDetailsImpl, содержащий информацию о текущем пользователе.
     * @return Объект UserDetailsImpl, содержащий информацию о пользователе, или null, если аутентификация не выполнена.
     */
    @ModelAttribute("user")
    public UserDetailsImpl getPrincipal(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userDetails;
    }

    /**
     * Обрабатывает исключение MethodArgumentNotValidException, возникающее при невалидных аргументах метода, и возвращает ответ с ошибками в формате JSON.
     *
     * @param ex Исключение MethodArgumentNotValidException.
     * @return Ответ с ошибками в формате JSON.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField,
                        fieldError -> Optional.ofNullable(fieldError.getDefaultMessage())
                                .orElse("Произошла неизвестная ошибка")));

        Map<String, Map<String, String>> response = Map.of("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
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

    /**
     * Обрабатывает ошибку 403 (Доступ запрещен) и возвращает шаблон "errors/403".
     *
     * @return Модель и представление для отображения страницы ошибки 403.
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView pageAccessDenied() {
        return new ModelAndView("errors/403");
    }
}
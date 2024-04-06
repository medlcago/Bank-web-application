package org.backend.bankwebapplication.controllers.html;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordController {

    @GetMapping(value = "forgot-password")
    public String showForgotPasswordForm(Model model, HttpSession session) {
        model.addAttribute("title", "Восстановление пароля");
        String invalidToken = (String) session.getAttribute("invalidToken");
        if (invalidToken != null) {
            session.removeAttribute("invalidToken");
            model.addAttribute("danger", invalidToken);
        }
        return "forgot-password";
    }
}
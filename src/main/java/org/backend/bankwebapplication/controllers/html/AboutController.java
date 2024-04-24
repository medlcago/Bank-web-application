package org.backend.bankwebapplication.controllers.html;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    @GetMapping("/about")
    public String getAboutPage(Model model) {
        model.addAttribute("title", "О проекте");
        return "about";
    }
}

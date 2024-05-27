package org.backend.bankwebapplication.controllers.api.v1;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dto.responses.AboutMeResponse;
import org.backend.bankwebapplication.dto.responses.MeResponse;
import org.backend.bankwebapplication.dto.responses.UserResponse;
import org.backend.bankwebapplication.entities.User;
import org.backend.bankwebapplication.security.user.UserDetailsImpl;
import org.backend.bankwebapplication.services.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController(value = "ApiAboutMeController")
@RequestMapping(value = "${api.v1.prefix}", produces = "application/json")
@RequiredArgsConstructor
public class AboutMeController {
    private final UserServiceImpl userService;

    @GetMapping(value = "/me/accounts")
    public ResponseEntity<?> aboutMe(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AboutMeResponse aboutMeResponse = userService.toAboutMeResponse(user.get());
        return ResponseEntity.ok(aboutMeResponse);
    }

    @GetMapping(value = "/me/")
    public ResponseEntity<?> aboutUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserResponse userResponse = userService.toUserResponse(user.get());
        return ResponseEntity.ok(new MeResponse(userResponse));
    }
}

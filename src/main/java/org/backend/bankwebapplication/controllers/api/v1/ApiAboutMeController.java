package org.backend.bankwebapplication.controllers.api.v1;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dto.AboutMeDTO;
import org.backend.bankwebapplication.dto.MeDTO;
import org.backend.bankwebapplication.dto.UserDTO;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.security.user.UserDetailsImpl;
import org.backend.bankwebapplication.services.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "${api.v1.prefix}", produces = "application/json")
@RequiredArgsConstructor
public class ApiAboutMeController {
    private final UserServiceImpl userService;

    @GetMapping(value = "/me/accounts")
    public ResponseEntity<?> aboutMe(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AboutMeDTO aboutMeDTO = userService.toAboutMeDTO(user.get());
        return ResponseEntity.ok(aboutMeDTO);
    }

    @GetMapping(value = "/me/")
    public ResponseEntity<?> aboutUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserDTO userDTO = userService.toUserDTO(user.get());
        return ResponseEntity.ok(new MeDTO(userDTO));
    }
}

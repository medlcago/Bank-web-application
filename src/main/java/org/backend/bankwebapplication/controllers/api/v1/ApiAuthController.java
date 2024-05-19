package org.backend.bankwebapplication.controllers.api.v1;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dto.requests.JwtRequest;
import org.backend.bankwebapplication.services.impl.AuthServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "${api.v1.prefix}/auth", produces = "application/json")
@RequiredArgsConstructor
public class ApiAuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }
}
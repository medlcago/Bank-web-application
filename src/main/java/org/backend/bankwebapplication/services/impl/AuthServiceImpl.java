package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dto.JwtRequest;
import org.backend.bankwebapplication.dto.response.ErrorResponse;
import org.backend.bankwebapplication.dto.response.JwtResponse;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.services.AuthService;
import org.backend.bankwebapplication.utils.JwtTokenUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserServiceImpl userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body(new ErrorResponse(401, ex.getMessage()));
        } catch (DisabledException | LockedException ex) {
            return ResponseEntity.status(403).body(new ErrorResponse(403, ex.getMessage()));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(404).body(new ErrorResponse(404, ex.getMessage()));
        }

        Optional<User> user = userService.findByUsername(authRequest.username());
        String token = jwtTokenUtils.generateToken(user.get());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
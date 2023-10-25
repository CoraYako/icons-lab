package com.icons.auth.controller;

import com.icons.auth.model.dto.AuthenticationRequestDTO;
import com.icons.auth.model.dto.AuthenticationResponseDTO;
import com.icons.auth.model.dto.RegistrationRequestDTO;
import com.icons.auth.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registration(@Valid @RequestBody RegistrationRequestDTO dto) {
        authenticationService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@Valid @RequestBody AuthenticationRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.authenticate(dto));
    }
}

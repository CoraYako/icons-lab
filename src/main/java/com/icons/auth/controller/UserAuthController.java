package com.icons.auth.controller;

import com.icons.auth.dto.AuthenticationResponse;
import com.icons.auth.dto.UserDTO;
import com.icons.auth.service.UserDetailsCustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserDetailsCustomService userDetailsCustomService;

    @PostMapping("/singup")
    public ResponseEntity<AuthenticationResponse> singUp(@Valid @RequestBody UserDTO user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/singup").toString());
        userDetailsCustomService.save(user);
        return ResponseEntity.created(uri).build();
    }
}

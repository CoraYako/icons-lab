package com.icons.auth.service.implementation;

import com.icons.auth.model.UserEntity;
import com.icons.auth.model.dto.AuthenticationRequestDTO;
import com.icons.auth.model.dto.AuthenticationResponseDTO;
import com.icons.auth.model.dto.RegistrationRequestDTO;
import com.icons.auth.model.mapper.UserMapper;
import com.icons.auth.repository.UserRepository;
import com.icons.auth.service.AuthenticationService;
import com.icons.auth.service.JwtService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.security.InvalidParameterException;
import java.util.Objects;

@Service
@Validated
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     UserMapper userMapper,
                                     JwtService jwtService,
                                     AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void register(RegistrationRequestDTO requestDTO) {
        if (Objects.isNull(requestDTO))
            throw new NullPointerException("The provided user is invalid or null.");
        if (Objects.isNull(requestDTO.email()) || requestDTO.email().trim().isEmpty())
            throw new InvalidParameterException("Must provide a valid email.");
        if (userRepository.existsByEmail(requestDTO.email()))
            throw new EntityExistsException(String.format("The email %s is already taken.", requestDTO.email()));
        UserEntity user = userMapper.toEntity(requestDTO);
        String passwordEncrypted = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncrypted);
        userRepository.save(user);
    }

    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO requestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.email(),
                requestDTO.password()));
        var user = userRepository.findByEmail(requestDTO.email())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User not found for email %s", requestDTO.email())));
        return new AuthenticationResponseDTO(jwtService.generateToken(user));
    }
}

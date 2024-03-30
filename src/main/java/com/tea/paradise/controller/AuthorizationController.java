package com.tea.paradise.controller;

import com.tea.paradise.config.auth.AuthenticationRequest;
import com.tea.paradise.config.auth.AuthenticationResponse;
import com.tea.paradise.config.auth.RegistrationRequest;
import com.tea.paradise.service.AuthenticationService;
import com.tea.paradise.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "Операции регистрации/авторизации")
@RestController
@RequestMapping("/auth")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthorizationController {
    AuthenticationService authenticationService;
    JwtService jwtService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/registration")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest request)
    {
        authenticationService.registration(request);
        return ResponseEntity.ok("Ваш аккаунт успешно зарегистрирован!");
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response)
    {
        return authenticationService.authenticate(request, response);
    }

    @Operation(summary = "Обновление access токена по refresh токену")
    @PostMapping("refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody AuthenticationResponse authenticationRequest) {
        String refreshToken = authenticationRequest.getRefreshToken();
        if (Objects.isNull(refreshToken)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String newToken = jwtService.generateTokenUseRefreshToken(refreshToken);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .token(newToken)
                .build());
    }

    @Operation(summary = "Выход из аккаунта")
    @GetMapping("/logout")
    public ResponseEntity<?> logout (){
        return ResponseEntity.ok().body("Вы вышли из аккаунта");
    }
}

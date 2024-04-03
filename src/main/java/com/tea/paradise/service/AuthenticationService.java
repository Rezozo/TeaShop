package com.tea.paradise.service;

import com.tea.paradise.config.auth.AuthenticationRequest;
import com.tea.paradise.config.auth.AuthenticationResponse;
import com.tea.paradise.config.auth.RegistrationRequest;
import com.tea.paradise.enums.UserRole;
import com.tea.paradise.model.Users;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthenticationService {
    PasswordEncoder passwordEncoder;
    RoleService roleService;
    AuthenticationManager manager;
    UserService userService;
    JwtService jwtService;
    BucketService bucketService;

    public void registration(RegistrationRequest request) {
        Users users = new Users()
                .setTeaBonuses(0)
                .setName(request.getName())
                .setCreatedDate(ZonedDateTime.now())
                .setEmail(request.getEmail())
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .setRole(roleService.getByTitle(UserRole.USER))
                .setDeleted(false);

        if (userService.existsByEmail(users.getEmail())) {
            throw new ConstraintViolationException("Пользователь с электронной почтой " + users.getEmail() + " уже существует", null);
        }

        userService.saveAndReturn(users);
        bucketService.createByUser(users);
    }

    @SneakyThrows
    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
        try {
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            Users user = userService.getByEmail(request.getEmail());
            String jwtToken = jwtService.generateToken(user); // TODO add role into token
            String refreshToken = jwtService.refreshToken(jwtToken);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Неправильный логин или пароль");
        }
    }

}

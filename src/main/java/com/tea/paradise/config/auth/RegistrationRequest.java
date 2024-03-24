package com.tea.paradise.config.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotNull(message = "Имя должно быть указано!")
    @NotBlank(message = "Имя не должно быть пустое")
    @Size(max = 255, message = "Имя не должно превышать 255 символов")
    private String name;
    @NotNull(message = "Адрес электронной почты должен быть указан!")
    @NotBlank(message = "Адрес электронной почты не должен быть пустым")
    @Size(max = 70, message = "Адрес электронной почты не должен превышать 255 символов")
    private String email;
    @NotNull
    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;
}

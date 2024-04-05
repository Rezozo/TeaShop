package com.tea.paradise.controller;

import com.tea.paradise.dto.saveDto.UserSaveDto;
import com.tea.paradise.dto.user.UserDto;
import com.tea.paradise.dto.user.mapper.UserMapper;
import com.tea.paradise.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Операции с пользователями")
@RestController
@RequestMapping("/users")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserController {
    UserService userService;
    UserMapper userMapper;

    @Operation(summary = "Получение данных авторизированного пользователя")
    @GetMapping("logged-user")
    @SneakyThrows
    public UserDto getLoggedUser() {
        return userMapper.map(userService.getLoggedUserInfo());
    }

    @Operation(summary = "Изменение своих данных пользователем")
    @PutMapping
    public UserDto changeData(@Valid @RequestBody UserSaveDto saveDto) {
        return userMapper.map(userService.updateUserInfo(saveDto));
    }

    @Operation(summary = "Удаление аккаунта")
    @DeleteMapping
    public void deleteAccount() {
        userService.deleteAccount();
    }
}

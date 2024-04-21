package com.tea.paradise.service;

import com.tea.paradise.dto.saveDto.UserSaveDto;
import com.tea.paradise.model.Users;
import com.tea.paradise.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public Users getLoggedUserInfo() {
        return userRepository.findById(getAuthInfo().getId()).orElseThrow();
    }

    public Users getAuthInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Users) {
            return (Users) principal;
        }
        return null;
    }

    public Users saveAndReturn(Users users) {
        return userRepository.save(users);
    }
    public Users getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<Users> getAll() {
        return userRepository.findAll();
    }

    public Users updateUserInfo(UserSaveDto saveDto) {
        Users current = getLoggedUserInfo();
        current.setName(saveDto.getName());
        current.setSurname(saveDto.getSurname());
        current.setEmail(saveDto.getEmail());
        if (existsByEmail(saveDto.getEmail()) && !Objects.equals(saveDto.getEmail(), current.getEmail())) {
            throw new ConstraintViolationException("Пользователь с данной электронной почтой уже существует", null);
        }
        if (Strings.isNotEmpty(saveDto.getPassword())) {
            current.setPassword(passwordEncoder.encode(saveDto.getPassword()));
        }
        return saveAndReturn(current);
    }

    public void deleteAccount() {
        Users current = getLoggedUserInfo();
        userRepository.deleteById(current.getId());
    }
}

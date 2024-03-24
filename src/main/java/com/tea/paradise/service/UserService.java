package com.tea.paradise.service;

import com.tea.paradise.model.Users;
import com.tea.paradise.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserService {
    UserRepository userRepository;

    public Users getLoggedUserInfo() {
        return userRepository.findById(getAuthInfo().getId()).orElseThrow();
    }

    public Users getAuthInfo() {
        return (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
}

package com.warriaty.dormbe.user.controller;

import com.warriaty.dormbe.user.entity.User;
import com.warriaty.dormbe.user.mapper.UserMapper;
import com.warriaty.dormbe.user.model.request.UserRequest;
import com.warriaty.dormbe.user.model.response.UserResponse;
import com.warriaty.dormbe.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::mapToUserResponse).toList();
    }

    @GetMapping("/me")
    public UserResponse getCurrentlyLoginUser(@AuthenticationPrincipal UserDetails currentUser) {
        return userRepository.findByEmail(currentUser.getUsername()).map(userMapper::mapToUserResponse).orElse(null);
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody @Valid UserRequest userRequest) {
        var user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userRepository.save(user);
    }

}

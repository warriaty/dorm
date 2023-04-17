package com.warriaty.dormbe.auth.service;

import com.warriaty.dormbe.auth.model.UserDetailsImpl;
import com.warriaty.dormbe.user.entity.User;
import com.warriaty.dormbe.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> userNotFound(email));
        return new UserDetailsImpl(user.getId(), user.getEmail(), user.getPassword());
    }

    private static UsernameNotFoundException userNotFound(String email) {
        return new UsernameNotFoundException("User with username: %s was not found".formatted(email));
    }

    public UserDetails loadUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> userNotFound(userId));
        return new UserDetailsImpl(userId, user.getEmail(), user.getPassword());
    }

    private static UsernameNotFoundException userNotFound(Long userId) {
        return new UsernameNotFoundException("User with id: %d was not found".formatted(userId));
    }
}

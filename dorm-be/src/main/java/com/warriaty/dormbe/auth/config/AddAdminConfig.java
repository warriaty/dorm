package com.warriaty.dormbe.auth.config;

import com.warriaty.dormbe.user.entity.User;
import com.warriaty.dormbe.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@AllArgsConstructor
public class AddAdminConfig {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner addTestUser() {
        return args -> {
            if (userRepository.findByEmail("admin@example.com").isEmpty()) {
                var user = new User();
                user.setEmail("admin@example.com");
                user.setPassword(passwordEncoder.encode("admin"));
                userRepository.save(user);
            }
            log.info("Test admin user present in DB to login use: email='admin@gmail.com', password='admin'");
        };
    }
}

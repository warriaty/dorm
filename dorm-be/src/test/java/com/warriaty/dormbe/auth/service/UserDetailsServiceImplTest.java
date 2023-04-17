package com.warriaty.dormbe.auth.service;

import com.warriaty.dormbe.auth.model.UserDetailsImpl;
import com.warriaty.dormbe.user.entity.User;
import com.warriaty.dormbe.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl testObject;

    @Test
    void shouldLoadUserByUsername() {
        //given
        when(userRepository.findByEmail("username@test.com")).thenReturn(Optional.of(new User(1L, "username@test.com", "1234")));

        //when
        var userDetails = testObject.loadUserByUsername("username@test.com");

        //then
        assertThat(userDetails).isEqualTo(new UserDetailsImpl(1L, "username@test.com", "1234"));
    }

    @Test
    void shouldThrowExceptionWhenUserWithGivenUsernameWasNotFound() {
        //given
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        //when
        var exc = assertThrows(UsernameNotFoundException.class, () -> testObject.loadUserByUsername("username@test.com"));

        //then
        assertThat(exc).hasMessage("User with username: username@test.com was not found");
    }

    @Test
    void shouldLoadUserById() {
        //given
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User(1L, "username@test.com", "1234")));

        //when
        var userDetails = testObject.loadUserById(1L);

        //then
        assertThat(userDetails).isEqualTo(new UserDetailsImpl(1L, "username@test.com", "1234"));
    }

    @Test
    void shouldThrowExceptionWhenUserWithGivenIdWasNotFound() {
        //given
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        //when
        var exc = assertThrows(UsernameNotFoundException.class, () -> testObject.loadUserById(1L));

        //then
        assertThat(exc).hasMessage("User with id: 1 was not found");
    }
}
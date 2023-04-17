package com.warriaty.dormbe.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warriaty.dormbe.config.DisableSecurityConfig;
import com.warriaty.dormbe.config.TestMapperConfiguration;
import com.warriaty.dormbe.user.entity.User;
import com.warriaty.dormbe.user.model.request.UserRequest;
import com.warriaty.dormbe.user.model.response.UserResponse;
import com.warriaty.dormbe.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = UserController.class)
@ContextConfiguration(classes = {DisableSecurityConfig.class, TestMapperConfiguration.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @WithUserDetails("test@example.com")
    void shouldReturnLoggedInUser() throws Exception {
        //given
        var user = user("test@example.com");
        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(new User(1L, "test@example.com", "test")));

        //when
        var result = mockMvc.perform(get("/api/users/me").with(user));

        //then
        result.andExpect(content().json(objectMapper.writeValueAsString(new UserResponse(1L, "test@example.com"))));
    }

    @Test
    void shouldRegisterUser() throws Exception {
        //given
        UserRequest user = new UserRequest("test@example.com", "test");

        //when
        var result = mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        //then
        result.andExpect(status().isOk());
    }

}
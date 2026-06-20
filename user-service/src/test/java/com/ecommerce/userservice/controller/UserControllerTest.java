package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.UserRegistrationRequest;
import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void shouldRegisterUser() throws Exception {

        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail("test@test.com");
        request.setPassword("1234");
        request.setFullName("John Doe");

        User saved = new User();
        saved.setId(1L);
        saved.setEmail("test@test.com");
        saved.setFullName("John Doe");

        when(userService.register(any()))
                .thenReturn(saved);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@test.com"));
    }

    @Test
    void shouldGetUserByEmail() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");

        when(userService.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/test@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@test.com"));
    }

    @Test
    void shouldReturn404WhenUserNotFound() throws Exception {

        when(userService.findByEmail("missing@test.com"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/users/missing@test.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldListUsers() throws Exception {

        User u1 = new User();
        u1.setId(1L);

        User u2 = new User();
        u2.setId(2L);

        when(userService.findAll())
                .thenReturn(List.of(u1, u2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
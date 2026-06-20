package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.UserRegistrationRequest;
import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterUserSuccessfully() {

        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail("test@test.com");
        request.setPassword("1234");
        request.setFullName("John Doe");

        User saved = new User();
        saved.setId(1L);
        saved.setEmail("test@test.com");
        saved.setFullName("John Doe");

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.empty());

        when(userRepository.save(any(User.class)))
                .thenReturn(saved);

        User result = userService.register(request);

        assertNotNull(result);
        assertEquals("test@test.com", result.getEmail());

        verify(userRepository).findByEmail("test@test.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {

        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail("existing@test.com");
        request.setPassword("1234");
        request.setFullName("John Doe");

        User existing = new User();
        existing.setEmail("existing@test.com");

        when(userRepository.findByEmail("existing@test.com"))
                .thenReturn(Optional.of(existing));

        assertThrows(IllegalStateException.class,
                () -> userService.register(request));

        verify(userRepository).findByEmail("existing@test.com");
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldFindUserByEmail() {

        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail("test@test.com");

        assertTrue(result.isPresent());
        assertEquals("test@test.com", result.get().getEmail());

        verify(userRepository).findByEmail("test@test.com");
    }

    @Test
    void shouldReturnAllUsers() {

        User u1 = new User();
        u1.setId(1L);

        User u2 = new User();
        u2.setId(2L);

        when(userRepository.findAll())
                .thenReturn(List.of(u1, u2));

        List<User> result = userService.findAll();

        assertEquals(2, result.size());

        verify(userRepository).findAll();
    }
}
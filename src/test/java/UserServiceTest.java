package com.example.demo.service;
/*
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkLogin_shouldReturnTrue_whenCredentialsAreCorrect() {
        // Arrange
        UserModel user = new UserModel();
        user.setUsername("john");
        user.setPassword("1234");

        when(userRepository.findByUsername("john")).thenReturn(user);

        // Act
        boolean result = userService.checkLogin("john", "1234");

        // Assert
        assertThat(result).isTrue();
        verify(userRepository).findByUsername("john");
    }

    @Test
    void checkLogin_shouldReturnFalse_whenPasswordIsWrong() {
        UserModel user = new UserModel();
        user.setUsername("john");
        user.setPassword("1234");

        when(userRepository.findByUsername("john")).thenReturn(user);

        boolean result = userService.checkLogin("john", "wrong");

        assertThat(result).isFalse();
        verify(userRepository).findByUsername("john");
    }

    @Test
    void checkLogin_shouldReturnFalse_whenUserDoesNotExist() {
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        boolean result = userService.checkLogin("unknown", "password");

        assertThat(result).isFalse();
        verify(userRepository).findByUsername("unknown");
    }
}
*/
package com.example.Clique.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import com.example.Clique.Entities.Users;
import com.example.Clique.repository.UsersRepository;
import com.example.Clique.security.JwtUtil;
import com.example.Clique.service.UserService;

class UserServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        Users user = new Users();
        user.setUsername("testUser");
        user.setPassword("testPass");

        when(usersRepository.save(any())).thenReturn(user);
        when(jwtUtil.generateToken(anyString())).thenReturn("dummyToken");

        String token = userService.registerUser(user);

        assertEquals("dummyToken", token);
        verify(usersRepository, times(1)).save(user);
    }

    @Test
    void testLoginUser() {
        Users user = new Users();
        user.setUsername("testUser");
        user.setPassword("testPass");

        when(jwtUtil.generateToken(anyString())).thenReturn("dummyToken");

        String token = userService.loginUser(user);

        assertEquals("dummyToken", token);
    }

    @Test
    void testLoginUserWithInvalidCredentials() {
        Users user = new Users();
        user.setUsername("testUser");
        user.setPassword("wrongPass");

        doThrow(new BadCredentialsException("Invalid credentials")).when(authenticationManager).authenticate(any());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.loginUser(user));
        assertEquals("Invalid username or password", exception.getMessage());
    }
}
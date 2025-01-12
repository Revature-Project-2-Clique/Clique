package com.example.Clique.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.Clique.Entities.Users;
import com.example.Clique.dto.BioDTO;
import com.example.Clique.dto.UpdateNameDTO;
import com.example.Clique.dto.UpdatePasswordDTO;
import com.example.Clique.dto.UsersDTO;
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

    private BCryptPasswordEncoder bcrypt;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bcrypt = new BCryptPasswordEncoder();
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

    @Test
    void testUpdateName_Success() {
        // Arrange
        UpdateNameDTO updateNameDTO = new UpdateNameDTO();
        updateNameDTO.setUsername("testUser");
        updateNameDTO.setFirstName("UpdatedFirst");
        updateNameDTO.setLastName("UpdatedLast");

        Users existingUser = new Users();
        existingUser.setUsername("testUser");
        existingUser.setFirstName("OldFirst");
        existingUser.setLastName("OldLast");

        Users updatedUser = new Users();
        updatedUser.setUsername("testUser");
        updatedUser.setFirstName("UpdatedFirst");
        updatedUser.setLastName("UpdatedLast");

        when(usersRepository.findByUsername("testUser")).thenReturn(Optional.of(existingUser));
        when(usersRepository.save(any(Users.class))).thenReturn(updatedUser);

        // Act
        UsersDTO result = userService.updateName(updateNameDTO);

        // Assert
        assertNotNull(result, "Expected a non-null UsersDTO");
        assertEquals("UpdatedFirst", result.getFirstName(), "First name should match the updated value");
        assertEquals("UpdatedLast", result.getLastName(), "Last name should match the updated value");
        verify(usersRepository, times(1)).findByUsername("testUser");
        verify(usersRepository, times(1)).save(existingUser);
    }

    @Test
    void testUpdateBio_Success() throws IllegalAccessException {
        // Arrange
        Long userId = 1L;
        BioDTO bioDTO = new BioDTO("Updated bio");

        Users user = new Users();
        user.setUserId(userId);
        user.setBio("Old bio");

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(usersRepository.save(any(Users.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        BioDTO result = userService.updateBio(userId, bioDTO);

        // Assert
        assertNotNull(result, "Expected a non-null BioDTO");
        assertEquals("Updated bio", result.getBio(), "Bio should match the updated value");
        verify(usersRepository, times(1)).findById(userId);
        verify(usersRepository, times(1)).save(user);
    }

    @Test
    void testUpdatePassword_Success() {
        // Arrange
        String username = "testUser";
        String currentPassword = "currentPassword";
        String newPassword = "newPassword";

        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        updatePasswordDTO.setUsername(username);
        updatePasswordDTO.setPassword(currentPassword);
        updatePasswordDTO.setNewPassword(newPassword);

        Users existingUser = new Users();
        existingUser.setUsername(username);
        existingUser.setPassword(bcrypt.encode(currentPassword));

        when(usersRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));
        when(usersRepository.save(any(Users.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        userService.updatePassword(updatePasswordDTO);

        // Assert
        verify(usersRepository, times(1)).findByUsername(username);
        verify(usersRepository, times(1)).save(existingUser);
        assertTrue(bcrypt.matches(newPassword, existingUser.getPassword()), "The password should be updated successfully.");
    }

    @Test
    void testUpdatePassword_PasswordMismatch() {
        // Arrange
        String username = "testUser";
        String currentPassword = "currentPassword";
        String wrongPassword = "wrongPassword";

        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        updatePasswordDTO.setUsername(username);
        updatePasswordDTO.setPassword(wrongPassword);
        updatePasswordDTO.setNewPassword("newPassword");

        Users existingUser = new Users();
        existingUser.setUsername(username);
        existingUser.setPassword(bcrypt.encode(currentPassword));

        when(usersRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> userService.updatePassword(updatePasswordDTO));
        assertEquals("Password mismatch", exception.getMessage());
        verify(usersRepository, times(1)).findByUsername(username);
        verify(usersRepository, never()).save(any());
    }
}
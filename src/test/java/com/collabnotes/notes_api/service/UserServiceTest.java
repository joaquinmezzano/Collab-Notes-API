package com.collabnotes.notes_api.service;

import com.collabnotes.notes_api.model.User;
import com.collabnotes.notes_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser_Success() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void testGetUserById_Found() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(1L);

        assertFalse(result.isPresent());
    }
}

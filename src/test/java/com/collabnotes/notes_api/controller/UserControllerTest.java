package com.collabnotes.notes_api.controller;

import com.collabnotes.notes_api.model.User;
import com.collabnotes.notes_api.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void testGetUser_Success() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testGetUser_NotFound() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllUsers() throws Exception {
        User u1 = new User();
        u1.setEmail("a@a.com");
        User u2 = new User();
        u2.setEmail("b@b.com");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(u1, u2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testCreateUser_Success() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");

        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testCreateUser_EmailExists() throws Exception {
        when(userService.createUser(any(User.class))).thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"duplicate@example.com\"}"))
                .andExpect(status().isBadRequest());
    }
}
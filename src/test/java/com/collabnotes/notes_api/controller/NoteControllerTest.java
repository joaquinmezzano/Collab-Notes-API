package com.collabnotes.notes_api.controller;

import com.collabnotes.notes_api.model.Note;
import com.collabnotes.notes_api.service.NoteService;
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

@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    @Test
    void testGetNote_Success() throws Exception {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Test Note");

        when(noteService.getNoteById(1L)).thenReturn(Optional.of(note));

        mockMvc.perform(get("/notes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Note"));
    }

    @Test
    void testGetNote_NotFound() throws Exception {
        when(noteService.getNoteById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/notes/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllNotes() throws Exception {
        Note n1 = new Note();
        n1.setTitle("Note 1");
        Note n2 = new Note();
        n2.setTitle("Note 2");

        when(noteService.getAllNotes()).thenReturn(Arrays.asList(n1, n2));

        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testCreateNote_Success() throws Exception {
        Note note = new Note();
        note.setTitle("Test Note");

        when(noteService.createNote(any(Note.class))).thenReturn(note);

        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Note\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Note"));
    }

    @Test
    void testCreateNote_TitleExists() throws Exception {
        when(noteService.createNote(any(Note.class))).thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Duplicate\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateNote_Success() throws Exception {
        Note updated = new Note();
        updated.setTitle("Updated");

        when(noteService.updateNote(eq(1L), any(Note.class))).thenReturn(updated);

        mockMvc.perform(put("/notes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated"));
    }

    @Test
    void testUpdateNote_NotFound() throws Exception {
        when(noteService.updateNote(eq(1L), any(Note.class))).thenThrow(new IllegalArgumentException());

        mockMvc.perform(put("/notes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteNote_Success() throws Exception {
        doNothing().when(noteService).deleteNote(1L);

        mockMvc.perform(delete("/notes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteNote_NotFound() throws Exception {
        doThrow(new IllegalArgumentException()).when(noteService).deleteNote(1L);

        mockMvc.perform(delete("/notes/1"))
                .andExpect(status().isNotFound());
    }
}
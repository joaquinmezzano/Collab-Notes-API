package com.collabnotes.notes_api.service;

import com.collabnotes.notes_api.model.Note;
import com.collabnotes.notes_api.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    void testCreateNote_Success() {
        Note note = new Note();
        note.setTitle("Test Note");

        when(noteRepository.existsByTitle(note.getTitle())).thenReturn(false);
        when(noteRepository.save(note)).thenReturn(note);

        Note result = noteService.createNote(note);

        assertEquals("Test Note", result.getTitle());
        verify(noteRepository).save(note);
    }

    @Test
    void testCreateNote_TitleAlreadyExists() {
        Note note = new Note();
        note.setTitle("Test Note");

        when(noteRepository.existsByTitle(note.getTitle())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> noteService.createNote(note));
    }

    @Test
    void testGetAllNotes() {
        List<Note> notes = Arrays.asList(new Note(), new Note());
        when(noteRepository.findAll()).thenReturn(notes);

        List<Note> result = noteService.getAllNotes();

        assertEquals(2, result.size());
    }

    @Test
    void testGetNoteById_Found() {
        Note note = new Note();
        note.setId(1L);

        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        Optional<Note> result = noteService.getNoteById(1L);

        assertTrue(result.isPresent());
    }

    @Test
    void testGetNoteById_NotFound() {
        when(noteRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Note> result = noteService.getNoteById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateNote_Success() {
        Note oldNote = new Note();
        oldNote.setId(1L);
        oldNote.setTitle("Old");

        Note newNote = new Note();
        newNote.setTitle("New");

        when(noteRepository.findById(1L)).thenReturn(Optional.of(oldNote));
        when(noteRepository.save(any(Note.class))).thenReturn(newNote);

        Note result = noteService.updateNote(1L, newNote);

        assertEquals("New", result.getTitle());
    }

    @Test
    void testUpdateNote_NotFound() {
        when(noteRepository.findById(1L)).thenReturn(Optional.empty());

        Note updated = new Note();
        updated.setTitle("New");

        assertThrows(IllegalArgumentException.class, () -> noteService.updateNote(1L, updated));
    }

    @Test
    void testDeleteNote_Success() {
        when(noteRepository.existsById(1L)).thenReturn(true);

        noteService.deleteNote(1L);

        verify(noteRepository).deleteById(1L);
    }

    @Test
    void testDeleteNote_NotFound() {
        when(noteRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> noteService.deleteNote(1L));
    }
}
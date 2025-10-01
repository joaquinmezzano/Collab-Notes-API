package com.collabnotes.notes_api.service;

import com.collabnotes.notes_api.model.Note;
import com.collabnotes.notes_api.repository.NoteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note createNote(Note note) {
        if (noteRepository.existsByTitle(note.getTitle())) {
            throw new IllegalArgumentException("Note with this title already exists.");
        }

        return noteRepository.save(note);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    public Note updateNote(Long id, Note updatedNote) {
        return noteRepository.findById(id)
                .map(note -> {
                    note.setTitle(updatedNote.getTitle());
                    note.setContent(updatedNote.getContent());
                    note.setCreation_date(updatedNote.getCreation_date());
                    return noteRepository.save(note);
                })
                .orElseThrow(() -> new IllegalArgumentException("Note not found"));
    }

    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new IllegalArgumentException(("Note not found."));
        }

        noteRepository.deleteById(id);
    }
}

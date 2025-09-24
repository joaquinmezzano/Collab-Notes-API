package com.collabnotes.notes_api.controller;

import com.collabnotes.notes_api.model.Note;
import com.collabnotes.notes_api.repository.NoteRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    // POST /notes -> Create notes
    @PostMapping
    public ResponseEntity<Note> createNote(@Valid @RequestBody Note note) {
        if (noteRepository.existsByTitle(note.getTitle())) {
            return ResponseEntity.badRequest().build();
        }

        Note saved = noteRepository.save(note);
        return ResponseEntity.ok(saved);
    }

    // GET /notes -> Get all notes
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok(noteRepository.findAll());
    }

    // GET /notes/{id} -> Get the note from the id
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(@PathVariable Long id) {
        Optional<Note> note = noteRepository.findById(id);

        if (note.isPresent()) {
            return ResponseEntity.ok(note.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /notes/{id} -> Updates the note from the id
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @Valid @RequestBody Note updatedNote) {
        Optional<Note> oldNote = noteRepository.findById(id);

        if (!oldNote.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Note note = oldNote.get();
        note.setTitle(updatedNote.getTitle());
        note.setContent(updatedNote.getContent());
        note.setCreation_date(updatedNote.getCreation_date());

        Note saved = noteRepository.save(note);
        return ResponseEntity.ok(saved);
    }

    // DELETE /notes/{id} -> Delete the note from the id
    @DeleteMapping("/{id}")
    public ResponseEntity<Note> deleteNote(@PathVariable Long id) {
        Optional<Note> obsoleteNote = noteRepository.findById(id);

        if (!obsoleteNote.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        noteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

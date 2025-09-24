package com.collabnotes.notes_api.repository;

import com.collabnotes.notes_api.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    boolean existsByTitle(String title);
}

package org.example.storage;

import org.example.model.NotePlain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<NotePlain, Long> {
    //all persistence methods will be handled by spring

    NotePlain findNoteByNoteId(Long noteId);
    //springs parses the method name to figure out what to do
}

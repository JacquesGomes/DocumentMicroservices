package org.example.storage;

import org.example.model.NoteDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteDocumentRepository extends JpaRepository<NoteDocument,
        Long> {

}

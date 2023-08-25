package org.example.storage;

import org.example.model.NotePlain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<NotePlain, Long> {
    //all persistence methods will be handled by spring

    NotePlain findNotePlainByNoteId(Long noteId);
    //springs parses the method name to figure out what to do
    //this is called query builder
    //operatoos can be concatenated and you can combine property expressions
    // with and/or. Also with 'Between, LessThan, GreaterThen, Like'.

    NotePlain[] findNotesByHeader(String header);
    NotePlain[] findNotesByHeaderAndBody(String header, String body);

}

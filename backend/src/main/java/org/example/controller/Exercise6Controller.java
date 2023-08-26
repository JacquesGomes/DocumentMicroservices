package org.example.controller;

import org.example.model.NoteDocument;
import org.example.model.NoteDocumentRefBinder;
import org.example.model.NoteException;
import org.example.model.NotePlain;
import org.example.storage.NoteDocumentRepository;
import org.example.storage.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
public class Exercise6Controller {

    private static final Logger log =
            LoggerFactory.getLogger(Exercise4Controller.class);

    @ExceptionHandler
    public ResponseEntity<String> handle(ResponseStatusException rse) {
        return new ResponseEntity<String>(rse.getMessage(), rse.getStatusCode());
    }


    @Autowired
    NoteRepository noteRepository = null;
    @Autowired
    NoteDocumentRepository noteDocRepository = null;

    @PostMapping("exercise6/noteDocReferences")
    public void storeNoteWithDocumentReferences(@RequestBody NoteDocumentRefBinder noteBinder){
        ArrayList<String> docIds = null;
        NotePlain note = null;
        NotePlain persistentNote = null;
        try{
            note = noteBinder.getNote();
            persistentNote =
                    noteRepository.findNotePlainByNoteId(note.getNoteId());
            if(persistentNote != null){
                throw new NoteException("The note did not exist, noteId: " + note.getNoteId());
            }

            docIds = noteBinder.getDocIds();
            for(int i = 0; i < docIds.size(); i++){
                log.info("Exercise 6, saved note id and doc id: " + note.getNoteId() + " / " + docIds.get(i));
                noteDocRepository.save(new NoteDocument(note.getNoteId(),
                        docIds.get(i)));
            }

        } catch (Exception e){
            log.error("Exercise 6, an error ocurred saving document reference" +
                    " to a note");
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Exercise 6, an error ocurred saving document reference" +
                            " to a note");

        }
    }

}

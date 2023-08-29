package org.example.controller;

import org.example.MicroClient;
import org.example.model.NoteDocument;
import org.example.model.NoteDocumentRefBinder;
import org.example.model.NoteException;
import org.example.model.NotePlain;
import org.example.service.NoteService;
import org.example.storage.NoteDocumentRepository;
import org.example.storage.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@CrossOrigin(origins = "*")
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

    @Autowired
    NoteService noteService = null;

    @PostMapping("exercise6/NoteDocReferences")
    public void storeNoteWithDocumentReferences(@RequestBody NoteDocumentRefBinder noteBinder){
        ArrayList<String> docIds = null;
        NotePlain note = null;
        NotePlain persistentNote = null;

        docIds = noteBinder.getDocIds();

        String urlTeste = noteService.buildDocumentExistenceURL(docIds);

        MicroClient client = null;
        client = new MicroClient();
        client.init();

        String TestResult = client.getDataFromEndpoint(urlTeste);

        log.warn("TestResult");
        if(TestResult.equals("false")){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "One of the documents doesnt exists on the database");
        }

        try{
            note = noteBinder.getNote();
            persistentNote =
                    noteRepository.findNotePlainByNoteId(note.getNoteId());
            if(persistentNote == null || persistentNote.getNoteId().equals("")){
                noteRepository.save(note);
            }

            //add a validation to check if the docs exists before storing it
            docIds = noteBinder.getDocIds();


            for(int i = 0; i < docIds.size(); i++){
                log.info("Exercise 6, saved note id " + note.getNoteId() + " " +
                                "and doc id: " + docIds.get(i));
                noteDocRepository.save(new NoteDocument(note.getNoteId(),
                        docIds.get(i)));
            }

            noteDocRepository.flush();

        } catch (Exception e){
            log.error("Exercise 6, an error ocurred saving document reference" +
                    " to a note", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Exercise 6, an error ocurred saving document reference" +
                            " to a note " + e.getMessage());

        }


    }

    @GetMapping("exercise6/NoteDocReferences/{noteId}")
    public NoteDocumentRefBinder getNoteWithDocumentReferentes(@PathVariable Long noteId){
        NoteDocumentRefBinder noteBinder = null;
        NotePlain note = null;
        NoteDocument[] noteDocuments = null;
        String docId = "";
        try{
            noteBinder = new NoteDocumentRefBinder();

            note = noteRepository.findNotePlainByNoteId(noteId);

            if(note == null || note.getNoteId().equals("")){
                throw new NoteException("The note did not exist in the " +
                        "database note id: " + noteId);
            }

            noteBinder.setNote(note);

            noteDocuments =
                    noteDocRepository.findAllNoteDocumentsBynoteId(noteId);

            for(int i = 0 ; i < noteDocuments.length; i++){
                docId = noteDocuments[i].getDocId();
                log.info("adding a document reference to the binder, docId: "+ docId);
                noteBinder.addDocumentReference(noteDocuments[i].getDocId());

            }

        } catch (Exception e){
            log.error("An error ocurred retrieving the note and note " +
                    "references for note id: " + noteId, e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "An error ocurred retrieving the note and note " +
                    "references for note id: " + noteId);

        }

        return noteBinder;

    }

    @GetMapping("NotesDocumentos/{noteId}")
    public void GetNoteAndDocuments(@PathVariable Long noteId){

        NotePlain note = null;
        NoteDocument[] noteDocuments = null;

        String docId = "";
        MicroClient client = null;

        String docResult = "";


        try{
            note = noteRepository.findNotePlainByNoteId(noteId);
            if(note == null || note.getNoteId().equals("")){
                throw new NoteException("The note did not exist in the " +
                        "database note id: " + noteId);
            }

            client = new MicroClient();
            client.init();

            noteDocuments =
                    noteDocRepository.findAllNoteDocumentsBynoteId(noteId);

            for(int i = 0; i < noteDocuments.length; i++){
                docId = noteDocuments[i].getDocId();
                log.info("Retrieved, doc id: " + docId);
                try {
                    docResult = client.getDataFromEndpoint("http://localhost:9000" + "/document/" + docId);
                    log.info("Result from the Doc Service: " + docResult);
                } catch (WebClientResponseException ex) {
                    log.error("HTTP Error: " + ex.getRawStatusCode() + ", Response Body: " + ex.getResponseBodyAsString(), ex);
                    throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                            "HTTP Error: " + ex.getRawStatusCode());
                } catch (Exception e) {
                    log.error("Error making GET request", e);
                    throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                            "Error making GET request");
                }

            }

        } catch (Exception e){
            log.error("Error retrieving the note or doc info", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Error retrieving the note or doc info");

        }
    }


}

package org.example.controller;

import jakarta.annotation.PostConstruct;
import org.example.model.NoteProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.management.BadStringOperationException;
import java.util.concurrent.ExecutionException;

@RestController
public class Log3Controller {
    private static final Logger log =
            LoggerFactory.getLogger(Log3Controller.class);

    private NoteProperties noteProperties = null;

    @Autowired
    protected Environment environment = null;

    @PostConstruct
    protected void init(){
        Float noteSize = null;

        try{
            noteSize = Float.valueOf(environment.getProperty("org.example" +
                    ".log3.note.size"));
            log.info("Log 3, Post Construct note size value: " + noteSize);
            this.noteProperties = new NoteProperties(noteSize);


        } catch(Exception e){
            log.error("Error initializing note properties", e);
        }
    }

    @ExceptionHandler // custom exception handler
    public ResponseEntity<String> handle(ResponseStatusException rse){
        return new ResponseEntity<String>(rse.getMessage() + ". Not a real " +
                "error dude",
                rse.getStatusCode());
    }

    @GetMapping("log3/note/properties")
    public NoteProperties getNoteProperties(){
        log.info("Log 3, get received, return note properties");
        return noteProperties;
    }

    @GetMapping("log3/note")
    public void returnErrorMessage(){
        try{
            log.info("Log 3 return an error message");
            throw new BadStringOperationException("Used to simulate an error");
        } catch (Exception e){
            log.error("Exception ocurred in log 3 return error message", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Log 3, Simulated error message");
        }
    }
}

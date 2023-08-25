package org.example.controller;

import org.example.model.NotePlain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class Log2Controller {

    private static final Logger log =
            LoggerFactory.getLogger(Log2Controller.class);

    @PostMapping("log2/note")
    public void logPOSTnote(@RequestBody NotePlain note){
        log.info("Log 2, post mapping received, note: " + note + note.retrieveNoteInfo());
    }

    @PutMapping("log2/note")
    public void logPUTnote(@RequestBody NotePlain note){
        log.info("Log 2, PUT mapping received, " + note.retrieveNoteInfo());
    }

    @DeleteMapping("log2/note/{id}")
    public void logDELETEnote(@PathVariable Integer id){
        log.info("Log 2, Delete received, note id: " + id);
    }

}

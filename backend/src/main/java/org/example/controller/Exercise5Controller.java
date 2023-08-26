package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.model.NotePlain;
import org.hibernate.type.descriptor.java.ObjectJavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Exercise5Controller {

    private static final Logger log =
            LoggerFactory.getLogger(Exercise4Controller.class);

    @PostMapping("exercise5/note/string")
    public void writeStringNoteToConsole(@RequestBody String payload){
        log.info("Exercise 5, GET mapping received, payload: " + payload);

    }

    @PostMapping("exercise5/serialize")
    public void writeNoteSerializedToConsole(@RequestBody NotePlain note){
        ObjectMapper mapper = new ObjectMapper();
        String noteString = "";
        try{
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            noteString = mapper.writeValueAsString(note);
            log.info("Exercise 5, GET mapping received, serialized value: " + noteString);
        } catch (Exception e){
            log.error("Exercise 5, an error occurred transforming the Note " +
                    "using ObjectMapper", e);
        }
    }

    @PostMapping("exercise5/note/deserialize")
    public void writeDeserializedStringConsole(@RequestBody String payload){
        ObjectMapper mapper = new ObjectMapper();
        NotePlain deserializedNote = null;
        log.info("Exercise 5, GET mapping received, payload: " + payload);
        try{
            deserializedNote = mapper.readValue(payload, NotePlain.class);
            log.info("Note information: " + deserializedNote.retrieveNoteInfo());
        } catch(Exception e){

        }
    }



}

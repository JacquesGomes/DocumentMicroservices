package org.example;

import org.example.model.NotePlain;
import org.example.storage.NoteRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class ExerciseControllerTest {

    private static final Logger log =
            LoggerFactory.getLogger(ExerciseControllerTest.class);

    @Autowired
    private NoteRepository noteRepository = null;

    private NotePlain getTestNotePlain(){
        return new NotePlain(TestNotePlain.ID,
                TestNotePlain.NOTE_ID, TestNotePlain.HEADER,
                TestNotePlain.BODY, TestNotePlain.COMMENT);
    }

    @Test
    public void testNotePlain(){
        NotePlain note = null;
        log.info(() -> "Testando Note Class");
        note = getTestNotePlain();
        assert (TestNotePlain.ID).equals(note.getId());
        assert (TestNotePlain.HEADER).equals(note.getHeader());
        log.info(() -> "Teste completo");
    }

}

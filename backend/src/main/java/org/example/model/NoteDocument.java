package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name="NOTE_DOCUMENT")
public class NoteDocument {

    @GeneratedValue(strategy = GenerationType.TABLE)
    @Id
    private Long id = null;

    @Column(name="NOTE_ID")
    private Long noteId = null;

    @Column(name = "DOC_ID")
    private String docId = "";

    public NoteDocument() {
    }

    public NoteDocument(Long noteId, String docId) {

        this.noteId = noteId;
        this.docId = docId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}

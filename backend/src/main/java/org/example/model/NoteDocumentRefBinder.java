package org.example.model;

import java.util.ArrayList;

public class NoteDocumentRefBinder {

    private NotePlain note = null;
    private ArrayList<String> docIds = null;

    public NoteDocumentRefBinder() {
    }

    public NoteDocumentRefBinder(NotePlain note, ArrayList<String> docIds) {
        this.note = note;
        this.docIds = docIds;
    }

    public NotePlain getNote() {
        return note;
    }

    public void setNote(NotePlain note) {
        this.note = note;
    }

    public ArrayList<String> getDocIds() {
        return docIds;
    }

    public void setDocIds(ArrayList<String> docIds) {
        this.docIds = docIds;
    }

    public void addDocumentReference(String docRef){
        this.docIds.add(docRef);
    }

}

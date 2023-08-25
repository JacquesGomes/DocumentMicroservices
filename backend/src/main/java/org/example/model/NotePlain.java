package org.example.model;


import jakarta.persistence.*;

@Entity
@Table(name="NOTE") // if not set, it would use the class name
public class NotePlain {

    @GeneratedValue(strategy = GenerationType.TABLE)
    @Id
    @Column(name="PK_ID")
    private Long id;

    //if column name was not set, it would use the attribute name and default
    // configs
    @Column(name="NOTE_ID", nullable = false, unique = true, length = 256)
    private Long noteId;

    @Column(name="HEADER")
    private String header = "";

    @Column(name="BODY")
    private String body = "";

    @Column(name="COMMENT")
    private String comment = "";

    public NotePlain(){
    }

    public NotePlain(Long noteId, String header, String body, String comment) {
        this.noteId = noteId;
        this.header = header;
        this.body = body;
        this.comment = comment;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getComment() {
        return comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    //if it has 'get' on the title, spring will retrieve along with the
    // attributes
    //To avoid that, we only need to rename the method

    //old method "public String getNoteInfo() -> it has coming along with the
    // database fields in the GetMapping
    public String retrieveNoteInfo() {
        return "NotePlain:" +
                "id=" + id +
                ", noteId=" + noteId +
                ", header='" + header +
                ", body='" + body  +
                ", comment='" + comment;
    }
}

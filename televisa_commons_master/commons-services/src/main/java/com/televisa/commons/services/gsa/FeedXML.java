package com.televisa.commons.services.gsa;

import com.televisa.commons.services.datamodel.Note;

public class FeedXML {

    private Note note;
    private String action;

    private Note[] notes;
    public FeedXML(Note note, String action) {
        this.note   = note;
        this.action = action;
    }

    public FeedXML(Note[] notes, String action) {
        this.notes   = notes;
        this.action  = action;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Note[] getNotes() {
        return notes;
    }

    public void setNotes(Note[] notes) {
        this.notes = notes;
    }

    protected String getGSAMultipleXMLAction() {
        return "";
    }

}

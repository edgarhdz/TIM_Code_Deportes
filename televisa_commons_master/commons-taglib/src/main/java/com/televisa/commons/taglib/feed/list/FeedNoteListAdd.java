/*
 * FeedNoteListAdd.java
 *
 * Feed note list provider append.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib.feed.list;

import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import com.televisa.commons.services.datamodel.Note;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Named Feed Provider Tag
 *
 * Feed note list provider append.
 *
 * Changes History:
 *
 *         2013-03-05 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class FeedNoteListAdd extends AbstractFeedNoteList {

    private String name;
    private Note note;

    @Override
    public void doTag() throws JspException, IOException {
        if (getRequestAttribute(name) instanceof FeedNoteList) {
            FeedNoteList notes = ((FeedNoteList) getRequestAttribute(name));
            if (notes != null && note != null) {
                boolean duplicatedNote = false;
                for(Note noteElement : notes){
                    if(noteElement.getUrl().equals(note.getUrl())){
                        duplicatedNote = true;
                        break;
                    }
                }
                if(!duplicatedNote){
                    notes.add(note);
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setName(String name) {
        this.name = name;
    }

    public Note getNote() {
        return note;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setNote(Note note) {
        this.note = note;
    }

}

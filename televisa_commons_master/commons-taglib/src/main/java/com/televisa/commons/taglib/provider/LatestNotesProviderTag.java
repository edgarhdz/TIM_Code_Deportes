/*
 * LatestNotesProviderTag.java
 *
 * Get the eight latest list of notes from a channel.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib.provider;

import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.services.NoteManagerServiceFactory;
import com.televisa.commons.services.services.NoteManagerService;
import com.televisa.commons.taglib.utilities.ListNoteDecorator;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Latest Notes Provider Tag
 *
 * Get the eight latest list of notes from a channel.
 *
 * Changes History:
 *
 *         2013-02-22 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class LatestNotesProviderTag extends AbstractNoteProvider {

    private String name = "notes";
    private String channel;
    private int limit;

    protected ListNoteDecorator<Note> getNotes() {
        NoteManagerServiceFactory factory = null;
        factory = getService(NoteManagerServiceFactory.class);
        if (factory != null) {
            NoteManagerService service = null;
            service = factory.getService(NoteManagerService.class);
            if (service != null) {
                return new ListNoteDecorator<Note>(service.getLatestNotes(channel, limit));
            }
        }
        return null;
    }

    @Override
    public void doTag() throws JspException, IOException {
        removeRequestAttribute(name);
        ListNoteDecorator<Note> notes = getNotes();
        if (notes != null) {
            setRequestAttribute(name, notes);
        }
    }

    public String getName() {
        return name;
    }

    @JspTagAttribute(required = false, rtexprvalue = true)
    public void setName(String name) {
        this.name = name;
    }

    public String getChannel() {
        return channel;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getLimit() {
        return limit;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setLimit(int limit) {
        this.limit = limit;
    }

}

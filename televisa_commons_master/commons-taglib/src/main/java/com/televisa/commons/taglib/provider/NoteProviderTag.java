/*
 * NoteProviderTag.java
 *
 * Assign a note the the page context.
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

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Note Provider Tag
 *
 * Assign a note the the page context.
 *
 * Changes History:
 *
 *         2013-02-07 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class NoteProviderTag extends AbstractNoteProvider {

    private String path;

    public void doTag() throws JspException, IOException {
        removeRequestAttribute("note");
        Note note = getNote(getPath());
        if (note != null) {
            setRequestAttribute("note", note);

        }
    }

    public String getPath() {
        return path;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setPath(String path) {
        this.path = path;
    }

}

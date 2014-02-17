/*
 * GetCurrentNoteInformationTag.java
 *
 * Get the information from the current page as a note.
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
 * Get Current Note Information
 *
 * Get the information from the current page as a note.
 *
 * Changes History:
 *
 *         2013-02-25 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class CurrentNoteInformationTag extends AbstractNoteProvider {

    private String name;

    @Override
    public void doTag() throws JspException, IOException {
        removeRequestAttribute(name);
        Note note = getNote(getCurrentPage().getPath());
        if (note != null) {
            setRequestAttribute(name, note);
        }
    }

    public String getName() {
        return name;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setName(String name) {
        this.name = name;
    }

}

/*
 * NamedNoteProviderTag.java
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
 * Named Note Provider Tag
 *
 * Assign a named note the the page context.
 *
 * Changes History:
 *
 *         2013-02-18 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class NamedNoteProviderTag extends AbstractNoteProvider {

    private String name;
    private String path;

    @Override
    public void doTag() throws JspException, IOException {
        removeRequestAttribute(name);
        Note note = getNote(path);
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

    public String getPath() {
        return path;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setPath(String path) {
        this.path = path;
    }

}

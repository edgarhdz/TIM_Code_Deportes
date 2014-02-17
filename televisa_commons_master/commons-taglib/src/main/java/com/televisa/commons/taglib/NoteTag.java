/*
 * NoteTag.java
 *
 * Display the title of a generic note.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import com.televisa.commons.services.services.NoteManagerService;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Generic Note Tag
 *
 * Display the title of a generic note.
 *
 * Changes History:
 *
 *         2013-01-29 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class NoteTag extends CqSimpleTagSupport {

    private String path;

    @Override
    public void doTag() throws JspException, IOException {
        NoteManagerService service = getService(NoteManagerService.class);
        getJspWriter().write(service.getNote(path).getTitle());
    }

    public String getPath() {
        return path;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setPath(String path) {
        this.path = path;
    }

}

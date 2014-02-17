/*
 * NoteRemoverTag.java
 *
 * Remove a note the the page context.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib.provider;

import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Note Remover Tag
 *
 * Remove a note from the page context.
 *
 * Changes History:
 *
 *         2013-02-21 gescobar Added the name parameter
 *         2013-02-20 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class NoteRemoverTag extends AbstractNoteProvider {

    private String name;

    @Override
    public void doTag() throws JspException, IOException {
        removeRequestAttribute(name);
    }

    public String getName() {
        return name;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setName(String name) {
        this.name = name;
    }

}

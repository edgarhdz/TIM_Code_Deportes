/*
 * FeedNoteListAfter.java
 *
 * Feed note list provider finalize.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib.feed.list;

import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Named Feed Provider Tag
 *
 * Feed note list provider finalize.
 *
 * Changes History:
 *
 *         2013-03-05 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class FeedNoteListAfter extends AbstractFeedNoteList {

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

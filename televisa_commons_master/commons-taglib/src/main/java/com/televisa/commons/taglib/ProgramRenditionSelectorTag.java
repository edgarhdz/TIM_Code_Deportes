/*
 * ProgramRenditionSelectorTag.java
 *
 * Select a rendition from the note if exist.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.squeakysand.jsp.AttributeScope;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.datamodel.objects.Rendition;
import com.televisa.commons.services.datamodel.Program;
import com.televisa.commons.services.datamodel.objects.ImageAsset;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Program Rendition Selector Tag
 *
 * Select a rendition from the program note if exist.
 *
 * Changes History:
 *
 *         2013-02-12 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class ProgramRenditionSelectorTag extends CqSimpleTagSupport {

    private Integer width;
    private Integer height;

    @Override
    public void doTag() throws JspException, IOException {
        Note note = (Note) getAttribute("note", AttributeScope.REQUEST);
        if (note != null && note.isProgram()) {
            Program program = (Program) note;
            ImageAsset image = program.getProgramImage();
            if (image != null) {
                Rendition rendition = image.getRendition(width, height);
                if (rendition == null) {
                    getJspWriter().write(image.getPath());
                } else {
                    getJspWriter().write(rendition.getPath());
                }
            }
        }
    }

    public Integer getWidth() {
        return width;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setHeight(Integer height) {
        this.height = height;
    }

}

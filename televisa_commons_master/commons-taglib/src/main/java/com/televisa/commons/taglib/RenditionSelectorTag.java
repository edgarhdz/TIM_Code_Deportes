/*
 * RenditionSelectorTag.java
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
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import com.televisa.commons.services.datamodel.objects.ImageAsset;
import com.televisa.commons.services.datamodel.objects.Rendition;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Rendition Selector Tag
 *
 * Select a rendition from the note if exist.
 *
 * Changes History:
 *
 *         2013-02-12 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class RenditionSelectorTag extends CqSimpleTagSupport {

    private ImageAsset image;
    private Integer width;
    private Integer height;

    @Override
    public void doTag() throws JspException, IOException {
        if (image != null) {
            Rendition rendition = image.getRendition(width, height);
            if (rendition == null) {
                getJspWriter().write(image.getPath());
            } else {
                getJspWriter().write(rendition.getPath());
            }
        }
    }

    public ImageAsset getImage() {
        return image;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setImage(ImageAsset image) {
        this.image = image;
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

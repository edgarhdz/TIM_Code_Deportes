/*
 * GetAssetPathBySizeTag.java
 *
 * Get the path of an asset by its size.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib.asset;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import com.televisa.commons.services.utilities.Utilities;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Get Asset Path By Size
 *
 * Get the path of an asset by its size.
 *
 * Changes History:
 *
 *         2013-02-27 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class GetAssetPathBySizeTag extends AbstractAssetResolver {

    private String path;
    private int width;
    private int height;

    @Override
    public void doTag() throws JspException, IOException {
        Asset asset = getAsset(path);
        if (asset != null) {
            Rendition rendition = new Utilities().getRendition(asset, width, height);
            if (rendition != null) {
                getJspWriter().write(rendition.getPath());
            }
        }
    }

    public String getPath() {
        return path;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setPath(String path) {
        this.path = path;
    }

    public int getWidth() {
        return width;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setHeight(int height) {
        this.height = height;
    }

}

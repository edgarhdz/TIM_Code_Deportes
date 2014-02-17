/*
 * GetAssetPathByNameTag.java
 *
 * Get the path of an asset by its name.
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

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Get Asset Path By Name
 *
 * Get the path of an asset by its name.
 *
 * Changes History:
 *
 *         2013-02-27 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class GetAssetPathByNameTag extends AbstractAssetResolver {

    private String path;
    private String name;

    @Override
    public void doTag() throws JspException, IOException {
        Asset asset = getAsset(path);
        if (asset != null) {
            Rendition rendition = asset.getRendition(name);
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

    public String getName() {
        return name;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setName(String name) {
        this.name = name;
    }

}

/*
 * MediaContentAssetListTag.java
 *
 * Returns a rendition iterator.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib.feed.asset;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import com.televisa.commons.services.datamodel.objects.Asset;
import com.televisa.commons.services.datamodel.objects.Rendition;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.util.List;

/**
 * Named Feed Provider Tag
 *
 * Returns a rendition iterator.
 *
 * Changes History:
 *
 *         2013-03-05 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class MediaContentAssetListTag extends CqSimpleTagSupport {

    private String name;
    private Asset asset;

    @Override
    public void doTag() throws JspException, IOException {
        if (asset != null) {
            List<Rendition> renditions = asset.getRenditions();
            if (renditions != null) {
                setPageAttribute(name, renditions);
            }
        }
    }

    public String getName() {
        return name;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setName(String name) {
        this.name = name;
    }

    public Asset getAsset() {
        return asset;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setAsset(Asset asset) {
        this.asset = asset;
    }

}

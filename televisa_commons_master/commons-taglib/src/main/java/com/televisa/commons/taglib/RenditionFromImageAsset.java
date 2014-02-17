/*
 * RenditionFromImageAsset.java
 *
 * Get a specific rendition from a image asset.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import com.televisa.commons.services.utilities.Utilities;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Rendition From Image Asset
 *
 * Get a specific rendition from a image asset.
 *
 * Changes History:
 *
 *         2013-02-21 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class RenditionFromImageAsset extends CqSimpleTagSupport {

    private static final Logger LOG = LoggerFactory.getLogger(RenditionFromImageAsset.class);

    private String name = "fileReference";
    private Integer width;
    private Integer height;

    /**
     * Get an Asset Rendition with de provided size.
     *
     * @throws javax.servlet.jsp.JspException if an JSP exception occurs
     * @throws java.io.IOException if an IO exception occurs
     */
    @Override
    public void doTag() throws JspException, IOException {
        try {
            Node node = getCurrentNode();
            if (node != null && node.hasProperty(name)) {
                Resource resource = getResourceResolver().resolve(node.getProperty(name).getString());
                if (resource != null && "dam:Asset".equalsIgnoreCase(resource.getResourceType())) {
                    Asset asset = resource.adaptTo(Asset.class);
                    if (asset != null) {
                        Rendition rendition = new Utilities().getRendition(asset, width, height);
                        if (rendition != null) {
                            getJspWriter().write(rendition.getPath());
                        }
                    }
                }
            }
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public String getName() {
        return name;
    }

    @JspTagAttribute(required = false, rtexprvalue = true)
    public void setName(String name) {
        this.name = name;
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

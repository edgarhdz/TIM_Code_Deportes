/*
 * FeedProviderTag.java
 *
 * Return a feed from a note.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib.feed;

import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import com.televisa.commons.services.dataaccess.TemplateType;
import com.televisa.commons.services.datamodel.Note;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.util.Map;

/**
 * Named Feed Provider Tag
 *
 * Return a feed from a note.
 *
 * Changes History:
 *
 *         2013-03-11 gescobar Feed service video and image implementation
 *         2013-02-27 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class FeedProviderTag extends AbstractFeedProvider {

    private String serviceType;
    private String type;
    private String rendering;
    private Note note;
    private Map<String, Object> additional;

    @Override
    public void doTag() throws JspException, IOException {
        Class<? extends Note> noteType = null;
        for (TemplateType item : TemplateType.values()) {
            if (item.getTemplateName().equalsIgnoreCase(type)) {
                noteType = item.getClazz();
                break;
            }
        }
        if (noteType != null) {
            String feed = getFeed(serviceType, noteType, rendering, note, additional);
            if (feed != null) {
                getJspWriter().write(feed);
            }
        }
    }

    public String getServiceType() {
        return serviceType;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getType() {
        return type;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setType(String type) {
        this.type = type;
    }

    public String getRendering() {
        return rendering;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setRendering(String rendering) {
        this.rendering = rendering;
    }

    public Note getNote() {
        return note;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setNote(Note note) {
        this.note = note;
    }

    public Map<String, Object> getAdditional() {
        return additional;
    }

    @JspTagAttribute(required = false, rtexprvalue = true)
    public void setAdditional(Map<String, Object> additional) {
        this.additional = additional;
    }

}

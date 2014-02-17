/*
 * GetChildRecursiveIterator.java
 *
 * Get all the child resources of a path in a list.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib.core;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import com.televisa.commons.services.dataaccess.TemplateType;
import com.televisa.commons.services.services.NoteManagerService;
import com.televisa.commons.services.services.NoteManagerServiceFactory;
import org.apache.sling.api.resource.Resource;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.util.List;

/**
 * Get Child Recursive Iterator
 *
 * Get all the child resources of a path in a list, which has a specific resource (node).
 *
 * Changes History:
 *
 *         2013-03-08 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class GetVideoResourcesIteratorTag extends CqSimpleTagSupport {

    private String name;
    private String path;
    private String type;

    private NoteManagerService getNoteManagerService() {
        NoteManagerServiceFactory factory = getService(NoteManagerServiceFactory.class);
        if (factory != null) {
            return factory.getService(NoteManagerService.class);
        }
        return null;
    }

    @Override
    public void doTag() throws JspException, IOException {
        removeRequestAttribute(name);
        NoteManagerService service = getNoteManagerService();
        if (service != null) {
            TemplateType templateType = TemplateType.VIDEO;
            List<Resource> list = service.getLatestVideoResources(path, templateType, type);
            if (list != null) {
                setRequestAttribute(name, list);
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

    public String getPath() {
        return path;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setType(String type) {
        this.type = type;
    }



}

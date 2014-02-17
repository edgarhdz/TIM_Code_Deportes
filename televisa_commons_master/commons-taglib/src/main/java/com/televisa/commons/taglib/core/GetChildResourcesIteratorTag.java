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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class GetChildResourcesIteratorTag extends CqSimpleTagSupport {

    private static final Logger LOG = LoggerFactory.getLogger(GetChildResourcesIteratorTag.class);

    private String name;
    private String path;
    private String type;
    private String which;

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
            TemplateType templateType = TemplateType.ARTICLE;
            try {
                templateType = TemplateType.valueOf(type);
            } catch (IllegalArgumentException e) {
                LOG.error(e.getMessage(), e);
            }
            List<Resource> list = service.getLatestChildResources(path, templateType, type);
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

    public String getWhich() {
        return which;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setWhich(String which) {
        this.which = which;
    }

}

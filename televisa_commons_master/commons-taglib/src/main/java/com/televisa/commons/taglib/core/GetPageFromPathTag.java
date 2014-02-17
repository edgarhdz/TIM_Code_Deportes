package com.televisa.commons.taglib.core;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: palecio
 * Date: 15/2/13
 * Time: 09:48
 */
@JspTag
public class GetPageFromPathTag extends CqSimpleTagSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetPageFromPathTag.class);

    private String path;
    private String var;

    @Override
    public void doTag() throws JspException, IOException {
        final String path = getPath();
        if ("".equals(path)) {
            throw new JspException("path is undefined.");
        }

        final String var = getVar();
        if ("".equals(var)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("var is empty.");
            }
            throw new JspException("var is undefined.");
        }
        final ResourceResolver resourceResolver = (ResourceResolver) getJspContext().getAttribute("resourceResolver");
        final PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        final Page page = pageManager.getPage(path);
        getJspContext().setAttribute(var, page);
    }

    /**
     * The variable to set the path to.
     * @param path the variable name to set the path.
     */
    public void setPath(final String path) {
        this.path = path;
    }

    /**
     * The variable to set the object to.
     * @param var the variable name to set the object.
     */
    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setVar(final String var) {
        this.var = var;
    }

    public String getPath() {
        return path;
    }

    public String getVar() {
        return var;
    }
}


package com.televisa.commons.taglib;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;

import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.jsp.PageContext;

import java.io.IOException;

/**
 * Find Component in page
 *
 * Find a component in a page with the name of the component an sets a variable with his path
 *
 * Changes History:
 *
 *         2013-08-13 Initial Development
 *
 * @author jbarrera
 * @version 1.0
 */
@JspTag
public class GetPropertyFromNodeTag extends CqSimpleTagSupport {
    private static final Logger LOG = LoggerFactory.getLogger(GetPropertyFromNodeTag.class);
    private String path;
    private String property;

    @Override
    public void doTag() throws IOException{
        final PageContext pageContext = (PageContext) getJspContext();
        final Resource resource = (Resource) pageContext.getAttribute("resource");
        String value = "";
        try {
            Session session = resource.getResourceResolver().adaptTo(Session.class);
            Node node = session.getNode(path);
            if(node.hasProperty(property)){
                value = node.getProperty(property).getString();
                getJspWriter().write(value);
            }
            //session.logout();
        } catch (RepositoryException e) {
            LOG.info(e.getMessage());
        }
    }

    public String getPath() {
        return path;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setPath(String path) {
        this.path = path;
    }

    public String getProperty() {
        return property;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setProperty(String property) {
        this.property = property;
    }

}

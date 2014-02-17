package com.televisa.commons.taglib.core;

/**
 * Created by IntelliJ IDEA.
 * User: palecio
 * Date: 15/2/13
 * Time: 08:39
 */

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.day.cq.wcm.api.Page;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * This tag returns the value map from a node for easy access in the JSP.
 *
 * @author sajid.momin
 */
@JspTag
public class GetPagePropertyFromCurrentPageTag extends CqSimpleTagSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetPagePropertyFromCurrentPageTag.class);

    private String property;
    private String var;

    @Override
    public void doTag() throws JspException, IOException {
        Page page = this.getCurrentPage();

        if (page == null) {
            throw new JspException("Page is null.");
        }

        final String property = getProperty();
        if ("".equals(property)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("property is empty.");
            }
            throw new JspException("property is undefined.");
        }

        final String var = getVar();
        if ("".equals(var)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("var is empty.");
            }
            throw new JspException("var is undefined.");
        }

        final ValueMap properties = page.getProperties();
        if(properties.containsKey(property)){
            getJspContext().setAttribute(var, properties.get(property));
        }else{
            LOGGER.debug("property desn't exist.");
            throw new JspException("property doesn't exist");
        }

    }

    /**
     * The variable to set the property to.
     * @param property the variable name to set the property.
     */
    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setProperty(final String property) {
        this.property = property;
    }

    /**
     * The variable to set the object to.
     * @param var the variable name to set the object.
     */
    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setVar(final String var) {
        this.var = var;
    }

    public String getProperty() {
        return property;
    }

    public String getVar() {
        return var;
    }
}


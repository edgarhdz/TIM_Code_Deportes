package com.televisa.commons.taglib.core;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.day.cq.wcm.api.Page;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
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
public class GetGrandparentFromCurrentPageTag extends CqSimpleTagSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetGrandparentFromCurrentPageTag.class);

    private String var;

    @Override
    public void doTag() throws JspException, IOException {

        final String var = getVar();
        if ("".equals(var)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("var is empty.");
            }
            throw new JspException("var is undefined.");
        }

        final Page page = this.getCurrentPage();
        final Page grandparent = page.getParent(2);
        getJspContext().setAttribute(var, grandparent);
    }



    /**
     * The variable to set the object to.
     * @param var the variable name to set the object.
     */
    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setVar(final String var) {
        this.var = var;
    }


    public String getVar() {
        return var;
    }
}


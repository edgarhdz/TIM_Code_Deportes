package com.televisa.commons.taglib.core;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
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
 * Date: 25/2/13
 * Time: 16:12
 */
@JspTag
public class GetCQTagFromStringTag extends CqSimpleTagSupport{

    private static final Logger LOGGER = LoggerFactory.getLogger(GetPageFromPathTag.class);

    private String tag;
    private String var;

    @Override
    public void doTag() throws JspException, IOException {
        final String tagString = getTag();
        if ("".equals(tagString)) {
            throw new JspException("tag is undefined.");
        }

        final String var = getVar();
        if ("".equals(var)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("var is empty.");
            }
            throw new JspException("var is undefined.");
        }
        //JcrTagManagerFactory jcrTagManagerFactory = this.getService(JcrTagManagerFactory.class);
        ResourceResolver resourceResolver =  getSlingRequest().getResourceResolver();
        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
        Tag tag = tagManager.resolve(tagString);
        if(tag != null){
            getJspContext().setAttribute(var, tag);
        }


    }

    /**
     * The variable to set the path to.
     * @param tag the variable name to set the path.
     */
    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setTag(final String tag) {
        this.tag = tag;
    }

    /**
     * The variable to set the object to.
     * @param var the variable name to set the object.
     */
    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setVar(final String var) {
        this.var = var;
    }

    public String getTag() {
        return tag;
    }

    public String getVar() {
        return var;
    }
}

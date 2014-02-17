package com.televisa.commons.taglib.core;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import org.apache.sling.api.resource.ResourceResolver;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: xumakgt
 * Date: 8/13/13
 * Time: 7:59 AM
 */
@JspTag
public class GetPageTitleFromPathTag extends CqSimpleTagSupport {

    private String path;
    private PageManager pageManager;
    private Page page;


    @Override
    public void doTag() throws JspException, IOException {
        String path=getPath();
        String pageTitle;
        ResourceResolver resourceResolver = (ResourceResolver) getJspContext().getAttribute("resourceResolver");
        this.pageManager = resourceResolver.adaptTo(PageManager.class);


        if(!path.isEmpty()){
            this.page = pageManager.getPage(path);
            pageTitle = this.page.getTitle();
            if (!pageTitle.isEmpty()){
                getJspWriter().write(pageTitle);
            }
        }
    }


    /**
     * The path where to search for a page and get it's title.
     * @param path the variable name to set the property.
     */
    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setPath(final String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }

}

package com.televisa.commons.taglib.asset;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.day.cq.dam.api.Asset;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: xumakgt
 * Date: 7/15/13
 * Time: 2:12 PM
 */
@JspTag
public class GetImageAssetTag extends CqSimpleTagSupport {

    private static final Logger LOG = LoggerFactory.getLogger(GetImageAssetTag.class);

    private String name = "fileReference";
    private Integer width;
    private Integer height;

    /**
     * Get an Asset Rendition .
     *
     * @throws JspException if an JSP exception occurs
     * @throws IOException if an IO exception occurs
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
                        getJspWriter().write(asset.getPath());
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

    @JspTagAttribute(required = false, rtexprvalue = true)
    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    @JspTagAttribute(required = false, rtexprvalue = true)
    public void setHeight(Integer height) {
        this.height = height;
    }



}

/*
 * SearchByTagUrl.java
 *
 * Creates a URL for the tag page.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;

import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.InvalidQueryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import java.io.IOException;

/**
 * Find Component in page
 *
 * Find a component in a page with the name of the component an sets a variable with his path
 *
 * Changes History:
 *
 *         2013-07-18 Initial Development
 *
 * @author ogiron
 * @version 1.0
 */
@JspTag
public class FindComponentInPageTag extends CqSimpleTagSupport {
    private static final Logger LOG = LoggerFactory.getLogger(FindComponentInPageTag.class);

    private String pagePath;
    private String componentResourceType;
    private String componentPath;

    @Override
    public void doTag() throws JspException, IOException {
        final PageContext pageContext = (PageContext) getJspContext();
        final Resource resource = (Resource) pageContext.getAttribute("resource");
        final Session session = resource.getResourceResolver().adaptTo(Session.class);
        String statement=String.format("SELECT * FROM [nt:base] AS s WHERE ISDESCENDANTNODE([%s]) and s.[sling:resourceType]='%s'", pagePath,componentResourceType);
        try {
            Query query=session.getWorkspace().getQueryManager().createQuery(statement,Query.JCR_SQL2);
            QueryResult queryResult=	query.execute();
            NodeIterator nodeIterator=queryResult.getNodes();
            Node componentNode=null;
            if(nodeIterator.hasNext()){
                componentNode=nodeIterator.nextNode();
                componentPath=componentNode.getPath();
                getJspWriter().write(componentPath);
            }

        } catch (InvalidQueryException e) {
            LOG.error(e.getMessage());
        } catch (RepositoryException e) {
            LOG.error(e.getMessage());
        }

    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    @JspTagAttribute(required=true,rtexprvalue=true)
    public void setComponentResourceType(String componentResourceType){
        this.componentResourceType=componentResourceType;
    }

}

/*
 * ArticleImpl.java
 *
 * The article data model.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel.impl;

import com.day.cq.wcm.api.Page;
import com.televisa.commons.services.datamodel.Article;
import com.televisa.commons.services.datamodel.NotePropertyName;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * Article Management Data Access
 *
 * The article data model.
 *
 * Changes History:
 *
 *         2013-03-14 gescobar Added properties stage
 *         2013-02-04 gescobar Added new properties
 *         2013-01-09 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class ArticleImpl extends NoteImpl implements Article {

    public ArticleImpl(ResourceResolver resourceResolver, Page page) {
        super(resourceResolver, page);
    }

    protected void readProperties() {
        super.readProperties();
        putProperty(NotePropertyName.ARTICLE_IMAGE_LINK_URL, propertiesReader.getArticleImageLinkUrl());
        putProperty(NotePropertyName.ARTICLE_STAGED, propertiesReader.getArticleStage());
    }

    @Override
    public String getArticleImageLinkUrl() {
        return (String) getProperty(NotePropertyName.ARTICLE_IMAGE_LINK_URL);
    }

    @Override
    public void setArticleImageLinkUrl(String articleImageLinkUrl) {
        putProperty(NotePropertyName.ARTICLE_IMAGE_LINK_URL, articleImageLinkUrl);
    }

    @Override
    public String getArticleStage() {
        return (String) getProperty(NotePropertyName.ARTICLE_STAGED);
    }

    @Override
    public void setArticleStage(String articleStage) {
        putProperty(NotePropertyName.ARTICLE_STAGED, articleStage);
    }

}

/*
 * Article.java
 *
 * The article data model.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel;

/**
 * The Article Data Model Interface
 *
 * Changes History:
 *
 *         2013-03-14 gescobar Added properties stage
 *         2013-02-04 gescobar Added properties
 *         2013-01-10 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public interface Article extends Note {

    String getArticleImageLinkUrl();

    void setArticleImageLinkUrl(String articleImageLinkUrl);

    String getArticleStage();

    void setArticleStage(String articleStage);

}

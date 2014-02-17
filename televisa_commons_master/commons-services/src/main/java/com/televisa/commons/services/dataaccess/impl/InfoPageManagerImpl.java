/*
 * InfoPageManagerImpl.java
 *
 * The component and service to perform the information of a pages and the respective pagination management.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.dataaccess.impl;

import com.day.cq.wcm.api.PageManager;
import com.televisa.commons.services.dataaccess.InfoPageManager;
import com.televisa.commons.services.dataaccess.helper.InfoPageManagerHelper;
import com.televisa.commons.services.datamodel.objects.FilterGalleryHistory;
import com.televisa.commons.services.datamodel.objects.FilterIndexByTags;
import com.televisa.commons.services.datamodel.objects.FilterVideoCarousel;
import com.televisa.commons.services.datamodel.objects.InfoPage;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * Information Page Management Service
 *
 * The component and service to perform the information of a pages and the respective pagination management.
 *
 * Changes History:
 *
 *         2013-01-22 Initial Development
 *
 * @author lsztul@xumak.com
 * @version 1.0
 */
public class InfoPageManagerImpl implements InfoPageManager<InfoPage> {

    protected ResourceResolver resourceResolver;
    protected PageManager pageManager;

    /**
     * Construct a new note manager with the page manager provided.
     *
     * @param pageManager the CQ Page Manager
     */
    public InfoPageManagerImpl(ResourceResolver resourceResolver, PageManager pageManager) {
        this.resourceResolver = resourceResolver;
        this.pageManager = pageManager;
    }

    /**
     * Get a list of notes from a path (tags are optional)
     * @param filter
     * @return InfoPage
     */
    @Override
    public InfoPage getNotesByPath(FilterIndexByTags filter) {
        InfoPageManagerHelper infoPageManagerHelper = new InfoPageManagerHelper(resourceResolver);
        return infoPageManagerHelper.findNodesByPath(filter);
    }

    /**
     * Get a list of notes from a path and list of tags.
     * @param filter
     * @return InfoPage
     */
    @Override
    public InfoPage getNotesByPathAndTags(FilterIndexByTags filter) {
        InfoPageManagerHelper infoPageManagerHelper = new InfoPageManagerHelper(resourceResolver);
        return infoPageManagerHelper.findNodesByTags(filter);
    }

    /**
     * Get a list of notes from a path and year of the date of publish.
     * @param filter
     * @return
     */
    @Override
    public InfoPage getNotesByPathAndYear(FilterGalleryHistory filter) {
        InfoPageManagerHelper infoPageManagerHelper = new InfoPageManagerHelper(resourceResolver);
        return infoPageManagerHelper.findNodesByYear(filter);
    }

    /**
     * Get a list of notes from a path, list of tags, title and description.
     * @param filter
     * @return InfoPage
     */
    @Override
    public InfoPage getVideoNotesByTagsAndTitle(FilterVideoCarousel filter) {
        InfoPageManagerHelper infoPageManagerHelper = new InfoPageManagerHelper(resourceResolver);
        return infoPageManagerHelper.findVideoNodesByPathAndTags(filter);
    }

    /**
     * Get a list of notes from a path, list of tags, title and description.
     * @param filter
     * @return InfoPage
     */
    @Override
    public InfoPage getVideoNotesByFilter(FilterVideoCarousel filter) {
        InfoPageManagerHelper infoPageManagerHelper = new InfoPageManagerHelper(resourceResolver);
        return infoPageManagerHelper.findVideoNodesByFilter(filter);
    }
}

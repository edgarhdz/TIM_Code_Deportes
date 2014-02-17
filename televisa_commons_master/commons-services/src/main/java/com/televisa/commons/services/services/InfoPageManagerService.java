/*
 * InfoPageManagerService.java
 *
 * The component and service to perform the information of a pages and the respective pagination management.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.services;

import com.televisa.commons.services.datamodel.objects.FilterIndexByTags;
import com.televisa.commons.services.datamodel.objects.FilterVideoCarousel;
import com.televisa.commons.services.datamodel.objects.InfoPage;
import com.televisa.commons.services.datamodel.objects.FilterGalleryHistory;
import org.apache.sling.api.SlingHttpServletRequest;

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
public interface InfoPageManagerService {

    /**
     * Get a list of notes from a path (tags are optional).
     * @param request
     * @param filter
     * @return InfoPage
     */
    InfoPage getNotesByPath(SlingHttpServletRequest request, FilterIndexByTags filter);

    /**
     * Get a list of notes from a path and list of tags.
     * @param request
     * @param filter
     * @return InfoPage
     */
    InfoPage getNotesByPathAndTags(SlingHttpServletRequest request, FilterIndexByTags filter);

    /**
     * Get a list of notes from a path and year of the date of publish.
     * @param request
     * @param filter
     * @return InfoPage
     */
    InfoPage getNotesByPathAndYear(SlingHttpServletRequest request, FilterGalleryHistory filter);

    /**
     * Get a list of notes from a path, list of tags, title and description.
     * @param request
     * @param filter
     * @return InfoPage
     */
    InfoPage getVideoNotesByTagsAndTitle(SlingHttpServletRequest request, FilterVideoCarousel filter);

    /**
     * Get a list of notes from a path, list of tags, title and description.
     * @param request
     * @param filter
     * @return InfoPage
     */
    InfoPage getVideoNotesByFilter(SlingHttpServletRequest request, FilterVideoCarousel filter);
}

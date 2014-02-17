/*
 * InfoPageManager.java
 *
 * The component and service to perform the information of a pages and the respective pagination management.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.dataaccess;

import com.televisa.commons.services.datamodel.objects.FilterGalleryHistory;
import com.televisa.commons.services.datamodel.objects.FilterIndexByTags;
import com.televisa.commons.services.datamodel.objects.FilterVideoCarousel;
import com.televisa.commons.services.datamodel.objects.InfoPage;

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
public interface InfoPageManager<T extends InfoPage> {

    /**
     * Get a list of notes from a path (tags are optional)
     * @param filter
     * @return InfoPage
     */
    T getNotesByPath(FilterIndexByTags filter);
 
    /**
     * Get a list of notes from a path and list of tags.
     * @param filter
     * @return InfoPage
     */
    T getNotesByPathAndTags(FilterIndexByTags filter);

    /**
     * Get a list of notes from a path and year of the date of publish.
     * @param filter
     * @return
     */
    T getNotesByPathAndYear(FilterGalleryHistory filter);

    /**
     * Get a list of notes from a path, list of tags, title and description.
     * @param filter
     * @return InfoPage
     */
    T getVideoNotesByTagsAndTitle(FilterVideoCarousel filter);

    /**
     * Get a list of notes from a path, list of tags, title and description.
     * @param filter
     * @return InfoPage
     */
    T getVideoNotesByFilter(FilterVideoCarousel filter);
}

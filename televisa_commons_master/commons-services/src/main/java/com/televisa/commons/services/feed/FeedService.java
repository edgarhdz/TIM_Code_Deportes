/*
 * FeedService.java
 *
 * The service to provide the feed service.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.feed;

import com.televisa.commons.services.datamodel.Note;
import org.apache.sling.api.SlingHttpServletRequest;

import java.util.Map;

/**
 * Feed Service
 *
 * The service to provide the feed service.
 *
 * Changes History:
 *
 *         2013-03-13 gescobar Added the request parameter
 *         2013-03-11 gescobar Feed service video and image implementation
 *         2013-02-28 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public interface FeedService {

    /**
     * Get a feed item form a note, the type is provided because the feed rendering could be interchanged.
     *
     * @param type the class of note to render as a feed
     * @param rendering the type of rendering to render
     * @param note the note to render as a feed
     * @return a string rendering of an feed item from a note
     */
    String getFeed(SlingHttpServletRequest request, Class<? extends Note> type, String rendering, Note note, Map<String, Object> additional);

}

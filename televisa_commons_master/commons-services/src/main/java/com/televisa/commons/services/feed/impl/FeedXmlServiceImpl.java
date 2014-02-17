/*
 * FeedXmlServiceImpl.java
 *
 * The service to provide the feed service.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.feed.impl;

import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.feed.FeedService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;

import java.util.Map;

/**
 * Feed XML Service Implementation
 *
 * The service to provide the feed service.
 *
 * Changes History:
 *
 *         2013-03-11 gescobar Feed service video and image implementation
 *         2013-02-28 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@Component(label = "Feed XML Service", description = "The feed XML service implementation.", immediate = true)
@Service(value = FeedService.class)
@Properties({
        @Property(name = "type", value = "xml")
})
public class FeedXmlServiceImpl implements FeedService {
    /**
     * Get a feed item form a note, the type is provided because the feed rendering could be
     * changed at runtime but also the note has already a Note class implementation extended.
     *
     * @param type the class of note to render as a feed
     * @param rendering the type of rendering to render
     * @param note the note to render as a feed
     * @return a string rendering of an feed item from a note
     */
    @Override
    public String getFeed(SlingHttpServletRequest request, Class<? extends Note> type, String rendering, Note note, Map<String, Object> additional) {
        return null;
    }

}

/*
 * AbstractFeedProvider.java
 *
 * A feed provider abstract implementation.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib.feed;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.feed.FeedServiceFactory;
import com.televisa.commons.services.feed.FeedService;

import java.util.Map;

/**
 * Feed Provider
 *
 * A feed provider abstract implementation.
 *
 * Changes History:
 *
 *         2013-03-11 gescobar Add the sling request parameter
 *         2013-03-11 gescobar Feed service video and image implementation
 *         2013-02-27 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public abstract class AbstractFeedProvider extends CqSimpleTagSupport {

    protected String getFeed(String serviceType, Class<? extends Note> type, String rendering, Note note, Map<String, Object> additional) {
        FeedServiceFactory factory = getService(FeedServiceFactory.class);
        if (factory != null) {
            FeedService service = factory.getService(serviceType);
            if (service != null) {
                return service.getFeed(getSlingRequest(), type, rendering, note, additional);
            }
        }
        return null;
    }

}

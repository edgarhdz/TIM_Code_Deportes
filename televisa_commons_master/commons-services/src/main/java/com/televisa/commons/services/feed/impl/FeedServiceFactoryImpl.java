/*
 * FeedServiceFactoryImpl.java
 *
 * The factory to provide the note management services.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.feed.impl;

import com.televisa.commons.services.feed.FeedService;
import com.televisa.commons.services.feed.FeedServiceFactory;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

/**
 * Feed Service Factory Implementation
 *
 * The factory to provide the note management services.
 *
 * Changes History:
 *
 *         2013-03-11 gescobar Feed service video and image implementation
 *         2013-02-28 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@Component(label = "Feed Service Factory", description = "The feed service factory implementation.", immediate = true)
@Service(value = FeedServiceFactory.class)
public class FeedServiceFactoryImpl implements FeedServiceFactory {

    @Reference(target = "(type=json)")
    private FeedService feedJSONService;

    @Reference(target = "(type=xml)")
    private FeedService feedXMLService;

    /**
     * Get a service implementation for a service type.
     *
     * @param type the type of service to obtain
     * @return the service requested or null if can't be resolved
     */
    @Override
    public FeedService getService(String type) {
        if ("json".equalsIgnoreCase(type)) {
            return feedJSONService;
        }
        if ("xml".equalsIgnoreCase(type)) {
            return feedXMLService;
        }
        return null;
    }

}

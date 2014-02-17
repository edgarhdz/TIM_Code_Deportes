/*
 * FeedServiceFactory.java
 *
 * The factory to provide the feed services.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.feed;

/**
 * Feed Service Factory
 *
 * The factory to provide the feed services.
 *
 * Changes History:
 *
 *         2013-03-11 gescobar Feed service video and image implementation
 *         2013-02-28 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public interface FeedServiceFactory {

    /**
     * Get a service implementation for a service type.
     *
     * @param type the type of service to obtain
     * @return the service requested or null if can't be resolved
     */
    FeedService getService(String type);

}

/*
 * BrightcoveProcessor.java
 *
 * The component and service to perform the brightcove process.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.brightcove.processor;

import org.apache.sling.api.SlingHttpServletRequest;

import java.io.Reader;

/**
 * Brightcove Processor
 *
 * The component and service to perform the brightcove process.
 *
 * Changes History:
 *
 *         2013-04-02 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public interface BrightcoveProcessor {

    /**
     * Process the data on the reader.
     * @param reader the reader to read data from
     * @return true if it was processed successfully, false otherwise
     */
    boolean process(Reader reader, String action);

}

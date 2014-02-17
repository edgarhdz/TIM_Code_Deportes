/*
 * Utilities.java
 *
 * General Utilities Methods.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib.utilities;

import com.televisa.commons.services.services.GsaService;

/**
 * Utilities
 *
 * General Utilities Methods
 *
 * Changes History:
 *
 *         2013-02-25 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class Utilities {

    /**
     * Creates a complete path URL using the Externalizer Service.
     *
     * @param gsaService the externalizer service
     * @param path the path to resolve to
     * @return the path translated to a URL
     */
    public static String getCompleteURL(GsaService gsaService, String path, String domain) {
        return gsaService.buildUrl(path, domain);
    }


}

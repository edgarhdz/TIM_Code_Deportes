/*
 * AbstractAssetResolver.java
 *
 * An asset abstract implementation.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib.asset;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.day.cq.dam.api.Asset;
import org.apache.sling.api.resource.Resource;

/**
 * Abstract Asset Resolver
 *
 * An asset abstract implementation.
 *
 * Changes History:
 *
 *         2013-02-27 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public abstract class AbstractAssetResolver extends CqSimpleTagSupport {

    protected Asset getAsset(String path) {
        Resource resource = getSlingRequest().getResourceResolver().resolve(path);
        if (resource != null) {
            return resource.adaptTo(Asset.class);
        }
        return null;
    }

}

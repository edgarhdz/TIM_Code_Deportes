/*
 * VideoReferenceImpl.java
 *
 * The image reference container.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel.objects.impl;

import com.day.cq.dam.api.Asset;
import com.televisa.commons.services.datamodel.objects.VideoAsset;

/**
 * Video Reference Implementation
 *
 * Changes History:
 *
 *         2013-02-06 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class VideoAssetImpl extends AbstractAssetImpl implements VideoAsset {

    public VideoAssetImpl(Asset asset) {
        super(asset);
    }

}

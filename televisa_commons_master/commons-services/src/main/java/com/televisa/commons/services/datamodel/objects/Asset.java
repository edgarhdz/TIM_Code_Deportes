/*
 * Asset.java
 *
 * The generic asset container.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel.objects;

import java.util.List;

/**
 * Generic Asset Interface
 *
 * Changes History:
 *
 *         2013-02-21 gescobar Added the description setter
 *         2013-02-11 gescobar Renamed and properties update
 *         2013-02-06 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public interface Asset {

    com.day.cq.dam.api.Asset getAsset();

    String getPath();

    String getDescription();

    void setDescription(String description);

    Rendition getRendition(int width, int height);

    List<Rendition> getRenditions();

    long getWidth();

    long getHeight();

    float getAspectRatio();

}

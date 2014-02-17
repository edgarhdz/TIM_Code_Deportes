/*
 * Rendition.java
 *
 * The generic rendition interface.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel.objects;

/**
 * Generic Rendition Interface
 *
 * Changes History:
 *
 *         2013-02-12 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public interface Rendition {

    com.day.cq.dam.api.Rendition getRendition();

    String getPath();

    long getWidth();

    long getHeight();

    float getAspectRatio();

}

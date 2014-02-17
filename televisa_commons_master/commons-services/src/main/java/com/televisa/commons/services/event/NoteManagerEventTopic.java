/*
 * NoteManagerEventTopic.java
 *
 * The note manager event topics.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.event;

/**
 * Note Management Event Topic
 *
 * The note manager event topics.
 *
 * Changes History:
 *
 *         2013-02-22 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public enum NoteManagerEventTopic {

    GETNOTE ("com/televisa/commons/services/services/api/GETNOTE"),
    CREATED_MAIN_VIDEO ("com/televisa/commons/services/services/api/CREATED_MAIN_VIDEO"),
    CREATED_VIDEO ("com/televisa/commons/services/services/api/CREATED_VIDEO");

    private final String path;

    private NoteManagerEventTopic(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}

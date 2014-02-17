/*
 * NoteManagerEventManager.java
 *
 * The event manager interface to handle application events.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.event;


/**
 * Note Management Event Manager Interface
 *
 * The event manager interface to handle application events.
 *
 * Changes History:
 *
 *         2013-02-22 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public interface NoteManagerEventManager {

    void setTopic(NoteManagerEventTopic topic);

    String putProperty(String key, String value);

    void raiseEvent();

}

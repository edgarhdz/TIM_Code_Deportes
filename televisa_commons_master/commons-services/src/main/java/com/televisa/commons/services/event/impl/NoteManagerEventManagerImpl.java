/*
 * NoteManagerEventManagerImpl.java
 *
 * The event manager implementation to handle application events.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.event.impl;

import com.televisa.commons.services.event.NoteManagerEventManager;
import com.televisa.commons.services.event.NoteManagerEventTopic;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Note Management Event Manager Implementation
 *
 * The event manager implementation to handle application events.
 *
 * Changes History:
 *
 *         2013-02-22 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class NoteManagerEventManagerImpl implements NoteManagerEventManager {

    private EventAdmin eventAdmin;
    private NoteManagerEventTopic topic;
    private Dictionary<String, String> properties;

    public NoteManagerEventManagerImpl(EventAdmin eventAdmin) {
        this.eventAdmin = eventAdmin;
        this.properties = new Hashtable<String, String>();
    }

    public EventAdmin getEventAdmin() {
        return eventAdmin;
    }

    public void setEventAdmin(EventAdmin eventAdmin) {
        this.eventAdmin = eventAdmin;
    }

    @Override
    public void setTopic(NoteManagerEventTopic topic) {
        this.topic = topic;
    }

    @Override
    public String putProperty(String key, String value) {
        return properties.put(key, value);
    }

    @Override
    public void raiseEvent() {
        eventAdmin.sendEvent(new Event(topic.getPath(), properties));
    }

}

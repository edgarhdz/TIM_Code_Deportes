/*
 * NoteManagerServiceFactoryImpl.java
 *
 * The factory to provide the note management services.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.services.impl;

import com.televisa.commons.services.services.NoteManagerService;
import com.televisa.commons.services.services.NoteManagerServiceFactory;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

/**
 * Note Management Service Factory Implementation
 *
 * The factory to provide the note management services.
 *
 * Changes History:
 *
 *         2013-01-17 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@Component(label = "Note Manager Service Factory", description = "The note manager service factory implementation.", immediate = true)
@Service(value = NoteManagerServiceFactory.class)
public class NoteManagerServiceFactoryImpl implements NoteManagerServiceFactory {

    @Reference(target = "(type=note)")
    private NoteManagerService noteManagerService;

    @Override
    public NoteManagerService getService(Class<? extends NoteManagerService> clazz) {
        return noteManagerService;
    }

}

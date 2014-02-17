/*
 * NoteManagerServiceFactory.java
 *
 * The factory to provide the note management services.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.services;

/**
 * Note Management Service Factory
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
public interface NoteManagerServiceFactory {

    NoteManagerService getService(Class<? extends NoteManagerService> clazz);

}

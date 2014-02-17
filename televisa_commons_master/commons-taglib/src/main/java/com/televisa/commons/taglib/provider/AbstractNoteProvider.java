/*
 * AbstractNoteProvider.java
 *
 * A note provider abstract implementation.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib.provider;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.services.NoteManagerService;
import com.televisa.commons.services.services.NoteManagerServiceFactory;

/**
 * Note Provider
 *
 * A note provider abstract implementation.
 *
 * Changes History:
 *
 *         2013-02-25 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public abstract class AbstractNoteProvider extends CqSimpleTagSupport {

    protected Note getNote(String path) {
        NoteManagerServiceFactory factory = getService(NoteManagerServiceFactory.class);
        if (factory != null) {
            NoteManagerService service = factory.getService(NoteManagerService.class);
            if (service != null) {
                return service.getNote(path);
            }
        }
        return null;
    }

}

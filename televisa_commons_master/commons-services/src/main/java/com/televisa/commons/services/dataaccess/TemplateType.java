/*
 * TemplateType.java
 *
 * The different template types.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.dataaccess;

import com.televisa.commons.services.datamodel.*;

/**
 * Template Types
 *
 * The different template types.
 *
 * Changes History:
 *
 *         2013-03-08 gescobar Updated the getTemplateName method name
 *         2013-02-04 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public enum TemplateType {

    ARTICLE ("article", Article.class),
    PHOTO ("photo", Photo.class),
    VIDEO ("video", Video.class),
    PROGRAM ("program", Program.class);

    private String name;
    private Class<? extends Note> clazz;

    private TemplateType(String name, Class<? extends Note> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getTemplateName() {
        return name;
    }

    public Class<? extends Note> getClazz() {
        return clazz;
    }

}

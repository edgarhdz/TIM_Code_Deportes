/*
 * ApplicationProperties.java
 *
 * The global application properties.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.utilities;

/**
 * Global Application Properties
 *
 * The global application PROPERTIES, as a static class.
 *
 * Changes History:
 *
 *         2013-01-29 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class ApplicationProperties {

    private static final java.util.Properties PROPERTIES = new java.util.Properties();

    public static final String DATA_CONTENT_PATH = "data.content.path";
    public static final String NOTICIEROS_DAM_PATH = "noticieros.dam.path";
    public static final String NOTICIEROS_CONTENT_PATH = "noticieros.content.path";

    public static final String APPLICATION_PATH = "application.path";


    public static final String DATA_CONTENT_PATH_DEFAULT_VALUE = "/content/televisa/";
    public static final String NOTICIEROS_CONTENT_PATH_DEFAULT_VALUE = "/content/televisa/noticieros";
    public static final String NOTICIEROS_DAM_PATH_DEFAULT_VALUE = "/content/dam/televisa/noticieros/fotos";

    public static final String APPLICATION_PATH_DEFAULT_VALUE = "/apps/televisa/";


    /* The name of the templates used to create pages with the Brightcove Processor, do not confuse with the template type directory name */

    public static final String CATEGORY_DATE_TEMPLATE_NAME = "template.category.date.name";
    public static final String VIDEO_TEMPLATE_NAME = "template.video.name";

    public static final String CATEGORY_DATE_TEMPLATE_NAME_DEFAULT_VALUE = "/apps/televisa/templates/parsys-template";
    public static final String VIDEO_TEMPLATE_NAME_DEFAULT_VALUE = "/apps/televisa/templates/noticieros/video/video";

    /**
     * The global PROPERTIES to make use of from the application.
     * The PROPERTIES do not need for external synchronization.
     *
     * @return the Properties of the application
     */
    public static final java.util.Properties getPROPERTIES() {
        return PROPERTIES;
    }

    public static String getDataContentPath() {
        return PROPERTIES.getProperty(DATA_CONTENT_PATH, DATA_CONTENT_PATH_DEFAULT_VALUE);
    }

    public static void setDataContentPath(String dataContentPath) {
        PROPERTIES.setProperty(DATA_CONTENT_PATH, dataContentPath);
    }



    public static String getNoticierosContentPath() {
        return PROPERTIES.getProperty(NOTICIEROS_CONTENT_PATH, NOTICIEROS_CONTENT_PATH_DEFAULT_VALUE);
    }

    public static void setNoticierosContentPath(String noticierosContentPath) {
        PROPERTIES.setProperty(NOTICIEROS_CONTENT_PATH, noticierosContentPath);
    }

    public static String getNoticierosDamPath() {
        return PROPERTIES.getProperty(NOTICIEROS_DAM_PATH, NOTICIEROS_DAM_PATH_DEFAULT_VALUE);
    }

    public static void setNoticierosDamPath(String noticierosDamPath) {
        PROPERTIES.setProperty(NOTICIEROS_CONTENT_PATH, noticierosDamPath);
    }

    public static String getApplicationPath() {
        return PROPERTIES.getProperty(APPLICATION_PATH, APPLICATION_PATH_DEFAULT_VALUE);
    }

    public static void setApplicationPath(String applicationPath) {
        PROPERTIES.setProperty(APPLICATION_PATH, applicationPath);
    }

    public static String getCategoryDateTemplateName() {
        return PROPERTIES.getProperty(CATEGORY_DATE_TEMPLATE_NAME, CATEGORY_DATE_TEMPLATE_NAME_DEFAULT_VALUE);
    }

    public static void setCategoryDateTemplateName(String categoryDateTemplateName) {
        PROPERTIES.setProperty(CATEGORY_DATE_TEMPLATE_NAME, categoryDateTemplateName);
    }

    public static String getVideoTemplateName() {
        return PROPERTIES.getProperty(VIDEO_TEMPLATE_NAME, VIDEO_TEMPLATE_NAME_DEFAULT_VALUE);
    }

    public static void setVideoTemplateName(String videoTemplateName) {
        PROPERTIES.setProperty(VIDEO_TEMPLATE_NAME, videoTemplateName);
    }

}

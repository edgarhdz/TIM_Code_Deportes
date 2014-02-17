package com.televisa.commons.services.gsa;

public final class Constants {

    private Constants() {
    }

    /* MISC */
    public static final String ACTION_ADD    = "add";
    public static final String ACTION_DELETE = "delete";
    public static final String CONTENT_KEY = "${CONTENT}";
    public static final String FEEDTYPE = "feedtype";
    public static final String DOCTYPE  = "<!DOCTYPE gsafeed PUBLIC \"-//Google//DTD GSA Feeds//EN\" \"\">";
    public static final String TEXTHTML = "text/html";
    public static final String TEXTPLAIN = "text/plain";
    public static final String TRUE = "true";

    /* ELEMENTS */
    public static final String GSAFEED_ELEMENT    = "gsafeed";
    public static final String HEADER_ELEMENT    = "header";
    public static final String DATASOURCE_ELEMENT    = "datasource";
    public static final String FEEDTYPE_ELEMENT    = "feedtype";
    public static final String GROUP_ELEMENT    = "group";
    public static final String RECORD_ELEMENT    = "record";
    public static final String METADATA_ELEMENT    = "metadata";
    public static final String CONTENT_ELEMENT    = "content";
    public static final String META_ELEMENT    = "meta";

    /* ATTRIBUTES */
    public static final String URL_ATTRIBUTE    = "url";
    public static final String ACTION_ATTRIBUTE    = "action";
    public static final String MIMETYPE_ATTRIBUTE    = "mimetype";
    public static final String LOCK_ATTRIBUTE    = "lock";
    public static final String LAST_MODIFIED_ATTRIBUTE    = "last-modified";
    public static final String NAME_ATTRIBUTE    = "name";
    public static final String CONTENT_ATTRIBUTE    = "content";

    /* TYPES */
    public static final String ARTICLE_TYPE    = "articulo";
    public static final String VIDEO_TYPE    = "video";
    public static final String PHOTO_TYPE    = "galeria";

    /* FORMATS */
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "HH:mm";

}

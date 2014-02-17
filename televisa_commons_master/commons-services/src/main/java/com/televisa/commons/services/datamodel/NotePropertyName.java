/*
 * NotePropertyName.java
 *
 * The name of the properties and their associated paths.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel;

import javax.jcr.PropertyType;

/**
 * Note Property Type, Name & Path
 *
 * The name of the properties and their associated paths. This is case sensitive. The type and path should be the node's type
 * and the relative path to the property, followed by the name of the property where the value are stored, the type can be
 * null if the path property contains a relative or absolute path.
 *
 * All the properties of article, video and others are included in this enumeration since it can't have multiple inheritance.
 *
 * Changes History:
 *
 *         2013-03-25 gescobar Appended a property.
 *         2013-03-15 gescobar Updated the video implementation.
 *         2013-03-06 gescobar Updated the general video data model.
 *         2013-02-21 gescobar Added the photo path and description properties
 *         2013-02-18 gescobar Updated the photo properties
 *         2013-02-11 gescobar Updated the program properties
 *         2013-02-04 gescobar Updated the properties names, added photo implementation
 *         2013-01-29 gescobar Added topic property
 *         2013-01-10 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public enum NotePropertyName {

	/* NOTE */

    NOTE_CATEGORY (PropertyType.UNDEFINED, "jcr:content", "category"),
    NOTE_CATEGORY_URL (PropertyType.UNDEFINED, "jcr:content", "categoryUrl"),
    NOTE_TITLE (PropertyType.UNDEFINED, "jcr:content", "title"),
    NOTE_TITLE_SEO (PropertyType.UNDEFINED, "jcr:content/article/body/pagetitle", "pageTitle"),
    NOTE_URL (PropertyType.UNDEFINED, "jcr:content", "url"),
    NOTE_EXTERNAL_URL (PropertyType.STRING, "jcr:content", "externalUrl"),
    NOTE_KEYWORDS (PropertyType.UNDEFINED, "jcr:content", "keywords"),
    NOTE_PUB_DATE (PropertyType.UNDEFINED, "jcr:content", "cq:lastReplicated"),
    NOTE_FIRST_PUB_DATE (PropertyType.UNDEFINED, "jcr:content", "cq:firstReplicated"),
    NOTE_CR_DATE (PropertyType.UNDEFINED, "jcr:content", "crDate"),
    NOTE_FECHA_MODIFICACION (PropertyType.UNDEFINED, "jcr:content", "cq:lastModified"),
    NOTE_CHANNEL (PropertyType.UNDEFINED, "jcr:content/article", "channel"),
    NOTE_CHANNEL_URL (PropertyType.UNDEFINED, "jcr:content/article", "channelUrl"),
    NOTE_SUMMARY (PropertyType.UNDEFINED, "jcr:content/article", "summary"),
    NOTE_TIPO (PropertyType.UNDEFINED, "jcr:content/article", "tipo"),
    NOTE_STRING_TAGS (PropertyType.UNDEFINED, "jcr:content", "cq:tags"),
    NOTE_TAGS (PropertyType.UNDEFINED, "jcr:content", "tags"),
    NOTE_CONTENT (PropertyType.STRING, "jcr:content", "textblock"),
    NOTE_DESCRIPTION (PropertyType.UNDEFINED, "jcr:content/article/description", "description"),
    NOTE_IMAGE_FILE_REFERENCE (PropertyType.UNDEFINED, "jcr:content/article/image", "fileReference"),
    NOTE_IMAGE_LINK_URL (PropertyType.UNDEFINED, "jcr:content/article/image", "fileReference"),
    NOTE_AUTHOR (PropertyType.UNDEFINED, "jcr:content/article/title", "author"),
    NOTE_SOURCE (PropertyType.UNDEFINED, "jcr:content/article/title", "source"),

    NOTE_STYLE (PropertyType.UNDEFINED, "jcr:content/article", "style"),
    NOTE_DATE_CREATED (PropertyType.UNDEFINED, "jcr:content/article", "date"),
    NOTE_TEMPLATE (PropertyType.UNDEFINED, "jcr:content", "cq:template"),
    NOTE_TOPIC (PropertyType.UNDEFINED, "jcr:content/article", "topic"),
    NOTE_IDENTIFIER (PropertyType.STRING, "jcr:content", "identifier"),
    NOTE_IMAGE_ASSET (PropertyType.UNDEFINED, "jcr:content/article/image", "fileReference"),

	/* ARTICLE */

    ARTICLE_IMAGE_LINK_URL (PropertyType.UNDEFINED, "jcr:content/article/image", "fileReference"),
    ARTICLE_STAGED (PropertyType.UNDEFINED, "jcr:content/article", "stage"),

	/* PHOTO */

    IMAGE_REFERENCE_URL (PropertyType.UNDEFINED, "jcr:content/imagegallerycontainer", "fileReference"), /* TODO */
    IMAGE_SHORT_DESCRIPTION (PropertyType.UNDEFINED, "jcr:content/imagegallerycontainer", "shortDescription"), /* TODO */
    IMAGE_TOPIC (PropertyType.UNDEFINED, "jcr:content/imagegallerycontainer", "topic"),
    IMAGE_REFERENCES_PATH (PropertyType.STRING, "jcr:content/imagegallerycontainer", "imagePaths"),
    IMAGE_REFERENCES_DESCRIPTION (PropertyType.STRING, "jcr:content/imagegallerycontainer", "imageDescriptions"),

	/* VIDEO */

    VIDEO_TITLE (PropertyType.UNDEFINED, "jcr:content/video", "title"),
    VIDEO_REFERENCE_URL (PropertyType.UNDEFINED, "jcr:content/video", "fileReference"),
    VIDEO_DURATION (PropertyType.UNDEFINED, "jcr:content/video", "youtubeVideoDuration"),
    VIDEO_CHAPTER (PropertyType.UNDEFINED, "jcr:content/video", "chapter"),
    VIDEO_SEASON (PropertyType.UNDEFINED, "jcr:content/video", "season"),
    VIDEO_PROGRAM_NOTE (PropertyType.UNDEFINED, "jcr:content/video", "program"),
    VIDEO_PROGRAM_NAME (PropertyType.UNDEFINED, "jcr:content/video", "programName"),
    VIDEO_SUMMARY (PropertyType.UNDEFINED, "jcr:content/video", "summary"),
    VIDEO_TOOLTIP (PropertyType.UNDEFINED, "jcr:content/video", "tooltip"),
    VIDEO_PROGRAM_URL (PropertyType.UNDEFINED, "jcr:content/video", "programUrl"),
    VIDEO_ID (PropertyType.UNDEFINED, "jcr:content/video", "videoPlayer"),
    VIDEO_PLAYER (PropertyType.UNDEFINED, "jcr:content/video", "videoPlayer"),
    VIDEO_ID_BRIGHTCOVE (PropertyType.UNDEFINED, "jcr:content/video", "brightcoveVideoId"),
    VIDEO_ID_YOUTUBE (PropertyType.UNDEFINED, "jcr:content/video", "youtubeVideoId"),
    VIDEO_ID_AKAMAI (PropertyType.UNDEFINED, "jcr:content/video", "akamaiVideoId"),
    VIDEO_TYPE (PropertyType.UNDEFINED, "jcr:content/video", "videoType"),
    VIDEO_IMAGE_ASSET (PropertyType.UNDEFINED, "jcr:content/video", "fileReference"),
    VIDEO_PROGRAM (PropertyType.UNDEFINED, "jcr:content/video", "program"),
    VIDEO_RELEASE_DATE (PropertyType.UNDEFINED, "jcr:content/video", "releaseDate"),
    VIDEO_BRIGHTCOVE_PROPERTIES (PropertyType.UNDEFINED, "jcr:content/video/brightcove", "properties"),
    VIDEO_TOPIC (PropertyType.UNDEFINED, "jcr:content/video", "topic"),

	/* PROGRAM */

    PROGRAM_NAME (PropertyType.UNDEFINED, "jcr:content/program", "programName"),
    PROGRAM_CAPTION (PropertyType.UNDEFINED, "jcr:content/program", "programCaption"),
    PROGRAM_SYNOPSIS (PropertyType.UNDEFINED, "jcr:content/program", "programSynopsis"),
    PROGRAM_CHANNEL (PropertyType.UNDEFINED, "jcr:content/program", "programChannel"),
    PROGRAM_SCHEDULE (PropertyType.UNDEFINED, "jcr:content/program", "programSchedule"),
    PROGRAM_IMAGE (PropertyType.UNDEFINED, "jcr:content/program", "fileReference"),
    PROGRAM_URL (PropertyType.UNDEFINED, "jcr:content/program", "programUrl"),
    PROGRAM_URL_TITLE (PropertyType.UNDEFINED, "jcr:content/program", "programUrlTitle");

    private int propertyType;
    private String propertyPath;
    private String propertyName;

    private NotePropertyName(int propertyType, String propertyPath, String propertyName) {
        this.propertyType = propertyType;
        this.propertyPath = propertyPath;
        this.propertyName = propertyName;
    }

    public int getPropertyType() {
        return propertyType;
    }

    public String getPropertyPath() {
        return propertyPath;
    }

    public String getPropertyName() {
        return propertyName;
    }

}

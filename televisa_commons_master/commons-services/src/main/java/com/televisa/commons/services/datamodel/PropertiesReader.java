/*
 * PropertiesReader.java
 *
 * Read the properties from a CQ Page.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel;

import com.day.cq.tagging.Tag;
import com.televisa.commons.services.datamodel.objects.ImageAsset;
import com.televisa.commons.services.datamodel.objects.VideoAsset;
import org.apache.sling.api.resource.ValueMap;

import java.util.Calendar;

/**
 * Read the properties from a CQ Page.
 *
 * Changes History:
 *
 *         2013-03-15 gescobar Updated the video implementation.
 *         2013-03-06 gescobar Updated the general video data model.
 *         2013-02-21 gescobar Added the image assets array
 *         2013-02-18 gescobar Updated the photo properties
 *         2013-02-11 gescobar Updated the program properties
 *         2013-02-04 gescobar Updated the properties names
 *         2013-01-29 gescobar Added topic property
 *         2013-01-16 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public interface PropertiesReader {

	/* NOTE */

    String getNoteCategory();

    String getNoteCategoryUrl();

    String getNoteTitle();

    String getNoteTitleSEO();

    String getNoteUrl();

    String getNoteExternalUrl();

    Tag[] getNoteKeywords();

    Calendar getNotePubDate();

    Calendar getNoteFirstPubDate();

    Calendar getNoteCrDate();

    Calendar getNoteFechaModificacion();

    String getNoteChannel();

    String getNoteChannelUrl();

    String getNoteSummary();

    String getNoteTipo();

    String getNoteStringTags();

    Tag[] getNoteTags();

    String getNoteContent();

    String getNoteDescription();

    String getNoteImageFileReference();

    String getNoteImageLinkURL();

    String getNoteAuthor();

    String getNoteSource();

    String getNoteStyle();

    String getNoteDateCreated();

    String getNoteTemplate();

    String getNoteTopic();

    String getNoteIdentifier();

    ImageAsset getNoteImageAsset();

	/* ARTICLE */

    String getArticleImageLinkUrl();

    String getArticleStage();

	/* PHOTO */

    ImageAsset getImageAsset();

    String getImageShortDescription();

    String getImageTopic();

    ImageAsset[] getImageAssets();

	/* VIDEO */

    String getVideoTitle();

    String getVideoTopic();

    VideoAsset getVideoAsset();

    String getVideoDuration();

    String getVideoChapter();

    String getVideoSeason();

    String getVideoProgram();

    String getVideoProgramName();

    String getVideoSummary();

    String getVideoTooltip();

    String getVideoReleaseDate();

    String getVideoProgramUrl();

    String getVideoId();

    String getVideoPlayer();

    String getVideoType();

    ImageAsset getVideoImageAsset();

    Program getVideoProgramNote();

    ValueMap getBrightcoveProperties();

	/* PROGRAM */

    String getProgramName();

    String getProgramCaption();

    String getProgramSynopsis();

    String getProgramChannel();

    String getProgramSchedule();

    ImageAsset getProgramImage();

    String getProgramUrl();

    String getProgramUrlTitle();

}

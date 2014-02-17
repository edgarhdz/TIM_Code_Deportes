/*
 * Video.java
 *
 * The video data model interface.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel;

import com.televisa.commons.services.datamodel.objects.ImageAsset;
import com.televisa.commons.services.datamodel.objects.VideoAsset;
import org.apache.sling.api.resource.ValueMap;

/**
 * The Video Data Model Interface
 *
 * Changes History:
 *
 *         2013-03-15 gescobar Updated the video implementation.
 *         2013-03-06 gescobar Updated the general video data model.
 *         2013-02-21 gescobar Updated the properties
 *         2013-01-30 gescobar Added new properties
 *         2013-01-10 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public interface Video extends Note {

    VideoAsset getVideoAsset();

    void setVideoAsset(VideoAsset videoAsset);

    String getVideoDuration();

    void setVideoDuration(String duration);

    String getVideoChapter();

    void setVideoChapter(String chapter);

    String getVideoSeason();

    void setVideoSeason(String season);

    String getVideoProgram();

    void setVideoProgram(String program);

    String getVideoReleaseDate();

    void setVideoReleaseDate(String releaseDate);

    String getVideoProgramName();

    void setVideoProgramName(String programName);

    String getVideoProgramUrl();

    void setVideoProgramUrl(String programUrl);

    String getVideoId();

    void setVideoId(String id);

    String getVideoTooltip();

    void setVideoTooltip(String tooltip);

    String getVideoPlayer();

    void setVideoPlayer(String videoPlayer);

    String getVideoType();

    void setVideoType(String type);

    ImageAsset getVideoImageAsset();

    void setVideoImageAsset(ImageAsset imageAsset);

    Program getVideoProgramNote();

    void setVideoProgramNote(Program videoProgramNote);

    ValueMap getBrightcoveProperties();

    void setBrightcoveProperties(ValueMap videoProgramNote);

}

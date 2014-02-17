/*
 * VideoImpl.java
 *
 * The video data model implementation.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel.impl;

import com.day.cq.wcm.api.Page;
import com.televisa.commons.services.datamodel.NotePropertyName;
import com.televisa.commons.services.datamodel.Program;
import com.televisa.commons.services.datamodel.objects.VideoAsset;
import com.televisa.commons.services.datamodel.Video;
import com.televisa.commons.services.datamodel.objects.ImageAsset;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

/**
 * The Video Data Model Implementation
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
public class VideoImpl extends NoteImpl implements Video {

    public VideoImpl(ResourceResolver resourceResolver, Page page) {
        super(resourceResolver, page);
    }

    protected void readProperties() {
        super.readProperties();
        putProperty(NotePropertyName.VIDEO_TITLE, propertiesReader.getVideoTitle());
        putProperty(NotePropertyName.VIDEO_REFERENCE_URL, propertiesReader.getVideoAsset());
        putProperty(NotePropertyName.VIDEO_DURATION, propertiesReader.getVideoDuration());
        putProperty(NotePropertyName.VIDEO_CHAPTER, propertiesReader.getVideoChapter());
        putProperty(NotePropertyName.VIDEO_SEASON, propertiesReader.getVideoSeason());
        putProperty(NotePropertyName.VIDEO_PROGRAM_NOTE, propertiesReader.getVideoProgram());
        putProperty(NotePropertyName.VIDEO_PROGRAM_NAME, propertiesReader.getVideoProgramName());
        putProperty(NotePropertyName.VIDEO_SUMMARY, propertiesReader.getVideoSummary());
        putProperty(NotePropertyName.VIDEO_TOOLTIP, propertiesReader.getVideoTooltip());
        putProperty(NotePropertyName.VIDEO_PROGRAM_URL, propertiesReader.getVideoProgramUrl());
        putProperty(NotePropertyName.VIDEO_ID, propertiesReader.getVideoId());
        putProperty(NotePropertyName.VIDEO_PLAYER, propertiesReader.getVideoPlayer());
        putProperty(NotePropertyName.VIDEO_TYPE, propertiesReader.getVideoType());
        putProperty(NotePropertyName.VIDEO_RELEASE_DATE, propertiesReader.getVideoReleaseDate());
        putProperty(NotePropertyName.VIDEO_IMAGE_ASSET, propertiesReader.getVideoImageAsset());
        putProperty(NotePropertyName.VIDEO_PROGRAM, propertiesReader.getVideoProgramNote());
        putProperty(NotePropertyName.VIDEO_BRIGHTCOVE_PROPERTIES, propertiesReader.getBrightcoveProperties());
        putProperty(NotePropertyName.VIDEO_TOPIC, propertiesReader.getVideoTopic());

    }

    @Override
    public String getTitle() {
        return (String) getProperty(NotePropertyName.VIDEO_TITLE);
    }

    @Override
    public void setTitle(String title) {
        putProperty(NotePropertyName.VIDEO_TITLE, title);
    }

    @Override
    public String getSummary() {
        return (String) getProperty(NotePropertyName.VIDEO_SUMMARY);
    }

    @Override
    public void setSummary(String summary) {
        putProperty(NotePropertyName.VIDEO_SUMMARY, summary);
    }

    @Override
    public String getDescription() {
        return (String) getProperty(NotePropertyName.VIDEO_SUMMARY);
    }

    @Override
    public void setDescription(String description) {
        putProperty(NotePropertyName.VIDEO_SUMMARY, description);
    }

    @Override
    public ImageAsset getNoteImageAsset() {
        return (ImageAsset) getProperty(NotePropertyName.VIDEO_IMAGE_ASSET);
    }

    @Override
    public void setNoteImageAsset(ImageAsset noteImageAsset) {
        putProperty(NotePropertyName.VIDEO_IMAGE_ASSET, noteImageAsset);
    }

    @Override
    public VideoAsset getVideoAsset() {
        return (VideoAsset) getProperty(NotePropertyName.VIDEO_REFERENCE_URL);
    }

    @Override
    public void setVideoAsset(VideoAsset videoReference) {
        putProperty(NotePropertyName.VIDEO_REFERENCE_URL, videoReference);
    }

    @Override
    public String getTopic() {
        return (String) getProperty(NotePropertyName.VIDEO_TOPIC);
    }

    @Override
    public void setTopic(String topic) {
        putProperty(NotePropertyName.VIDEO_TOPIC, topic);
    }

    @Override
    public String getVideoDuration() {
        return (String) getProperty(NotePropertyName.VIDEO_DURATION);
    }

    @Override
    public void setVideoDuration(String duration) {
        putProperty(NotePropertyName.VIDEO_DURATION, duration);
    }

    @Override
    public String getVideoReleaseDate() {
        return (String) getProperty(NotePropertyName.VIDEO_RELEASE_DATE);
    }

    @Override
    public void setVideoReleaseDate(String videoReleaseDate) {
        putProperty(NotePropertyName.VIDEO_RELEASE_DATE, videoReleaseDate);
    }

    @Override
    public String getVideoChapter() {
        return (String) getProperty(NotePropertyName.VIDEO_CHAPTER);
    }

    @Override
    public String getVideoTooltip() {
        return (String) getProperty(NotePropertyName.VIDEO_TOOLTIP);
    }

    @Override
    public void setVideoTooltip(String tooltip) {
        putProperty(NotePropertyName.VIDEO_TOOLTIP, tooltip);
    }

    @Override
    public void setVideoChapter(String chapter) {
        putProperty(NotePropertyName.VIDEO_CHAPTER, chapter);
    }

    @Override
    public String getVideoSeason() {
        return (String) getProperty(NotePropertyName.VIDEO_SEASON);
    }

    @Override
    public void setVideoSeason(String season) {
        putProperty(NotePropertyName.VIDEO_SEASON, season);
    }

    @Override
    public String getVideoProgram() {
        return (String) getProperty(NotePropertyName.VIDEO_PROGRAM_NOTE);
    }

    @Override
    public void setVideoProgram(String program) {
        putProperty(NotePropertyName.VIDEO_PROGRAM_NOTE, program);
    }

    @Override
    public String getVideoProgramName() {
        return (String) getProperty(NotePropertyName.VIDEO_PROGRAM_NAME);
    }

    @Override
    public void setVideoProgramName(String programName) {
        putProperty(NotePropertyName.VIDEO_PROGRAM_NAME, programName);
    }

    @Override
    public String getVideoProgramUrl() {
        return (String) getProperty(NotePropertyName.VIDEO_PROGRAM_URL);
    }

    @Override
    public void setVideoProgramUrl(String programUrl) {
        putProperty(NotePropertyName.VIDEO_PROGRAM_URL, programUrl);
    }

    @Override
    public String getVideoId() {
        return (String) getProperty(NotePropertyName.VIDEO_ID);
    }

    @Override
    public void setVideoId(String id) {
        putProperty(NotePropertyName.VIDEO_ID, id);
    }

    @Override
    public String getVideoPlayer() {
        return (String) getProperty(NotePropertyName.VIDEO_PLAYER);
    }

    @Override
    public void setVideoPlayer(String videoPlayer) {
        putProperty(NotePropertyName.VIDEO_PLAYER, videoPlayer);
    }

    @Override
    public String getVideoType() {
        return (String) getProperty(NotePropertyName.VIDEO_TYPE);
    }

    @Override
    public void setVideoType(String type) {
        putProperty(NotePropertyName.VIDEO_TYPE, type);
    }

    @Override
    public ImageAsset getVideoImageAsset() {
        return (ImageAsset) getProperty(NotePropertyName.VIDEO_IMAGE_ASSET);
    }

    @Override
    public void setVideoImageAsset(ImageAsset imageAsset) {
        putProperty(NotePropertyName.VIDEO_IMAGE_ASSET, imageAsset);
    }

    @Override
    public Program getVideoProgramNote() {
        return (Program) getProperty(NotePropertyName.VIDEO_PROGRAM);
    }

    @Override
    public void setVideoProgramNote(Program videoProgramNote) {
        putProperty(NotePropertyName.VIDEO_PROGRAM, videoProgramNote);
    }

    @Override
    public ValueMap getBrightcoveProperties() {
        return (ValueMap) getProperty(NotePropertyName.VIDEO_BRIGHTCOVE_PROPERTIES);
    }

    @Override
    public void setBrightcoveProperties(ValueMap videoProgramNote) {
        putProperty(NotePropertyName.VIDEO_BRIGHTCOVE_PROPERTIES, videoProgramNote);
    }

}

/*
 * ProgramImpl.java
 *
 * The program data model implementation.
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
import com.televisa.commons.services.datamodel.objects.ImageAsset;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * The Program Data Model Implementation
 *
 * Changes History:
 *
 *         2013-02-11 gescobar Updated the program properties
 *         2013-02-04 gescobar Added new properties
 *         2013-02-01 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class ProgramImpl extends NoteImpl implements Program {

    public ProgramImpl(ResourceResolver resourceResolver, Page page) {
        super(resourceResolver, page);
    }

    public void readProperties() {
        super.readProperties();
        putProperty(NotePropertyName.PROGRAM_NAME, propertiesReader.getProgramName());
        putProperty(NotePropertyName.PROGRAM_CAPTION, propertiesReader.getProgramCaption());
        putProperty(NotePropertyName.PROGRAM_SYNOPSIS, propertiesReader.getProgramSynopsis());
        putProperty(NotePropertyName.PROGRAM_CHANNEL, propertiesReader.getProgramChannel());
        putProperty(NotePropertyName.PROGRAM_SCHEDULE, propertiesReader.getProgramSchedule());
        putProperty(NotePropertyName.PROGRAM_IMAGE, propertiesReader.getProgramImage());
        putProperty(NotePropertyName.PROGRAM_URL, propertiesReader.getProgramUrl());
        putProperty(NotePropertyName.PROGRAM_URL_TITLE, propertiesReader.getProgramUrlTitle());
    }

    @Override
    public String getProgramName() {
        return (String) getProperty(NotePropertyName.PROGRAM_NAME);
    }

    @Override
    public void setProgramName(String programName) {
        putProperty(NotePropertyName.PROGRAM_NAME, programName);
    }

    @Override
    public String getProgramCaption() {
        return (String) getProperty(NotePropertyName.PROGRAM_CAPTION);
    }

    @Override
    public void setProgramCaption(String programCaption) {
        putProperty(NotePropertyName.PROGRAM_CAPTION, programCaption);
    }

    @Override
    public String getProgramSynopsis() {
        return (String) getProperty(NotePropertyName.PROGRAM_SYNOPSIS);
    }

    @Override
    public void setProgramSynopsis(String programSynopsis) {
        putProperty(NotePropertyName.PROGRAM_SYNOPSIS, programSynopsis);
    }

    @Override
    public String getProgramChannel() {
        return (String) getProperty(NotePropertyName.PROGRAM_CHANNEL);
    }

    @Override
    public void setProgramChannel(String programChannel) {
        putProperty(NotePropertyName.PROGRAM_CHANNEL, programChannel);
    }

    @Override
    public String getProgramSchedule() {
        return (String) getProperty(NotePropertyName.PROGRAM_SCHEDULE);
    }

    @Override
    public void setProgramSchedule(String programSchedule) {
        putProperty(NotePropertyName.PROGRAM_SCHEDULE, programSchedule);
    }

    @Override
    public ImageAsset getProgramImage() {
        return (ImageAsset) getProperty(NotePropertyName.PROGRAM_IMAGE);
    }

    @Override
    public void setProgramImage(ImageAsset programImage) {
        putProperty(NotePropertyName.PROGRAM_IMAGE, programImage);
    }

    @Override
    public String getProgramUrl() {
        return (String) getProperty(NotePropertyName.PROGRAM_URL);
    }

    @Override
    public void setProgramUrl(String programUrl) {
        putProperty(NotePropertyName.PROGRAM_URL, programUrl);
    }

    @Override
    public String getProgramUrlTitle() {
        return (String) getProperty(NotePropertyName.PROGRAM_URL_TITLE);
    }

    @Override
    public void setProgramUrlTitle(String programUrlTitle) {
        putProperty(NotePropertyName.PROGRAM_URL_TITLE, programUrlTitle);
    }

    // Note's Overrides

    @Override
    public String getContent() {
        return (String) getProperty(NotePropertyName.PROGRAM_SYNOPSIS);
    }

    @Override
    public String getDescription() {
        return (String) getProperty(NotePropertyName.PROGRAM_CAPTION);
    }

    @Override
    public ImageAsset getNoteImageAsset() {
        return (ImageAsset) getProperty(NotePropertyName.PROGRAM_IMAGE);
    }

}

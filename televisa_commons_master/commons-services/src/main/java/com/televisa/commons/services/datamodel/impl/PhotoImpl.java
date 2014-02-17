/*
 * PhotoImpl.java
 *
 * The photo data model.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel.impl;

import com.day.cq.wcm.api.Page;
import com.televisa.commons.services.datamodel.NotePropertyName;
import com.televisa.commons.services.datamodel.Photo;
import com.televisa.commons.services.datamodel.objects.ImageAsset;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * Photo Management Data Access
 *
 * The photo data model.
 *
 * Changes History:
 *
 *         2013-02-21 gescobar Added the image assets array
 *         2013-02-18 gescobar Updated the photo properties
 *         2013-02-04 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class PhotoImpl extends NoteImpl implements Photo {

    public PhotoImpl(ResourceResolver resourceResolver, Page page) {
        super(resourceResolver, page);
    }

    protected void readProperties() {
        super.readProperties();
        putProperty(NotePropertyName.IMAGE_REFERENCE_URL, propertiesReader.getImageAsset());
        putProperty(NotePropertyName.IMAGE_SHORT_DESCRIPTION, propertiesReader.getImageShortDescription());
        putProperty(NotePropertyName.IMAGE_TOPIC, propertiesReader.getImageTopic());
        putProperty(NotePropertyName.IMAGE_REFERENCES_PATH, propertiesReader.getImageAssets());

        putProperty(NotePropertyName.NOTE_IMAGE_ASSET, getProperty(NotePropertyName.IMAGE_REFERENCE_URL));
        putProperty(NotePropertyName.NOTE_DESCRIPTION, getProperty(NotePropertyName.IMAGE_SHORT_DESCRIPTION));
        putProperty(NotePropertyName.NOTE_TOPIC, getProperty(NotePropertyName.IMAGE_TOPIC));

        ImageAsset imageAsset = getImageAsset();
        if (imageAsset != null) {
            String fileReference = imageAsset.getPath();
            if (fileReference != null) {
                setRenditions(fileReference);
            }
        }
    }

    @Override
    public String getDescription() {
        return (String) getProperty(NotePropertyName.IMAGE_SHORT_DESCRIPTION);
    }

    @Override
    public void setDescription(String description) {
        putProperty(NotePropertyName.IMAGE_SHORT_DESCRIPTION, description);
    }

    @Override
    public ImageAsset getImageAsset() {
        return (ImageAsset) getProperty(NotePropertyName.IMAGE_REFERENCE_URL);
    }

    @Override
    public void setImageAsset(ImageAsset imageReference) {
        putProperty(NotePropertyName.IMAGE_REFERENCE_URL, imageReference);
    }

    @Override
    public String getImageShortDescription() {
        return (String) getProperty(NotePropertyName.IMAGE_SHORT_DESCRIPTION);
    }

    @Override
    public void setImageShortDescription(String imageShortDescription) {
        putProperty(NotePropertyName.IMAGE_SHORT_DESCRIPTION, imageShortDescription);
    }

    @Override
    public String getImageTopic() {
        return (String) getProperty(NotePropertyName.IMAGE_TOPIC);
    }

    @Override
    public void setImageTopic(String imageTopic) {
        putProperty(NotePropertyName.IMAGE_TOPIC, imageTopic);
    }

    @Override
    public ImageAsset[] getImageAssets() {
        return (ImageAsset[]) getProperty(NotePropertyName.IMAGE_REFERENCES_PATH);
    }

    @Override
    public void setImageAssets(ImageAsset[] imageAssets) {
        putProperty(NotePropertyName.IMAGE_REFERENCES_PATH, imageAssets);
    }

}

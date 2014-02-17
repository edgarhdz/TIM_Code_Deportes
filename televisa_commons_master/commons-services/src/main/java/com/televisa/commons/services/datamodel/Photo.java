/*
 * Photo.java
 *
 * The photo data model.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel;

import com.televisa.commons.services.datamodel.objects.ImageAsset;

/**
 * The Photo Data Model Interface
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
public interface Photo extends Note {

    ImageAsset getImageAsset();

    void setImageAsset(ImageAsset imageAsset);

    String getImageShortDescription();

    void setImageShortDescription(String imageShortDescription);

    String getImageTopic();

    void setImageTopic(String imageTopic);

    ImageAsset[] getImageAssets();

    void setImageAssets(ImageAsset[] imageAssets);

}

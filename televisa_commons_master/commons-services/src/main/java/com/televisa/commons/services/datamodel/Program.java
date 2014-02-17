/*
 * Program.java
 *
 * The program data model interface.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel;

import com.televisa.commons.services.datamodel.objects.ImageAsset;

/**
 * The Program Data Model Interface
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
public interface Program extends Note {

    String getProgramName();

    void setProgramName(String programName);

    String getProgramCaption();

    void setProgramCaption(String programCaption);

    String getProgramSynopsis();

    void setProgramSynopsis(String programSynopsis);

    String getProgramChannel();

    void setProgramChannel(String programChannel);

    String getProgramSchedule();

    void setProgramSchedule(String programSchedule);

    ImageAsset getProgramImage();

    void setProgramImage(ImageAsset programImage);

    String getProgramUrl();

    void setProgramUrl(String programUrl);

    String getProgramUrlTitle();

    void setProgramUrlTitle(String programUrlTitle);

}

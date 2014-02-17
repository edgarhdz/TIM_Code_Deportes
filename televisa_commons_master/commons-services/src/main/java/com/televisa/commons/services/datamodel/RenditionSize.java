/*
 * RenditionSize.java
 *
 * The size of the different rendition of the data model images.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel;

/**
 * Rendition Size
 *
 * The size of the different rendition sizes of the data model images.
 *
 * Changes History:
 *
 *         2013-02-04 gescobar Added utility path
 *         2013-01-24 Jbarrera - Added rendition sizes
 *         2013-01-15 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public enum RenditionSize {

    RENDITION_PATH ("jcr:content/renditions/cq5dam.thumbnail"),

    RENDITION_1900_2420 ("jcr:content/renditions/cq5dam.thumbnail.1900.2420.png"),
    RENDITION_610_250 ("jcr:content/renditions/cq5dam.thumbnail.610.250.png"),
    RENDITION_610_200 ("jcr:content/renditions/cq5dam.thumbnail.610.200.png"),
    RENDITION_610_150 ("jcr:content/renditions/cq5dam.thumbnail.610.150.png"),
    RENDITION_300_350 ("jcr:content/renditions/cq5dam.thumbnail.300.350.png"),
    RENDITION_300_168 ("jcr:content/renditions/cq5dam.thumbnail.300.168.png"),
    RENDITION_300_150 ("jcr:content/renditions/cq5dam.thumbnail.300.150.png"),
    RENDITION_120_90 ("jcr:content/renditions/cq5dam.thumbnail.120.90.png"),
    RENDITION_70_70 ("jcr:content/renditions/cq5dam.thumbnail.70.70.png"),
    RENDITION_140_100 ("jcr:content/renditions/cq5dam.thumbnail.140.100.png"),
    RENDITION_1024_768 ("jcr:content/renditions/cq5dam.thumbnail.1024.768.jpeg"),
    RENDITION_624_468 ("jcr:content/renditions/cq5dam.thumbnail.624.468.jpeg"),
    RENDITION_408_306 ("jcr:content/renditions/cq5dam.thumbnail.408.306.jpeg"),
    RENDITION_136_102 ("jcr:content/renditions/cq5dam.thumbnail.136.102.png"),
    RENDITION_624_351 ("jcr:content/renditions/cq5dam.thumbnail.624.351.jpeg"),
    RENDITION_192_108 ("jcr:content/renditions/cq5dam.thumbnail.192.108.png"),
    RENDITION_136_76 ("jcr:content/renditions/cq5dam.thumbnail.136.76.png"),
    RENDITION_63_63 ("jcr:content/renditions/cq5dam.thumbnail.63.63.png"),
    RENDITION_1024_576 ("jcr:content/renditions/cq5dam.thumbnail.1024.576.jpeg"),
    RENDITION_768_768 ("jcr:content/renditions/cq5dam.thumbnail.768.768.jpeg"),
    RENDITION_768_576 ("jcr:content/renditions/cq5dam.thumbnail.768.576.jpeg"),
    RENDITION_84_63 ("jcr:content/renditions/cq5dam.thumbnail.84.63.png");

    private String name;

    private RenditionSize(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

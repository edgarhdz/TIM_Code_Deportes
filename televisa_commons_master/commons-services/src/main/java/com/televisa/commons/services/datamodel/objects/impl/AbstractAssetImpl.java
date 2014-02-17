/*
 * AssetImpl.java
 *
 * The generic asset container.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel.objects.impl;

import com.televisa.commons.services.datamodel.objects.Asset;
import com.televisa.commons.services.datamodel.objects.Rendition;
import com.televisa.commons.services.utilities.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic Asset Implementation
 *
 * Changes History:
 *
 *         2013-02-21 gescobar Added the description setter
 *         2013-02-06 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public abstract class AbstractAssetImpl implements Asset {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractAssetImpl.class);

    private com.day.cq.dam.api.Asset asset;
    private String description;
    private long width;
    private long height;

    private long getLongMetadataValue(com.day.cq.dam.api.Asset asset, String name) {
        if (asset != null) {
            try {
                String value = asset.getMetadataValue(name);
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                LOG.debug(e.getMessage(),e);
            }
        }
        return 0;
    }

    public AbstractAssetImpl(com.day.cq.dam.api.Asset asset) {
        this.asset = asset;
        if (asset != null) {
            this.description = asset.getMetadataValue("dc:description");
            this.width = getLongMetadataValue(asset, "tiff:ImageWidth");
            this.height = getLongMetadataValue(asset, "tiff:ImageLength");
        }
    }

    @Override
    public com.day.cq.dam.api.Asset getAsset() {
        return asset;
    }

    @Override
    public String getPath() {
        if (asset != null) {
            return asset.getPath();
        }
        return null;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Rendition getRendition(int widthRendition, int heightRendition) {
        if (this.asset != null) {
            com.day.cq.dam.api.Rendition rendition = new Utilities().getRendition(this.asset, widthRendition, heightRendition);
            if (rendition != null) {
                return new RenditionImpl(rendition);
            }
        }
        return null;
    }

    @Override
    public List<Rendition> getRenditions() {
        List<Rendition> list = new ArrayList<Rendition>();
        if (this.asset != null) {
            for (com.day.cq.dam.api.Rendition rendition : this.asset.getRenditions()) {
                list.add(new RenditionImpl(rendition));
            }
        }
        return list;
    }

    public long getWidth() {
        return width;
    }

    public long getHeight() {
        return height;
    }

    public float getAspectRatio() {
        return width / height;
    }

}

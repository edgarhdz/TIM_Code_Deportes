/*
 * Rendition.java
 *
 * The generic rendition implementation.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel.objects.impl;

import com.televisa.commons.services.datamodel.objects.Rendition;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

/**
 * Generic Rendition Implementation.
 *
 * Changes History:
 *
 *         2013-02-12 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class RenditionImpl implements Rendition {

    private static final Logger LOG = LoggerFactory.getLogger(RenditionImpl.class);

    private com.day.cq.dam.api.Rendition rendition;

    private long width = -1;
    private long height = -1;

    public RenditionImpl(com.day.cq.dam.api.Rendition rendition) {
        this.rendition = rendition;
    }

    @Override
    public com.day.cq.dam.api.Rendition getRendition() {
        return rendition;
    }

    @Override
    public String getPath() {
        if (rendition != null) {
            return rendition.getPath();
        }
        return null;
    }

    @Override
    public long getWidth() {
        if (width == -1) {
            readDimension();
        }
        return width;
    }

    @Override
    public long getHeight() {
        if (height == -1) {
            readDimension();
        }
        return height;
    }

    @Override
    public float getAspectRatio() {
        return width / height;
    }

    /**
     * Read the image dimensions from the rendition name, or from the JCR, or from the image stream itself.
     * Sample : cq5dam.web.360.240.jpeg.
     */
    private void readDimension() {
        Pattern pattern = Pattern.compile("^cq5dam\\.[^\\d]*?(\\d.*?)\\.(\\d.*?)\\..*?$");
        Matcher matcher = pattern.matcher(rendition.getName());
        if (matcher.matches() && matcher.groupCount() > 1) {
            try {
                width = Integer.parseInt(matcher.group(1));
                height = Integer.parseInt(matcher.group(2));
            } catch (NumberFormatException e) {
                LOG.debug(e.getMessage(), e);
            }
        }

        Dimension dimension;
        if (width == -1 || height == -1) {
            dimension = readDimensionFromJCR();
            if (dimension == null) {
                dimension = readBufferedImageDimension();
                if ( dimension != null) {
                    width = dimension.width;
                    height = dimension.height;
                    writeDimensionToJCR(dimension);
                }
            } else {
                width = dimension.width;
                height = dimension.height;
            }
        }
    }

    /**
     * Read the dimension from the image stream directly.
     * @return a new dimension from the image, or null if it can't be read
     */
    private Dimension readBufferedImageDimension() {
        try {
            BufferedImage image = ImageIO.read(rendition.getStream());
            if (image != null) {
                Dimension dimension = new Dimension();
                dimension.width = image.getWidth();
                dimension.height = image.getHeight();
                return dimension;
            }
        } catch (IOException e) {
            LOG.debug(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Read the dimensions from the JCR in the jcr:description property.
     * We use that property because the node type of the rendition is nt:File which just allow mix:title mixing.
     * @return a new dimension, or null if it can't be read
     */
    private Dimension readDimensionFromJCR() {
        try {
            if (rendition != null) {
                ResourceResolver resolver = rendition.getResourceResolver();
                Resource resource = resolver.resolve(rendition.getPath());
                Node node = resource.adaptTo(Node.class);

                Property property = node.getProperty("jcr:description");
                if (property != null && !property.isMultiple()) {
                    String[] string = property.getString().split(":");
                    if (string.length >= 2) {
                        Dimension dimension = new Dimension();
                        dimension.width = Integer.parseInt(string[0]);
                        dimension.height = Integer.parseInt(string[1]);
                        return dimension;
                    }
                }
            }
        } catch (RepositoryException e) {
            LOG.debug(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Write the dimensions to the JCR in the jcr:description property.
     * We use that property because the node type of the rendition is nt:File which just allow mix:title mixing.
     * @param dimension the dimension to write
     */
    private void writeDimensionToJCR(Dimension dimension) {
        try {
            if (rendition != null) {
                ResourceResolver resolver = rendition.getResourceResolver();
                Resource resource = resolver.resolve(rendition.getPath());
                Node node = resource.adaptTo(Node.class);

                if (node.canAddMixin("mix:title")) {
                    node.addMixin("mix:title");
                }
                node.setProperty("jcr:description", String.format("%d:%d", dimension.width, dimension.height));
                node.getSession().save();
            }
        } catch (RepositoryException e) {
            LOG.debug(e.getMessage(), e);
        }
    }

}

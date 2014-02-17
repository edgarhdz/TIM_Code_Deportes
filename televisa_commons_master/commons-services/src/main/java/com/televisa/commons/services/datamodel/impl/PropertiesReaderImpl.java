/*
 * PropertiesReaderImpl.java
 *
 * Read the properties from a CQ Page.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel.impl;

import com.day.cq.dam.commons.util.DamUtil;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.televisa.commons.services.dataaccess.impl.NoteManagerImpl;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.datamodel.NotePropertyName;
import com.televisa.commons.services.datamodel.Program;
import com.televisa.commons.services.datamodel.PropertiesReader;
import com.televisa.commons.services.datamodel.objects.ImageAsset;
import com.televisa.commons.services.datamodel.objects.VideoAsset;
import com.televisa.commons.services.datamodel.objects.impl.ImageAssetImpl;
import com.televisa.commons.services.datamodel.objects.impl.VideoAssetImpl;
import com.televisa.commons.services.utilities.ApplicationProperties;
import com.televisa.commons.services.dataaccess.NoteManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Properties Reader Implementation
 *
 * Read the properties from a CQ Page.
 *
 * Changes History:
 *
 *         2013-03-15 gescobar Updated the video implementation.
 *         2013-03-06 gescobar Updated the general video data model.
 *         2013-02-21 gescobar Added the image assets array
 *         2013-02-20 gescobar Added the recursively read by name and type.
 *         2013-02-18 gescobar Updated the photo properties
 *         2013-02-12 gescobar Asset reader methods update.
 *         2013-02-04 gescobar Updated the properties names, generalized.
 *         2013-01-29 gescobar Added topic property
 *         2013-01-16 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class PropertiesReaderImpl implements PropertiesReader {

    private static final Logger LOG = LoggerFactory.getLogger(PropertiesReaderImpl.class);

    private final ResourceResolver resourceResolver;
    private final Page page;

    /**
     * The properties reader constructor.
     *
     * @param resourceResolver the Sling Resource Resolver
     * @param page the page to the main article
     */
    public PropertiesReaderImpl(ResourceResolver resourceResolver, Page page) {
        this.resourceResolver = resourceResolver;
        this.page = page;
    }

    /**
     * Read a property from the selected property path and name.
     *
     * @param path the path to the node
     * @param name the note property name
     * @return the property in the path and the name
     * @throws javax.jcr.PathNotFoundException if the path does not exist
     * @throws javax.jcr.RepositoryException if a repository exception occurs
     */
    protected Property getGenericProperty(String path, String name) throws PathNotFoundException, RepositoryException {
        Node node = page.adaptTo(Node.class);
        if (node != null && node.hasNode(path)) {
            Node child = node.getNode(path);
            if (child != null && child.hasProperty(name)) {
                return child.getProperty(name);
            }
        }
        return null;
    }

    /**
     * Read a property from the selected NotePropertyName.
     *
     * @param notePropertyName the note property name
     * @return the value of the property
     * @throws javax.jcr.PathNotFoundException if the path does not exist
     * @throws javax.jcr.RepositoryException if a repository exception occurs
     */
    protected Property getGenericProperty(NotePropertyName notePropertyName) throws PathNotFoundException, RepositoryException {
        Node node = page.adaptTo(Node.class);
        if (notePropertyName.getPropertyPath() != null) {
            if (node != null && node.hasNode(notePropertyName.getPropertyPath())) {
                Node childNode = node.getNode(notePropertyName.getPropertyPath());
                if (childNode != null && childNode.hasProperty(notePropertyName.getPropertyName())) {
                    return childNode.getProperty(notePropertyName.getPropertyName());
                }
            }
        }
        return null;
    }

    /**
     * Read all the properties from a selected NotePropertyName parameter.
     *
     * @param notePropertyName the NotePropertyName to use
     * @return all the properties of the selected NotePropertyName as a property iterator
     * @throws javax.jcr.PathNotFoundException if the path doesn't exist
     * @throws javax.jcr.RepositoryException if a repository exception occurs
     */
    protected PropertyIterator getGenericProperties(NotePropertyName notePropertyName) throws PathNotFoundException, RepositoryException {
        Node node = page.adaptTo(Node.class);
        if (notePropertyName.getPropertyPath() != null && node != null) {
            if (node.hasNode(notePropertyName.getPropertyPath())) {
                Node childNode = node.getNode(notePropertyName.getPropertyPath());
                return childNode.getProperties();
            }
        }
        return null;
    }

    /**
     * Read all the properties from a selected NotePropertyName parameter.
     *
     * @param notePropertyName the NotePropertyName to use
     * @return all the properties of the selected NotePropertyName as a value map
     * @throws javax.jcr.RepositoryException if a repository exception occurs
     */
    protected ValueMap getGenericMapProperties(NotePropertyName notePropertyName) throws RepositoryException {
        Node node = page.adaptTo(Node.class);
        if (notePropertyName.getPropertyPath() != null) {
            if (node != null && node.hasNode(notePropertyName.getPropertyPath())) {
                Node childNode = node.getNode(notePropertyName.getPropertyPath());
                Resource childResource = resourceResolver.resolve(childNode.getPath());
                if (childResource != null) {
                    return childResource.adaptTo(ValueMap.class);
                }
            }
        }
        return null;
    }

    /**
     * Read a property value, appending multiple values into a buffer.
     *
     * @param property the property to read values from
     * @return the value of the property read
     * @throws javax.jcr.ValueFormatException if an error occurs
     * @throws javax.jcr.RepositoryException if an error occurs
     */
    private String getStringFromProperty(Property property) throws ValueFormatException, RepositoryException {
        if (property.isMultiple()) {
            StringBuilder builder = new StringBuilder();
            for (Value value : property.getValues()) {
                builder.append(value.getString()).append("\n");
            }
            return builder.toString();
        }
        return property.getString();
    }

    /**
     * Recursively get the value from a property with the defined type and name.
     *
     * @param notePropertyName the note property name
     * @param node the node to read values from
     * @param builder a string builder to store the result
     * @throws javax.jcr.RepositoryException if an error occurs
     */
    private void getTextPropertyRecursive(NotePropertyName notePropertyName, Node node, StringBuilder builder) throws RepositoryException {
        NodeIterator nodes = node.getNodes();
        if (nodes != null) {
            while (nodes.hasNext()) {
                Node child = nodes.nextNode();
                PropertyIterator properties = child.getProperties();
                while (properties.hasNext()) {
                    Property property = properties.nextProperty();
                    if (property.getType() == notePropertyName.getPropertyType()
                            && property.getName().equalsIgnoreCase(notePropertyName.getPropertyName())) {
                        builder.append(getStringFromProperty(property));
                    }
                }
                getTextPropertyRecursive(notePropertyName, child, builder);
            }
        }
    }

    /**
     * Generic Type Properties Reader.
     *
     * @param notePropertyName the NotePropertyName to read
     * @return the value of the property
     */
    protected String getGenericTypeProperty(NotePropertyName notePropertyName) {
        StringBuilder builder = new StringBuilder();
        Node node = page.adaptTo(Node.class);
        if (notePropertyName.getPropertyPath() != null) {
            try {
                if (node != null && node.hasNode(notePropertyName.getPropertyPath())) {
                    Node childNode = node.getNode(notePropertyName.getPropertyPath());
                    getTextPropertyRecursive(notePropertyName, childNode, builder);
                }
            } catch (RepositoryException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return builder.toString();
    }

    /**
     * Generic String Properties Reader.
     *
     * @param notePropertyName the NotePropertyName to read
     * @return the value of the property
     */
    protected String getGenericStringProperty(NotePropertyName notePropertyName) {
        try {
            Property property = getGenericProperty(notePropertyName);
            if (property != null) {
                if (property.isMultiple()) {
                    // Only return the first value encountered.
                    for (Value value : property.getValues()) {
                        return value.getString();
                    }
                } else {
                    return property.getString();
                }
            }
        } catch (PathNotFoundException e) {
            LOG.error(e.getMessage(), e);
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Generic Calendar Properties Reader.
     *
     * @param notePropertyName the NotePropertyName to read
     * @return the value of the property
     */
    protected Calendar getGenericCalendarProperty(NotePropertyName notePropertyName) {
        try {
            Property property = getGenericProperty(notePropertyName);
            if (property != null && !property.isMultiple()) {
                if (property.isMultiple()) {
                    // Only return the first value encountered.
                    for (Value value : property.getValues()) {
                        return value.getDate();
                    }
                } else {
                    return property.getDate();
                }
            }
        } catch (PathNotFoundException e) {
            LOG.error(e.getMessage(), e);
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Generic Tag Properties Reader.
     *
     * @param notePropertyName the NotePropertyName to read
     * @return the value of the property
     */
    protected Tag getGenericTagProperty(NotePropertyName notePropertyName) {
        try {
            Property property = getGenericProperty(notePropertyName);
            if (property != null) {
                if (property.isMultiple()) {
                    // Only return the first value encountered.
                    for (Value value : property.getValues()) {
                        return translateTag(value.getString());
                    }
                } else {
                    return translateTag(property.getString());
                }
            }
        } catch (PathNotFoundException e) {
            LOG.error(e.getMessage(), e);
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Generic Image Asset Reader.
     * The DamUtil.resolveToAsset method retains in the Asset the ResourceResolver used to obtain it.
     *
     * @param path the path to the DAM asset
     * @return a new ImageAsset for the DAM asset
     */
    protected ImageAsset getGenericImageAsset(String path) {
        Resource resource = resourceResolver.getResource(path);
        if (resource != null) {
            com.day.cq.dam.api.Asset asset = DamUtil.resolveToAsset(resource);
            if (asset != null) {
                return new ImageAssetImpl(asset);
            }
        }
        return null;
    }

    /**
     * Generic Video Asset Reader.
     * The DamUtil.resolveToAsset method retains in the Asset the ResourceResolver used to obtain it.
     *
     * @param path the path to the DAM asset
     * @return a new VideoAsset for the DAM asset
     */
    protected VideoAsset getGenericVideoAsset(String path) {
        Resource resource = resourceResolver.getResource(path);
        if (resource != null) {
            com.day.cq.dam.api.Asset asset = DamUtil.resolveToAsset(resource);
            if (asset != null) {
                return new VideoAssetImpl(asset);
            }
        }
        return null;
    }

    /**
     * Translates a String Tag name to a Tag Class
     *
     * @param tag the string tag name
     * @return the tag class
     */
    private Tag translateTag(String tag) {
        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
        return tagManager.resolve(tag);
    }

    /**
     * Get a page title from a path.
     *
     * @param path the page to get a title from
     * @return the title of the page
     */
    private String getPageTitleFromPath(String path) {
        if (path != null && !path.isEmpty()) {
            Resource resource = this.resourceResolver.resolve(path);
            if (resource != null && resource.isResourceType("cq:Page")) {
                Page page = resource.adaptTo(Page.class);
                if (page != null && page.getTitle() != null && !page.getTitle().isEmpty()) {
                    return page.getTitle();
                }
            }
        }
        return null;
    }

	/* NOTE */

    /**
     * The expression will search in the data content path for [nacional]:
     * /content/televisa/noticieros/[nacional]/1302/lolita_ayala
     * @return the note category
     */
    @Override
    public String getNoteCategory() {
        String title = getPageTitleFromPath(getNoteCategoryUrl());
        if (title != null) {
            return title;
        }
        Pattern pattern = Pattern.compile(String.format("^%s[^/]+/([^/]+).*$", ApplicationProperties.getDataContentPath()));
        Matcher matcher = pattern.matcher(page.getPath());
        if (matcher.matches() && matcher.groupCount() > 0) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * The expression will search in the data content path for [/content/televisa/noticieros/nacional]:
     * [/content/televisa/noticieros/nacional]/1302/lolita_ayala
     * Note : It will not include the slash [/]
     * @return the note category url
     */
    @Override
    public String getNoteCategoryUrl() {
        Pattern pattern = Pattern.compile(String.format("^(%s[^/]+/[^/]+).*$", ApplicationProperties.getDataContentPath()));
        Matcher matcher = pattern.matcher(page.getPath());
        if (matcher.matches() && matcher.groupCount() > 0) {
            return matcher.group(1);
        }
        return null;
    }

    @Override
    public String getNoteTitle() {
        return page.getTitle();
    }

    @Override
    public String getNoteTitleSEO() {
        return getGenericStringProperty(NotePropertyName.NOTE_TITLE_SEO);
    }

    @Override
    public String getNoteUrl() {
        return page.getPath();
    }

    @Override
    public String getNoteExternalUrl() {
        return getGenericStringProperty(NotePropertyName.NOTE_EXTERNAL_URL);
    }

    @Override
    public Tag[] getNoteKeywords() {
        return page.getTags();
    }

    @Override
    public Calendar getNotePubDate() {
        return getGenericCalendarProperty(NotePropertyName.NOTE_PUB_DATE);
    }

    @Override
    public Calendar getNoteFirstPubDate() {
        return getGenericCalendarProperty(NotePropertyName.NOTE_PUB_DATE);
    }

    @Override
    public Calendar getNoteCrDate() {
        return page.getLastModified();
    }

    @Override
    public Calendar getNoteFechaModificacion() {
        return getGenericCalendarProperty(NotePropertyName.NOTE_FECHA_MODIFICACION);
    }

    /**
     * The expression will search in the data content path for [noticieros]:
     * /content/televisa/[noticieros]/nacional/1302/lolita_ayala
     * @return the note channel
     */
    @Override
    public String getNoteChannel() {
        String title = getPageTitleFromPath(getNoteChannelUrl());
        if (title != null) {
            return title;
        }
        Pattern pattern = Pattern.compile(String.format("^%s([^/]+).*$", ApplicationProperties.getDataContentPath()));
        Matcher matcher = pattern.matcher(page.getPath());
        if (matcher.matches() && matcher.groupCount() > 0) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * The expression will search in the data content path for [/content/televisa/noticieros/nacional]:
     * [/content/televisa/noticieros]/nacional/1302/lolita_ayala
     * Note : It will not include the slash [/]
     * @return the note category url
     */
    @Override
    public String getNoteChannelUrl() {
        Pattern pattern = Pattern.compile(String.format("^(%s[^/]+).*$", ApplicationProperties.getDataContentPath()));
        Matcher matcher = pattern.matcher(page.getPath());
        if (matcher.matches() && matcher.groupCount() > 0) {
            return matcher.group(1);
        }
        return null;
    }

    @Override
    public String getNoteSummary() {
        return getGenericStringProperty(NotePropertyName.NOTE_SUMMARY);
    }

    @Override
    public String getNoteTipo() {
        return getGenericStringProperty(NotePropertyName.NOTE_TIPO);
    }

    @Override
    public String getNoteStringTags() {
        StringBuilder builder = new StringBuilder();
        Tag[] tags = page.getTags();
        for (int i = 0; i < tags.length; i++) {
            builder.append(tags[i].getName());
            if (i + 1 < tags.length) {
                builder.append(";");
            }
        }
        return builder.toString();
    }

    @Override
    public Tag[] getNoteTags() {
        return page.getTags();
    }

    @Override
    public String getNoteContent() {
        return getGenericTypeProperty(NotePropertyName.NOTE_CONTENT);
    }

    @Override
    public String getNoteDescription() {
        return getGenericStringProperty(NotePropertyName.NOTE_DESCRIPTION);
    }

    @Override
    public String getNoteImageFileReference() {
        return getGenericStringProperty(NotePropertyName.NOTE_IMAGE_FILE_REFERENCE);
    }

    @Override
    public String getNoteImageLinkURL() {
        return getGenericStringProperty(NotePropertyName.NOTE_IMAGE_LINK_URL);
    }

    @Override
    public String getNoteAuthor() {
        return getGenericStringProperty(NotePropertyName.NOTE_AUTHOR);
    }

    @Override
    public String getNoteSource() {
        return getGenericStringProperty(NotePropertyName.NOTE_SOURCE);
    }

    @Override
    public String getNoteStyle() {
        return getGenericStringProperty(NotePropertyName.NOTE_STYLE);
    }

    @Override
    public String getNoteDateCreated() {
        return getGenericStringProperty(NotePropertyName.NOTE_DATE_CREATED);
    }

    @Override
    public String getNoteTemplate() {
        if (page.getTemplate() != null) {
            return page.getTemplate().getName();
        }
        return null;
    }

    @Override
    public String getNoteTopic() {
        Tag tag = getGenericTagProperty(NotePropertyName.NOTE_TOPIC);
        if (tag != null) {
            return tag.getTitle();
        }
        return null;
    }

    @Override
    public String getVideoTopic() {
        Tag tag = getGenericTagProperty(NotePropertyName.VIDEO_TOPIC);
        if (tag != null) {
            return tag.getTitle();
        }
        return null;
    }

    @Override
    public String getNoteIdentifier() {
        try {
            Node node = page.adaptTo(Node.class);
            return node.getIdentifier();
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public ImageAsset getNoteImageAsset() {
        String path = getGenericStringProperty(NotePropertyName.NOTE_IMAGE_ASSET);
        if (path != null) {
            return getGenericImageAsset(path);
        }
        return null;
    }

	/* ARTICLE */

    @Override
    public String getArticleImageLinkUrl() {
        return getGenericStringProperty(NotePropertyName.ARTICLE_IMAGE_LINK_URL);
    }

    @Override
    public String getArticleStage() {
        return getGenericStringProperty(NotePropertyName.ARTICLE_STAGED);
    }

	/* PHOTO */

    @Override
    public ImageAsset getImageAsset() {
        String path = getGenericStringProperty(NotePropertyName.IMAGE_REFERENCE_URL);
        if (path != null) {
            return getGenericImageAsset(path);
        }
        return null;
    }

    @Override
    public String getImageShortDescription() {
        return getGenericStringProperty(NotePropertyName.IMAGE_SHORT_DESCRIPTION);
    }

    @Override
    public String getImageTopic() {
        Tag tag = getGenericTagProperty(NotePropertyName.IMAGE_TOPIC);
        if (tag != null) {
            return tag.getTitle();
        }
        return null;
    }

    /**
     * Read the images and its descriptions, observe that multiple values could not coincide on the
     * quantity of references and its descriptions, but has to coincide on the isMultiple statement,
     * for multiple images should be multiple descriptions.
     */
    @Override
    public ImageAsset[] getImageAssets() {
        ImageAsset[] assets = null;
        try {
            Property propertyPath = getGenericProperty(NotePropertyName.IMAGE_REFERENCES_PATH);
            Property propertyDescription = getGenericProperty(NotePropertyName.IMAGE_REFERENCES_DESCRIPTION);

            if (propertyPath != null) {
                if (propertyPath.isMultiple()) {
                    Value[] valuePaths = propertyPath.getValues();
                    Value[] valueDescriptions = null;
                    if (propertyDescription != null) {
                        valueDescriptions = propertyDescription.getValues();
                    }

                    int index = 0;
                    List<ImageAsset> list = new ArrayList<ImageAsset>();
                    for (Value path : valuePaths) {
                        ImageAsset imageAsset = getGenericImageAsset(path.getString());
                        if (imageAsset != null) {
                            if (valueDescriptions != null && index < valueDescriptions.length) {
                                imageAsset.setDescription(valueDescriptions[index++].getString());
                            }
                            list.add(imageAsset);
                        }
                    }

                    assets = list.toArray(new ImageAsset[list.size()]);
                } else {
                    ImageAsset imageAsset = getGenericImageAsset(propertyPath.getString());
                    if (imageAsset != null) {
                        if (propertyDescription != null && !propertyDescription.isMultiple()) {
                            imageAsset.setDescription(propertyDescription.getString());
                        }
                        assets = new ImageAsset[] { imageAsset };
                    }
                }
            }
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
        }
        return assets;
    }

	/* VIDEO */

    @Override
    public String getVideoTitle() {
        String title = getGenericStringProperty(NotePropertyName.VIDEO_TITLE);
        if (title == null) {
            title = getNoteTitle();
        }
        return title;
    }

    @Override
    public VideoAsset getVideoAsset() {
        String path = getGenericStringProperty(NotePropertyName.VIDEO_REFERENCE_URL);
        if (path != null) {
            return getGenericVideoAsset(path);
        }
        return null;
    }

    @Override
    public String getVideoDuration() {
        return getGenericStringProperty(NotePropertyName.VIDEO_DURATION);
    }

    @Override
    public String getVideoChapter() {
        return getGenericStringProperty(NotePropertyName.VIDEO_CHAPTER);
    }

    @Override
    public String getVideoSeason() {
        return getGenericStringProperty(NotePropertyName.VIDEO_SEASON);
    }

    @Override
    public String getVideoProgram() {
        String program = getGenericStringProperty(NotePropertyName.VIDEO_PROGRAM_NOTE);
        if (program == null) {
            Page parent = page.getParent(2);
            if (parent != null) {
                // Instantiate the NoteManager without PageManager and TagManager, since it
                // isn't used, to avoid the multi threaded environment of the service.
                NoteManager<Note> manager = new NoteManagerImpl(resourceResolver, null, null);
                Note note = manager.getNoteImplementation(parent);
                if (note != null && note.isProgram()) {
                    Program programNote = ((Program) note);
                    return programNote.getProgramUrlTitle();
                }
            }
        }
        return program;
    }

    @Override
    public String getVideoProgramName() {
        String program = getGenericStringProperty(NotePropertyName.VIDEO_PROGRAM_NAME);
        if (program == null) {
            Page parent = page.getParent(2);
            if (parent != null) {
                // Instantiate the NoteManager without PageManager and TagManager, since it
                // isn't used, to avoid the multi threaded environment of the service.
                NoteManager<Note> manager = new NoteManagerImpl(resourceResolver, null, null);
                Note note = manager.getNoteImplementation(parent);
                if (note != null && note.isProgram()) {
                    Program programNote = ((Program) note);
                    return programNote.getProgramName();
                }
            }
        }
        return program;
    }

    @Override
    public String getVideoSummary() {
        return getGenericStringProperty(NotePropertyName.VIDEO_SUMMARY);
    }

    @Override
    public String getVideoTooltip() {
        return getGenericStringProperty(NotePropertyName.VIDEO_TOOLTIP);
    }

    @Override
    public String getVideoProgramUrl() {
        Page parent = page.getParent(2);
        if (parent != null) {
            // Instantiate the NoteManager without PageManager and TagManager, since it
            // isn't used, to avoid the multi threaded environment of the service.
            NoteManager<Note> manager = new NoteManagerImpl(resourceResolver, null, null);
            Note note = manager.getNoteImplementation(parent);
            if (note != null && note.isProgram()) {
                Program programNote = ((Program) note);
                return programNote.getProgramUrl();
            }
        }
        return null;
    }

    @Override
    public String getVideoId() {
        String videoId = getGenericStringProperty(NotePropertyName.VIDEO_ID);
        if (videoId != null) {
            try {
                switch (VideoID.getVideoID(videoId)) {
                    case BRIGHTCOVE:
                        return getGenericStringProperty(NotePropertyName.VIDEO_ID_BRIGHTCOVE);
                    case YOUTUBE:
                        return getGenericStringProperty(NotePropertyName.VIDEO_ID_YOUTUBE);
                    case AKAMAI:
                        return getGenericStringProperty(NotePropertyName.VIDEO_ID_AKAMAI);
                    default:
                        return null;
                }
            } catch (IllegalArgumentException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    public String getVideoPlayer() {
        return getGenericStringProperty(NotePropertyName.VIDEO_PLAYER);
    }

    @Override
    public String getVideoReleaseDate() {
        return getGenericStringProperty(NotePropertyName.VIDEO_RELEASE_DATE);
    }

    @Override
    public String getVideoType() {
        return getGenericStringProperty(NotePropertyName.VIDEO_TYPE);
    }

    @Override
    public ImageAsset getVideoImageAsset() {
        String path = getGenericStringProperty(NotePropertyName.VIDEO_IMAGE_ASSET);
        if (path != null) {
            return getGenericImageAsset(path);
        }
        return null;
    }

    @Override
    public Program getVideoProgramNote() {
        Page parent = page.getParent(2);
        if (parent != null) {
            // Instantiate the NoteManager without PageManager and TagManager, since it
            // isn't used, to avoid the multi threaded environment of the service.
            NoteManager<Note> manager = new NoteManagerImpl(resourceResolver, null, null);
            Note note = manager.getNoteImplementation(parent);
            if (note != null && note.isProgram()) {
                return ((Program) note);
            }
        }
        return null;
    }

    @Override
    public ValueMap getBrightcoveProperties() {
        ValueMap map = null;
        try {
            map = getGenericMapProperties(NotePropertyName.VIDEO_BRIGHTCOVE_PROPERTIES);
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
        }
        return map;
    }

	/* PROGRAM */

    @Override
    public String getProgramName() {
        return getGenericStringProperty(NotePropertyName.PROGRAM_NAME);
    }

    @Override
    public String getProgramCaption() {
        return getGenericStringProperty(NotePropertyName.PROGRAM_CAPTION);
    }

    @Override
    public String getProgramSynopsis() {
        return getGenericStringProperty(NotePropertyName.PROGRAM_SYNOPSIS);
    }

    @Override
    public String getProgramChannel() {
        return getGenericStringProperty(NotePropertyName.PROGRAM_CHANNEL);
    }

    @Override
    public String getProgramSchedule() {
        return getGenericStringProperty(NotePropertyName.PROGRAM_SCHEDULE);
    }

    @Override
    public ImageAsset getProgramImage() {
        String path = getGenericStringProperty(NotePropertyName.PROGRAM_IMAGE);
        if (path != null) {
            return getGenericImageAsset(path);
        }
        return null;
    }

    @Override
    public String getProgramUrl() {
        return getGenericStringProperty(NotePropertyName.PROGRAM_URL);
    }

    @Override
    public String getProgramUrlTitle() {
        return getGenericStringProperty(NotePropertyName.PROGRAM_URL_TITLE);
    }

}

/*
 * NoteImpl.java
 *
 * The note data model implementation.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel.impl;

import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.televisa.commons.services.datamodel.*;
import com.televisa.commons.services.datamodel.objects.ImageAsset;
import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The Note Data Model Implementation
 *
 * Changes History:
 *
 *         2013-02-21 gescobar Added the image assets array
 *         2013-02-04 gescobar Added note type properties, added photo implementation
 *         2013-01-29 gescobar Added topic property
 *         2013-01-29 gescobar Properties Return Values
 *         2013-01-10 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class NoteImpl implements Note {

    private Map<String, Object> map = new HashMap<String, Object>();
    private Page page;

    protected ResourceResolver resourceResolver;
    protected PropertiesReader propertiesReader;

    public NoteImpl(ResourceResolver resourceResolver, Page page) {
        this.resourceResolver = resourceResolver;
        this.page = page;
        this.propertiesReader = new PropertiesReaderImpl(resourceResolver, page);
        readProperties();
    }

    /**
     * The method <code>getGenericPropety</code> recovers the value of a specific property of any node, if not found,
     * then return the page title, only if the property is equals to "jcr:title" or the jcr:created property if equals to "date".
     *
     * @param node the node parent.
     * @param nodeChildName the name of the child node, where we need get the value of the property.
     * @param property the name of the property.
     * @return the name of the property / title page.
     * @throws javax.jcr.RepositoryException
     */
    protected String getGenericPropety(Node node, String nodeChildName, String property) throws RepositoryException {
        String propertyValue = "";

        if ("..".equals(nodeChildName)) {
            if (node.getParent() != null) {
                Node nodeParent = node.getParent();
                if (nodeParent.hasProperty(property)) {
                    Property tempProperty = nodeParent.getProperty(property);
                    if (tempProperty.isMultiple()) {
                        for (Value value : tempProperty.getValues()) {
                            propertyValue = propertyValue.concat(value.getString()).concat(",");
                        }
                        if (propertyValue.lastIndexOf(",") == propertyValue.length()) {
                            propertyValue = propertyValue.substring(0, propertyValue.length() - 1);
                        }
                    } else {
                        propertyValue = nodeParent.getProperty(property).getValue().getString();
                    }
                }
            }
        } else {
            if (nodeChildName.isEmpty()) {
                if (node.hasProperty(property)) {
                    propertyValue = node.getProperty(property).getValue().getString();
                } else {
                    if ("date".equals(property)) {
                        String jcrCreated = node.getParent().getProperty("jcr:created").getValue().getString();
                        propertyValue = jcrCreated.substring(0, 10);
                    }
                }
            } else {
                if (node.hasNode(nodeChildName)) {
                    Node nodeChild = node.getNode(nodeChildName);
                    if (nodeChild.hasProperty(property)) {
                        propertyValue = nodeChild.getProperty(property).getValue().getString();
                    } else {
                        if ("jcr:title".equals(property)) {
                            propertyValue = this.page.getTitle();
                        }
                    }
                } else {
                    propertyValue = node.getParent().getProperty(property).getValue().getString();
                }
            }
        }

        return propertyValue;
    }

    /**
     * This method gets the value of all the text components on the parsys "body" of the article component.
     *
     * @param node the node parent.
     * @return the value of the text components.
     * @throws javax.jcr.RepositoryException
     */
    protected String getContentPropety(Node node) throws RepositoryException {
        String propertyValue = "";
        if (node.hasNode("body")) {
            Node bodyNode = node.getNode("body");
            Iterator<Node> iterator = bodyNode.getNodes();

            if (iterator != null) {
                while (iterator.hasNext()) {
                    Node textNode = iterator.next();
                    if (textNode.hasProperty("sling:resourceType")) {
                        String resourceType = textNode.getProperty("sling:resourceType").getValue().getString();
                        if ("/apps/televisa/components/content/generics/article/articleNote".equals(resourceType)) {
                            if (textNode.hasProperty("textblock")) {
                                String text = textNode.getProperty("textblock").getValue().getString();
                                propertyValue = new StringBuffer(propertyValue).append(text).toString();
                            }
                        }
                    }
                }
            }
        }
        return propertyValue;
    }

    protected void setRenditions(String path) {
        path = path.concat("/");
        for (RenditionSize renditionSize : RenditionSize.values()) {
            putProperty(renditionSize, path.concat(renditionSize.getName()));
        }
    }

    /**
     * Read the properties of the page.
     * Override to read extended properties.
     */
    protected void readProperties() {

        putProperty(NotePropertyName.NOTE_CATEGORY, this.propertiesReader.getNoteCategory());
        putProperty(NotePropertyName.NOTE_CATEGORY_URL, this.propertiesReader.getNoteCategoryUrl());
        putProperty(NotePropertyName.NOTE_TITLE, this.propertiesReader.getNoteTitle());
        putProperty(NotePropertyName.NOTE_TITLE_SEO, this.propertiesReader.getNoteTitleSEO());
        putProperty(NotePropertyName.NOTE_URL, this.propertiesReader.getNoteUrl());
        putProperty(NotePropertyName.NOTE_EXTERNAL_URL, this.propertiesReader.getNoteExternalUrl());
        putProperty(NotePropertyName.NOTE_KEYWORDS, this.propertiesReader.getNoteKeywords());
        putProperty(NotePropertyName.NOTE_PUB_DATE, this.propertiesReader.getNotePubDate());
        putProperty(NotePropertyName.NOTE_FIRST_PUB_DATE, this.propertiesReader.getNoteFirstPubDate());
        putProperty(NotePropertyName.NOTE_CR_DATE, this.propertiesReader.getNoteCrDate());
        putProperty(NotePropertyName.NOTE_FECHA_MODIFICACION, this.propertiesReader.getNoteFechaModificacion());
        putProperty(NotePropertyName.NOTE_CHANNEL, this.propertiesReader.getNoteChannel());
        putProperty(NotePropertyName.NOTE_CHANNEL_URL, this.propertiesReader.getNoteChannelUrl());
        putProperty(NotePropertyName.NOTE_SUMMARY, this.propertiesReader.getNoteSummary());
        putProperty(NotePropertyName.NOTE_TIPO, this.propertiesReader.getNoteTipo());
        putProperty(NotePropertyName.NOTE_STRING_TAGS, this.propertiesReader.getNoteStringTags());
        putProperty(NotePropertyName.NOTE_TAGS, this.propertiesReader.getNoteTags());
        putProperty(NotePropertyName.NOTE_IDENTIFIER, this.propertiesReader.getNoteIdentifier());
        putProperty(NotePropertyName.NOTE_CONTENT, this.propertiesReader.getNoteContent());
        putProperty(NotePropertyName.NOTE_DESCRIPTION, this.propertiesReader.getNoteDescription());
        putProperty(NotePropertyName.NOTE_IMAGE_FILE_REFERENCE, this.propertiesReader.getNoteImageFileReference());
        putProperty(NotePropertyName.NOTE_IMAGE_LINK_URL, this.propertiesReader.getNoteImageLinkURL());
        putProperty(NotePropertyName.NOTE_AUTHOR, this.propertiesReader.getNoteAuthor());
        putProperty(NotePropertyName.NOTE_SOURCE, this.propertiesReader.getNoteSource());
        putProperty(NotePropertyName.NOTE_STYLE, this.propertiesReader.getNoteStyle());
        putProperty(NotePropertyName.NOTE_DATE_CREATED, this.propertiesReader.getNoteDateCreated());
        putProperty(NotePropertyName.NOTE_TEMPLATE, this.propertiesReader.getNoteTemplate());
        putProperty(NotePropertyName.NOTE_TOPIC, this.propertiesReader.getNoteTopic());
        putProperty(NotePropertyName.NOTE_IMAGE_ASSET, this.propertiesReader.getNoteImageAsset());



        String fileReference = getImageFileReference();
        if (fileReference != null) {
            setRenditions(fileReference);
        }

/*
 *	 fix:  thumbnail image from the path  /video/referenceFile
 */
        if ( isVideo() ) {
            if (this.propertiesReader.getVideoAsset() != null) {
                putProperty(NotePropertyName.NOTE_IMAGE_FILE_REFERENCE, this.propertiesReader.getVideoImageAsset().getAsset().getPath() );
                putProperty(NotePropertyName.NOTE_IMAGE_ASSET, this.propertiesReader.getVideoImageAsset().getAsset() );
                String fileReference2 = this.propertiesReader.getVideoImageAsset().getAsset().getPath();
                setRenditions(fileReference2);
            }
        }
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

	/* Note */

    @Override
    public String getCategory() {
        return (String) getProperty(NotePropertyName.NOTE_CATEGORY);
    }

    @Override
    public void setCategory(String category) {
        putProperty(NotePropertyName.NOTE_CATEGORY, category);
    }

    @Override
    public String getCategoryUrl() {
        return (String) getProperty(NotePropertyName.NOTE_CATEGORY_URL);
    }

    @Override
    public void setCategoryUrl(String categoryUrl) {
        putProperty(NotePropertyName.NOTE_CATEGORY_URL, categoryUrl);
    }

    @Override
    public String getTitle() {
        return (String) getProperty(NotePropertyName.NOTE_TITLE);
    }

    @Override
    public void setTitle(String title) {
        putProperty(NotePropertyName.NOTE_TITLE, title);
    }

    @Override
    public String getTitleSEO() {
        return (String) getProperty(NotePropertyName.NOTE_TITLE_SEO);
    }

    @Override
    public void setTitleSEO(String title) {
        putProperty(NotePropertyName.NOTE_TITLE_SEO, title);
    }

    @Override
    public String getUrl() {
        return (String) getProperty(NotePropertyName.NOTE_URL);
    }

    @Override
    public void setUrl(String url) {
        putProperty(NotePropertyName.NOTE_URL, url);
    }

    @Override
    public String getExternalUrl() {
        return (String) getProperty(NotePropertyName.NOTE_EXTERNAL_URL);
    }

    @Override
    public void setExternalUrl(String externalUrl) {
        putProperty(NotePropertyName.NOTE_EXTERNAL_URL, externalUrl);
    }

    @Override
    public Tag[] getKeywords() {
        return (Tag[]) getProperty(NotePropertyName.NOTE_KEYWORDS);
    }

    @Override
    public void setKeywords(Tag[] keywords) {
        putProperty(NotePropertyName.NOTE_KEYWORDS, keywords);
    }

    @Override
    public Calendar getPubDate() {
        return (Calendar) getProperty(NotePropertyName.NOTE_PUB_DATE);
    }

    @Override
    public void setPubDate(Calendar pubDate) {
        putProperty(NotePropertyName.NOTE_PUB_DATE, pubDate);
    }

    @Override
    public Calendar getFirstPubDate() {
        return (Calendar) getProperty(NotePropertyName.NOTE_FIRST_PUB_DATE);
    }

    @Override
    public void setFirstPubDate(Calendar firstPubDate) {
        putProperty(NotePropertyName.NOTE_FIRST_PUB_DATE, firstPubDate);
    }

    @Override
    public Calendar getCrDate() {
        return (Calendar) getProperty(NotePropertyName.NOTE_CR_DATE);
    }

    @Override
    public void setCrDate(Calendar crDate) {
        putProperty(NotePropertyName.NOTE_CR_DATE, crDate);
    }

    @Override
    public Calendar getFechaModificacion() {
        return (Calendar) getProperty(NotePropertyName.NOTE_FECHA_MODIFICACION);
    }

    @Override
    public void setFechaModificacion(Calendar fechaModificacion) {
        putProperty(NotePropertyName.NOTE_FECHA_MODIFICACION, fechaModificacion);
    }

    @Override
    public String getChannel() {
        return (String) getProperty(NotePropertyName.NOTE_CHANNEL);
    }

    @Override
    public void setChannel(String channel) {
        putProperty(NotePropertyName.NOTE_CHANNEL, channel);
    }

    @Override
    public String getChannelUrl() {
        return (String) getProperty(NotePropertyName.NOTE_CHANNEL_URL);
    }

    @Override
    public void setChannelUrl(String channelUrl) {
        putProperty(NotePropertyName.NOTE_CHANNEL_URL, channelUrl);
    }

    @Override
    public String getSummary() {
        return (String) getProperty(NotePropertyName.NOTE_SUMMARY);
    }

    @Override
    public void setSummary(String summary) {
        putProperty(NotePropertyName.NOTE_SUMMARY, summary);
    }

    @Override
    public String getTipo() {
        return (String) getProperty(NotePropertyName.NOTE_TIPO);
    }

    @Override
    public void setTipo(String tipo) {
        putProperty(NotePropertyName.NOTE_TIPO, tipo);
    }

    @Override
    public String getStringTags() {
        return ((String) getProperty(NotePropertyName.NOTE_STRING_TAGS)).replace(";",",");
    }

    @Override
    public void setStringTags(String tags) {
        putProperty(NotePropertyName.NOTE_STRING_TAGS, tags);
    }

    @Override
    public Tag[] getTags() {
        return (Tag[]) getProperty(NotePropertyName.NOTE_TAGS);
    }

    @Override
    public void setTags(Tag[] tags) {
        putProperty(NotePropertyName.NOTE_TAGS, tags);
    }

    @Override
    public String getContent() {
        return (String) getProperty(NotePropertyName.NOTE_CONTENT);
    }

    @Override
    public void setContent(String content) {
        putProperty(NotePropertyName.NOTE_CONTENT, content);
    }

    @Override
    public String getDescription() {
        return (String) getProperty(NotePropertyName.NOTE_DESCRIPTION);
    }

    @Override
    public void setDescription(String description) {
        putProperty(NotePropertyName.NOTE_DESCRIPTION, description);
    }

    @Override
    public String getImageFileReference() {
        return (String) getProperty(NotePropertyName.NOTE_IMAGE_FILE_REFERENCE);
    }

    @Override
    public void setImageFileReference(String imageFileReference) {
        putProperty(NotePropertyName.NOTE_IMAGE_FILE_REFERENCE, imageFileReference);
    }

    @Override
    public String getImageLinkURL() {
        return (String) getProperty(NotePropertyName.NOTE_IMAGE_LINK_URL);
    }

    @Override
    public void setImageLinkURL(String imageLinkURL) {
        putProperty(NotePropertyName.NOTE_IMAGE_LINK_URL, imageLinkURL);
    }

    @Override
    public String getAuthor() {
        return (String) getProperty(NotePropertyName.NOTE_AUTHOR);
    }

    @Override
    public void setAuthor(String author) {
        putProperty(NotePropertyName.NOTE_AUTHOR, author);
    }

    @Override
    public String getSource() {
        return (String) getProperty(NotePropertyName.NOTE_SOURCE);
    }

    @Override
    public void setSource(String source) {
        putProperty(NotePropertyName.NOTE_SOURCE, source);
    }


    @Override
    public String getStyle() {
        return (String) getProperty(NotePropertyName.NOTE_STYLE);
    }

    @Override
    public void setStyle(String style) {
        putProperty(NotePropertyName.NOTE_STYLE, style);
    }

    @Override
    public String getDateCreated() {
        return (String) getProperty(NotePropertyName.NOTE_DATE_CREATED);
    }

    @Override
    public void setDateCreated(String dateCreated) {
        putProperty(NotePropertyName.NOTE_DATE_CREATED, dateCreated);
    }

    @Override
    public String getTemplate() {
        return (String) getProperty(NotePropertyName.NOTE_TEMPLATE);
    }

    @Override
    public void setTemplate(String template) {
        putProperty(NotePropertyName.NOTE_TEMPLATE, template);
    }

    @Override
    public String getTopic() {
        return (String) getProperty(NotePropertyName.NOTE_TOPIC);
    }

    @Override
    public	void setTopic(String topic) {
        putProperty(NotePropertyName.NOTE_TOPIC, topic);
    }

    @Override
    public String getIdentifier() {
        return (String) getProperty(NotePropertyName.NOTE_IDENTIFIER);
    }

    @Override
    public void setIdentifier(String identifier) {
        putProperty(NotePropertyName.NOTE_IDENTIFIER, identifier);
    }

    // Renditions

    @Override
    public ImageAsset getNoteImageAsset() {
        return (ImageAsset) getProperty(NotePropertyName.NOTE_IMAGE_ASSET);
    }

    @Override
    public void setNoteImageAsset(ImageAsset noteImageAsset) {
        putProperty(NotePropertyName.NOTE_IMAGE_ASSET, noteImageAsset);
    }

    // Generic properties getters and setters.
    // Store the values in a generic string typed hash map.

    @Override
    public Object getProperty(NotePropertyName key) {
        return map.get(key.name());
    }

    @Override
    public Object putProperty(NotePropertyName key, Object value) {
        return map.put(key.name(), value);
    }

    @Override
    public Object getProperty(RenditionSize key) {
        return map.get(key.name());
    }

    @Override
    public Object putProperty(RenditionSize key, Object value) {
        return map.put(key.name(), value);
    }

    @Override
    public Object getProperty(String key) {
        return map.get(key);
    }

    @Override
    public Object putProperty(String key, Object value) {
        return map.put(key, value);
    }

    // Type

    @Override
    public boolean isArticle() {
        return this instanceof Article;
    }

    @Override
    public boolean isVideo() {
        return this instanceof Video;
    }

    @Override
    public boolean isPhoto() {
        return this instanceof Photo;
    }

    @Override
    public boolean isProgram() {
        return this instanceof Program;
    }

}

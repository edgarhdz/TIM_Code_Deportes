/*
 * Note.java
 *
 * The note data model.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel;

import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.televisa.commons.services.datamodel.objects.ImageAsset;

import java.util.Calendar;

/**
 * The Note Data Model Interface
 *
 * Changes History:
 *
 *         2013-02-04 gescobar Added note type properties, added photo implementation
 *         2013-01-29 gescobar Added topic property
 *         2013-01-29 gescobar Properties Return Values
 *         2013-01-10 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public interface Note {

    Page getPage();

    void setPage(Page page);

	/* Note */

    String getCategory();

    void setCategory(String category);

    String getCategoryUrl();

    void setCategoryUrl(String categoryUrl);

    String getTitle();

    void setTitle(String title);

    String getTitleSEO();

    void setTitleSEO(String title);

    String getUrl();

    void setUrl(String url);

    String getExternalUrl();

    void setExternalUrl(String externalUrl);

    Tag[] getKeywords();

    void setKeywords(Tag[] keywords);

    Calendar getPubDate();

    void setPubDate(Calendar pubDate);

    Calendar getFirstPubDate();

    void setFirstPubDate(Calendar firstPubDate);

    Calendar getCrDate();

    void setCrDate(Calendar crDate);

    Calendar getFechaModificacion();

    void setFechaModificacion(Calendar fechaModificacion);

    String getChannel();

    void setChannel(String channel);

    String getChannelUrl();

    void setChannelUrl(String channelUrl);

    String getSummary();

    void setSummary(String summary);

    String getTipo();

    void setTipo(String tipo);

    String getStringTags();

    void setStringTags(String tags);

    Tag[] getTags();

    void setTags(Tag[] tags);

    String getContent();

    void setContent(String content);

    String getDescription();

    void setDescription(String description);

    String getImageFileReference();

    void setImageFileReference(String fileReference);

    String getImageLinkURL();

    void setImageLinkURL(String linkURL);

    String getAuthor();

    void setAuthor(String author);

    String getSource();

    void setSource(String source);

    String getStyle();

    void setStyle(String style);

    String getDateCreated();

    void setDateCreated(String dateCreated);

    String getTemplate();

    void setTemplate(String template);

    String getTopic();

    void setTopic(String topic);

    String getIdentifier();

    void setIdentifier(String identifier);

    // Renditions

    ImageAsset getNoteImageAsset();

    void setNoteImageAsset(ImageAsset noteImageAsset);

    // Generic properties getters and setters.

    Object getProperty(NotePropertyName key);

    Object putProperty(NotePropertyName key, Object value);

    Object getProperty(RenditionSize key);

    Object putProperty(RenditionSize key, Object value);

    Object getProperty(String key);

    Object putProperty(String key, Object value);

    // Type

    boolean isArticle();

    boolean isPhoto();

    boolean isVideo();

    boolean isProgram();

}

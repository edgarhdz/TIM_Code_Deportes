/*
 * NoteManagerService.java
 *
 * The component and service to perform the note management.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.services;

import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.televisa.commons.services.dataaccess.NoteType;
import com.televisa.commons.services.dataaccess.TemplateType;
import com.televisa.commons.services.datamodel.Note;
import org.apache.sling.api.resource.Resource;

import java.util.List;

/**
 * Note Management Service
 *
 * The component and service to perform the note management.
 *
 * Changes History:
 *
 *         2013-03-11 gescobar Added getChildComponentResources method and update hasResource method
 *         2013-03-08 gescobar Added getLatestRecursiveResources method and hasResource method
 *         2013-02-22 gescobar Added getLatestNotes method
 *         2013-01-09 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public interface NoteManagerService {

    /**
     * Get a note from the data model.
     *
     * @param channel the channel to select from
     * @param year the year of the note
     * @param month the month of the note
     * @param title the title of the note
     * @return a note from the data model with the selected parameters
     */
    Note getNote(String channel, String year, String month, String title);



    /**
     * Get a note from the data model.
     *
     * @param pagePath the path to the node
     * @return a note from the data model with the selected path
     */
    Note getNote(String pagePath);

    /**
     * Get a list of notes from the data model.
     *
     * @param tags the tags to use to select notes
     * @return the list of notes
     */
    List<Note> getNotes(String[] tags);

    /**
     * Get a list of notes from the data model.
     *
     * @param tags the tags to use to select notes
     * @return the list of notes
     */
    List<Note> getNotesByTags(Tag[] tags);

    /**
     * Get the eight latest list of notes from a channel.
     *
     * @param channel the channel to retrieve notes from
     * @return a new list of Notes from the channel
     */
    List<Note> getLatestNotes(String channel, int limit);

    /**
     * Get a list of resources from a component.
     *
     * @param page the page to resolve the child resources from
     * @param path the path to the resource
     * @param which the resource which has to provide the resource
     * @return a new list of Resource from the content resource
     */
    List<Resource> getChildComponentResources(Page page, String path, String which);

    /**
     * Get a list of child resources from a start parent path node.
     *
     * @param path the path to the resource
     * @param type the template type expected
     * @param name the named of the internal node name to check
     * @return a new list of Resource from the path
     */
    List<Resource> getLatestChildResources(String path, TemplateType type, String name);

    /**
     * Get the template name based on a page path.
     *
     * @param path the path to the page
     * @return the name of the template
     */
    String getTemplateName(String path);

    List<Resource> getLatestVideoResources(String path, TemplateType templateType, String type);

    /**
     * Get the latest list of notes from a path.
     *
     * @param path the channel to retrieve notes from
     * @return a new list of the latest Notes from the channel
     */
    public List<Note> getLatestNotesFromPath(String path, int limit, int index, NoteType noteType);

    /**
     * Get the latest list of notes from a path and a specified tag.
     *
     * @param path the channel to retrieve notes from
     * @return a new list of the latest Notes from the channel
     */
    public List<Note> getNotesFromPathAndTag(String path, int limit, int index, String tag, NoteType noteType);
}

/*
 * NoteManagerServiceImpl.java
 *
 * The component and service to perform the note management.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.services.impl;

import com.day.cq.search.QueryBuilder;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;
import com.televisa.commons.services.dataaccess.NoteType;
import com.televisa.commons.services.dataaccess.TemplateType;
import com.televisa.commons.services.dataaccess.impl.NoteManagerImpl;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.event.NoteManagerEventTopic;
import com.televisa.commons.services.services.NoteManagerService;
import com.televisa.commons.services.utilities.ApplicationProperties;
import com.televisa.commons.services.dataaccess.NoteManager;
import com.televisa.commons.services.event.NoteManagerEventManager;
import com.televisa.commons.services.event.impl.NoteManagerEventManagerImpl;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.List;

/**
 * Note Management Service Implementation
 *
 * The component and service to perform the note management.
 *
 * The note need access to the Template node to get its name and know which implementation to use,
 * since only an administration has access to such node we need to provide an administrative
 * resource resolver.
 *
 * Changes History:
 *
 *         2013-04-02 gescobar Added the templates to the OSGi configuration parameters
 *         2013-03-11 gescobar Added getChildComponentResources method and update hasResource method
 *         2013-03-08 gescobar Added getLatestRecursiveResources method and hasResource method
 *         2013-02-22 gescobar Added getLatestNotes method
 *         2013-01-09 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@Component(label = "Note Manager Service", description = "The note manager service implementation.", immediate = true, metatype = true)
@Service(value = NoteManagerService.class)
@Properties({
        @Property(name = "type", value = "note", label = "Service Type", description = "The service type to provide to the factory."),
        @Property(name = ApplicationProperties.DATA_CONTENT_PATH, value = ApplicationProperties.DATA_CONTENT_PATH_DEFAULT_VALUE, label = "Data Content Path", description = "Data Content Path"),
        @Property(name = ApplicationProperties.APPLICATION_PATH, value = ApplicationProperties.APPLICATION_PATH_DEFAULT_VALUE, label = "Application Path", description = "Application Path"),
        @Property(name = ApplicationProperties.CATEGORY_DATE_TEMPLATE_NAME, value = ApplicationProperties.CATEGORY_DATE_TEMPLATE_NAME_DEFAULT_VALUE, label = "Category Date Template Name", description = "The name of the template used to create pages with the Brightcove Processor, do not confuse with the template type directory name."),
        @Property(name = ApplicationProperties.VIDEO_TEMPLATE_NAME, value = ApplicationProperties.VIDEO_TEMPLATE_NAME_DEFAULT_VALUE, label = "Video Template Name", description = "The name of the template used to create pages with the Brightcove Processor, do not confuse with the template type directory name.")
})
public class NoteManagerServiceImpl implements NoteManagerService {

    private static final Logger LOG = LoggerFactory.getLogger(NoteManagerServiceImpl.class);


    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private PageManagerFactory pageManagerFactory;

    @Reference
    private EventAdmin eventAdmin;

    private ResourceResolver resourceResolver;


    @Activate
    protected void activate(ComponentContext context) {
        if (context.getProperties().get(ApplicationProperties.DATA_CONTENT_PATH) instanceof String) {
            String data_content_path = (String) context.getProperties().get(ApplicationProperties.DATA_CONTENT_PATH);
            ApplicationProperties.setDataContentPath(data_content_path);
        }
        if (context.getProperties().get(ApplicationProperties.APPLICATION_PATH) instanceof String) {
            String application_path = (String) context.getProperties().get(ApplicationProperties.APPLICATION_PATH);
            ApplicationProperties.setApplicationPath(application_path);
        }
        if (context.getProperties().get(ApplicationProperties.CATEGORY_DATE_TEMPLATE_NAME) instanceof String) {
            String category_date_template_name = (String) context.getProperties().get(ApplicationProperties.CATEGORY_DATE_TEMPLATE_NAME);
            ApplicationProperties.setCategoryDateTemplateName(category_date_template_name);
        }
        if (context.getProperties().get(ApplicationProperties.VIDEO_TEMPLATE_NAME) instanceof String) {
            String video_template_name = (String) context.getProperties().get(ApplicationProperties.VIDEO_TEMPLATE_NAME);
            ApplicationProperties.setVideoTemplateName(video_template_name);
        }

        try {
            this.resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
        } catch (LoginException e) {
            LOG.error(e.getMessage());
        }
    }

    @Deactivate
    protected void deactivate(final ComponentContext context) {
        this.resourceResolver.adaptTo(Session.class).logout();
    }

    /**
     * Creates a path to select a note from the data model.
     *
     * @param channel the channel to select from
     * @param year the year of the note
     * @param month the month of the note
     * @param title the title of the note
     * @return a string with the path to the note
     */
    private String getNotePath(String channel, String year, String month, String title) {
        return ApplicationProperties.getDataContentPath().concat(String.format("%s/%s/%s/%s", channel, year, month, title));
    }

    /**
     * Raise a Note Manager Service event.
     *
     * @param topic the topic to raise
     * @param userid the userid from the user
     * @param path the path to registers
     */
    protected void raiseNoteManagerServiceEvent(NoteManagerEventTopic topic, String userid, String path) {
        NoteManagerEventManager noteManagerEventManager = new NoteManagerEventManagerImpl(eventAdmin);
        noteManagerEventManager.setTopic(topic);
        noteManagerEventManager.putProperty("path", path);
        noteManagerEventManager.putProperty("userid", userid);
        noteManagerEventManager.raiseEvent();
        //LOG.info("EVENT : {} : {}", topic.getPath(), path);
    }

    /**
     * Get a note manager.
     *
     * @return a new NoteManager of note type
     */
    protected NoteManager<Note> getNoteManager() {
        PageManager pageManager = pageManagerFactory.getPageManager(resourceResolver);
        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
        return new NoteManagerImpl(resourceResolver, pageManager, tagManager);
    }

    /**
     * Get a note from the data model.
     *
     * @param channel the channel to select from
     * @param year the year of the note
     * @param month the month of the note
     * @param title the title of the note
     * @return a note from the data model with the selected parameters
     */
    @Override
    public Note getNote(String channel, String year, String month, String title) {
        NoteManager<Note> noteManager = getNoteManager();
        String path = getNotePath(channel, year, month, title);
        raiseNoteManagerServiceEvent(NoteManagerEventTopic.GETNOTE, resourceResolver.getUserID(), path);
        return noteManager.getNote(path);
    }



    /**
     *
     * @param pagePath the path to the node
     * @return
     */
    @Override
    public Note getNote(String pagePath) {
        NoteManager<Note> noteManager = getNoteManager();
        raiseNoteManagerServiceEvent(NoteManagerEventTopic.GETNOTE, resourceResolver.getUserID(), pagePath);
        return noteManager.getNote(pagePath);
    }

    /**
     * Get a list of notes from the data model.
     *
     * @param tags the tags to use to select notes
     * @return the list of notes
     */
    @Override
    public List<Note> getNotes(String[] tags) {

        NoteManager<Note> noteManager = getNoteManager();
        return noteManager.getNotes(tags);
    }

    /**
     * Get a list of notes from the data model.
     *
     * @param tags the tags to use to select notes
     * @return the list of notes
     */
    @Override
    public List<Note> getNotesByTags(Tag[] tags) {
        NoteManager<Note> noteManager = getNoteManager();
        return noteManager.getNotesByTags(tags);
    }

    /**
     * Get the eight latest list of notes from a channel.
     *
     * @param channel the channel to retrieve notes from
     * @return a new list of the latest Notes from the channel
     */
    @Override
    public List<Note> getLatestNotes(String channel, int limit) {
        NoteManager<Note> noteManager = getNoteManager();
        List<Note> latestNotes = noteManager.getLatestNotes(channel, limit,
                resourceResolver.adaptTo(QueryBuilder.class),
                resourceResolver.adaptTo(Session.class));
        return latestNotes;
    }

    /**
     * Get a list of resources from a component.
     *
     * @param page the page to resolve the child resources from
     * @param path the path to the resource
     * @param which the resource which has to provide the resource
     * @return a new list of Resource from the content resource
     */
    public List<Resource> getChildComponentResources(Page page, String path, String which) {
        NoteManager<Note> noteManager = getNoteManager();
        List<Resource> resources = null;
        try {
            resources = noteManager.getChildComponentResources(page, path, which);
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
        }
        return resources;
    }

    /**
     * Get a list of resources recursive from a start parent path node.
     *
     * @param path the path to the resource
     * @param type the template type expected
     * @param name the named of the internal node name to check
     * @return a new list of Resource from the path
     * @throws javax.jcr.RepositoryException if a repository exception occurs
     */
    public List<Resource> getLatestChildResources(String path, TemplateType type, String name) {
        NoteManager<Note> noteManager = getNoteManager();
        List<Resource> resources = null;
        try {
            resources = noteManager.getLatestChildResources(resourceResolver.adaptTo(Session.class), path, type, name);
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
        }
        return resources;
    }

    public List<Resource> getLatestVideoResources(String path, TemplateType templateType, String type){
        NoteManager<Note> noteManager = getNoteManager();
        List<Resource> resources = null;
        try {
            resources = noteManager.getLatestVideoResources(resourceResolver.adaptTo(Session.class), path, templateType, type);
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
        }
        return resources;
    }

    /**
     * Get the template name based on a page path.
     *
     * @param path the path to the page
     * @return the name of the template
     */
    public String getTemplateName(final String path) {
        NoteManager<Note> noteManager = getNoteManager();
        return noteManager.getTemplateName(path);
    }

    /**
     * Get the eight list of notes from a path.
     *
     * @param path the channel to retrieve notes from
     * @return a new list of the latest Notes from the channel
     */
    @Override
    public List<Note> getLatestNotesFromPath(String path, int limit, int index, NoteType noteType) {
        NoteManager<Note> noteManager = getNoteManager();
        List<Note> latestNotes = noteManager.getLatestNotesFromPath(path, limit, index, noteType,
                resourceResolver.adaptTo(QueryBuilder.class),
                resourceResolver.adaptTo(Session.class));
        return latestNotes;
    }

    /**
     * Get the latest list of notes from a path.
     *
     * @param path the channel to retrieve notes from
     * @return a new list of the latest Notes from the channel
     */
    @Override
    public List<Note> getNotesFromPathAndTag(String path, int limit, int index, String tag, NoteType noteType) {
        NoteManager<Note> noteManager = getNoteManager();
        List<Note> latestNotes = noteManager.getNotesFromPathAndTag(path, limit, index, noteType, tag,
                resourceResolver.adaptTo(QueryBuilder.class),
                resourceResolver.adaptTo(Session.class));
        return latestNotes;
    }

}

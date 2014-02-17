/*
 * InfoPageManagerServiceImpl.java
 *
 * The component and service to perform the information of a pages and the respective pagination management.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.services.impl;

import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;
import com.televisa.commons.services.dataaccess.InfoPageManager;
import com.televisa.commons.services.dataaccess.NoteManager;
import com.televisa.commons.services.dataaccess.impl.InfoPageManagerImpl;
import com.televisa.commons.services.dataaccess.impl.NoteManagerImpl;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.datamodel.objects.FilterGalleryHistory;
import com.televisa.commons.services.datamodel.objects.FilterVideoCarousel;
import com.televisa.commons.services.datamodel.objects.InfoPage;
import com.televisa.commons.services.services.InfoPageManagerService;
import com.televisa.commons.services.utilities.ApplicationProperties;
import com.televisa.commons.services.datamodel.objects.FilterIndexByTags;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;

/**
 * Information Page Management Service
 *
 * The component and service to perform the information of a pages and the respective pagination management.
 *
 * Changes History:
 *
 *         2013-01-22 Initial Development
 *
 * @author lsztul@xumak.com
 * @version 1.0
 */
@Component(label = "Information Page Manager Service", description = "The information page manager service implementation.", immediate = true)
@Service(value = InfoPageManagerService.class)
@Properties({
        @Property(name = "type", value = "note"),
        @Property(name = ApplicationProperties.DATA_CONTENT_PATH, value = ApplicationProperties.DATA_CONTENT_PATH_DEFAULT_VALUE),
        @Property(name = ApplicationProperties.APPLICATION_PATH, value = ApplicationProperties.APPLICATION_PATH_DEFAULT_VALUE)
})
public class InfoPageManagerServiceImpl implements InfoPageManagerService {

    private static final Logger LOG = LoggerFactory.getLogger(InfoPageManagerServiceImpl.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private PageManagerFactory pageManagerFactory;

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
     *
     * @param request
     * @param filter
     * @return
     */
    @Override
    public InfoPage getNotesByPath(SlingHttpServletRequest request, FilterIndexByTags filter) {
        /*
		ResourceResolver resourceResolver = request.getResourceResolver();
        try {
            resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
        } catch (LoginException e) {
            e.printStackTrace();

        }
        */
        PageManager pageManager = pageManagerFactory.getPageManager(resourceResolver);

        InfoPageManager<InfoPage> infoPageManager = new InfoPageManagerImpl(resourceResolver, pageManager);
        InfoPage infoPage = infoPageManager.getNotesByPath(filter);
        if(infoPage != null){
            TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
            NoteManager<Note> noteManager = new NoteManagerImpl(resourceResolver, pageManager, tagManager);
            infoPage.setNotes(noteManager.getNotesByNodes(infoPage.getNodes()));
        }
        return infoPage;
    }

    /**
     *
     * @param request
     * @param filter
     * @return
     */
    @Override
    public InfoPage getNotesByPathAndTags(SlingHttpServletRequest request, FilterIndexByTags filter) {
        /*
		ResourceResolver resourceResolver = request.getResourceResolver();
        try {
            resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
        } catch (LoginException e) {
            e.printStackTrace();

        }
        */
        PageManager pageManager = pageManagerFactory.getPageManager(resourceResolver);

        InfoPageManager<InfoPage> infoPageManager = new InfoPageManagerImpl(resourceResolver, pageManager);
        InfoPage infoPage = infoPageManager.getNotesByPathAndTags(filter);
        if(infoPage != null){
            TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
            NoteManager<Note> noteManager = new NoteManagerImpl(resourceResolver, pageManager, tagManager);
            infoPage.setNotes(noteManager.getNotesByNodes(infoPage.getNodes()));
        }
        return infoPage;
    }

    @Override
    public InfoPage getNotesByPathAndYear(SlingHttpServletRequest request, FilterGalleryHistory filter) {
        /*
		ResourceResolver resourceResolver = request.getResourceResolver();
        try {
            resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
        } catch (LoginException e) {
            e.printStackTrace();

        }
        */
        PageManager pageManager = pageManagerFactory.getPageManager(resourceResolver);

        InfoPageManager<InfoPage> infoPageManager = new InfoPageManagerImpl(resourceResolver, pageManager);
        InfoPage infoPage = infoPageManager.getNotesByPathAndYear(filter);
        if(infoPage != null){
            TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
            NoteManager<Note> noteManager = new NoteManagerImpl(resourceResolver, pageManager, tagManager);
            infoPage.setNotes(noteManager.getNotesByNodes(infoPage.getNodes()));
        }
        return infoPage;
    }


    @Override
    public InfoPage getVideoNotesByTagsAndTitle(SlingHttpServletRequest request, FilterVideoCarousel filter) {
        /*
		ResourceResolver resourceResolver = request.getResourceResolver();
        try {
            resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
        } catch (LoginException e) {
            e.printStackTrace();

        }
        */


        PageManager pageManager = pageManagerFactory.getPageManager(resourceResolver);

        InfoPageManager<InfoPage> infoPageManager = new InfoPageManagerImpl(resourceResolver, pageManager);
        InfoPage infoPage = infoPageManager.getVideoNotesByTagsAndTitle(filter);
        if(infoPage != null){
            TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
            NoteManager<Note> noteManager = new NoteManagerImpl(resourceResolver, pageManager, tagManager);
            infoPage.setNotes(noteManager.getNotesByNodes(infoPage.getNodes()));
        }
        return infoPage;
    }

    @Override
    public InfoPage getVideoNotesByFilter(SlingHttpServletRequest request, FilterVideoCarousel filter) {

        PageManager pageManager = pageManagerFactory.getPageManager(resourceResolver);

        InfoPageManager<InfoPage> infoPageManager = new InfoPageManagerImpl(resourceResolver, pageManager);
        InfoPage infoPage = infoPageManager.getVideoNotesByFilter(filter);
        if(infoPage != null){
            TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
            NoteManager<Note> noteManager = new NoteManagerImpl(resourceResolver, pageManager, tagManager);
            infoPage.setNotes(noteManager.getNotesByNodes(infoPage.getNodes()));
        }
        return infoPage;
    }
}

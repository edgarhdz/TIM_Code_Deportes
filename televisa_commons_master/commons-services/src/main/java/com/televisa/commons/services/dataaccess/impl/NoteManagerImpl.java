/*
 * NoteManagerImpl.java
 *
 * The implementation to access the notes data.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.dataaccess.impl;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.Template;
import com.day.cq.wcm.api.WCMException;
import com.televisa.commons.services.dataaccess.NoteType;
import com.televisa.commons.services.dataaccess.helper.VideoNodeHelper;
import com.televisa.commons.services.datamodel.*;
import com.televisa.commons.services.datamodel.impl.*;
import com.televisa.commons.services.query.QueryBuilderManager;
import com.televisa.commons.services.query.impl.QueryBuilderManagerJcrImpl;
import com.televisa.commons.services.utilities.ApplicationProperties;
import com.televisa.commons.services.dataaccess.NoteManager;
import com.televisa.commons.services.dataaccess.TemplateType;
import com.televisa.commons.services.query.impl.QueryBuilderManagerImpl;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.*;

/**
 * Note Management Data Access Implementation
 *
 * The implementation to access the notes data. We had a two Resource Resolvers, one with the credentials
 * from the request and one administrative from the Resource Resolver Factory. Use it accordingly to the
 * needed credentials.
 *
 * Changes History:
 *
 *         2013-03-11 gescobar Added getChildComponentResources method and update hasResource method
 *         2013-03-08 gescobar Added getLatestRecursiveResources method and hasResource method
 *         2013-03-05 gescobar Added the administrative Resource Resolver
 *         2013-02-22 gescobar Added getLatestNotes method
 *         2013-02-19 lsztul
 *         2013-02-04 gescobar Added photo implementation
 *         2013-01-29 gescobar Remove unused getters/setters
 *         2013-01-25 jbarrera Added method getNotesByTags
 *         2013-01-09 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class NoteManagerImpl implements NoteManager<Note> {

    private static final Logger LOG = LoggerFactory.getLogger(NoteManagerImpl.class);

    protected ResourceResolver resourceResolver;
    protected PageManager pageManager;
    protected TagManager tagManager;

    /**
     * Construct a new note manager with the page manager provided.
     *
     * @param resourceResolver the Sling Resource Resolver from the request
     * @param pageManager the PageManager
     * @param tagManager the TagManager
     */
    public NoteManagerImpl(ResourceResolver resourceResolver, PageManager pageManager, TagManager tagManager) {
        this.resourceResolver = resourceResolver;
        this.pageManager = pageManager;
        this.tagManager = tagManager;
    }

    @Override
    public Note create(String parentPath, String pageName, String template, String title) throws WCMException {
        return new NoteImpl(this.resourceResolver, this.pageManager.create(parentPath, pageName, template, title));
    }

    @Override
    public void delete(Note page, boolean shallow) throws WCMException {
        this.pageManager.delete(page.getPage(), shallow);
    }

    /**
     * Get a note from a path, returning different Note
     * implementations based on the template.
     *
     * @param path the path to obtain the page from
     * @return a new Note from the page path
     */
    @Override
    public Note getNote(String path) {
        Page page = this.pageManager.getPage(path);
        if (page != null) {
            return getNoteImplementation(page);
        }
        return null;
    }

    /**
     * Get a list of notes from a list of string tag names.
     *
     * @param tags the tag names
     * @return the list of notes
     */
    @Override
    public List<Note> getNotes(String[] tags) {
        List<Note> notes = new LinkedList<Note>();
        for (String tag : tags) {
            Tag resolve = this.tagManager.resolve(tag);
            if (resolve != null) {
                Iterator<Resource> iterator = resolve.find();
                if (iterator != null) {
                    while (iterator.hasNext()) {
                        Resource resource = iterator.next();
                        Resource parent = resource.getParent();
                        if ("cq:Page".equals(parent.getResourceType())) {
                            notes.add(getNote(parent.getPath()));
                        }
                    }
                }
            }
        }
        return notes;
    }

    /**
     * Get a list of notes using tags.
     *
     * @param tags the tag names
     * @return the list of notes by tags
     */
    @Override
    public List<Note> getNotesByTags(Tag[] tags) {
        List<Note> notes = new LinkedList<Note>();
        for (Tag tag : tags) {
            Iterator<Resource> iterator = tag.find();
            if (iterator != null) {
                while (iterator.hasNext()) {
                    Resource resource = iterator.next();
                    Resource parent = resource.getParent();
                    if ("cq:Page".equals(parent.getResourceType())) {
                        notes.add(getNote(parent.getPath()));
                    }
                }
            }
        }
        return notes;
    }

    /**
     * Get the eight latest notes from a channel.
     *
     * @param channel the channel to retrieve notes from
     * @param queryBuilder the Query Builder to use
     * @param session the JCR session to use
     * @return a new list of Notes from the channel
     */
    @Override
    public List<Note> getLatestNotes(String channel, int limit, QueryBuilder queryBuilder, Session session) {
        List<Note> notes = new LinkedList<Note>();

        QueryBuilderManager queryBuilderManager = new QueryBuilderManagerImpl(queryBuilder, session);

        String path = ApplicationProperties.getDataContentPath().concat(channel);
        Map<String, String> map = queryBuilderManager.createNotesMap(path,TemplateType.ARTICLE , limit);
        SearchResult result = queryBuilderManager.getResult(PredicateGroup.create(map));

        Iterator<Node> iterator = result.getNodes();
        if (iterator != null) {
            try {
                while (iterator.hasNext()) {
                    Node node = iterator.next();
                    notes.add(getNote(node.getPath()));
                }
            } catch (RepositoryException e) {
                LOG.error(e.getMessage(), e);
            }
        }

        return notes;
    }

    /**
     * Get a list of resources from a component.
     *
     * @param page the page to resolve the child resources from
     * @param path the path to the resource
     * @param which the resource which has to provide the resource
     * @return a new list of Resource from the content resource
     * @throws javax.jcr.RepositoryException if a repository exception occurs
     */
    @Override
    public List<Resource> getChildComponentResources(Page page, String path, String which) throws RepositoryException {
        List<Resource> list = new LinkedList<Resource>();
        Resource resource = page.getContentResource();
        if (resource != null) {
            resource = resource.getChild(path);
            if (resource != null) {
                Iterator<Resource> iterator = resource.listChildren();
                while (iterator.hasNext()) {
                    Resource next = iterator.next();
                    if (hasResource(next, which)) {
                        list.add(next);
                    }
                }
            }
        }
        return list;
    }

    /**
     * Get a list of resources recursive from a start parent path node. This removes the jcr:content part of the name.
     *
     * @param session the JCR session
     * @param path the path to the resource
     * @param type the template type expected
     * @param name the named of the internal node name to check
     * @return a new list of Resource from the path
     * @throws javax.jcr.RepositoryException if a repository exception occurs
     */
    @Override
    public List<Resource> getLatestChildResources(Session session, String path, TemplateType type, String name) throws RepositoryException {

        if(!type.equals(TemplateType.VIDEO)){
            path = "/content/televisa/noticieros".equals(path)? "/content/televisa/noticieros/fotos" : path;
        }
        QueryBuilderManager queryBuilderManager = new QueryBuilderManagerJcrImpl(session);

        Formatter formatter = new Formatter();
        formatter.format("SELECT * FROM [cq:PageContent] AS s WHERE (ISDESCENDANTNODE([%s]) AND NOT ISCHILDNODE([%s])) "
                .concat("AND CONTAINS(s.[cq:template], '/%s/') ORDER BY s.[cq:firstReplicated] DESC"), path, path, type.getTemplateName());

        List<Resource> list = new LinkedList<Resource>();

        int index = 0;
        NodeIterator result = queryBuilderManager.getResult(formatter.toString());
        if(type.equals(TemplateType.VIDEO)){
            while (result.hasNext() && index < 20) {
                Node node = (Node) result.next();
                if (node.hasNode(name.toLowerCase())) {
                    if (node.getParent() != null && "jcr:content".equalsIgnoreCase(node.getName())) {
                        if((VideoNodeHelper.hasM3u8(node) && VideoNodeHelper.getVideoDuration(node) > 10) ||
                                (VideoNodeHelper.hasVideoUrl(node) && VideoNodeHelper.getVideoDuration(node) <= 10)
                                ){
                            Resource resource = this.resourceResolver.resolve(node.getParent().getPath());
                            list.add(resource);
                            index++;
                        }

                    }
                }
            }
        }
        if(type.equals(TemplateType.PHOTO)){
            while (result.hasNext() && index < 15) {
                Node node = (Node) result.next();
                if (node.getParent() != null && "jcr:content".equalsIgnoreCase(node.getName())) {
                    Resource resource = this.resourceResolver.resolve(node.getParent().getPath());
                    list.add(resource);
                    index++;
                }
            }
        }

        formatter.close();
        return list;
    }


    public List<Resource> getLatestVideoResources(Session session, String path, TemplateType templateType, String type) throws RepositoryException{
        QueryBuilderManager queryBuilderManager = new QueryBuilderManagerJcrImpl(session);

        Formatter formatter = new Formatter();
        formatter.format("SELECT * FROM [cq:PageContent] AS s WHERE (ISDESCENDANTNODE([%s]) AND NOT ISCHILDNODE([%s])) "
                .concat("AND CONTAINS(s.[cq:template], '/%s/') ORDER BY s.[cq:firstReplicated] DESC"), path, path, templateType.getTemplateName());

        List<Resource> list = new LinkedList<Resource>();

        int index = 0;
        NodeIterator result = queryBuilderManager.getResult(formatter.toString());
        while (result.hasNext() && index < 60) {
            Node node = (Node) result.next();
            if (node.hasNode("video")) {
                if(VideoNodeHelper.hasVideoType(node, type)){
                    Resource resource = this.resourceResolver.resolve(node.getParent().getPath());
                    list.add(resource);
                    index++;
                }
            }
        }

        formatter.close();
        return list;
    }
    /**
     * Get a list of notes from a list of Nodes.
     *
     * @param nodes
     * @return
     */
    public List<Note> getNotesByNodes(final NodeIterator nodes) {
        List<Note> notes = new ArrayList<Note>();
        try {
            if(nodes != null){
                Iterator<Node> iterator = nodes;
                if (iterator != null) {
                    while (iterator.hasNext()) {
                        Node node = iterator.next();
                        Resource resource;
                        resource = this.resourceResolver.getResource(node.getPath());
                        Resource parent = resource.getParent();
                        Page page = this.pageManager.getPage(parent.getPath());
                        notes.add(getNote(page.getPath()));
                    }
                }
            }
        } catch (RepositoryException e) {
            LOG.error("Error while searching for content tagged with: {}, {}.", nodes, e.getMessage());
        }
        return notes;
    }

    /**
     * Get the template type from the template path.
     *
     * @param path the path of the template
     * @return the name of the template type
     */
    private String getTemplateType(final String path) {
        int lastIndex = path.lastIndexOf('/');
        if (lastIndex > 1) {
            int initIndexTemplate = path.lastIndexOf('/', lastIndex - 1);
            if (initIndexTemplate > -1) {
                return path.substring(initIndexTemplate + 1, lastIndex);
            }
        }
        return null;
    }

    /**
     * Get the note class by inspecting the page template and comparing with the different types.
     * If the page doen't have a template then returns a Note class to a page.
     *
     * @param page the page to get the note class
     * @return the class of the note
     */
    protected Class<? extends Note> getNoteType(final Page page) {
        if (page.getTemplate() != null) {
            String templateType = getTemplateType(page.getTemplate().getPath());
            for (TemplateType type : TemplateType.values()) {
                if (type.getTemplateName().equalsIgnoreCase(templateType)) {
                    return type.getClazz();
                }
            }
        }
        return Note.class;
    }

    /**
     * Get a new note implementation depending of the note type.
     * This was not implemented using reflection for clarity
     * (noteType.getClass().newInstance())
     *
     * Caution: Expected to not make use of the PageManager and TagManager since its used on the PropertiesReaderImpl
     * Caution: The Program Template was changed to be a Video Template, adjusting accordingly.
     *
     * @param page the page to get the note implementation
     * @return a new note implementation based on the class
     * @see com.televisa.commons.services.datamodel.impl.PropertiesReaderImpl#getVideoProgramNote
     */
    public Note getNoteImplementation(Page page) {
        if (page.getTemplate() != null) {
            if ("VideoMain".equalsIgnoreCase(page.getTemplate().getName())) {
                return new ProgramImpl(this.resourceResolver, page);
            }
        }
        Class<? extends Note> noteType = getNoteType(page);
        if (noteType == Article.class) {
            return new ArticleImpl(this.resourceResolver, page);
        } else {
            if (noteType == Video.class) {
                return new VideoImpl(this.resourceResolver, page);
            } else {
                if (noteType == Photo.class) {
                    return new PhotoImpl(this.resourceResolver, page);
                } else {
                    if (noteType == Program.class) {
                        return new ProgramImpl(this.resourceResolver, page);
                    } else {
                        return new NoteImpl(this.resourceResolver, page);
                    }
                }
            }
        }
    }

    /**
     * The Sling Resource Resolver API doesn't resolve the resources name hierarchically.
     * Try to resolve the resource manually.
     *
     * @param resourceType the resource type to try to resolve
     * @return the resource resolved
     */
    protected Resource resolve(final String resourceType) {
        Resource resolve = this.resourceResolver.resolve(resourceType);
        if (resolve != null && resolve.isResourceType(Resource.RESOURCE_TYPE_NON_EXISTING)) {
            resolve = this.resourceResolver.resolve("/libs/".concat(resourceType));
            if (resolve != null && resolve.isResourceType(Resource.RESOURCE_TYPE_NON_EXISTING)) {
                resolve = this.resourceResolver.resolve("/apps/".concat(resourceType));
            }
        }
        return resolve;
    }

    /**
     * Checks if a resource has a named node inside.
     *
     * @param resource the resource to check if it has a node inside
     * @param name the name of the node to check
     * @return true if the resource has the named node, false otherwise
     * @throws javax.jcr.RepositoryException if a repository exception occurs
     */
    @Override
    public boolean hasResource(final Resource resource, final String name) throws RepositoryException {
        String resourceType = resource.getResourceType();
        if (resourceType != null && !resourceType.isEmpty()) {
            Resource resolve = resolve(resourceType);
            if (resolve != null) {
                Node node = resolve.adaptTo(Node.class);
                if (node != null) {
                    return node.hasNode(name);
                }
            }
        }
        return false;
    }

    /**
     * Checks if a node points to a resource which has a named node inside.
     *
     * @param resourceNode the node to check if it has a node inside
     * @param name the name of the node to check
     * @return true if the resource has the named node, false otherwise
     * @throws javax.jcr.RepositoryException if a repository exception occurs
     */
    @Override
    public boolean hasResource(final Node resourceNode, final String name) throws RepositoryException {
        Resource resource = this.resourceResolver.resolve(resourceNode.getPath());
        if (resource != null) {
            String resourceType = resource.getResourceType();
            if (resourceType != null && !resourceType.isEmpty()) {
                Resource resolve = resolve(resourceType);
                if (resolve != null) {
                    Node node = resolve.adaptTo(Node.class);
                    if (node != null) {
                        return node.hasNode(name);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Get the template name based on a page path.
     *
     * @param path the path to the page
     * @return the name of the template
     */
    @Override
    public String getTemplateName(String path) {
        Page page = this.pageManager.getPage(path);
        if (page != null) {
            Template template = page.getTemplate();
            return template.getName();
        }
        return null;
    }

    /**
     * Get the eight latest notes from a channel.
     *
     * @param path the channel to retrieve notes from
     * @param queryBuilder the Query Builder to use
     * @param session the JCR session to use
     * @return a new list of Notes from the channel
     */
    @Override
    public List<Note> getLatestNotesFromPath(String path, int limit, int index, NoteType noteType, QueryBuilder queryBuilder, Session session) {
        List<Note> notes = new LinkedList<Note>();
        QueryBuilderManager queryBuilderManager = new QueryBuilderManagerImpl(queryBuilder, session);
        Map<String, String> map = queryBuilderManager.createRecentNotesMap(path, NoteType.GENERIC_NOTE , limit, index);
        SearchResult result = queryBuilderManager.getResult(PredicateGroup.create(map));

        Iterator<Node> iterator = result.getNodes();
        if (iterator != null) {
            try {
                while (iterator.hasNext()) {
                    Node node = iterator.next();
                    notes.add(getNote(node.getPath()));
                }
            } catch (RepositoryException e) {
                LOG.error(e.getMessage(), e);
            }
        }

        return notes;
    }

    /**
     * Get the eight latest notes from a channel.
     *
     * @param path the channel to retrieve notes from
     * @param queryBuilder the Query Builder to use
     * @param session the JCR session to use
     * @return a new list of Notes from the channel
     */
    @Override
    public List<Note> getNotesFromPathAndTag(String path, int limit, int index, NoteType noteType, String tag, QueryBuilder queryBuilder, Session session) {
        List<Note> notes = new LinkedList<Note>();
        QueryBuilderManager queryBuilderManager = new QueryBuilderManagerImpl(queryBuilder, session);
        Map<String, String> map = queryBuilderManager.createRecentNotesByTagsMap(path, NoteType.GENERIC_NOTE, limit, tag, index);
        SearchResult result = queryBuilderManager.getResult(PredicateGroup.create(map));

        Iterator<Node> iterator = result.getNodes();
        if (iterator != null) {
            try {
                while (iterator.hasNext()) {
                    Node node = iterator.next();
                    notes.add(getNote(node.getPath()));
                }
            } catch (RepositoryException e) {
                LOG.error(e.getMessage(), e);
            }
        }

        return notes;
    }
}

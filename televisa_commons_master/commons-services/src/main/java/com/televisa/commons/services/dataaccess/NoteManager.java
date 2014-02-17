/*
 * NoteManager.java
 *
 * The generic interface to access the notes data.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.dataaccess;

import com.day.cq.search.QueryBuilder;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.WCMException;
import com.televisa.commons.services.datamodel.Note;
import org.apache.sling.api.resource.Resource;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.List;

/**
 * Note Management Data Access
 *
 * The generic interface to access the notes data.
 *
 * Changes History:
 *
 *         2013-03-11 gescobar Added getChildComponentResources method and update hasResource method
 *         2013-03-08 gescobar Added getLatestRecursiveResources method and hasResource method
 *         2013-02-22 gescobar Added getLatestNotes method
 *         2013-02-19 lsztul
 *         2013-01-29 gescobar Remove unused getters/setters
 *         2013-01-25 jbarrera Added method getNotesByTags
 *         2013-01-09 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public interface NoteManager<T extends Note> {

    T create(String parentPath, String pageName, String template, String title) throws WCMException;

    void delete(T page, boolean shallow) throws WCMException;

    /**
     * Get a note from a path.
     *
     * @param path the path to obtain the page from
     * @return a new Note from the page path
     */
    T getNote(String path);

    /**
     * Get a list of notes from a list of string tag names.
     *
     * @param tags the list of tags to select from
     * @return a new list of Notes from the tags
     */
    List<T> getNotes(String[] tags);

    /**
     * Get a list of notes from a list of tags.
     *
     * @param tags the list of tags to select from
     * @return a new list of Notes from the tags
     */
    List<T> getNotesByTags(Tag[] tags);

    /**
     * Get the eight latest notes from a channel.
     *
     * @param channel the channel to retrieve notes from
     * @param queryBuilder the Query Builder to use
     * @param session the JCR session to use
     * @return a new list of Notes from the channel
     */
    List<Note> getLatestNotes(String channel, int limit, QueryBuilder queryBuilder, Session session);

    /**
     * Get a list of resources from a component.
     *
     * @param page the page to resolve the child resources from
     * @param path the path to the resource
     * @param which the resource which has to provide the resource
     * @return a new list of Resource from the content resource
     */
    List<Resource> getChildComponentResources(Page page, String path, String which) throws RepositoryException;

    /**
     * Get a list of resources recursive from a start parent path node.
     *
     * @param session the JCR session
     * @param path the path to the resource
     * @param type the template type expected
     * @param name the named of the internal node name to check
     * @return a new list of Resource from the path
     * @throws javax.jcr.RepositoryException if a repository exception occurs
     */
    List<Resource> getLatestChildResources(Session session, String path, TemplateType type, String name) throws RepositoryException;

    /**
     * Get a list of notes from a list of Nodes
     * @param nodes
     * @return
     */
    List<T> getNotesByNodes(NodeIterator nodes);

    /**
     * Returns a note implementation based on the page and it's template.
     *
     * @param page the page to get the note implementation from
     * @return a new note implementation
     */
    Note getNoteImplementation(Page page);

    /**
     * Checks if a resource has a named node inside.
     *
     * @param resource the resource to check if it has a node inside
     * @param name the name of the node to check
     * @return true if the resource has the named node, false otherwise
     * @throws javax.jcr.RepositoryException if a repository exception occurs
     */
    boolean hasResource(Resource resource, String name) throws RepositoryException;

    /**
     * Checks if a node points to a resource which has a named node inside.
     *
     * @param resourceNode the node to check if it has a node inside
     * @param name the name of the node to check
     * @return true if the resource has the named node, false otherwise
     * @throws javax.jcr.RepositoryException if a repository exception occurs
     */
    boolean hasResource(Node resourceNode, String name) throws RepositoryException;

    /**
     * Get the template name based on a page path.
     *
     * @param path the path to the page
     * @return the name of the template
     */
    String getTemplateName(String path);

    List<Resource> getLatestVideoResources(Session session, String path, TemplateType templateType, String type) throws RepositoryException;

    /**
     * Get the latest notes from a channel.
     *
     * @param path the channel to retrieve notes from
     * @param queryBuilder the Query Builder to use
     * @param session the JCR session to use
     * @return a new list of Notes from the channel
     */
    List<Note> getLatestNotesFromPath(String path, int limit, int index, NoteType noteType, QueryBuilder queryBuilder, Session session);

    /**
     * Get the latest notes from a channel.
     *
     * @param path the channel to retrieve notes from
     * @param queryBuilder the Query Builder to use
     * @param session the JCR session to use
     * @return a new list of Notes from the channel
     */
    public List<Note> getNotesFromPathAndTag(String path, int limit, int index, NoteType noteType, String tag, QueryBuilder queryBuilder, Session session);
}

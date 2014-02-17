/*
 * QueryBuilderImpl.java
 *
 * Encapsulates the Query Builder functionality.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.query.impl;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.televisa.commons.services.dataaccess.NoteType;
import com.televisa.commons.services.dataaccess.TemplateType;
import com.televisa.commons.services.query.QueryBuilderManager;
import com.televisa.commons.services.dataaccess.TemplateType;
import com.televisa.commons.services.query.QueryBuilderManager;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.RepositoryException;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Query Builder Encapsulation Implementation
 *
 * Encapsulates the Query Builder functionality.
 *
 * Changes History:
 *
 *         2013-02-22 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class QueryBuilderManagerImpl implements QueryBuilderManager {

    private static final Logger LOG = LoggerFactory.getLogger(QueryBuilderManagerImpl.class);

    private final QueryBuilder queryBuilder;
    private Session session;

    public QueryBuilderManagerImpl(QueryBuilder queryBuilder, Session session) {
        this.queryBuilder = queryBuilder;
        this.session = session;
    }

    @Override
    public SearchResult getResult(Map<String, String> map) {
        Query query = queryBuilder.createQuery(PredicateGroup.create(map), session);
        return query.getResult();
    }

    @Override
    public SearchResult getResult(PredicateGroup group) {
        Query query = queryBuilder.createQuery(group, session);
        return query.getResult();
    }

    @Override
    public NodeIterator getResult(String statement) {
        return null;
    }

    @Override
    public Map<String, String> createNotesMap(String path, TemplateType type, int limit) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("path", path);
        map.put("type", "cq:Page");
        map.put("property", "jcr:content/cq:template");
        map.put("property.operation", "like");
        map.put("property.value", "%".concat(type.getTemplateName()).concat("%"));
        map.put("p.offset", "0");
        map.put("p.limit", String.valueOf(limit));
        map.put("orderby", "jcr:content/cq:lastModified");
        return map;
    }

    @Override
    public Map<String, String> createRecentNotesMap(String path, NoteType type, int limit, int offset) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("path", path);
        map.put("type", "cq:Page");
        map.put("property", "jcr:content/cq:template");
        map.put("property.value", type.getTemplatePath());
        map.put("p.offset", "0");
        map.put("p.limit", String.valueOf(limit));
        map.put("orderby", "@jcr:content/cq:lastModified");
        map.put("orderby.sort", "desc");
        return map;
    }

    @Override
    public Map<String, String> createRecentNotesByTagsMap(String path, NoteType type, int limit, String tag, int offset) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("path", path);
        map.put("type", "cq:Page");
        map.put("property", "jcr:content/cq:template");
        map.put("property.value", type.getTemplatePath());
        map.put("tagid",tag);
        map.put("tagid.property", "jcr:content/cq:tags");
        map.put("p.offset", String.valueOf(offset));
        map.put("p.limit", String.valueOf(limit));
        map.put("orderby", "@jcr:content/cq:lastModified");
        map.put("orderby.sort", "desc");
        return map;
    }

    
    public String createInheritanceResource(String path, String resourceType) {
        Map<String, String> map = new HashMap<String, String>();
	map.put("type", "nt:unstructured");
        map.put("path", path + "/jcr:content");
	map.put("property", "sling:resourceType");
	map.put("property.value", resourceType);
        map.put("p.offset", "0");
        map.put("p.limit", "1");
        map.put("orderby", "@jcr:created");
        map.put("orderby.sort", "asc");
        
	try{
		SearchResult searchResult = this.getResult(map);
		Iterator<Node> nodes = searchResult.getNodes();
		String pathResource = null;
		if(nodes.hasNext()){
		    Node nodeResult = nodes.next();
		    pathResource = nodeResult.getPath();
		}
		return pathResource;
	} catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
        }

        return null;
    }

}

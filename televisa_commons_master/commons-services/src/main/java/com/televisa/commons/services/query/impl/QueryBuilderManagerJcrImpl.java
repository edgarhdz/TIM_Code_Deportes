/*
 * QueryBuilderManagerJcr.java
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
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.televisa.commons.services.dataaccess.NoteType;
import com.televisa.commons.services.dataaccess.TemplateType;
import com.televisa.commons.services.dataaccess.TemplateType;
import com.televisa.commons.services.query.QueryBuilderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import java.util.HashMap;
import java.util.Map;

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
public class QueryBuilderManagerJcrImpl implements QueryBuilderManager {

    private static final Logger LOG = LoggerFactory.getLogger(QueryBuilderManagerJcrImpl.class);

    private Session session;

    public QueryBuilderManagerJcrImpl(Session session) {
        this.session = session;
    }

    @Override
    public SearchResult getResult(Map<String, String> map) {
        return null;
    }

    @Override
    public SearchResult getResult(PredicateGroup group) {
        return null;
    }

    @Override
    public NodeIterator getResult(String statement) {
        try {
            Workspace workspace = session.getWorkspace();
            QueryManager queryManager = workspace.getQueryManager();
            Query query = queryManager.createQuery(statement, Query.JCR_SQL2);
            return query.execute().getNodes();
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
        }
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
        map.put("offset", "0");
        map.put("limit", String.valueOf(limit));
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
        map.put("p.offset", String.valueOf(offset));
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
}

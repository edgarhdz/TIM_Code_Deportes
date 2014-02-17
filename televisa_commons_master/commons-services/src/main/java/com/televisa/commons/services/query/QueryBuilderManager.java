/*
 * QueryBuilder.java
 *
 * Encapsulates the Query Builder functionality.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.query;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.result.SearchResult;
import com.televisa.commons.services.dataaccess.NoteType;
import com.televisa.commons.services.dataaccess.TemplateType;
import com.televisa.commons.services.dataaccess.TemplateType;

import javax.jcr.NodeIterator;
import java.util.Map;

/**
 * Query Builder Encapsulation Interface
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
public interface QueryBuilderManager {

    SearchResult getResult(Map<String, String> map);

    SearchResult getResult(PredicateGroup group);

    NodeIterator getResult(String statement);

    Map<String, String> createNotesMap(String path, TemplateType type, int limit);

    Map<String, String> createRecentNotesMap(String path, NoteType type, int limit, int offset);

    public Map<String, String> createRecentNotesByTagsMap(String path, NoteType type, int limit, String tag, int offset);

}

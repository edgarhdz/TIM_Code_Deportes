/*
 * QueryBuilderManagerImplTest.java
 *
 * Encapsulates the Query Builder functionality test.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.query.impl;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Session;

import org.easymock.EasyMock;
import org.junit.Ignore;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;

/**
 * Query Builder Encapsulation Implementation Test
 *
 * Encapsulates the Query Builder functionality test.
 *
 * Changes History:
 *
 *         2013-02-25 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class QueryBuilderManagerImplTest {

	private static final Logger LOG = LoggerFactory.getLogger(QueryBuilderManagerImplTest.class);

	private QueryBuilderManagerImpl instance;

	@Ignore
	@Test
	public void testGetResult() {
		Map<String, String> map = new HashMap<String, String>();

		// TODO : Test with the local JCR.
		QueryBuilder queryBuilder = PowerMock.createMock(QueryBuilder.class);
		Session session = PowerMock.createMock(Session.class);
		Query query = PowerMock.createMock(Query.class);
		SearchResult searchResult = PowerMock.createMock(SearchResult.class);

		EasyMock.expect(queryBuilder.createQuery(PredicateGroup.create(map), session)).andReturn(query);
		EasyMock.expect(query.getResult()).andReturn(searchResult);

		PowerMock.replayAll();

		instance = new QueryBuilderManagerImpl(queryBuilder, session);
		instance.getResult(map);
	}

}

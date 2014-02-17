/*
 * NoteManagerImplTest.java
 *
 * The test of the implementation to access the notes data.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.dataaccess.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.jcr.query.QueryManager;

import com.televisa.commons.services.dataaccess.TemplateType;
import com.televisa.commons.services.dataaccess.impl.NoteManagerImpl;
import org.apache.sling.api.resource.Resource;
import org.easymock.EasyMock;
import org.junit.Ignore;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;
import org.powermock.reflect.Whitebox;

import com.televisa.commons.services.dataaccess.TemplateType;

/**
 * Note Management Data Access Implementation Test
 *
 * The test of the implementation to access the notes data.
 *
 * Changes History:
 *
 *         2013-02-04 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class NoteManagerImplTest {

	@Ignore
	@Test
	public void testGetTemplateType() throws Exception {
		NoteManagerImpl instance = new NoteManagerImpl(null, null, null);
		String path = "/apps/televisa/templates/noticieros/article/article-template";
		String templateType = Whitebox.invokeMethod(instance, "getTemplateType", path);
		assertEquals(templateType, "article");
	}

	@Ignore
	@Test
	public void testGetLatestRecursiveResources() throws RepositoryException {
		Session session = EasyMock.createMock(Session.class);
		Workspace workspace = EasyMock.createMock(Workspace.class);
		QueryManager queryManager = EasyMock.createMock(QueryManager.class);

		EasyMock.expect(session.getWorkspace()).andReturn(workspace);
		EasyMock.expect(workspace.getQueryManager()).andReturn(queryManager);

		PowerMock.replayAll();

		NoteManagerImpl instance = new NoteManagerImpl(null, null, null);
		List<Resource> list = instance.getLatestChildResources(session, "/content/televisa/noticieros/nacional/1303/lolita_ayala", TemplateType.VIDEO, "video.xml.jsp");
	}

}

/*
 * RenditionImplTest.java
 *
 * Test read the properties from a CQ Page.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel.objects.impl;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.powermock.api.easymock.PowerMock;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.televisa.commons.services.datamodel.objects.Rendition;

/**
 * Rendition Implementation Test
 *
 * Test the rendition implementation.
 *
 * Changes History:
 *
 *         2013-03-14 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@RunWith(Parameterized.class)
public class RenditionImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(RenditionImplTest.class);

    private Rendition instance;

    private String filename;
    private int width;
    private int height;

	public RenditionImplTest(String filename, int width, int height) {
		this.filename = filename;
		this.width = width;
		this.height = height;
	}

	@Parameters
	public static Collection<Object[]> data() {
		List<Object[]> data = new LinkedList<Object[]>();
		data.add(new Object[] { "cq5dam.thumbnail.1024.768.jpeg", 1024, 768 });
		data.add(new Object[] { "cq5dam.thumbnail.360.68.jpeg", 360, 68 });
		data.add(new Object[] { "cq5dam.web.640.480.jpeg", 640, 480 });
		data.add(new Object[] { "cq5dam.thumbnails.grey.640.480.jpeg", 640, 480 });
		data.add(new Object[] { "cq5dam.thumbnails.web.grey.720.348.jpeg", 720, 348 });
		data.add(new Object[] { "cq5dam.thumbnails.web.grey.362.248.jpeg.png", 362, 248 });
		data.add(new Object[] { "cq5dam.thumbnails.web.grey.tres.cuatro.jpeg.png", -1, -1 }); // Unexpected
		data.add(new Object[] { "cq5dam.thumbnails.web.grey.1.2.300.400.jpeg.png", 1, 2 });
		return data;
	}

	@Ignore
	@Test
	public void testReadDimension() throws Exception {
		com.day.cq.dam.api.Rendition rendition = PowerMock.createMock(com.day.cq.dam.api.Rendition.class);

		EasyMock.expect(rendition.getName()).andReturn(filename).atLeastOnce();

		PowerMock.replayAll();

		instance = new RenditionImpl(rendition);

		Whitebox.invokeMethod(instance, "readDimension");
		LOG.trace(String.format("[%d x %d] : [%d x %d]", width, height, instance.getWidth(), instance.getHeight()));
		assertEquals(width, instance.getWidth());
		assertEquals(height, instance.getHeight());
	}

}

/*
 * PropertiesReaderImplTest.java
 *
 * Test read the properties from a CQ Page.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.datamodel.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

import com.televisa.commons.services.datamodel.NotePropertyName;
import org.apache.jackrabbit.api.JackrabbitValue;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.televisa.commons.services.datamodel.objects.ImageAsset;

/**
 * Properties Reader Implementation Test
 *
 * Test read the properties from a CQ Page.
 *
 * Changes History:
 *
 *         2013-02-06 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class PropertiesReaderImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(PropertiesReaderImplTest.class);

	private PropertiesReaderImpl propertiesReaderImpl;
	private ResourceResolver resourceResolver;
	private Page page;

	@Before
	public void setUp() {
		resourceResolver = PowerMock.createMock(ResourceResolver.class);
		page = PowerMock.createMock(Page.class);
	}

	@Ignore
	@Test
	public void testGetGenericProperty() throws PathNotFoundException, RepositoryException {
		LOG.trace("testGetGenericProperty");

		String path = "jcr:content/article/content";
		String name = "textblock";

		Node node = PowerMock.createMock(Node.class);
		Node child = PowerMock.createMock(Node.class);
		Property property = PowerMock.createMock(Property.class);

		EasyMock.expect(page.adaptTo(Node.class)).andReturn(node);
		EasyMock.expect(node.hasNode(path)).andReturn(true);
		EasyMock.expect(node.getNode(path)).andReturn(child);
		EasyMock.expect(child.hasProperty(name)).andReturn(true);
		EasyMock.expect(child.getProperty(name)).andReturn(property);

		PowerMock.replayAll();

		propertiesReaderImpl = new PropertiesReaderImpl(resourceResolver, page);
		assertEquals(propertiesReaderImpl.getGenericProperty(path, name), property);
	}

	@Ignore
	@Test
	public void testGetGenericPropertyByPropertyName() throws PathNotFoundException, RepositoryException {
		LOG.trace("testGetGenericProperty");

		NotePropertyName name = NotePropertyName.NOTE_CONTENT;

		Node node = PowerMock.createMock(Node.class);
		Node child = PowerMock.createMock(Node.class);
		Property property = PowerMock.createMock(Property.class);

		EasyMock.expect(page.adaptTo(Node.class)).andReturn(node);
		EasyMock.expect(node.hasNode(name.getPropertyPath())).andReturn(true);
		EasyMock.expect(node.getNode(name.getPropertyPath())).andReturn(child);
		EasyMock.expect(child.hasProperty(name.getPropertyName())).andReturn(true);
		EasyMock.expect(child.getProperty(name.getPropertyName())).andReturn(property);

		PowerMock.replayAll();

		propertiesReaderImpl = new PropertiesReaderImpl(resourceResolver, page);
		assertEquals(propertiesReaderImpl.getGenericProperty(name.getPropertyPath(), name.getPropertyName()), property);
	}

	@Ignore
	@Test
	public void testGetStringFromProperty() throws Exception {
		LOG.trace("testGetStringFromProperty");

		String result = "<p>This is the content.</p>";

		Property property = PowerMock.createMock(Property.class);
		Value value = PowerMock.createMock(JackrabbitValue.class);
		Value[] values = new Value[] { value };

		EasyMock.expect(property.isMultiple()).andReturn(true);
		EasyMock.expect(property.getValues()).andReturn(values);
		EasyMock.expect(value.getString()).andReturn(result);

		PowerMock.replayAll();

		propertiesReaderImpl = new PropertiesReaderImpl(resourceResolver, page);
		assertEquals(Whitebox.invokeMethod(propertiesReaderImpl, "getStringFromProperty", property), result.concat("\n"));
	}

	@Ignore
	@Test
	public void testGetImageAsset() throws RepositoryException {
		LOG.trace("testGetImageAsset");

		Node node;
		Node childNode;
		Property property;
		Resource resource;

		String result = "http://127.0.0.1/content/dam/televisa/entretenimiento/cine/Closer-natalie-portman.jpg";
		String pagePath = "http://127.0.0.1/content/televisa/entretenimiento/cine/1212/natalie-portman-estrella-mas-rentable-hollywood";
		String fileReference = "/content/dam/televisa/entretenimiento/cine/Closer-natalie-portman.jpg";

		page = PowerMock.createMock(Page.class);
		node = PowerMock.createMock(Node.class);
		childNode = PowerMock.createMock(Node.class);
		property = PowerMock.createMock(Property.class);

		resourceResolver = PowerMock.createMock(ResourceResolver.class);
		resource = PowerMock.createMock(Resource.class);

		EasyMock.expect(page.adaptTo(Node.class)).andReturn(node);
		EasyMock.expect(node.hasNode("jcr:content/imagegallerycontainer")).andReturn(true);
		EasyMock.expect(node.getNode("jcr:content/imagegallerycontainer")).andReturn(childNode);
		EasyMock.expect(childNode.hasProperty("imagePaths")).andReturn(true);
		EasyMock.expect(childNode.getProperty("imagePaths")).andReturn(property);

		EasyMock.expect(property.isMultiple()).andReturn(false);
		EasyMock.expect(property.getString()).andReturn(pagePath);

		EasyMock.expect(resourceResolver.getResource(fileReference)).andReturn(resource);

		PowerMock.replayAll();

		propertiesReaderImpl = new PropertiesReaderImpl(resourceResolver, page);
		ImageAsset imageAsset = propertiesReaderImpl.getImageAsset();
		assertEquals(imageAsset.getPath(), result);
	}

	@Ignore
	@Test
	public void testGetNoteCategory() {
		resourceResolver = PowerMock.createMock(ResourceResolver.class);
		page = PowerMock.createMock(Page.class);

		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros/nacional/1302/lolita_ayala").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros/").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros/nacional/").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros/nacional").once();

		PowerMock.replayAll();

		propertiesReaderImpl = new PropertiesReaderImpl(resourceResolver, page);
		assertEquals("nacional", propertiesReaderImpl.getNoteCategory());
		assertNull(propertiesReaderImpl.getNoteCategory());
		assertNull(propertiesReaderImpl.getNoteCategory());
		assertNull(propertiesReaderImpl.getNoteCategory());
		assertEquals("nacional", propertiesReaderImpl.getNoteCategory());
		assertEquals("nacional", propertiesReaderImpl.getNoteCategory());
	}

	@Ignore
	@Test
	public void testNoteCategoryUrl() {
		resourceResolver = PowerMock.createMock(ResourceResolver.class);
		page = PowerMock.createMock(Page.class);

		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros/nacional/1302/lolita_ayala").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros/").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros/nacional/").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros/nacional").once();

		PowerMock.replayAll();

		propertiesReaderImpl = new PropertiesReaderImpl(resourceResolver, page);
		assertEquals("/content/televisa/noticieros/nacional", propertiesReaderImpl.getNoteCategoryUrl());
		assertNull(propertiesReaderImpl.getNoteCategoryUrl());
		assertNull(propertiesReaderImpl.getNoteCategoryUrl());
		assertNull(propertiesReaderImpl.getNoteCategoryUrl());
		assertEquals("/content/televisa/noticieros/nacional", propertiesReaderImpl.getNoteCategoryUrl());
		assertEquals("/content/televisa/noticieros/nacional", propertiesReaderImpl.getNoteCategoryUrl());
	}

	@Ignore
	@Test
	public void testNoteChannel() {
		resourceResolver = PowerMock.createMock(ResourceResolver.class);
		page = PowerMock.createMock(Page.class);

		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros/nacional/1302/lolita_ayala").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros/").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros/nacional/").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros/nacional").once();

		PowerMock.replayAll();

		propertiesReaderImpl = new PropertiesReaderImpl(resourceResolver, page);
		assertEquals("noticieros", propertiesReaderImpl.getNoteChannel());
		assertNull(propertiesReaderImpl.getNoteChannel());
		assertEquals("noticieros", propertiesReaderImpl.getNoteChannel());
		assertEquals("noticieros", propertiesReaderImpl.getNoteChannel());
		assertEquals("noticieros", propertiesReaderImpl.getNoteChannel());
		assertEquals("noticieros", propertiesReaderImpl.getNoteChannel());
	}

	@Ignore
	@Test
	public void testNoteChannelUrl() {
		resourceResolver = PowerMock.createMock(ResourceResolver.class);
		page = PowerMock.createMock(Page.class);

		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros/nacional/1302/lolita_ayala").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros/").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros/nacional/").once();
		EasyMock.expect(page.getPath()).andReturn("/content/televisa/noticieros/nacional").once();

		PowerMock.replayAll();

		propertiesReaderImpl = new PropertiesReaderImpl(resourceResolver, page);
		assertEquals("/content/televisa/noticieros", propertiesReaderImpl.getNoteChannelUrl());
		assertNull(propertiesReaderImpl.getNoteChannel());
		assertEquals("/content/televisa/noticieros", propertiesReaderImpl.getNoteChannelUrl());
		assertEquals("/content/televisa/noticieros", propertiesReaderImpl.getNoteChannelUrl());
		assertEquals("/content/televisa/noticieros", propertiesReaderImpl.getNoteChannelUrl());
		assertEquals("/content/televisa/noticieros", propertiesReaderImpl.getNoteChannelUrl());
	}

	@Ignore
	@Test
	public void testGetVideoId() throws RepositoryException {
		propertiesReaderImpl = new PropertiesReaderImpl(resourceResolver, page);

		Node node = PowerMock.createMock(Node.class);
		Node childNode = PowerMock.createMock(Node.class);
		Property property = PowerMock.createMock(Property.class);
		String string = "8";

		// Get the Video Player Property

		// getGenericProperty()

		EasyMock.expect(page.adaptTo(Node.class)).andReturn(node).once();
		EasyMock.expect(node.hasNode("jcr:content/video")).andReturn(true);
		EasyMock.expect(node.getNode("jcr:content/video")).andReturn(childNode);
		EasyMock.expect(childNode.hasProperty("videoPlayer")).andReturn(true);
		EasyMock.expect(childNode.getProperty("videoPlayer")).andReturn(property);

		// getGenericStringProperty()

		EasyMock.expect(property.isMultiple()).andReturn(false);
		EasyMock.expect(property.getString()).andReturn(string);

		Node node2 = PowerMock.createMock(Node.class);
		Node childNode2 = PowerMock.createMock(Node.class);
		Property property2 = PowerMock.createMock(Property.class);
		String string2 = "bright001.php.flv.mp4";

		// Get the Video ID from Brightcove since its the "8"

		// getGenericProperty()

		EasyMock.expect(page.adaptTo(Node.class)).andReturn(node2).once();
		EasyMock.expect(node2.hasNode("jcr:content/video")).andReturn(true);
		EasyMock.expect(node2.getNode("jcr:content/video")).andReturn(childNode2);
		EasyMock.expect(childNode2.hasProperty("brightcoveVideoId")).andReturn(true);
		EasyMock.expect(childNode2.getProperty("brightcoveVideoId")).andReturn(property2);

		// getGenericStringProperty()

		EasyMock.expect(property2.isMultiple()).andReturn(false);
		EasyMock.expect(property2.getString()).andReturn(string2);

		// getVideoId()

		PowerMock.replayAll();

		assertEquals(propertiesReaderImpl.getVideoId(), string2);
	}

}

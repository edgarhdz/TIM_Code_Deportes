/*
 * RenditionFromImageAssetTest.java
 *
 * Get a specific rendition from a image asset test.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.televisa.commons.taglib.RenditionFromImageAsset;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Rendition From Image Asset Test
 *
 * Get a specific rendition from a image asset test.
 *
 * Changes History:
 *
 *         2013-02-21 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class RenditionFromImageAssetTest {

	private RenditionFromImageAsset renditionFromImageAsset = new RenditionFromImageAsset();

	@Ignore
	@Test
	public void testDoTag() throws JspException, IOException {
		renditionFromImageAsset.doTag();
	}

}

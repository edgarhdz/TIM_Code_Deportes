/*
 * UtilitiesTest.java
 *
 * Test General Utilities Methods
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.utilities;

import static org.junit.Assert.assertEquals;

import com.televisa.commons.services.utilities.Utilities;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Utilities Test
 *
 * Test General Utilities Methods
 *
 * Changes History:
 *
 *         2013-02-06 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class UtilitiesTest {

    @Ignore
    @Test
    public void testBuildReferencePath() {
        String result = "http://127.0.0.1:4502/content/dam/televisa/entretenimiento/natalie-portman.jpg";
        String path = "http://127.0.0.1:4502/content/televisa/natalie-portman";
        String relative;

        relative = "/content/dam/televisa/entretenimiento/natalie-portman.jpg";
        assertEquals(new Utilities().buildReferencePath(path, relative), result);

        relative = "content/dam/televisa/entretenimiento/natalie-portman.jpg";
        assertEquals(new Utilities().buildReferencePath(path, relative), result);
    }

}

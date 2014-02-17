/*
 * SearchByTagUrl.java
 *
 * Creates a URL for the tag page.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import com.televisa.commons.services.datamodel.PropertiesReader;
import com.televisa.commons.services.datamodel.impl.PropertiesReaderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Search By Tag URL
 *
 * Creates a URL for the tag page.
 *
 * Changes History:
 *
 *         2013-02-15 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class SearchByTagUrl extends CqSimpleTagSupport {

    private static final Logger LOG = LoggerFactory.getLogger(SearchByTagUrl.class);
    private static final String TAG = "/indice/tag.";
    private static final String HTM = ".html";

    private String tag;

    @Override
    public void doTag() throws JspException, IOException {
        PropertiesReader reader = new PropertiesReaderImpl(getSlingRequest().getResourceResolver(), getCurrentPage());
        StringBuilder builder = new StringBuilder();
        builder.append(reader.getNoteChannelUrl()).append(TAG).append(urlEncoderUTF(tag)).append(HTM);
        getJspWriter().write(builder.toString());
    }

    public String getTag() {
        return tag;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setTag(String tag) {
        this.tag = tag;
    }

    private String urlEncoderUTF(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

}

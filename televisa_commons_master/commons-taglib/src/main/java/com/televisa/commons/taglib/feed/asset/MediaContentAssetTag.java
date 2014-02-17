/*
 * MediaContentAssetTag.java
 *
 * Write an image tag from an rendition.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib.feed.asset;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import com.televisa.commons.services.datamodel.objects.Rendition;
import com.televisa.commons.services.services.Externalizer;
import com.televisa.commons.services.services.GsaService;
import com.televisa.commons.taglib.utilities.Utilities;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.util.Formatter;

/**
 * Named Feed Provider Tag
 *
 * Write an image tag from an rendition.
 *
 * Changes History:
 *
 *         2013-03-05 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@JspTag
public class MediaContentAssetTag extends CqSimpleTagSupport {

    private Formatter formatter;
    private Rendition rendition;

    @Override
    public void doTag() throws JspException, IOException {
        if (rendition != null) {
            GsaService gsaService = getService(GsaService.class);
            formatter = new Formatter();
            getJspWriter().write(formatter.format("<media:content url='%s' medium='image' height='%d'  width='%d' />",
                    Utilities.getCompleteURL(gsaService, rendition.getPath(), Externalizer.STATIC_DOMAIN)).toString());
            formatter.close();
        }
    }

    public Rendition getRendition() {
        return rendition;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setRendition(Rendition rendition) {
        this.rendition = rendition;
    }

}

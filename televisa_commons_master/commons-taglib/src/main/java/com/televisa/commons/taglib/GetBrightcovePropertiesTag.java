package com.televisa.commons.taglib;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import com.televisa.commons.services.brightcove.BrightcoveProperties;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.datamodel.Video;
import com.televisa.commons.services.services.GsaService;
import com.televisa.commons.services.services.NoteManagerService;
import com.televisa.commons.services.services.NoteManagerServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: palecio
 * Date: 25/2/13
 * Time: 16:12
 */

@JspTag
public class GetBrightcovePropertiesTag extends CqSimpleTagSupport{

    private static final Logger LOGGER = LoggerFactory.getLogger(GetBrightcovePropertiesTag.class);

    private String path;
    private String var; //the variable to assign the result to

    @Override
    public void doTag() throws JspException, IOException {
        final String pathString = getPath();
        if ("".equals(pathString)) {
            throw new JspException("path is undefined.");
        }

        final String var = getVar();
        if ("".equals(var)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("var is empty.");
            }
            throw new JspException("var is undefined.");
        }

        NoteManagerServiceFactory serviceFactory = getSlingScriptHelper().getService(NoteManagerServiceFactory.class);
        NoteManagerService noteManagerService = serviceFactory.getService(NoteManagerService.class);
        Note note = noteManagerService.getNote(path);

        GsaService gsaService = getSlingScriptHelper().getService(GsaService.class);
        try{
            Video video = (Video) note;
            BrightcoveProperties brightcoveHTML5Properties = new BrightcoveProperties(video, gsaService);
            Map<String,String> brightcoveProperties = brightcoveHTML5Properties.getProperties();
            getJspContext().setAttribute(var, brightcoveProperties);
        }catch (ClassCastException cce){
            LOGGER.warn("Note could not be cast to Video");
        }
    }

    /**
     * The variable to set the path to.
     * @param path the variable name to set the path.
     */
    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setPath(final String path) {
        this.path = path;
    }

    /**
     * The variable to set the object to.
     * @param var the variable name to set the object.
     */
    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setVar(final String var) {
        this.var = var;
    }

    public String getPath() {
        return path;
    }


    public String getVar() {
        return var;
    }
}

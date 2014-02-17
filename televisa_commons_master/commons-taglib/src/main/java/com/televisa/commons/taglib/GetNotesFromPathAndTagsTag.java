package com.televisa.commons.taglib;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.day.cq.tagging.Tag;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import com.televisa.commons.services.dataaccess.NoteType;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.services.NoteManagerService;
import com.televisa.commons.services.services.NoteManagerServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: antonio
 * Date: 7/29/13
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
@JspTag
public class GetNotesFromPathAndTagsTag extends CqSimpleTagSupport{
    private static final Logger LOG = LoggerFactory.getLogger(GetLatestNotesTag.class);
    private String tag;
    private String path;
    private String name = "note";
    private int limit;
    private int index;
    private String asList;
    private String noteType = "genericNote";

    public void doTag() throws IOException {
        removeRequestAttribute(name);
        List<Note> notes = null;
        NoteManagerServiceFactory factory = null;
        factory = getService(NoteManagerServiceFactory.class);
        if (factory != null) {
            NoteManagerService service = null;
            service = factory.getService(NoteManagerService.class);
            if (service != null) {
                if(!"".equals(noteType)) {
                    NoteType noteT =getNoteType(noteType);
                    notes =  new LinkedList<Note>(service.getNotesFromPathAndTag(path, limit,index, tag, NoteType.GENERIC_NOTE));
                }
            }
        }
        if (notes != null) {
            if(asList.equals("false") && notes.size()>0){
                setRequestAttribute(name, notes.get(0));
            } else if(asList.equals("true")) {
                setRequestAttribute(name, notes);
            }
        }
    }

    private NoteType getNoteType(String noteString) {
        NoteType noteType1 = NoteType.GENERIC_NOTE;
        if(noteType1.getTemplateName().equals(noteString)) {
            noteType1 = NoteType.GENERIC_NOTE;
        }
        return noteType1;
    }

    public String getPath() {
        return path;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setName(String name) {
        this.name = name;
    }

    public int getLimit() {
        return limit;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getIndex() {
        return index;
    }

    @JspTagAttribute(required = false, rtexprvalue = true)
    public void setIndex(int index) {
        this.index = index;
    }

    public String getTag() {
        return tag;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAsList() {
        return asList;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setAsList(String asList) {
        this.asList = asList;
    }

    public String getNoteType() {
        return noteType;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setNoteType(String noteType) {
        this.noteType = asList;
    }
}

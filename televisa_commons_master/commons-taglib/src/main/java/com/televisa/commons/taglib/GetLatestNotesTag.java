package com.televisa.commons.taglib;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import com.televisa.commons.services.dataaccess.NoteType;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.services.NoteManagerService;
import com.televisa.commons.services.services.NoteManagerServiceFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: antonio
 * Date: 7/24/13
 * Time: 3:19 PM
 */
@JspTag
public class GetLatestNotesTag extends CqSimpleTagSupport {

    private String path;
    private String name = "note2";
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
                //if (noteType != null && !noteType.isEmpty()) {
                if (!"".equals(noteType)) {
                    notes =  new LinkedList<Note>(service.getLatestNotesFromPath(path, index, limit, getNoteType(noteType)));
                }
            }
        }
        if ( (null != notes) && (0!=notes.size()) ) {
            if("false".equals(asList)){
                setRequestAttribute(name, notes.get(0));
            } else if("true".equals(asList)) {
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

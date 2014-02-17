package com.televisa.commons.taglib;

import com.cqblueprints.taglib.CqSimpleTagSupport;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.squeakysand.jsp.tagext.annotations.JspTag;
import com.squeakysand.jsp.tagext.annotations.JspTagAttribute;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.services.NoteManagerService;
import com.televisa.commons.services.services.NoteManagerServiceFactory;
import org.apache.sling.api.resource.ResourceResolver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: antonio
 * Date: 7/26/13
 * Time: 3:36 PM
 */
@JspTag
public class GetNotesFromTagsTag extends CqSimpleTagSupport {
    private Object[] tags;
    private String asList;
    private int limit;
    private int index = 0;

    private String name = "note";


    public void doTag() throws IOException {
        removeRequestAttribute(name);
        List<Note> notes = null;
        List<Note> limitedNotes = null;
        NoteManagerServiceFactory factory = null;
        factory = getService(NoteManagerServiceFactory.class);
        if (factory != null) {
            NoteManagerService service = null;
            service = factory.getService(NoteManagerService.class);
            if (service != null) {
                ResourceResolver resourceResolver =  getSlingRequest().getResourceResolver();
                TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
                List<Tag> tagList = new LinkedList<Tag>();
                for (Object tagString:tags) {
                    Tag tag = tagManager.resolve(tagString.toString());
                    tagList.add(tag);
                }
                notes =  new LinkedList<Note>(service.getNotesByTags(tagList.toArray(new Tag[tagList.size()])));
                limitedNotes =  new LinkedList<Note>();
                for(int count=0; count<limit; count++) {
                    if(count<notes.size()){
                        limitedNotes.add(notes.get(count));
                    } else {
                        break;
                    }
                }
            }
        }
        if (notes != null) {
            if("false".equals(asList)) {
                if(index > 0) {
                    setRequestAttribute(name, limitedNotes.get(index));
                } else {
                    setRequestAttribute(name, limitedNotes.get(0));
                }
            } else if("true".equals(asList)) {
                setRequestAttribute(name, limitedNotes);
            }
        }
    }

    public String getName() {
        return name;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setName(String name) {
        this.name = name;
    }

    public Object[] getTags() {
        return tags;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setTags(Object[] tags) {
        this.tags = tags;
    }

    public String getAsList() {
        return asList;
    }

    @JspTagAttribute(required = true, rtexprvalue = true)
    public void setAsList(String asList) {
        this.asList = asList;
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
}

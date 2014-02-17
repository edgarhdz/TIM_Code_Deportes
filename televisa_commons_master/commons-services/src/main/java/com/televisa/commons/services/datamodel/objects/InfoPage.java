package com.televisa.commons.services.datamodel.objects;

import com.televisa.commons.services.datamodel.Note;
import javax.jcr.NodeIterator;
import java.util.List;

public interface InfoPage {

    NodeIterator getNodes();

    void setNodes(NodeIterator nodes);

    int getActualPage();

    void setActualPage(int actualPage);

    int getTotalPages();

    void setTotalPages(int totalPages);

    public List<Note> getNotes();

    public void setNotes(List<Note> notes);

}

package com.televisa.commons.services.datamodel.objects.impl;

import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.datamodel.objects.InfoPage;

import javax.jcr.NodeIterator;
import java.util.List;

public class InfoPageImpl implements InfoPage {

    public NodeIterator nodes;
    public List<Note> notes;
    public int actualPage;
    public int totalPages;

    public InfoPageImpl(NodeIterator nodes, Integer totalPages) {
        super();
        this.nodes = nodes;
        this.totalPages = totalPages;
    }

    public InfoPageImpl(NodeIterator nodes, Integer actualPage, Integer totalPages) {
        super();
        this.nodes = nodes;
        this.actualPage = actualPage;
        this.totalPages = totalPages;
    }

    public NodeIterator getNodes() {
        return nodes;
    }
    public void setNodes(NodeIterator nodes) {
        this.nodes = nodes;
    }
    public int getActualPage() {
        return actualPage;
    }
    public void setActualPage(int actualPage) {
        this.actualPage = actualPage;
    }
    public int getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public List<Note> getNotes() {
        return notes;
    }
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}

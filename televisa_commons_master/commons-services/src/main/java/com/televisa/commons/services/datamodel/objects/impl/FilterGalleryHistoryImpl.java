package com.televisa.commons.services.datamodel.objects.impl;

import com.televisa.commons.services.datamodel.objects.FilterGalleryHistory;

public class FilterGalleryHistoryImpl extends FilterArticleImpl implements FilterGalleryHistory {

    public String year;
    public String typeOfNote;

    public FilterGalleryHistoryImpl(String path, String year, String typeOfNote, int itemsPerPage, int totalPages, int page) {
        this.path = path;
        this.year = year;
        this.typeOfNote = typeOfNote;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = totalPages;
        this.page = page;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTypeOfNote() {
        return typeOfNote;
    }

    public void setTypeOfNote(String typeOfNote) {
        this.typeOfNote = typeOfNote;
    }
}

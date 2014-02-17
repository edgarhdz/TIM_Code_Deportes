package com.televisa.commons.services.datamodel.objects.impl;

import com.televisa.commons.services.datamodel.objects.FilterVideoCarousel;

public class FilterVideoCarouselImpl extends FilterArticleImpl implements FilterVideoCarousel {

    public String[] tags;
    public String title;
    public String description;
    public String typeOfNote;

    public FilterVideoCarouselImpl(String path, String typeOfNote, String[] tags, String title,
                                   String description, int itemsPerPage,
                                   int totalPages, int page) {
        super();
        this.path = path;
        this.tags = tags;
        this.title = title;
        this.description = description;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = totalPages;
        this.page = page;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeOfNote() {
        return typeOfNote;
    }

    public void setTypeOfNote(String typeOfNote) {
        this.typeOfNote = typeOfNote;
    }
}

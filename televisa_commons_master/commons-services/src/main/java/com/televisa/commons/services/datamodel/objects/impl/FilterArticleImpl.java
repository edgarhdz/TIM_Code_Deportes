package com.televisa.commons.services.datamodel.objects.impl;

import com.televisa.commons.services.datamodel.objects.FilterArticle;

public class FilterArticleImpl implements FilterArticle {

    public String path;
    public int itemsPerPage;
    public int totalPages;
    public int page;

    public FilterArticleImpl() {
        super();
    }

    public FilterArticleImpl(String path, int itemsPerPage, int totalPages, int page) {
        super();
        this.path = path;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = totalPages;
        this.page = page;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}

package com.televisa.commons.services.datamodel.objects;

public interface FilterArticle {

    String getPath();

    void setPath(String path);

    int getItemsPerPage();

    void setItemsPerPage(int itemsPerPage);

    int getPage();

    void setPage(int page);

    int getTotalPages();

    void setTotalPages(int totalPages);
}

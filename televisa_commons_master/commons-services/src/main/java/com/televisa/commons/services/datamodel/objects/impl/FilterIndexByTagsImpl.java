package com.televisa.commons.services.datamodel.objects.impl;

import com.televisa.commons.services.datamodel.objects.FilterIndexByTags;

public class FilterIndexByTagsImpl extends FilterArticleImpl implements FilterIndexByTags {

    public String[] tags;
    public boolean articles;
    public boolean videos;
    public boolean photoGalleries;
    public boolean partidos;

    public FilterIndexByTagsImpl(String path, String[] tags, boolean articles,
                                 boolean videos, boolean photoGalleries,
                                 boolean partidos, int itemsPerPage,  
                                 int totalPages, int page) {
        super();
        this.path = path;
        this.tags = tags;
        this.articles = articles;
        this.videos = videos;
        this.photoGalleries = photoGalleries;
        this.partidos = partidos;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = totalPages;
        this.page = page;
    }

    @Override
    public String[] getTags() {
        return tags;
    }

    @Override
    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public boolean getArticles() {
        return articles;
    }

    @Override
    public void setArticles(boolean articles) {
        this.articles = articles;
    }

    @Override
    public boolean getVideos() {
        return videos;
    }

    @Override
    public void setVideos(boolean videos) {
        this.videos = videos;
    }

    @Override
    public boolean getPhotoGalleries() {
        return photoGalleries;
    }

    @Override
    public void setPhotoGalleries(boolean photoGalleries) {
        this.photoGalleries = photoGalleries;
    }
    
    @Override
    public boolean getPartidos() {
        return partidos;
    }

    @Override
    public void setPartidos(boolean partidos) {
        this.partidos = partidos;
    }
}
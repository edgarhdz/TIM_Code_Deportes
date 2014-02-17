package com.televisa.commons.services.datamodel.objects;

public interface FilterIndexByTags extends FilterArticle {

    String[] getTags();

    void setTags(String[] tags);

    boolean getArticles();

    void setArticles(boolean articles);

    boolean getVideos();

    void setVideos(boolean videos);

    boolean getPhotoGalleries();

    void setPhotoGalleries(boolean photoGalleries);
    
    public boolean getPartidos();

    public void setPartidos(boolean partidos);

}

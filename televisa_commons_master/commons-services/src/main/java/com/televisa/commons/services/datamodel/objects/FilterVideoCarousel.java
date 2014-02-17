package com.televisa.commons.services.datamodel.objects;

public interface FilterVideoCarousel extends FilterArticle {

    String[] getTags();

    void setTags(String[] tags);

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    String getTypeOfNote();
        
    void setTypeOfNote(String typeOfNote);


}

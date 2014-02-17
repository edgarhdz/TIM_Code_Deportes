package com.televisa.commons.services.dataaccess;

/**
 * Created with IntelliJ IDEA.
 * User: antonio
 * Date: 7/26/13
 * Time: 8:08 AM
 * To change this template use File | Settings | File Templates.
 */
public enum NoteType {

    PARTIDO_NOTE ("partidosNote","/apps/deportes/local/templates/partidoNote"),
    GENERIC_NOTE ("genericNote","/apps/deportes/local/templates/article/genericNote");


    private String name;
    private String templatePath;

    private NoteType(String name, String templatePath) {
        this.name = name;
        this.templatePath = templatePath;
    }

    public String getTemplateName() {
        return name;
    }

    public String getTemplatePath() {
        return templatePath;
    }
}

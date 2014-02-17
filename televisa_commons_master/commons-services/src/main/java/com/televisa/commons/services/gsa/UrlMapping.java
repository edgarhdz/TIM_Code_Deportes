package com.televisa.commons.services.gsa;

public class UrlMapping {

    private String path;
    private String vanityPath;
    private String domain;

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getVanityPath() {
        return vanityPath;
    }
    public void setVanityPath(String vanityPath) {
        this.vanityPath = vanityPath;
    }
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String toString(){
        return ">>" + this.getPath() + "::" + this.getVanityPath() + "::" + this.getDomain();
    }

}

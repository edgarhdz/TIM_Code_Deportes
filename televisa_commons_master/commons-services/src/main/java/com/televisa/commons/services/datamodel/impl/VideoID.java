package com.televisa.commons.services.datamodel.impl;

public enum VideoID {

    BRIGHTCOVE	("8"),
    YOUTUBE		("13"),
    AKAMAI		("11");

    private String id;

    VideoID(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public static VideoID getVideoID(String id) {
        if (id != null) {
            for (VideoID i : VideoID.values()) {
                if (i.getID().equalsIgnoreCase(id.trim())) {
                    return i;
                }
            }
        }
        return null;
    }

}

package com.televisa.commons.services.services;

import org.apache.sling.api.SlingHttpServletRequest;

public interface Externalizer {

    public static String STATIC_DOMAIN = "static";
    public static String HTML_DOMAIN = "html";

    public String externalizeURL(String domain, String path, SlingHttpServletRequest request);

    public String getDomain(String domain);
}
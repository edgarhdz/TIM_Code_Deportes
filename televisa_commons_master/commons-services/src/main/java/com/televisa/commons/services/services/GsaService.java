package com.televisa.commons.services.services;

import com.day.cq.wcm.api.Page;

public interface GsaService {

    public static final String HTML_DOMAIN = "html";
    public static final String STATIC_DOMAIN = "static";

    String buildUrl(String url, String domainType);

    void pushXML(Page[] pages, String action);

}

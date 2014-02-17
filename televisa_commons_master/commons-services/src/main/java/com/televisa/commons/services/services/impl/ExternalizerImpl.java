package com.televisa.commons.services.services.impl;

import com.televisa.commons.services.services.Externalizer;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.osgi.OsgiUtil;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;


@Component(immediate=true, metatype = true, label = "Televisa URL Externalizer")
@Service
public class ExternalizerImpl implements Externalizer {

    private static final Logger LOG = LoggerFactory.getLogger(ExternalizerImpl.class);

    @Property(label = "Static Domain", description="The domain to use for the static content in the form <protocol>://<Domain>:<port>")
    static final String SERVICE_PROPERTY_STATIC_DOMAIN = "domain.static";
    @Property(label = "HTML Domain", description="The domain to use for the HTML content in the form <protocol>://<Domain>:<port>")
    static final String SERVICE_PROPERTY_HTML_DOMAIN = "domain.html";

    private String staticDomain;
    private String htmlDomain;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;
    private ResourceResolver resourceResolver;

    public ExternalizerImpl(){
        /* empty constructor */
    }

    @Activate
    protected void activate(ComponentContext componentContext){
        this.staticDomain = OsgiUtil.toString(componentContext.getProperties().get(SERVICE_PROPERTY_STATIC_DOMAIN), "");
        this.htmlDomain = OsgiUtil.toString(componentContext.getProperties().get(SERVICE_PROPERTY_HTML_DOMAIN), "");
        try {
            resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
        } catch (LoginException e) {
            LOG.error(e.getMessage());
        }
    }

    @Deactivate
    protected void deactivate(final ComponentContext context) {
        this.resourceResolver.adaptTo(Session.class).logout();
    }
    public String externalizeURL(String domain, String path, SlingHttpServletRequest request){
        String result = "";
        String mappedPath;
        // if domain is not null then check if it's either static or html domain, if neither then use its own value; if it's null then use an empty string
        if (domain != null) {
            if (domain.equalsIgnoreCase(STATIC_DOMAIN)) {
                result = this.staticDomain;
            } else if(domain.equalsIgnoreCase(HTML_DOMAIN)) {
                result =  this.htmlDomain;
            }  else {
                result = domain;
            }
        }

        mappedPath = this.resourceResolver.map(request, path);

        // append the mapped path
        result = new StringBuffer(result).append(mappedPath).toString();
        return result;
    }

    public String getDomain(String domain){
        String returnDomain = "";
        if(domain.equalsIgnoreCase(STATIC_DOMAIN)){
            returnDomain = this.staticDomain;
        }else if(domain.equalsIgnoreCase(HTML_DOMAIN)){
            returnDomain = this.htmlDomain;
        }else{
            returnDomain = domain;
        }
        return returnDomain;
    }
}
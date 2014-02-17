package com.televisa.commons.services.services.impl;

import com.televisa.commons.services.services.TwitterService;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.osgi.OsgiUtil;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;


@Component(immediate=true, metatype = true, label = "Televisa Twitter Access Token Configuration")
@Service(value = TwitterService.class)
public class TwitterServiceImpl implements TwitterService {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterServiceImpl.class);

    @Property(label = "Access Token", description="Twitter Authentication Token (to allow access to Twitter API)")
    static final String SERVICE_PROPERTY_ACCESS_TOKEN= "access.token";

    private String accessToken;

    private ResourceResolver resourceResolver;
    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    public TwitterServiceImpl() {
        /* empty constructor */
    }

    @Activate
    protected void activate(ComponentContext componentContext) {
        this.accessToken = OsgiUtil.toString(componentContext.getProperties().get(SERVICE_PROPERTY_ACCESS_TOKEN), "");
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

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

}
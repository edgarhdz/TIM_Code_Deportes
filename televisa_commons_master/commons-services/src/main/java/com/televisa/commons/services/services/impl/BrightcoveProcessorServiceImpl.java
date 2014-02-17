
package com.televisa.commons.services.services.impl;

import com.day.cq.wcm.api.PageManagerFactory;
import com.televisa.commons.services.brightcove.processor.impl.BrightcoveProcessorImpl;
import com.televisa.commons.services.services.BrightcoveProcessorService;
import com.televisa.commons.services.utilities.ServicesTransferModel;
import com.televisa.commons.services.brightcove.processor.BrightcoveProcessor;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.io.Reader;

@Component(immediate = true)
@Service(value = BrightcoveProcessorService.class)
public class BrightcoveProcessorServiceImpl implements BrightcoveProcessorService {

    private static final Logger LOG = LoggerFactory.getLogger(BrightcoveProcessorServiceImpl.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private PageManagerFactory pageManagerFactory;

    @Reference
    private EventAdmin eventAdmin;

    private ResourceResolver resourceResolver;

    private ServicesTransferModel getServicesModel() {
        ServicesTransferModel services = new ServicesTransferModel();
        services.getServices().put(ResourceResolverFactory.class, resourceResolverFactory);
        services.getServices().put(PageManagerFactory.class, pageManagerFactory);
        services.getServices().put(EventAdmin.class, eventAdmin);
        return services;
    }

    @Override
    public boolean process(Reader reader, String action) {
        BrightcoveProcessor processor = new BrightcoveProcessorImpl(getServicesModel(), resourceResolver);
        return processor.process(reader, action);
    }

    @Activate
    protected void activate(ComponentContext context) {
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

}

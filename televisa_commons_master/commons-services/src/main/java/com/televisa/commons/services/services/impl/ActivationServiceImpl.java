package com.televisa.commons.services.services.impl;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;
import com.televisa.commons.services.gsa.Constants;
import com.televisa.commons.services.gsa.GsaUtilities;
import com.televisa.commons.services.services.ActivationService;
import com.televisa.commons.services.services.GsaService;
import com.televisa.commons.services.utilities.Utilities;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: palecio
 * Date: 30/7/13
 * Time: 15:26
 */
@Component(immediate=true,
        metatype = true,
        label = "Televisa Activation Service"
)
@Service(value = ActivationService.class)
public class ActivationServiceImpl implements ActivationService {

    private static final Logger LOG = LoggerFactory.getLogger(GsaServiceImpl.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;
    @Reference
    private Replicator replicator;
    @Reference
    private GsaService gsaService;
    @Reference
    private PageManagerFactory pageManagerFactory;


    private ResourceResolver resourceResolver;
    private Session session;
    private PageManager pageManager;


    @Activate
    protected void activate(ComponentContext componentContext){
        try {
            resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
            session = resourceResolver.adaptTo(Session.class);
            pageManager = pageManagerFactory.getPageManager(resourceResolver);
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    @Deactivate
    protected void deactivate(final ComponentContext context) {
        this.resourceResolver.adaptTo(Session.class).logout();
    }

    public void activatePage(String path){
        if(replicatePage(path)){
            setFirstReplicatedDate(path);
            gsaPush(path);
        }
    }
    
    public void gsaPush(String path){
        Page page = pageManager.getPage(path);
        if(page != null){
            Page[] pages = new Page[1];
            pages[0] = page;
            try {
                gsaService.pushXML( pages, Constants.ACTION_ADD );
            } catch (Exception e) {
            	LOG.error(e.getMessage());
                e.printStackTrace();
            }

            Node node;
            try {
                node = session.getNode(page.getContentResource().getPath());
                node.setProperty("gsadeliverydate", GsaUtilities.getDateToString("dd-MM-yyyy HH:mm:ss z", new Date()));
                session.save();
            } catch (RepositoryException e) {
                LOG.error("Cannot set gsadeliverydate property");
                LOG.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void gsaDelete(String path){
        Page page = pageManager.getPage(path);
        if(page != null){
            Page[] pages = new Page[1];
            pages[0] = page;
            try {
                gsaService.pushXML( pages, Constants.ACTION_DELETE );
            } catch (Exception e) {
                LOG.error("Exception -> " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    public void setFirstReplicatedDate(String path){
        Node node;
        try {
            if ((node = session.getNode(path)) != null) {
                if((node = node.getNode("jcr:content")) != null){
                    Calendar firstReplicated = Utilities.getCalendar(node, "cq:firstReplicated");
                    if (firstReplicated == null) {
                            node.setProperty("cq:firstReplicated", Calendar.getInstance());
                            session.save();
                    }
                }
            }
        } catch (RepositoryException e) {
            LOG.error("Cannot set cq:firstReplicated property");
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
    }
    
    private boolean replicatePage(String path){
        boolean success = true;
        try {
            replicator.replicate(session, ReplicationActionType.ACTIVATE, path);
        } catch (ReplicationException e) {
            success = false;
            LOG.error("Error replicating page.");
            LOG.error(e.getMessage());
            e.printStackTrace(); 
        }
        return success;
    }
    
}

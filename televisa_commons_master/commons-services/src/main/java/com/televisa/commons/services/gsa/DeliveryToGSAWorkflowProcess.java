/**
 * GSA delivery workflow process class
 *
 * This class define the process of the xml feed creation and delivery to the GSA server.
 *
 * Changes History:
 *
 *         2013-01-17 Initial Development
 *
 * @author jesquivel@xumak.com
 * @version 1.0
 */

package com.televisa.commons.services.gsa;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import com.televisa.commons.services.services.GsaService;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.LoginException;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.Date;


@Component
@Service
@Properties({
        @Property(name = Constants.SERVICE_DESCRIPTION, value = "Workflow process to delivery xml feed to GSA."),
        @Property(name = Constants.SERVICE_VENDOR, value = "Televisa"),
        @Property(name = "process.label", value = "Delivery GSA feed WorkflowProcess")
})

public class DeliveryToGSAWorkflowProcess implements WorkflowProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryToGSAWorkflowProcess.class);
    private static final String TYPE_JCR_PATH = "JCR_PATH";

    private String gsaUrlArg;
    private String actionArg;
    private String applyArg;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private PageManagerFactory pageManagerFactory;

    @Reference
    private GsaService gsaService;

    @Override
    public void execute(WorkItem item, WorkflowSession session, MetaDataMap args) throws WorkflowException {


        gsaUrlArg = "http://localhost:gsafeed";
        actionArg = args.get("action", "add");
        applyArg  = args.get("enable", "false");

        LOGGER.info(", url: " + gsaUrlArg + ", action: " + actionArg + ", enabled: " + applyArg);

        if ("false".equals(applyArg)) return;

        LOGGER.info("apply gsa feed delivery");

        WorkflowData workflowData = item.getWorkflowData();
        String currentPage = (String)workflowData.getPayload();

        if (workflowData.getPayloadType().equals(TYPE_JCR_PATH)) {
            String path = workflowData.getPayload().toString() + "/jcr:content";

            try {

                Node node = (Node) session.getSession().getItem(path);
                if (node != null) {
                    ResourceResolver resolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
                    PageManager pageManager   = pageManagerFactory.getPageManager(resolver);
                    Page page 				  = pageManager.getPage(currentPage);

                    Page[] pages = new Page[1];
                    pages[0] = page;

                    gsaService.pushXML( pages, actionArg );

                    node.setProperty("gsadeliverydate", GsaUtilities.getDateToString("dd-MM-yyyy HH:mm:ss z", new Date()));
                    session.getSession().save();
                }

            } catch(RepositoryException e) {
                LOGGER.error("Exception: " + e.getMessage());
                throw new WorkflowException(e.getMessage(), e);
            } catch(LoginException e) {
                LOGGER.error("Exception: " + e.getMessage());
                throw new WorkflowException(e.getMessage(), e);
            } catch(Exception e) {
                LOGGER.error("Exception: " + e.getMessage());
                throw new WorkflowException(e.getMessage(), e);
            }

        }


    }

}

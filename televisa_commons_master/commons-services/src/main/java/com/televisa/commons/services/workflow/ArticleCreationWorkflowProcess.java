package com.televisa.commons.services.workflow;

import com.day.cq.dam.commons.process.AbstractAssetWorkflowProcess;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.metadata.MetaDataMap;
import com.televisa.commons.services.articlecreation.ArticleRelocator;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * Article Creation Workflow Process
 *
 * This workflow process should be triggered when a page is created. It places the page in its correct path.
 * Changes History:
 *
 *         2013-02-5 Initial Development
 *
 * @author palecio@xumak.com
 * @version 1.0
 */
@Component
@Service
@Properties({
        @Property(name = Constants.SERVICE_DESCRIPTION, value = "Article Creation workflow process implementation."),
        @Property(name = Constants.SERVICE_VENDOR, value = "XumaK"),
        @Property(name = "process.label", value = "Article Creation Workflow")})
public class ArticleCreationWorkflowProcess extends AbstractAssetWorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleCreationWorkflowProcess.class);

    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaData) throws WorkflowException {

        LOG.info(">>>> Starting Article Creation Workflow");

        Node pageNode = this.getNodeFromPayload(workItem,workflowSession.getSession());
        PageManager pageManager = this.getResourceResolver(workflowSession.getSession()).adaptTo(PageManager.class);
        Session session = workflowSession.getSession();
        try{
            ArticleRelocator articleRelocator = new ArticleRelocator(pageNode.getPath(), pageManager, session );
            articleRelocator.relocatePage();
        }catch (WCMException wcmException){
            LOG.error("Exception: " + wcmException.getMessage());
        } catch (RepositoryException e) {
            LOG.error("Exception: " + e.getMessage());
        } finally {
            workflowSession.terminateWorkflow(workItem.getWorkflow());
        }

    }
}

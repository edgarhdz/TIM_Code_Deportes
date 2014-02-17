/*
 * ArticlePublishDateUpdateWorkflowProcess.java
 *
 * Workflow step process to update the publish date.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.workflow;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.util.Calendar;

/**
 * Article Publish Date Update Workflow Process
 *
 * Workflow step process to update the publish date.
 *
 * Changes History:
 *
 *         2013-03-18 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@Component
@Service
@Properties({
        @Property(name = Constants.SERVICE_DESCRIPTION, value = "Workflow step process to update the publish date."),
        @Property(name = Constants.SERVICE_VENDOR, value = "Televisa"),
        @Property(name = "process.label", value = "Publish Date Update Workflow Process")
})
public class ArticlePublishDateUpdateWorkflowProcess implements WorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(ArticlePublishDateUpdateWorkflowProcess.class);

    /**
     * The JCR_CONTENT path
     */
    private static final String JCR_CONTENT = "jcr:content";

    /**
     * Get a calendar property from a node.
     *
     * @param node the node to get the property of
     * @param relPath the name of the property
     * @return the calendar property or null
     */
    private Calendar getCalendar(Node node, String relPath) {
        try {
            if (node.hasProperty(relPath)) {
                return node.getProperty(relPath).getDate();
            }
        } catch (ValueFormatException e) {
            LOG.debug(e.getMessage(), e);
        } catch (PathNotFoundException e) {
            LOG.debug(e.getMessage(), e);
        } catch (RepositoryException e) {
            LOG.debug(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Update the last replicated time in the first replicated time.
     *
     * @param path the path to the node
     * @param session the JCR Session
     * @throws javax.jcr.RepositoryException if an error occurs
     */
    public void updateLastReplicatedTime(String path, Session session) throws RepositoryException {
        Node node;
        node = session.getNode(path);
        if (node == null) {
            LOG.warn("The {} workflow got a payload which was not found.",
            ArticlePublishDateUpdateWorkflowProcess.class.getCanonicalName());
        } else {
            if (node.hasNode(JCR_CONTENT)) {
                node = node.getNode(JCR_CONTENT);
                Calendar firstReplicated = getCalendar(node, "cq:firstReplicated");
                Calendar lastReplicated = getCalendar(node, "cq:lastReplicated");
                if (firstReplicated == null && lastReplicated != null) {
                    node.setProperty("cq:firstReplicated", lastReplicated);
                    session.save();
                }
            }
        }
    }

    /**
     * Executes the workflow step, if the page has not the firstReplicated property then the lastReplicated property
     * is stored if exists. The session doesn't need to be closed or the flow throws an exception.
     *
     * @param workItem the WorkItem
     * @param workflowSession the WorkflowSession
     * @param metaDataMap the MetaDataMap
     */
    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        if (workItem.getWorkflowData().getPayload() instanceof String) {
            String path = ((String) workItem.getWorkflowData().getPayload());
            try {
                Session session = workflowSession.getSession();
                updateLastReplicatedTime(path, session);
            } catch (RepositoryException e) {
                LOG.error(e.getMessage(), e);
            }
        } else {
            LOG.warn("The {} workflow got a payload which is not a string path to a page.",
                    ArticlePublishDateUpdateWorkflowProcess.class.getCanonicalName());
        }
    }

}

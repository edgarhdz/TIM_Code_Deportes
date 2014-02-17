/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.televisa.commons.services.services.impl;

import java.util.Calendar;
import java.util.logging.Level;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.Workspace;
import javax.jcr.query.InvalidQueryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.televisa.commons.services.services.VideoPlayerstateJob;

/**
 *
 * @author jdiaz86
 */
@Component(immediate=true, metatype = true, label = "VideoPlayerStateJob", description="Cron job that will update livestream video assets status")
@Service(value = VideoPlayerstateJob.class)
public class VideoPlayerstateJobImpl implements VideoPlayerstateJob {

    private static final String FORMAT_DATE = "dd/MM/yyyy";

    /** The scheduler expression that use for active the process  */
    @Property(
            label="Scheduler Expression",
            name="schedulerExpression",
            value="0 45 23 * * ? *",
            description="Default value: 0 45 23 * * ? *, where each position means (minutes hours dayOfMonth month weekday year)")
    public static final String SCHEDULER_EXPRESSION = "schedulerExpression";

    /** Node that contains all data from unsent mail data  */
    @Property(
            label="Path of Live Streams",
            name="pathLiveStreams",
            value="/content/televisa/deportes",
            description="Default value: /content/televisa/deportes/otros-deportes")
    public static final String PATH_LIVE_STREAMS = "pathLiveStreams";

    @Reference
    public Scheduler scheduler;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    private ResourceResolver resourceResolver;

    @Reference
    public SlingRepository repository;

    private static final Logger LOG = LoggerFactory.getLogger(VideoPlayerstateJobImpl.class);

    @Override
    public String runJob() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Activate
    protected void activate(ComponentContext componentContext) throws RepositoryException{
        final String schedulerExpression = (String)componentContext.getProperties().get(SCHEDULER_EXPRESSION);
        final String pathLiveStreams = (String)componentContext.getProperties().get(PATH_LIVE_STREAMS);

        // give administrative permission
        final Session session = repository.loginAdministrative(null);

        final Runnable VideoPlayerstate = new Runnable() {
            @Override
            public void run() {
                try {
                    LOG.info("VideoPlayerstate:  " + pathLiveStreams);

                    Workspace workspace = session.getWorkspace();
                    //QueryManager queryManager = workspace.getQueryManager();
                    //Query query = queryManager.createQuery("", javax.jcr.query.Query.JCR_SQL2);

                    Node rootNode = session.getNode(pathLiveStreams);
                    processPathLiveStreams(rootNode);

                } catch (InvalidQueryException ex) {
                    java.util.logging.Logger.getLogger(VideoPlayerstateJobImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RepositoryException ex) {
                    java.util.logging.Logger.getLogger(VideoPlayerstateJobImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        //create the runnable objects
        try{
            this.scheduler.addJob("VideoPlayerState", VideoPlayerstate, null, schedulerExpression, true);
        }catch(Exception e){
            LOG.error("Exception " + e.getMessage());
            LOG.error("Exception ", e);
        }
    }

    protected void deactivate(ComponentContext componentContext) {
        LOG.info("AccountExpire --- Desactivated ---");
    }


    /**
     * @param node
     */
    private void processPathLiveStreams(Node node){


        try {
            if(node != null){
                processLiveStream(node);
                NodeIterator nodes = node.getNodes();

                if(nodes != null){
                    while (nodes.hasNext()) {
                        processPathLiveStreams(nodes.nextNode());
                    }
                }else{
                    processLiveStream(node);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param node
     */
    private void processLiveStream(Node node){

        try {
            if(node.getName().equalsIgnoreCase("livestream") || node.getName().equalsIgnoreCase("multistream")){
                if(node != null && node.hasProperties()){
                    PropertyIterator properties = node.getProperties();
                    if(properties.getSize() > 1){
                        String status = null;
                        Calendar initialTime = null;
                        Calendar finishTime = null;

                        Calendar actualTime = Calendar.getInstance();

                        while (properties.hasNext()) {
                            javax.jcr.Property property = (javax.jcr.Property) properties.nextProperty();
                            if(property != null){
                                String name = property.getName();
                                if(name != null){
                                    if(name.equalsIgnoreCase("status")){
                                        Value value = property.getValue();
                                        if(value != null){
                                            String strValue = value.getString();
                                            if(strValue.equalsIgnoreCase("on")){
                                                status 	= strValue;
                                            }
                                        }
                                    }

                                    if(name.equalsIgnoreCase("initialDate")){
                                        Value value = property.getValue();
                                        if(value != null){
                                            initialTime = value.getDate();
                                        }
                                    }

                                    if(name.equalsIgnoreCase("finishDate")){
                                        Value value = property.getValue();
                                        if(value != null){
                                            finishTime = value.getDate();
                                        }
                                    }
                                }
                            }
                        }

                        String newStatus = "off";
                        if (actualTime.after(finishTime)) {
                            newStatus = "post";
                        }
                        if (actualTime.before(initialTime)) {
                            newStatus="pre";
                        }
                        if (actualTime.before(finishTime) && actualTime.after(initialTime)) {
                            newStatus="on";
                        }
                        final Session session = repository.loginAdministrative(null);
                        Node jcrContentText = null;
                        if (node.hasNode("playerstate.txt")) {
                            String pathTXT = node.getPath()+"/playerstate.txt/jcr:content";
                            jcrContentText = session.getNode(pathTXT);

                            if(jcrContentText.hasProperty("jcr:data")){
                                String currentStatus = jcrContentText.getProperty("jcr:data").getValue().getString();
                                if(!currentStatus.trim().equals(newStatus)){
                                    jcrContentText.setProperty("jcr:data",newStatus);
                                    session.save();
                                    session.logout();
                                }

                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            LOG.error("ERROR: "+e.getMessage());
        }
    }
}
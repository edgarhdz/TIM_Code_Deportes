/*
 * BrightcoveProcessorImpl.java
 *
 * The component and service to perform the brightcove process.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.brightcove.processor.impl;

import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import com.day.cq.dam.api.Asset;
import com.televisa.commons.services.brightcove.BrightcoveHelper;

import com.televisa.commons.services.services.ActivationService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;
import com.day.cq.wcm.api.WCMException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.televisa.commons.services.brightcove.processor.BrightcoveProcessor;
import com.televisa.commons.services.event.NoteManagerEventManager;
import com.televisa.commons.services.event.NoteManagerEventTopic;
import com.televisa.commons.services.event.impl.NoteManagerEventManagerImpl;
import com.televisa.commons.services.utilities.ApplicationProperties;
import com.televisa.commons.services.utilities.ServicesTransferModel;

/**
 * Brightcove Processor
 *
 * The component and service to perform the brightcove process.
 *
 * Changes History:
 *
 *         2013-04-02 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class BrightcoveProcessorImpl implements BrightcoveProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(BrightcoveProcessorImpl.class);
    public static final String ARTICLE_INDEX_TEMPLATE = "/apps/deportes/local/templates/articleIndex";
    private static final String ADD_ACTION = "add";
    private static final String MODIFY_ACTION = "modify";

/*

JSON Sample
 - Add -
{
    "name": "video",
    "title": "Video",
    "program_node": "programas-tercer-grado",
    "release_date": "2013-04-03 21:00:00",
    "ref_id": "7ba1d5505d23b0ac7b59b224fb8998fb",
    "brightcove_id": "1116920512001",
    "geofilter": "ONLY_MEX",
    "program": "La entrevista por Adela",
    "summary": "Adela Micha entrevista a las personalidades que marcan la age en México y el mundo",
    "season": "8",
    "chapter": "6",
    "source": "EFE",
    "cast": "cast1, cast2",
    "tooltip": "text",
    "author": "Jeremy Pleuger",
    "video_type": "clip",
    "video_low": "http://m4v.tvolucion.com/m4v/boh/venda/7ba1d5505d23b0ac7b59b224fb8998fb/23b0ac7b59-150.mp4",
    "video_medium": "http://m4v.tvolucion.com/m4v/boh/venda/7ba1d5505d23b0ac7b59b224fb8998fb/23b0ac7b59-480.mp4",
    "thumbnail_url": "http://m4v.tvolucion.com/m4v/boh/venda/7ba1d5505d23b0ac7b59b224fb8998fb/23b0ac7b59.jpg",
    "image_url": "http://m4v.tvolucion.com/m4v/boh/venda/7ba1d5505d23b0ac7b59b224fb8998fb/23b0ac7b59-STILL.jpg",
    "video_path": "/m4v/boh/venda/7ba1d5505d23b0ac7b59b224fb8998fb/23b0ac7b59",
    "duration": "01:45:30",
    "player_id": "643082227001"
}
 - Modify -

{
    "ref_id": "7ba1d5505d23b0ac7b59b224fb8998fb",
    "video_high": "http://m4v.tvolucion.com/m4v/boh/venda/7ba1d5505d23b0ac7b59b224fb8998fb/23b0ac7b59-600.mp4"
}

*/

    private PageManagerFactory pageManagerFactory;
    private NoteManagerEventManager noteManagerEventManager;
    private ResourceResolver resourceResolver;
    private Session session;
    private ActivationService activationService;

    /**
     * Brightcove process constructor.
     * @param services the map of services transfered
     * @param resourceResolver the resource resolver
     */
    public BrightcoveProcessorImpl(final ServicesTransferModel services, ResourceResolver resourceResolver) {
        pageManagerFactory = (PageManagerFactory) services.getServices().get(PageManagerFactory.class);
        EventAdmin eventAdmin = (EventAdmin) services.getServices().get(EventAdmin.class);
        noteManagerEventManager = new NoteManagerEventManagerImpl(eventAdmin);
        this.resourceResolver = resourceResolver;
        session = this.resourceResolver.adaptTo(Session.class);
        activationService = (ActivationService) services.getServices().get(ActivationService.class);
    }

    /**
     * Process the data on the reader.
     * @param reader the reader to read data from
     * @return true if it was processed successfully, false otherwise
     */
    @Override
    public boolean process(final Reader reader, final String action) {

        try {
            return processMessage(getMessage(reader), action);
        } catch (WCMException e) {
            LOG.error(e.getMessage(), e);
        } catch (LoginException e) {
            LOG.error(e.getMessage(), e);
        } catch (JsonSyntaxException e) {
            LOG.error(e.getMessage(), e);
        } catch (JsonIOException e) {
            LOG.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * Get a JSON message from the reader and convert into a message object.
     * @param reader the reader to read data from
     * @return a new message object
     * @throws JsonSyntaxException if there is an error in the JSON syntax
     * @throws JsonIOException if an IO exception occurs
     */
    protected Message getMessage(final Reader reader) throws JsonSyntaxException, JsonIOException {
        Gson gson = new GsonBuilder().registerTypeAdapter(Message.class, new MessageInstanceCreator()).create();
        return gson.fromJson(reader, Message.class);
    }


    protected void raiseNoteManagerServiceEvent(NoteManagerEventTopic topic, String name) {
        noteManagerEventManager.setTopic(topic);
        noteManagerEventManager.putProperty("NODE_NAME", name);
        noteManagerEventManager.raiseEvent();
        LOG.info("EVENT : {} : {}", topic.getPath(), name);
    }

    private Node getNode(String path){
        Node node = null;
        try {
             node = session.getNode(path);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return node;
    }

    private boolean setNodeProperty(Node node, String path, String name, String value) {
        try {

            if (value != null && ! "".equals(value) && node != null && node.hasNode(path)) {
                Node child = getNode(node.getPath() + "/" + path);
                child.setProperty(name, value);
                session.save();
                return true;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return false;
    }

    private boolean setNodeDateProperty(Node node, String path, String name, Calendar date) {
        try {
            if (date != null && node != null && node.hasNode(path)) {
                Node child = getNode(node.getPath() + "/" + path);
                child.setProperty(name, date);
                session.save();
                return true;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * Try to create a video note with the provided parameters.
     *
     * @param message the message obtained from the JSON to process
     * @param action add or modify
     * @throws LoginException If an error occurs creating the new ResourceResolver with the provided credential data
     * @throws WCMException if an error during the create page operation occurs
     * @return true if the message was processed correctly, false otherwise
     */
    protected boolean processMessage(Message message, String action) throws LoginException, WCMException {

        boolean success = false;
        String pagePath = "";

        if (LOG.isDebugEnabled()) {
            LOG.debug("message = [ {} ]", message);
            LOG.debug("pagePath = {}", message.getPagePath());
            LOG.debug("categoryDate = {}", message.getCategoryDate());
            LOG.debug("parentPagePath = {}", message.getParentPagePath());
        }
        LOG.info(message.toString());
        if(ADD_ACTION.equals(action)){
            PageManager pageManager = pageManagerFactory.getPageManager(resourceResolver);
            if(message.title != null && message.name != null && message.program_node != null && message.ref_id != null ){

                if (pageManager.getPage(message.getParentPagePath()) == null) {

                    String pageId = message.getPageID();
                    String parentPage = message.getParentPagePath().substring(0, message.getParentPagePath().indexOf("/"+pageId));
                    Page dateDir = pageManager.create(parentPage, pageId, ARTICLE_INDEX_TEMPLATE, pageId);
                    Node dateDirNode = dateDir.adaptTo(Node.class);
                    try{
                        Node jcrContent = dateDirNode.getNode( "jcr:content" );
                        jcrContent.setProperty( "redirectTarget", dateDir.getParent().getPath() );
                        jcrContent.setProperty( "sling:redirect", Boolean.TRUE );
                        jcrContent.setProperty( "sling:redirectStatus", "302" );
                        jcrContent.setProperty( "sling:resourceType", "foundation/components/redirect" );
                    } catch( Exception e2 ) {
                        LOG.error( "error when setting redirect properties", e2 );
                    }
                }

                Page page = pageManager.getPage(message.getPagePath() + "/" + message.name);
                if(page == null){
                    page = pageManager.create(message.getParentPagePath(), message.name, ApplicationProperties.getVideoTemplateName(), message.title);
                }
                pagePath = page.getPath();
                Node jcrContent;
                Node node = null;
                try{
                    node = getNode(page.getPath());
                    jcrContent = getNode(page.getContentResource().getPath());
                    jcrContent.addNode("video");
                    Node videoNode = jcrContent.getNode("video");
                    videoNode.addNode("brightcove");
                    session.save();
                }catch (RepositoryException re){
                    re.printStackTrace();
                    LOG.error(re.getMessage());
                }

                Calendar releaseDate = BrightcoveHelper.toDate(message.release_date);
                String geofilter = BrightcoveHelper.toMD5(message.geofilter);
                Asset image = BrightcoveHelper.uploadImageToDam(resourceResolver, message.image_url, message.name);
                String imagePath = image != null? image.getPath() : "";

                setNodeProperty(node, "jcr:content", "createdAutomatically", "true");
                setNodeProperty(node, "jcr:content/video", "sling:resourceType", "deportes/generics/components/content/video/video");
                setNodeProperty(node, "jcr:content/video", "videoPlayer", "8");
                setNodeDateProperty(node, "jcr:content/video", "releaseDate", releaseDate);
                setNodeProperty(node, "jcr:content/video", "program", message.program);
                setNodeProperty(node, "jcr:content/video", "programName", message.program);
                setNodeProperty(node, "jcr:content/video", "summary", message.summary);
                setNodeProperty(node, "jcr:content/video", "season", message.season);
                setNodeProperty(node, "jcr:content/video", "chapter", message.chapter);
                setNodeProperty(node, "jcr:content/video", "source", message.source);
                setNodeProperty(node, "jcr:content/video", "cast", message.cast);
                setNodeProperty(node, "jcr:content/video", "tooltip", message.tooltip);
                setNodeProperty(node, "jcr:content/video", "author", message.author);
                setNodeProperty(node, "jcr:content/video", "videoType", message.video_type);
                setNodeProperty(node, "jcr:content/video", "fileReference", imagePath);
                setNodeProperty(node, "jcr:content/video/brightcove", "referenceId", message.ref_id);
                setNodeProperty(node, "jcr:content/video/brightcove", "videoId", message.brightcove_id);
                setNodeProperty(node, "jcr:content/video/brightcove", "geoFilter", geofilter);
                setNodeProperty(node, "jcr:content/video/brightcove", "videoUrl", message.video_low);
                setNodeProperty(node, "jcr:content/video/brightcove", "videoMedium", message.video_medium);
                setNodeProperty(node, "jcr:content/video/brightcove", "thumbnailUrl", message.thumbnail_url);
                setNodeProperty(node, "jcr:content/video/brightcove", "imageUrl", message.image_url);
                setNodeProperty(node, "jcr:content/video/brightcove", "assetPath", message.video_path);
                setNodeProperty(node, "jcr:content/video/brightcove", "duration", message.duration);
                setNodeProperty(node, "jcr:content/video/brightcove", "playerId", message.player_id);
                setNodeProperty(node, "jcr:content/video/brightcove", "videoHigh", message.video_high);
                setNodeProperty(node, "jcr:content/video/brightcove", "m3u8", message.video_hls);
                setNodeProperty(node, "jcr:content/video/brightcove", "f4m", message.video_hds);

                raiseNoteManagerServiceEvent(NoteManagerEventTopic.CREATED_VIDEO, page.getPath());
                success = true;
                }

        }else if(MODIFY_ACTION.equals(action)){
            if(message.ref_id != null && !message.ref_id.equals("")){
                Page page = BrightcoveHelper.getPageByRefId(message.ref_id, resourceResolver);
                if(page != null){
                    pagePath = page.getPath();
                    Node node = page.adaptTo(Node.class);

                    if(message.video_high != null && !message.video_high.equals("")){
                        setNodeProperty(node, "jcr:content/video/brightcove", "videoHigh", message.video_high);
                    }
                    if(message.video_hls != null && !message.video_hls.equals("")){
                        setNodeProperty(node, "jcr:content/video/brightcove", "m3u8", message.video_hls);
                    }
                    if(message.video_hds != null && !message.video_hds.equals("")){
                        setNodeProperty(node, "jcr:content/video/brightcove", "f4m", message.video_hds);
                    }
                    if(message.title != null && !message.title.equals("")){
                        setNodeProperty(node, "jcr:content", "jcr:title", message.title);
                    }
                    if(message.release_date != null && !message.release_date.equals("")){
                        Calendar releaseDate = BrightcoveHelper.toDate(message.release_date);
                        setNodeDateProperty(node, "jcr:content/video", "releaseDate", releaseDate);
                    }
                    if(message.brightcove_id != null && !message.brightcove_id.equals("")){
                        setNodeProperty(node, "jcr:content/video/brightcove", "videoId", message.brightcove_id);
                    }
                    if(message.geofilter != null && !message.geofilter.equals("")){
                        String geofilter = BrightcoveHelper.toMD5(message.geofilter);
                        setNodeProperty(node, "jcr:content/video/brightcove", "geoFilter", geofilter);
                    }
                    if(message.program != null && !message.program.equals("")){
                        setNodeProperty(node, "jcr:content/video", "program", message.program);
                        setNodeProperty(node, "jcr:content/video", "programName", message.program);
                    }
                    if(message.summary != null && !message.summary.equals("")){
                        setNodeProperty(node, "jcr:content/video", "summary", message.summary);
                    }
                    if(message.season != null && !message.season.equals("")){
                        setNodeProperty(node, "jcr:content/video", "season", message.season);
                    }
                    if(message.chapter != null && !message.chapter.equals("")){
                        setNodeProperty(node, "jcr:content/video", "chapter", message.chapter);
                    }
                    if(message.source != null && !message.source.equals("")){
                        setNodeProperty(node, "jcr:content/video", "source", message.source);
                    }
                    if(message.cast != null && !message.cast.equals("")){
                        setNodeProperty(node, "jcr:content/video", "cast", message.cast);
                    }
                    if(message.tooltip != null && !message.tooltip.equals("")){
                        setNodeProperty(node, "jcr:content/video", "tooltip", message.tooltip);
                    }
                    if(message.author != null && !message.author.equals("")){
                        setNodeProperty(node, "jcr:content/video", "author", message.author);
                    }
                    if(message.video_type != null && !message.video_type.equals("")){
                        setNodeProperty(node, "jcr:content/video", "videoType", message.video_type);
                    }
                    if(message.video_low != null && !message.video_low.equals("")){
                        setNodeProperty(node, "jcr:content/video/brightcove", "videoUrl", message.video_low);
                    }
                    if(message.video_medium != null && !message.video_medium.equals("")){
                        setNodeProperty(node, "jcr:content/video/brightcove", "videoMedium", message.video_medium);
                    }
                    if(message.video_path != null && !message.video_path.equals("")){
                        setNodeProperty(node, "jcr:content/video/brightcove", "assetPath", message.video_path);
                    }
                    if(message.duration != null && !message.duration.equals("")){
                        setNodeProperty(node, "jcr:content/video/brightcove", "duration", message.duration);
                    }
                    if(message.player_id != null && !message.player_id.equals("")){
                        setNodeProperty(node, "jcr:content/video/brightcove", "playerId", message.player_id);
                    }
                    try {
                        if(node.getProperty("jcr:content/cq:lastReplicated") != null){
                            activationService.activatePage(pagePath);
                        }
                    } catch (RepositoryException e) {
                        e.printStackTrace();
                    }
                    success = true;
                }
            }
        }
        return success;
    }

    public class Message {

        private SimpleDateFormat sdf = new SimpleDateFormat("yyMM");

        public Message() {

        }

        @Expose
        public String name;

        @Expose
        public String title;

        @Expose
        public String program_node;

        @Expose
        public String release_date;

        @Expose
        public String ref_id;

        @Expose
        public String brightcove_id;

        @Expose
        public String geofilter;

        @Expose
        public String program;

        @Expose
        public String summary;

        @Expose
        public String season;

        @Expose
        public String chapter;

        @Expose
        public String source;

        @Expose
        public String cast;

        @Expose
        public String tooltip;

        @Expose
        public String author;

        @Expose
        public String video_type;

        @Expose
        public String video_low;

        @Expose
        public String video_medium;

        @Expose
        public String thumbnail_url;

        @Expose
        public String image_url;

        @Expose
        public String video_path;

        @Expose
        public String duration;

        @Expose
        public String player_id;

        @Expose
        public String video_high;

        @Expose
        public String video_hls;

        @Expose
        public String video_hds;



        public String getParentPagePath() {
            return ApplicationProperties.getNoticierosContentPath() + "/" + program_node + "/" + getPageID();
        }

        /**
         * Try to convert the publishedDate UNIX Timestamp into a valid number.
         * Will make use of the current timestamp if it couldn't be converted.
         * &lt;b&gt;It will use milliseconds as the convert units.&lt;/b&gt;
         *
         * @return the format required by the Televisa Team site structure.
         */
        public String getCategoryDate() {
            Calendar calendar = Calendar.getInstance();
            long date = (new Date()).getTime();
            calendar.setTimeInMillis(date);
            return sdf.format(calendar.getTime());
        }

        public String getPageID(){
            Calendar calendar = Calendar.getInstance();
            String year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2);
            String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            month = Integer.parseInt(month) < 10 ? '0' + month : month;
            return year + month;
        }
        public String getPagePath() {
            return String.format("%s/%s", getParentPagePath(), getCategoryDate());
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            try {
                for (Field field : this.getClass().getFields()) {
                    builder.append(String.format("[%s = %s] ", field.getName(), field.get(this)));
                }
            } catch (IllegalArgumentException e) {
                LOG.error(e.getMessage());
            } catch (IllegalAccessException e) {
                LOG.error(e.getMessage());
            }
            return builder.toString();
        }

    }

    public class MessageInstanceCreator implements InstanceCreator<Message> {

        @Override
        public Message createInstance(Type type) {
            return new Message();
        }

    }


}

/*
 * Functions.java
 *
 * Defined utility functions.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib.functions;

import com.day.cq.wcm.api.Page;
import com.squeakysand.jsp.tagext.annotations.JspElFunction;
import com.televisa.commons.services.dataaccess.helper.VideoNodeHelper;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.datamodel.Video;
import com.televisa.commons.services.services.GsaService;
import com.televisa.commons.services.utilities.Utilities;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Functions
 *
 * Defined utility functions.
 *
 * Changes History:
 *
 *         2013-02-27 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class Functions {

    @JspElFunction(example = "<cq:text value=\"${tg:appendHTML(path)}\" />")
    public static String appendHTML(String path) {
        if (!path.endsWith("html")) {
            return path.concat("html");
        }
        return path;
    }

    @JspElFunction(example = "<cq:text value=\"${tg:getChannel(sling, currentPage)}\" />")
    public static String getChannel(ResourceResolver resourceResolver, Page page) {
        String[] paths = page.getPath().split("/");
        if (paths.length > 3) {
            return paths[3];
        }
        return null;
    }

    @JspElFunction(example = "<cq:text value=\"${tg:getCompleteURL(gsaService, path, domain)}\" />")
    public static String getCompleteURL(GsaService gsaService, String path, String domain) {
        return Utilities.getCompleteURL(gsaService, path, domain);
    }

    @JspElFunction(example = "<cq:text value=\"${tg:wrapCDATA(text)}\" />")
    public static String wrapCDATA(String text) {
        return "<![CDATA[".concat(text).concat("]]>");
    }

    @JspElFunction(example = "<cq:text value=\"${tg:getChannelID(channel)}\" />")
    public static String getChannelID(String channel) {
        return String.valueOf(channel.hashCode());
    }

    @JspElFunction(example = "<cq:text value=\"${tg:getMobileDate(date)}\" />")
    public static String getMobileDate(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("ES"));
        return date == null? "" : dateFormat.format(date.getTime());
    }

    @JspElFunction(example = "<cq:text value=\"${tg:removeHtml(text)}\" />")
    public static String removeHtml(String text) {
        Pattern pattern = Pattern.compile("\\<.*?\\>"); //Matches any kind of tag
        Matcher matcher = pattern.matcher(text);
        String newText = matcher.replaceAll(""); //Removes all the tags and replaces them with space characters
        newText = StringEscapeUtils.unescapeHtml(newText);

        return newText;
    }

    @JspElFunction(example = "<cq:text value=\"${tg:getVideoPropertyName(videoType)}\" />")
    public static String getVideoProperty(Note note, String videotype) {
        String videoProperty = "";
        if(note.isVideo()){
            Video video = (Video) note;
            ValueMap brightcoveProperties = video.getBrightcoveProperties();
            if(brightcoveProperties != null){
                if(VideoNodeHelper.HLS_HIGH_VIDEO_TYPE.equals(videotype)){
                    if(brightcoveProperties.containsKey(VideoNodeHelper.M3U8_PROPERTY)){
                        videoProperty = brightcoveProperties.get(VideoNodeHelper.M3U8_PROPERTY).toString();
                    }else if(brightcoveProperties.containsKey(VideoNodeHelper.HIGH_PROPERTY)){
                        videoProperty = brightcoveProperties.get(VideoNodeHelper.HIGH_PROPERTY).toString();
                    }
                }else if(VideoNodeHelper.HLS_VIDEO_TYPE.equals(videotype)){
                    if(brightcoveProperties.containsKey(VideoNodeHelper.M3U8_PROPERTY)){
                        videoProperty = brightcoveProperties.get(VideoNodeHelper.M3U8_PROPERTY).toString();
                    }
                }else if(VideoNodeHelper.LOW_VIDEO_TYPE.equals(videotype)){
                    if(brightcoveProperties.containsKey(VideoNodeHelper.VIDEO_URL_PROPERTY)){
                        videoProperty = brightcoveProperties.get(VideoNodeHelper.VIDEO_URL_PROPERTY).toString();
                    }
                }else if(VideoNodeHelper.HIGH_VIDEO_TYPE.equals(videotype)){
                    if(brightcoveProperties.containsKey(VideoNodeHelper.HIGH_PROPERTY)){
                        videoProperty = brightcoveProperties.get(VideoNodeHelper.HIGH_PROPERTY).toString();
                    }
                }else if(VideoNodeHelper.MEDIUM_VIDEO_TYPE.equals(videotype)){
                    if(brightcoveProperties.containsKey(VideoNodeHelper.MEDIUM_PROPERTY)){
                        videoProperty = brightcoveProperties.get(VideoNodeHelper.MEDIUM_PROPERTY).toString();
                    }
                }

            }
        }
        videoProperty = Utilities.cleanVideoUrl(videoProperty);

        return videoProperty;
    }

    /**
     * If the image path contains /jcr:content/renditions/original, removes it since the dispatcher
     * is not configured to handle requests without extensions.
     * @param path the image path
     * @return the image path without /jcr:content/renditions/original
     */
    @JspElFunction(example = "<cq:text value=\"${tg:removeOriginal(path)}\" />")
    public static String removeOriginal(String path) {
        if(path != null && path.contains("/jcr:content/renditions/original")){
            path = path.replaceAll("/jcr:content/renditions/original","");
        }
        return path;
    }
    
    @JspElFunction(example = "<cq:text value=\"${tg:jsonEscape(srting)}\" />")
    public static String jsonEscape(String string) {
        string = StringEscapeUtils.escapeXml(string);
        string = string.replaceAll("\n","&#10;");
        string = string.replaceAll("\t","&#09;");
        string = string.replaceAll("\r","&#13;");

        return string;
    }

    @JspElFunction(example = "<c:forEach items=\"${tg:listChildren(resource)}\" >")
    public static Iterator<Resource> listChildren(Resource resource) {
        return resource.listChildren();
    }

    @JspElFunction(example = "<c:set var=\"firstChildNode\" value=\"${tg:getFirstChildNode(node)}\" />")
    public static Node getFirstChildNode(Node node) {
        Node firstChild = null;
        try {
            if(node != null){
                NodeIterator nodeIterator = node.getNodes();
                firstChild = nodeIterator.hasNext()? nodeIterator.nextNode() : null;
            }
        } catch (RepositoryException e) {
            e.printStackTrace();

        }
        return firstChild;
    }

    @JspElFunction(example = "<c:set var=\"property\" value=\"${tg:getPropertyString(node, \"title\")}\" />")
    public static String getPropertyString(Node node, String property) {

        String propertyString = "";
        try {
            if(node != null && node.hasProperty(property)){

                propertyString = node.getProperty(property).getString();
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return propertyString;
    }
}

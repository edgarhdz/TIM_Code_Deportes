package com.televisa.commons.services.dataaccess.helper;

import com.televisa.commons.services.brightcove.BrightcoveHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

/**
 * Created by IntelliJ IDEA.
 * User: palecio
 * Date: 24/4/13
 * Time: 10:23
 */
public class VideoNodeHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoNodeHelper.class);
    public static final String VIDEO_NODE = "video";
    public static final String BRIGHTCOVE_NODE = "brightcove";

    public static final String VIDEO_DURATION_PROPERTY = "duration";

    public static final String M3U8_PROPERTY = "m3u8";  //HLS format
    public static final String VIDEO_URL_PROPERTY = "videoUrl"; //LOW
    public static final String MEDIUM_PROPERTY = "videoMedium"; //MEDIUM
    public static final String HIGH_PROPERTY = "videoHigh"; //HIGH

    public static final String HLS_HIGH_VIDEO_TYPE = "hls-high";
    public static final String HLS_VIDEO_TYPE = "hls";
    public static final String LOW_VIDEO_TYPE = "low";
    public static final String HIGH_VIDEO_TYPE = "high";
    public static final String MEDIUM_VIDEO_TYPE = "medium";
    private static final String SLASH = "/";

    public static boolean hasM3u8(Node node) {
        boolean hasM3u8 = false;
        try {
            if(node.hasNode(VIDEO_NODE + SLASH + BRIGHTCOVE_NODE)) {
                Node videoNode = node.getNode(VIDEO_NODE + SLASH + BRIGHTCOVE_NODE);
                if(videoNode.hasProperty(M3U8_PROPERTY)) {
                    hasM3u8 = true;
                }
            }
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
        }
        return hasM3u8;
    }

    public static boolean hasVideoUrl(Node node) {
        boolean videoUrl = false;
        try {
            if(node.hasNode(VIDEO_NODE + SLASH + BRIGHTCOVE_NODE)) {
                Node videoNode = node.getNode(VIDEO_NODE + SLASH + BRIGHTCOVE_NODE);
                if(videoNode.hasProperty(VIDEO_URL_PROPERTY)){
                    videoUrl = true;
                }
            }
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
        }
        return videoUrl;
    }

    public static boolean hasVideoType(Node node, String videoType) {
        boolean hasVideoType = false;
        try {
            if(node.hasNode(VIDEO_NODE + SLASH + BRIGHTCOVE_NODE)){
                final Node nodeBC = node.getNode(VIDEO_NODE + SLASH + BRIGHTCOVE_NODE);
                if(HLS_HIGH_VIDEO_TYPE.equalsIgnoreCase(videoType)){
                    if(nodeBC.hasProperty(M3U8_PROPERTY) || nodeBC.hasProperty(HIGH_PROPERTY)) {
                        hasVideoType = true;
                    }
                }else if(HLS_VIDEO_TYPE.equalsIgnoreCase(videoType)) {
                    if(nodeBC.hasProperty(M3U8_PROPERTY)){
                        hasVideoType = true;
                    }
                }else if(LOW_VIDEO_TYPE.equalsIgnoreCase(videoType)) {
                    if(nodeBC.hasProperty(VIDEO_URL_PROPERTY)){
                        hasVideoType = true;
                    }
                }else if(HIGH_VIDEO_TYPE.equalsIgnoreCase(videoType)) {
                    if(nodeBC.hasProperty(HIGH_PROPERTY)){
                        hasVideoType = true;
                    }
                }else if(MEDIUM_VIDEO_TYPE.equalsIgnoreCase(videoType)) {
                    if(nodeBC.hasProperty(MEDIUM_PROPERTY)){
                        hasVideoType = true;
                    }
                }
            }
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
        }
        return hasVideoType;
    }

    public static int getVideoDuration(Node node) {
        try {
            if(node.hasNode(VIDEO_NODE + SLASH + BRIGHTCOVE_NODE)) {
                Node nodeBC = node.getNode(VIDEO_NODE + SLASH + BRIGHTCOVE_NODE);
                if(nodeBC.hasProperty(VIDEO_DURATION_PROPERTY)) {
                    String duration = nodeBC.getProperty(VIDEO_DURATION_PROPERTY).getString();
                    return BrightcoveHelper.getDurationInMinutes(duration);
                }
            }
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

}

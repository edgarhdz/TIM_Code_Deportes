package com.televisa.commons.services.brightcove;

import com.televisa.commons.services.datamodel.Video;
import com.televisa.commons.services.services.Externalizer;
import com.televisa.commons.services.services.GsaService;
import org.apache.sling.api.resource.ValueMap;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: palecio
 * Date: 15/3/13
 * Time: 10:12
 */
public class BrightcoveProperties {

    //private static final Logger LOGGER = LoggerFactory.getLogger(BrightcoveProperties.class);
    
    private static final String HTML5_PLAYER_WIDTH = "624";
    private static final String HTML5_PLAYER_HEIGHT = "380";

    private static final String FLASH_PLAYER_WIDTH = "624";
    private static final String FLASH_PLAYER_HEIGHT = "380";

    private static final String FLASH_PLAYER_EXPAND_WIDTH = "955";
    private static final String FLASH_PLAYER_EXPAND_HEIGHT = "544";

    private static final String PUBLISHER_ID = "74091787001";

    private static final String IS_VID = "true";
    private static final String IS_UI = "true";
    private static final String W_MODE = "transparent";
    private static final String DYNAMIC_STREAMING = "false";
    private static final String ENABLE_GBS = "true";
    private static final String ENABLE_VIDEO_LOG = "true";
    private static final String USE_PARENT = "false";

    
    private Video video;

    private String profile;
    private String id;
    private String cat;
    private String origin;
    private String m3u8;
    private String gbs;
    private String publicUrl;
    private String duration;
    private String title;
    private String playerId;
    private String videoId;
    private String bgColor;
    private String autoStart;
    private String showRepeatVideoButton;
    private String showFacebookTools;
    private String showTwitterTools;
    private String showExpandTools;
    private String showPopOutTools;
    private String socialText;
    private String extras;
    private String videoUrl;
    private boolean isM3u8;
    
    public BrightcoveProperties(Video video, GsaService gsaService){
        this.video = video;
        if(video != null){
            this.videoUrl = this.getBrightcoveProperty("videoUrl");
            this.isM3u8 = !this.getBrightcoveProperty("m3u8").equals("");
            this.m3u8 = this.getBrightcoveProperty("m3u8");
            this.origin = video.getVideoPlayer();
            this.gbs = this.getBrightcoveProperty("geoFilter");
            this.setProfile();
            this.setCatAndId();
            this.publicUrl =  gsaService.buildUrl(video.getUrl(),Externalizer.HTML_DOMAIN);
            this.socialText = this.getBrightcoveProperty("socialText");
            this.extras = this.getBrightcoveProperty("extras");
            String chapter = video.getVideoChapter() != null ? " capítulo " + video.getVideoChapter() : "";
            this.duration = this.getBrightcoveProperty("duration");
            this.title = video.getTitle() + chapter;
            this.playerId = !this.getBrightcoveProperty("playerId").equals("") ? this.getBrightcoveProperty("playerId") : "643082227001";
            this.videoId = this.getBrightcoveProperty("videoId");
            this.bgColor =  !this.getBrightcoveProperty("bgColor").equals("") ? this.getBrightcoveProperty("bgColor") : "#000000";
            this.autoStart = !this.getBrightcoveProperty("autoStart").equals("") ? this.getBrightcoveProperty("autoStart") : "false";
            this.showRepeatVideoButton = !this.getBrightcoveProperty("showRepeatVideoButton").equals("") ? this.getBrightcoveProperty("showRepeatVideoButton") : "false";
            this.showFacebookTools = !this.getBrightcoveProperty("showFacebookTools").equals("") ? this.getBrightcoveProperty("showFacebookTools") : "false";
            this.showTwitterTools = !this.getBrightcoveProperty("showTwitterTools").equals("") ? this.getBrightcoveProperty("showTwitterTools") : "false";
            this.showExpandTools = !this.getBrightcoveProperty("showExpandTools").equals("") ? this.getBrightcoveProperty("showExpandTools") : "false";
            this.showPopOutTools = !this.getBrightcoveProperty("showPopOutTools").equals("") ? this.getBrightcoveProperty("showPopOutTools") : "false";        	
        }
    }
    
    private String getBrightcoveProperty(String propertyName){
        String property = "";
        if(this.video != null){
            ValueMap map = this.video.getBrightcoveProperties();
            if(map != null){
                if(map.containsKey(propertyName)){
                    property = map.get(propertyName).toString();
                }               	
            }
        }
        return property;
    }
    public void setProfile(){
        String profile = "";
        String path = this.videoUrl;


        
        if("8".equals(origin)){
            profile = "bca";
            if(path.contains("flash") ){
                profile = "bcs";
            }
            if(path.contains("m4v.tvolucion.com") ){
                profile = "bcm4";
            }

            if(path.contains("m4v.tvolucion.com/m4v") || path.contains("m4vhd.tvolucion.com/m4v") || path.contains("m4vhds.tvolucion.com") ){
                profile = "hls2b";
            }
            if(path.contains("gbs04.esmas.com") ){
                profile = "bctp";
            }
            if((path.contains("-480.mp4") ) && (path.contains("m4v.tvolucion.com/m4v") ) &&  ("clip".equals(this.video.getVideoType()))){
                profile = "tp1";
            }

        }

        this.profile = profile;
    }

    public String getProfile(){
        return this.profile;
    }

    public void setCatAndId(){
        String cat = "";
        String id = "";
        String path = this.videoUrl;
        String[] pathArray = path.split("/");

        if(pathArray.length > 4){
            if("bca".equals(profile) || "bcm4".equals(profile) || "bctp".equals(profile)){
                cat = pathArray[pathArray.length -4] + "/" + pathArray[pathArray.length - 3] + "/" + pathArray[pathArray.length -2];
                id = pathArray[pathArray.length-1];
                id = id.substring(0, id.length() - 4);
            }else if(profile.equals("bcs")){
                cat = pathArray[pathArray.length-2];
                
                id = pathArray[pathArray.length-1];
                id = id.substring(0, id.length() - 4);
            }else if(origin.equals("9") && (profile.equals("hls2") || profile.equals("tp2"))){
                id = path.substring(path.indexOf("/") + 1);
                cat = pathArray[0];
            }else if(origin.equals("8") && (profile.equals("hls2b") || profile.equals("tp2"))){
                id = pathArray[pathArray.length-2];
                cat = pathArray[pathArray.length-4]+"/"+pathArray[pathArray.length-3]+"/"+pathArray[pathArray.length-2];
            }else if(origin.equals("6") && path.contains(".mp4")){
                profile = "tp1";
                cat = pathArray[pathArray.length-4] + "/" + pathArray[pathArray.length-3] + "/" + pathArray[pathArray.length-2];
                id = pathArray[pathArray.length-1];
                id =  id.substring(0, id.length() - 4);
            }else if(origin.equals("8") && profile.equals("tp1")){
                id = pathArray[pathArray.length - 2];
                if(pathArray[pathArray.length - 4].equals("m4v") && pathArray[pathArray.length - 4].equals("mp4")){
                    cat = pathArray[pathArray.length - 4] + "/" + pathArray[pathArray.length - 3] + "/" + pathArray[pathArray.length - 2];
                }else{
                    cat = pathArray[pathArray.length - 3] + "/" + pathArray[pathArray.length - 2];
                }
            }else{
                cat = pathArray[pathArray.length-2];
                id = pathArray[pathArray.length-1];
                id =  id.substring(0, id.length() - 4);
            }

        }
        this.cat = cat;
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public String getCat(){
        return this.cat;
    }

    public String getM3u8(){
        return this.m3u8;
    }
    public String getOrigin(){
        return String.valueOf(this.origin);
    }
    
    public String getGbs(){
        return this.gbs;
    }
    
    public String getDuration(){
        return this.duration;
    }

    public String getSocialText(){
        return this.socialText;
    }

    public String getExtras(){
        return this.extras;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getBgColor() {
        return bgColor;
    }

    public String getAutoStart() {
        return autoStart;
    }

    public String getShowRepeatVideoButton() {
        return showRepeatVideoButton;
    }

    public String getShowFacebookTools() {
        return showFacebookTools;
    }

    public String getShowTwitterTools() {
        return showTwitterTools;
    }

    public String getShowExpandTools() {
        return showExpandTools;
    }

    public String getShowPopOutTools() {
        return showPopOutTools;
    }
    
    public String getIsM3u8(){
        return String.valueOf(isM3u8);
    }
    
    public HashMap<String, String> getProperties(){
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("title", getTitle());
        properties.put("publicUrl", getPublicUrl());
        properties.put("id", getId());
        properties.put("cat", getCat());
        properties.put("profile", getProfile());
        properties.put("origin", getOrigin());
        properties.put("m3u8", getM3u8());
        properties.put("gbs", getGbs());
        properties.put("extras", getExtras());
        properties.put("duration", getDuration());
        properties.put("html5PlayerWidth", HTML5_PLAYER_WIDTH);
        properties.put("html5PlayerHeight", HTML5_PLAYER_HEIGHT);
        properties.put("playerId",getPlayerId());
        properties.put("socialText", getSocialText());
        properties.put("publisherId", PUBLISHER_ID);
        properties.put("videoId",getVideoId());
        properties.put("bgColor", getBgColor());
        properties.put("flashPlayerWidth", FLASH_PLAYER_WIDTH);
        properties.put("flashPlayerHeight", FLASH_PLAYER_HEIGHT);
        properties.put("flashPlayerExpandWidth", FLASH_PLAYER_EXPAND_WIDTH);
        properties.put("flashPlayerExpandHeight", FLASH_PLAYER_EXPAND_HEIGHT);
        properties.put("isVid", IS_VID);
        properties.put("isUi", IS_UI);
        properties.put("wMode", W_MODE);
        properties.put("dynamicStreaming", DYNAMIC_STREAMING);
        properties.put("autoStart", getAutoStart());
        properties.put("enableGbs", ENABLE_GBS);
        properties.put("enableVideoLog", ENABLE_VIDEO_LOG);
        properties.put("showRepeatVideoButton", getShowRepeatVideoButton());
        properties.put("showFacebookTools", getShowFacebookTools());
        properties.put("showTwitterTools", getShowTwitterTools());
        properties.put("showExpandTools", getShowExpandTools());
        properties.put("showPopOutTools", getShowPopOutTools());
        properties.put("useParent", USE_PARENT);
        properties.put("isM3u8", getIsM3u8());

        return properties;
    }
}


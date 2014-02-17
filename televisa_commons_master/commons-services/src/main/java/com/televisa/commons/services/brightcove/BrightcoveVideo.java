package com.televisa.commons.services.brightcove;

import com.brightcove.proserve.mediaapi.wrapper.apiobjects.Video;
import com.day.cq.dam.api.Asset;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: palecio
 * Date: 10/4/13
 * Time: 15:56
 */
public class BrightcoveVideo {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrightcoveVideo.class);

    public static final String LOW_QUALITY = "low";
    public static final String MEDIUM_QUALITY = "medium";
    public static final String HIGH_QUALITY = "high";
    private Hashtable<String, String> videoProperties;

    /**
     * Constructs a new BrightCove video object with the Information received from BrightCove
     * @param request this request
     * @param videoId the brightcove video id
     */
    public BrightcoveVideo(SlingHttpServletRequest request, String videoId){

        Video video = BrightcoveHelper.getBrightcoveVideo(videoId);
        if(video != null){
            String geoFilter = BrightcoveHelper.getCustomField(video, "geofilter");
            geoFilter = geoFilter.equals("")? geoFilter : BrightcoveHelper.toMD5(geoFilter);

            String releaseDate = BrightcoveHelper.getCustomField(video, "release_date");
            releaseDate = BrightcoveHelper.toFullDate(releaseDate);

            Asset image = BrightcoveHelper.uploadImageToDam(request, video.getVideoStillUrl(), video.getName());
            String imagePath = image != null? image.getPath() : "";

            videoProperties = new Hashtable<String, String>();
            
            addToVideoProperties("title", video.getName());
            addToVideoProperties("summary", video.getLongDescription());
            addToVideoProperties("tooltip", video.getShortDescription());
            addToVideoProperties("duration", BrightcoveHelper.getDuration(video.getLength()));
            addToVideoProperties("fileReference", imagePath);
            addToVideoProperties("referenceId", video.getReferenceId());
            addToVideoProperties("geoFilter", geoFilter);
            addToVideoProperties("chapter", BrightcoveHelper.getCustomField(video, "episode"));
            addToVideoProperties("season", BrightcoveHelper.getCustomField(video, "season"));
            addToVideoProperties("program", BrightcoveHelper.getCustomField(video, "program"));
            addToVideoProperties("releaseDate", releaseDate);
            addToVideoProperties("assetPath", BrightcoveHelper.getCustomField(video, "asset_path"));
            addToVideoProperties("m3u8", BrightcoveHelper.getCustomField(video, "m3u8"));
            addToVideoProperties("f4m", BrightcoveHelper.getCustomField(video, "f4m"));
            addToVideoProperties("videoHigh", BrightcoveHelper.getVideoUrl(video, BrightcoveHelper.HIGH_QUALITY));
            addToVideoProperties("videoMedium", BrightcoveHelper.getVideoUrl(video, BrightcoveHelper.MEDIUM_QUALITY));
            addToVideoProperties("videoUrl", BrightcoveHelper.getVideoUrl(video, BrightcoveHelper.LOW_QUALITY));
            addToVideoProperties("thumbnailUrl", video.getThumbnailUrl());
            addToVideoProperties("imageUrl", video.getVideoStillUrl());
        }
    }

    public String validateNullJSON(JSONObject jsonOb, String key) throws JSONException {
        if(jsonOb.has(key) && !jsonOb.isNull(key)){
            return jsonOb.getString(key);
        } else {
            return "";
        }
    }

    public BrightcoveVideo(SlingHttpServletRequest request, String videoId, boolean restful){
        try {
            String video = BrightcoveHelper.getBrightcoveVideo(videoId, true);
            if(video != null){
                JSONObject object = new JSONObject(new JSONTokener(video));
                videoProperties = new Hashtable<String, String>();
                String videoStillURL = validateNullJSON(object, "videoStillURL");
                String name = validateNullJSON(object, "name");
                Asset image = BrightcoveHelper.uploadImageToDam(request, videoStillURL, name);
                String imagePath = image != null? image.getPath() : "";
                addToVideoProperties("title", name);
                addToVideoProperties("summary", validateNullJSON(object, "longDescription"));
                addToVideoProperties("tooltip", validateNullJSON(object, "shortDescription"));
                addToVideoProperties("duration", BrightcoveHelper.getDuration(object.getLong("length")));
                addToVideoProperties("fileReference", imagePath);
                addToVideoProperties("referenceId", validateNullJSON(object, "referenceId"));
                addToVideoProperties("thumbnailUrl", validateNullJSON(object, "thumbnailURL"));
                addToVideoProperties("imageUrl", videoStillURL);

                if(!object.isNull("customFields")){
                    JSONObject customFields = object.getJSONObject("customFields");
                    addToVideoProperties("geoFilter", BrightcoveHelper.toMD5(validateNullJSON(customFields, "geofilter")));
                    addToVideoProperties("releaseDate", BrightcoveHelper.toFullDate(validateNullJSON(customFields, "release_date")));
                    addToVideoProperties("chapter", validateNullJSON(customFields, "episode"));
                    addToVideoProperties("season", validateNullJSON(customFields, "season"));
                    addToVideoProperties("program", validateNullJSON(customFields, "program"));
                    addToVideoProperties("assetPath", validateNullJSON(customFields, "asset_path"));
                    addToVideoProperties("m3u8", validateNullJSON(customFields, "m3u8"));
                    addToVideoProperties("f4m", validateNullJSON(customFields   , "f4m"));
                } else {
                    addToVideoProperties("geoFilter", "");
                    addToVideoProperties("releaseDate", "");
                    addToVideoProperties("chapter", "");
                    addToVideoProperties("season", "");
                    addToVideoProperties("program", "");
                    addToVideoProperties("assetPath", "");
                    addToVideoProperties("m3u8", "");
                    addToVideoProperties("f4m", "");
                }

                if(!object.isNull("renditions")){
                    JSONArray renditions = object.getJSONArray("renditions");
                    for(int i = 0; i < renditions.length(); i++){
                        JSONObject vidRendition = renditions.getJSONObject(i);
                        int encodingRateLowerBound = 130000;
                        int encodingRateUpperBound = 170000;
                        long encodingRate = vidRendition.getLong("encodingRate");
                        if(encodingRateLowerBound <= encodingRate && encodingRate <= encodingRateUpperBound){
                            addToVideoProperties("videoUrl", vidRendition.getString("url"));
                        }
                        encodingRateLowerBound = 470000;
                        encodingRateUpperBound = 490000;
                        if(encodingRateLowerBound <= encodingRate && encodingRate <= encodingRateUpperBound){
                            addToVideoProperties("videoMedium", vidRendition.getString("url"));
                        }
                        encodingRateLowerBound = 580000;
                        encodingRateUpperBound = 620000;
                        if(encodingRateLowerBound <= encodingRate && encodingRate <= encodingRateUpperBound){
                            addToVideoProperties("videoHigh", vidRendition.getString("url"));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * converts the brightcove video to a json object
     * @return a json object representation of the video
     */
    public JSONObject toJson(){
        return new JSONObject(this.videoProperties);
    }

    /**
     *
     * @param property the property to add
     * @param value the value of the property
     */
    private void addToVideoProperties(String property, String value){
        //value = StringEscapeUtils.escapeHtml(value);
        this.videoProperties.put(property, value);
        
    }

}

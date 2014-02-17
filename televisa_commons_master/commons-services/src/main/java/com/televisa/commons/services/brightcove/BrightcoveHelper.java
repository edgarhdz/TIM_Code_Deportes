package com.televisa.commons.services.brightcove;

import com.brightcove.proserve.mediaapi.wrapper.ReadApi;
import com.brightcove.proserve.mediaapi.wrapper.apiobjects.CustomField;
import com.brightcove.proserve.mediaapi.wrapper.apiobjects.Rendition;
import com.brightcove.proserve.mediaapi.wrapper.apiobjects.Video;
import com.brightcove.proserve.mediaapi.wrapper.apiobjects.enums.VideoFieldEnum;
import com.brightcove.proserve.mediaapi.wrapper.exceptions.BrightcoveException;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.televisa.commons.services.utilities.ApplicationProperties;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: palecio
 * Date: 15/3/13
 * Time: 11:24
 */
public class BrightcoveHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrightcoveHelper.class);
    private static final String TOKEN = "Wyts7AraMt8vDWBrEoqqI0b8DY1AGKzGek66aPdaLAnYQ2oPVxQMWA.."; //This token is valid until the end of April

    public static final String LOW_QUALITY = "low";
    public static final String MEDIUM_QUALITY = "medium";
    public static final String HIGH_QUALITY = "high";

    /**
     * Transforms a miliseconds value to a string with the format HH:mm:ss
     * @param length the length in miliseconds
     * @return a string with the format HH:mm:ss
     */
    public static String getDuration(long length){

        long totalSeconds = length/1000;
        long hours = totalSeconds/3600;
        long minutes = (totalSeconds%3600)/60;
        long seconds = (totalSeconds%60);

        String secondsString = (seconds > 10)? String.valueOf(seconds) : "0" + String.valueOf(seconds);
        String minutesString = (minutes > 10)? String.valueOf(minutes) : "0" + String.valueOf(minutes);
        String hoursString = (hours > 10)? String.valueOf(hours) : "0" + String.valueOf(hours);

        return hoursString + ":" + minutesString + ":" + secondsString;
    }

    /**
     * Receives a string in the form HH:mm:ss and converts it ino minutes
     * @param duration a string in the form HH:mm:ss
     * @return the equivalent in minutes
     */
    public static int getDurationInMinutes(String duration){
        int durationInMinutes = -1;
        int hours;
        int minutes;
        int seconds;
        String[] durationArray = duration.split(":");
        if(durationArray.length == 3){
            hours = Integer.parseInt(durationArray[0]);
            minutes = Integer.parseInt(durationArray[1]);
            seconds = Integer.parseInt(durationArray[2]);
            durationInMinutes = (int) Math.ceil(hours * 60 + minutes + seconds / 60.00);
        }else{
            try{
                long durationInMilliseconds = Long.parseLong(duration);
                durationInMinutes = (int) Math.ceil((durationInMilliseconds / 1000.00) / 60);
            }catch (NumberFormatException nfe){
                LOGGER.error("Could not parse duration");
            }   
        }
        return durationInMinutes;

    }


    /**
     * uses the DAM_BASE_PATH, the current date and the video name to build a path for the image
     * @param videoName the video name 'as is'
     * @return the path where the image will live
     */
    public static String getImagePath(String videoName){

        String assetName = videoName.replaceAll(" ","-");
        assetName = assetName.toLowerCase();

        assetName = Normalizer.normalize(assetName, Normalizer.Form.NFD);
        assetName= assetName.replaceAll("[^\\p{ASCII}]", "");
        assetName= assetName.replaceAll("=", "");

        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2);

        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        month = Integer.parseInt(month) < 10 ? '0' + month : month;

        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH ));
        day = Integer.parseInt( day ) < 10 ? '0' + day : day;

        return ApplicationProperties.getNoticierosDamPath() + "/" + year + month + "/" + day + "/" + assetName + ".jpg";
        
    }

    /**
     * Uploads an image to the DAM
     * @param request the request, used to get the resource resolver
     * @param imageUrl the original image url
     * @param videoName the name of the video 'as is'
     * @return the new Image Asset
     */
    public static Asset uploadImageToDam(SlingHttpServletRequest request, String imageUrl, String videoName ){
        ResourceResolver resolver = request.getResourceResolver();
        Session session = resolver.adaptTo(Session.class);
        AssetManager assetManager = resolver.adaptTo(AssetManager.class);
        String assetPath = BrightcoveHelper.getImagePath(videoName);
        Asset asset = null;
        try{
            /* Open a URLConnection from the input, fetch the asset into a InputStream and create a JCR Binary object. */
            URL url = new URL( imageUrl );
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream assetInputStream =  connection.getInputStream();
            String assetContentType = connection.getContentType();
            Binary assetBinary = session.getValueFactory().createBinary( assetInputStream );
            /* Store the JCR Binary object into the DAM via the CQ AssetManager. */
            asset = assetManager.createAsset( assetPath, assetBinary.getStream(), assetContentType, true );

            /* Closing InputStream */
            if(assetInputStream != null){
                assetInputStream.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return asset;
    }




    /**
     * Uploads an image to the DAM
     * @param resolver the resource resolver
     * @param imageUrl the original image url
     * @param videoName the name of the video 'as is'
     * @return the new Image Asset
     */
    public static Asset uploadImageToDam(ResourceResolver resolver, String imageUrl, String videoName ){
        Session session = resolver.adaptTo(Session.class);
        AssetManager assetManager = resolver.adaptTo(AssetManager.class);
        String assetPath = BrightcoveHelper.getImagePath(videoName);
        Asset asset = null;
        try{
            /* Open a URLConnection from the input, fetch the asset into a InputStream and create a JCR Binary object. */
            URL url = new URL( imageUrl );
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream assetInputStream =  connection.getInputStream();
            String assetContentType = connection.getContentType();
            Binary assetBinary = session.getValueFactory().createBinary( assetInputStream );

            /* Store the JCR Binary object into the DAM via the CQ AssetManager. */
            asset = assetManager.createAsset( assetPath, assetBinary.getStream(), assetContentType, true );

            /* Closing InputStream */
            if(assetInputStream != null){
                assetInputStream.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return asset;
    }

    public static String getVideoUrl(Video video, String quality){
        List<Rendition> renditions = video.getRenditions();
        String videoUrl = "";
        int encodingRateLowerBound = 0;
        int encodingRateUpperBound = 0;

        if(LOW_QUALITY.equals(quality)){
            encodingRateLowerBound = 130000;
            encodingRateUpperBound = 170000;
        }else if(MEDIUM_QUALITY.equals(quality)){
            encodingRateLowerBound = 470000;
            encodingRateUpperBound = 490000;
        }else if(HIGH_QUALITY.equals(quality)){
            encodingRateLowerBound = 580000;
            encodingRateUpperBound = 620000;
        }
        for(Rendition rendition : renditions){
            int encodingRate = rendition.getEncodingRate();
            if(encodingRateLowerBound < encodingRate && encodingRateUpperBound > encodingRate){
                videoUrl = rendition.getUrl();
                break;
            }
        }
        return videoUrl;
    }
    
    /**
     * Gets a certain custom field
     * @param video the video from where to obtain the field
     * @param name the name of the custom field
     * @return the value of the custom field if any
     */
    public static String getCustomField(Video video, String name){
        List<CustomField> customFields = video.getCustomFields();
        String customValue = "";
        if(customFields != null){
            for(CustomField customField : customFields){
                if(name.equals(customField.getName())){
                        customValue = customField.getValue();
                    break;
                }
            }
        }
        return customValue;
    }

    /**
     * Converts the date from a 'yyyy-MM-dd HH:mm:ss'   to a full date format
     * @param dateString the date to parse
     * @return the new full date
     */
    public static String toFullDate(String dateString){
        if(dateString != null && dateString.trim().length() > 0){
            /* 2013-04-03 21:00:00 */
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = formatter.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage());
            }
            return date == null ? "" : date.toString();
        } else {
            return "";
        }
    }

    /**
     * converts a string to a date
     * @param dateString the string with a date
     * @return the date obtained from the string
     */
    public static Calendar toDate(String dateString){
        /* 2013-04-03 21:00:00 */
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        if(dateString != null && !dateString.equals("")){
            try {
                date = formatter.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage());
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }else {
            return null;
        }

    }

    /**
     * gets any given string's MD5 hash
     * @param string to 'convert'
     * @return the MD5 hash
     */
    public static String toMD5(String string){

        if(string != null){
            byte[] bytes = string.getBytes();

            MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            String hash = "";
            if(messageDigest != null){
                byte[] md5 = messageDigest.digest(bytes);
                BigInteger bigInt = new BigInteger(1,md5);
                hash = bigInt.toString(16);
            }

            return hash;
        }else{
            return null;
        }
    }

    /**
     * Gets a brightcove video
     * @param brightcoveId the brightcove id
     * @return the video info
     */
    public static Video getBrightcoveVideo(String brightcoveId){
        Video video = null;
        ReadApi api = new ReadApi("ISO-8859-1");
        try {
           video = api.FindVideoById(TOKEN, Long.parseLong(brightcoveId), VideoFieldEnum.CreateFullEnumSet(), null);
        } catch (BrightcoveException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Video" + video);
        return video;
    }

    public static String getBrightcoveVideo(String brightcoveId, boolean restful){
        StringBuilder brightcoveURL = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        String video = null;
        try {
            brightcoveURL.append("http://api.brightcove.com/services/library?command=find_video_by_id");
            brightcoveURL.append("&token=" + TOKEN);
            brightcoveURL.append("&video_id=").append(brightcoveId);
            brightcoveURL.append("&video_fields=");
            brightcoveURL.append(String.valueOf(VideoFieldEnum.CreateFullEnumSet()).replace("[", "").replace("]", "").replaceAll(", ", ","));
            URL brightcoveAPI = new URL(brightcoveURL.toString());
            BufferedReader in = new BufferedReader(new InputStreamReader(brightcoveAPI.openStream(), "UTF-8"));
            String line = in.readLine();
            while(line != null){
                if(line.equals("null")){
                    return null;
                }
                sb.append(line);
                line = in.readLine();
            }
            video = sb.toString();
        } catch (MalformedURLException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return video;
    }

    /**
     * Performs a query looking for the referenceId property in any page under /content/televisa/noticieros
     * and returns the page with that referenceId. If the result of the query != 1 this method returns null
     * @param refId the referenceId to look for
     * @param resourceResolver the resource resolver
     * @return the page that contains the referenceId
     */
    public static Page getPageByRefId(String refId, ResourceResolver resourceResolver){

        Query query;
        QueryBuilder builder = resourceResolver.adaptTo(QueryBuilder.class);
        Session mySession =  resourceResolver.adaptTo(Session.class);
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        Map<String, String> map = new HashMap<String, String>();
        map.put("path", "/content/televisa/noticieros"); //path of the query
        map.put("type", "cq:Page"); //type of the results
        map.put("property","jcr:content/video/brightcove/referenceId"); //property to look for
        map.put("property.value", refId); //value to look for

        query = builder.createQuery(PredicateGroup.create(map), mySession);
        //sets the star of the query, offset
        query.setStart(0);
        //sets the hitsPerPage of the query, limit
        query.setHitsPerPage(0);

        //does the search
        SearchResult result = query.getResult(); //performs query
        Page page = null;
        if(result.getHits().size() == 1){
            Node node = result.getNodes().next();
            try {
                page = pageManager.getPage(node.getPath());
            } catch (RepositoryException e) {
               LOGGER.error(e.getMessage());
            }
        }
        return page;
    }

}

/*
 * Utilities.java
 *
 * General Utilities Methods
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.utilities;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.dam.commons.util.PrefixRenditionPicker;
import com.televisa.commons.services.services.GsaService;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.WCMMode;
import com.televisa.commons.services.datamodel.Note;

/**
 * Utilities
 *
 * General Utilities Methods
 *
 * Changes History:
 *
 *         2013-02-06 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class Utilities {

    private static final Logger LOG = LoggerFactory.getLogger(Utilities.class);

    private static final String PREFIX = "cq5dam.thumbnail.%d.%d";
    private static final String HTM = ".htm";
    private static final String SLASH = "/";
    private static final String POINT = ".";

    /**
     * Get a rendition with the standard prefix by its width and height.
     *
     * @param asset the Asset resource to get the rendition from
     * @param width the width of the rendition
     * @param height the height of the rendition
     * @return the rendition from the asset
     */
    public Rendition getRendition(Asset asset, int width, int height) {
        return asset.getRendition(new PrefixRenditionPicker(String.format(PREFIX, width, height)));
    }

    /**
     * Build a URL from a page path and a relative URL.
     *
     * Example:
     *
     * path
     * http://127.0.0.1:4502/content/televisa/natalie-portman
     *
     * relative
     * /content/dam/televisa/entretenimiento/natalie-portman.jpg
     *
     * result
     * http://127.0.0.1:4502/content/dam/televisa/entretenimiento/natalie-portman.jpg
     *
     * @param path the URL from a page path
     * @param relative the URL relative to the page path
     * @return the new absolute URL to the relative page path
     */
    public String buildReferencePath(String path, String relative) {
        Pattern referencePattern = Pattern.compile("^(.*?://.*?)/.*?$");
        Matcher matcher = referencePattern.matcher(path);
        if (matcher.matches() && matcher.groupCount() > 0) {
            return StringUtils.chomp(matcher.group(1), SLASH).concat(SLASH).concat(
                    relative.startsWith(SLASH) ? relative.substring(1) : relative);
        }
        return null;
    }

    /**
     * Creates a complete path URL using the Externalizer Service.
     *
     * @param gsaService the gsa service
     * @param path the path to resolve to
     * @param domain can be "html" or "static"
     * @return the path translated to a URL
     */
    public static String getCompleteURL(GsaService gsaService, String path, String domain) {
        String url = gsaService.buildUrl(path, domain);
        url = encodeUrl(url);
        return url;
    }

    /**
     *
     * @param selectors
     * @param hasTag
     * @return
     */
    public static String[] validateSelectors(String[] selectors, boolean hasTag) {
        String[] newSelectors = {"", "1"};
        if(selectors == null){
            return newSelectors;
        }
        if(selectors.length <= 0 || selectors.length > 2) {
            return newSelectors;
        }
        if(selectors.length > 0){
            // if there are one or more selectors
            if(selectors.length >= 1){
                if(hasTag){
                    String tag = selectors[0];
                    if(tag == null || tag.trim().length() <= 0) {
                        return newSelectors;
                    }else{
                        newSelectors[0] = tag;
                    }
                } else {
                    String pageNumber = selectors[0];
                    if(pageNumber == null || pageNumber.trim().length() <= 0) {
                        return newSelectors;
                    } else {
                        boolean isNumber;
                        try{
                            Integer.parseInt(pageNumber.trim());
                            isNumber = true;
                        }catch (NumberFormatException e) {
                            isNumber = false;
                        }
                        if(isNumber) {
                            newSelectors[1] = pageNumber;
                        }else{
                            newSelectors[1] = "1";
                        }
                        return newSelectors;
                    }
                }
            }
            // when there are two selectors
            if(selectors.length == 2) {
                String pageNumber = selectors[1];
                if(pageNumber == null || pageNumber.trim().length() <= 0) {
                    return newSelectors;
                }
                boolean isNumber;
                try{
                    Integer.parseInt(pageNumber.trim());
                    isNumber = true;
                }catch (NumberFormatException e) {
                    isNumber = false;
                }
                if(isNumber){
                    newSelectors[1] = pageNumber;
                }else{
                    newSelectors[1] = "1";
                }
            }else{
                newSelectors[1] = "1";
            }
        }
        return newSelectors;
    }

    /**
     *
     * @param url
     * @param numberOfPage
     * @param hasTag
     * @return
     */
    public static String getPageURL(String url, int numberOfPage, boolean hasTag) {

        String pageURL = "";
        String extension = "";

        if(url == null || !url.contains(SLASH) || !url.contains(POINT)) {
            return null;
        }
        if(numberOfPage <= 0){
            return url;
        }
        String newURL = url;
        boolean hasExtension = false;
        if(newURL.contains(HTM)){
            hasExtension = true;
            extension = newURL.substring(newURL.indexOf(HTM));
            newURL = newURL.substring(0, newURL.indexOf(HTM));
        }

        if(newURL.lastIndexOf(POINT) != -1){
            String numberPage = newURL.substring(newURL.lastIndexOf(POINT) + 1);

            boolean isNumber;
            try{
                Integer.parseInt(numberPage);
                isNumber = true;
            }catch (NumberFormatException e) {
                isNumber = false;
            }
            if(isNumber){
                newURL = newURL.substring(0, newURL.lastIndexOf(POINT));
            }

        }

        if(newURL.lastIndexOf(SLASH) > -1) {
            String pathPageURL = newURL.substring(newURL.lastIndexOf(SLASH));
            String[] pathPage =  pathPageURL.split("\\.");
            if (pathPage.length == 0) {
                return null;
            } else if(pathPage.length == 1) {
                if (hasTag) {
                    return newURL;
                } else {
                    pageURL = newURL + POINT + numberOfPage;
                }
            } else if (pathPage.length > 1) {
                pageURL = newURL + POINT + numberOfPage;
            }
        }
        if (hasExtension) {
            pageURL = new StringBuffer(pageURL).append(extension).toString();
        }
        return pageURL;
    }

    public static String encodeUrl(String url) {
        String encodeUrl = url;
        try {
            encodeUrl = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("Exception: " + e.getMessage());
        }
        encodeUrl = encodeUrl.replaceAll("%2F", SLASH);
        encodeUrl = encodeUrl.replaceAll("%3A", ":");
        encodeUrl = encodeUrl.replaceAll("\\+", "%20");
        return encodeUrl;
    }

    public static String cleanVideoUrl(String videoUrl ) {

        String cleanVideoUrl = videoUrl.replace("media.esmas.com", "apps.esmas.com");
        cleanVideoUrl = cleanVideoUrl.replace("m4v.tvolucion.com", "apps.tvolucion.com");

        if(cleanVideoUrl.contains("?")){
            int index = cleanVideoUrl.indexOf("?");
            cleanVideoUrl = cleanVideoUrl.substring(0,index);
        }

        cleanVideoUrl = cleanVideoUrl.replaceAll("&","%26");

        return cleanVideoUrl;
    }

    public static String normalizeString(String string) {

        /*Remove accents and convert to lower case*/
        String normlString = Normalizer.normalize(string, Normalizer.Form.NFD);
        normlString = normlString.replaceAll("[^\\p{ASCII}]", "");
        normlString = normlString.replaceAll("=", "");
        normlString = normlString.toLowerCase();
        return normlString;

    }

    public static String postRequest(String url, Map<String,String> parameters, Map<String,String> headers) {
        HttpClient client;
        PostMethod method = null;
        String response = "";

        try {

            client = new HttpClient();
            method = new PostMethod(url);

            if(parameters != null){
                for (Map.Entry<String, String> parameter : parameters.entrySet()) {
                    method.addParameter(parameter.getKey(), parameter.getValue());
                }
            }

            if(headers != null){
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    method.addRequestHeader(header.getKey(), header.getValue());
                }
            }

            int statusCode = client.executeMethod(method);
            response = method.getResponseBodyAsString();
            LOG.info("response: " + response);

        } catch(IOException e) {
            LOG.error("post request exception: " + e);
        } finally {
            if(method != null){
                method.releaseConnection();
            }
        }
        return response;
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Get a calendar property from a node.
     *
     * @param node the node to get the property of
     * @param relPath the name of the property
     * @return the calendar property or null
     */
    public static Calendar getCalendar(Node node, String relPath) {
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
     * method used to get the current year
     *
     * @return
     */
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }
    
    /**
     * Converts a calendar into a date with dd/mm/yyyy format
     * @param date the date to beautify
     * @return a date with dd/mm/yyyy format
     */
    public static String beautifyDate(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy 'a las' hh:mm a", new Locale("ES") );
        return date == null? "" : dateFormat.format(date.getTime());
    }


    /**
     * 
     * @param parameter
     * @return
     */
	public static boolean isNumber(String parameter){
		boolean isNumber = false;
		if(parameter != null){
			try{
				Integer.parseInt(parameter);
				isNumber = true;
			}catch (NumberFormatException e){
				isNumber = false;
			}				
		}
		return isNumber;
	}

    /**
     *
     * @param path
     * @return
     */
    public static boolean isValidPath(String path){
        if(path == null || path.length() <= 0){
            return false;
        }
        return  (path.split("/").length > 0);
    }

    /**
     *
     * @param path
     * @return
     */
    public static String externalizePath(WCMMode wcmMode, GsaService gsaService, String path){
        if(wcmMode.equals(WCMMode.DISABLED)){
            path = getCompleteURL(gsaService, path, GsaService.HTML_DOMAIN);
        }else{
            path += ".html";
        }
        return path;
    }

    public static List<Note> eliminateRepatedNote(List<Note> notes, String actualPath){
        List<Note> finalNotes = null;

        if(notes != null && notes.size() > 0){
            boolean existActualPath = true;

            try {
                for (int i = 0; i < notes.size(); i++) {
                    Note note = notes.get(i);
                    if(note != null && note.getPage() != null){
                        Page page = note.getPage();
                        if(page.getPath() != null && page.getPath().length() >0) {
                            String path = page.getPath();
                            if(existActualPath && path.equals(actualPath)) {
                                notes.remove(note);
                                existActualPath =false;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //if(existActualPath){
            //    notes.remove(notes.size() - 1);
            //}
            finalNotes = notes;
        }
        return finalNotes;
    }

}

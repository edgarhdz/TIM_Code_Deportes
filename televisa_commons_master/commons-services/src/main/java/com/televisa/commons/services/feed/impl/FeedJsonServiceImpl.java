/*
 * FeedJsonServiceImpl.java
 *
 * The service to provide the feed service.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.feed.impl;

import com.televisa.commons.services.dataaccess.TemplateType;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.datamodel.Photo;
import com.televisa.commons.services.datamodel.objects.ImageAsset;
import com.televisa.commons.services.datamodel.objects.Rendition;
import com.televisa.commons.services.feed.FeedService;
import com.televisa.commons.services.services.Externalizer;
import com.televisa.commons.services.utilities.Utilities;
import com.televisa.commons.services.brightcove.BrightcoveHelper;
import com.televisa.commons.services.datamodel.Video;
import com.televisa.commons.services.services.GsaService;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.commons.json.JSONArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Feed JSON Service Implementation
 *
 * The service to provide the feed service.
 *
 * Changes History:
 *
 *         2013-04-01 gescobar Added the externalizer service
 *         2013-03-13 gescobar Added the request parameter
 *         2013-03-11 gescobar Feed service video and image implementation
 *         2013-02-28 gescobar Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
@Component(label = "Feed JSON Service", description = "The feed JSON service implementation.", immediate = true)
@Service(value = FeedService.class)
@Properties({
        @Property(name = "type", value = "json")
})
public class FeedJsonServiceImpl implements FeedService {

    private static final String COMMENTS_URL = "http://comentarios.esmas.com/mobile/?url=";
    private static final String WEB_MOBILE = "web_mobile";
    private static final String DETAILS = "details";

    @Reference
    private GsaService gsaService;

    /**
     * Get a feed item form a note, the type is provided because the feed rendering could be
     * changed at runtime but also the note has already a Note class implementation extended.
     *
     * @param type the class of note to render as a feed
     * @param rendering the type of rendering to render
     * @param note the note to render as a feed
     * @return a string rendering of an feed item from a note
     */
    @Override
    public String getFeed(SlingHttpServletRequest request, Class<? extends Note> type, String rendering, Note note, Map<String, Object> additional) {

        // If we got a empty additional properties then assign a empty one.
        if (additional == null) {
            additional = new HashMap<String, Object>();
        }

        TemplateType templateType = null;
        for (TemplateType template : TemplateType.values()) {
            if (template.getClazz() == type) {
                templateType = template;
                break;
            }
        }

        if (templateType != null) {
            switch (templateType) {
                case ARTICLE:
                    return getArticleFeed(request, rendering, note, additional);
                case PHOTO:
                    return getPhotoFeed(request, rendering, note, additional);
                case VIDEO:
                    return getVideoFeed(request, rendering, note, additional);
                case PROGRAM:
                    return getProgramFeed(request, rendering, note, additional);
                default:
                    return null;
            }
        }

        return null;
    }

    /**
     * Render a note as a feed item.
     *
     * @param rendering the type of rendering to render
     * @param note the note to render as a feed item
     * @return a string representing a JSON feed item
     */
    protected String getArticleFeed(SlingHttpServletRequest request, String rendering, Note note, Map<String, Object> additional) {
        JSONArray jsonArray = new JSONArray();

/*
Issue 112 : News_feeds.docx

{ "sectionsNews" :
    [ { "Home Noticieros" : [
            [
                "Reportan explosión de artefacto en Tlalpan, hay un herido",
                "La tarde de este jueves se registró la explosión de un artefacto en Tlalpan y Periférico lo",
                "http://i2.esmas.com/2013/02/21/483471/still-explota-artefacto-en-tlalpan-120x90.jpg",
                "http://m.noticieros.televisa.com/df/563573/reportan-explosion-artefacto-tlalpan-hay-herido/index_mobile_apps.html",
                "http://noticierostelevisa.esmas.com/df/563573/reportan-explosion-artefacto-tlalpan-hay-herido//index_comentarios.html" ],
            [
                "Narro: Violencia no tiene cabida en la UNAM",
                "El rector pidió a los paristas del CCH mostrar compromiso universitario; dijo que",
                "http://i2.esmas.com/2013/02/21/483448/conferencia-del-rector-jose-narro-120x90.jpg",
                "http://m.noticieros.televisa.com/df/563364/narro-violencia-no-tiene-cabida-unam/index_mobile_apps.html",
                "http://noticierostelevisa.esmas.com/df/563364/narro-violencia-no-tiene-cabida-unam//index_comentarios.html" ]
    ] } ]
}

{ "sectionsNews":
	[ { "Home Noticieros": [
            [
                Title of the news,
                Description of the news ,
                Thumbnail (120x90) of the news,
                Mobile Web link of the detail of the news,
                Web link of the comments of article,

            ]
    }]
}
*/
        String title = (note.getTitle() == null)? "" : note.getTitle();
        jsonArray.put(title);

        String description = (note.getDescription() == null)? "" : note.getDescription();

        jsonArray.put(description);

        ImageAsset image = note.getNoteImageAsset();
        String value = "";
        if (image != null) {
            Rendition rendition = image.getRendition(136, 102);
            if (rendition == null) {
                value = note.getNoteImageAsset().getPath();
            } else {
                value = rendition.getPath();
            }
        }
        jsonArray.put(Utilities.getCompleteURL(gsaService, value, Externalizer.STATIC_DOMAIN));

        if (!additional.containsKey(WEB_MOBILE)) additional.put(WEB_MOBILE, ".index_mobile_apps");

        jsonArray.put(Utilities.getCompleteURL(gsaService, note.getUrl().concat(String.valueOf(additional.get(WEB_MOBILE))), Externalizer.HTML_DOMAIN) +".html");
        jsonArray.put(COMMENTS_URL + Utilities.getCompleteURL(gsaService, note.getUrl(), Externalizer.HTML_DOMAIN));

        return jsonArray.toString();
    }

    /**
     * Render a note as a feed item.
     *
     * @param rendering the type of rendering to render
     * @param note the note to render as a feed item
     * @return a string representing a JSON feed item
     */
    protected String getPhotoFeed(SlingHttpServletRequest request, String rendering, Note note, Map<String, Object> additional) {
        JSONArray jsonArray = new JSONArray();

        Photo photo = null;
        if (note.isPhoto()) {
            photo = (Photo) note;
        }

/*
{ "sectionsPhotos" :
	[ { "main" : [
				[
						"Los aliados de Hugo Chávez",
						"Hugo Chávez tuvo aliados no sólo en América Latina, sino en Asia e incluso África",
						"http://i2.esmas.com/galerias/thumbnails/201351728251362526105.jpg",
						"http://feeds.esmas.com/data-feeds-esmas/iphone/nt/fotogalerias/46197.xml" ],
				[
						"Última audiencia pública de Benedicto XVI",
						"El papa asegura que -no abandona la cruz- y que ha vivido momentos difíciles",
						"http://i2.esmas.com/galerias/thumbnails/201327155221361999122.jpg",
						"http://feeds.esmas.com/data-feeds-esmas/iphone/nt/fotogalerias/45995.xml" ]
	] } ]

}

{ "sectionsPhotos" :
	[ { "main" : [
			[
				"Title of photo gallery",
				"Description of photo gallery",
				"thumbnail of photo gallery ",
				"Link with the detail of photo galleries in others words, a xml file with all photos of photo gallery" ]
	] } ]

}
*/
        jsonArray.put(note.getTitle());
        String description = note.getDescription() == null? "" : note.getDescription();
        jsonArray.put(description);

		/*
		 * Notice I will use the first image as thumbnail.
		 */

        String value = "";
        if (note.isPhoto()) {
            ImageAsset[] assets = photo.getImageAssets();
            if (assets != null && assets.length > 0) {
                Rendition rendition = assets[0].getRendition(136, 102);
                if (rendition == null) {
                    value = Utilities.getCompleteURL(gsaService, note.getNoteImageAsset().getPath(), Externalizer.STATIC_DOMAIN);
                } else {
                    value = Utilities.getCompleteURL(gsaService, rendition.getPath(), Externalizer.STATIC_DOMAIN);
                }
            }
        }
        jsonArray.put(value);

        value = "";
        if (note.isPhoto() && photo.getUrl() != null) {
            if (!additional.containsKey(DETAILS)) additional.put(DETAILS, ".details.xml");
            value = Utilities.getCompleteURL(gsaService, photo.getUrl().concat(String.valueOf(additional.get(DETAILS))), Externalizer.HTML_DOMAIN);
            value = value.replaceAll(".html", "");
        }
        jsonArray.put(value);

        return jsonArray.toString();
    }

    /**
     * Render a note as a feed item.
     *
     * @param rendering the type of rendering to render
     * @param note the note to render as a feed item
     * @return a string representing a JSON feed item
     */
    protected String getVideoFeed(SlingHttpServletRequest request, String rendering, Note note, Map<String, Object> additional) {
        JSONArray jsonArray = new JSONArray();

        Video video = null;
        if (note.isVideo()) {
            video = (Video) note;
        }

/*
Issue 112 : FEEDS_videos_nt.docx

{ "sectionsVideos" :
	[ { "main" : [
				[
						"El Noticiero del 26 de febrero del 2013",
						"http://m4v.tvolucion.com/m4v/not/ntjld/9a75cba2e2fb0fc30e0a0c9568448cdf/fb0fc30e0a.jpg",
						"http://tvolucion.esmas.com/noticieros/noticiero-con-joaquin-lopez-doriga/210147/noticiero-del-26-febrero-del-2013",
						"Http://m4vhds.tvolucion.com/i/m4v/not/ntjld/9a75cba2e2fb0fc30e0a0c9568448cdf/fb0fc30e0a-,150,235,480,600,970,.mp4.csmil/master.m3u8",
						"2013-02-27 00:31:40", "00:42:55" ],
				[
						"La opinión de Mauricio Merino",
						"http://media.esmas.com/criticalmedia/files/2013/02/27/11553616-9d8c96c6-025d-4938-aa93-ef021c405063.jpg?co_id=373",
						"http://tvolucion.esmas.com/foro-tv/el-mananero/210164/opinion-mauricio-merino",
						"http://apps.esmas.com/criticalmedia/files/2013/02/27/11553619-3d25ee4c-14b4-44c1-adc6-11d2ed05462e.mp4",
						"2013-02-27 08:36:13", "00:09:43" ]

	] } ]
}

{ "sectionsVideos":
	[ { "main": [
                [
                   Title of the video,
                   thumbnail,
                   Url web of the video ,
                   Url of the video,
                   Publish DateTime of the video,
                   Duration
                ]
            ] <<-- gescobar * added
        }
    ]
}
*/
        /* Set title */
        jsonArray.put(note.getTitle());

        /* Set thumbnail*/
        String value = "";
        if (note.isVideo()) {
            ImageAsset image = video.getVideoImageAsset();
            if (image != null) {
                Rendition rendition = image.getRendition(136, 102);
                if (rendition == null) {
                    value = Utilities.getCompleteURL(gsaService, video.getVideoImageAsset().getPath(), Externalizer.STATIC_DOMAIN);
                } else {
                    value = Utilities.getCompleteURL(gsaService, rendition.getPath(), Externalizer.STATIC_DOMAIN);
                }
            }
        }
        jsonArray.put(value);

        /* Set web video url */
        value = "";
        if (note.isVideo()) {
            if (video.getUrl() != null) {
                value = Utilities.getCompleteURL(gsaService, video.getUrl(), Externalizer.HTML_DOMAIN);
            }
        }
        jsonArray.put(value);

        /* Set video duration */
        String duration = "";
        if("8".equals(video.getVideoPlayer())){
            if(video.getBrightcoveProperties() != null && video.getBrightcoveProperties().get("duration") != null){
                duration = (String) video.getBrightcoveProperties().get("duration");
            }
        }else if("13".equals(video.getVideoPlayer())){
            duration = video.getVideoDuration();
        }
        Pattern pattern = Pattern.compile("\\d\\d:\\d\\d:\\d\\d");
        Matcher matcher = pattern.matcher(duration);
        boolean matches = matcher.matches();

        if(!matches){
            long miliseconds = Long.parseLong(duration);
            duration = BrightcoveHelper.getDuration(miliseconds);
        }

        /* Set video url */
        String videoUrl = "";
        int durationInMinutes = BrightcoveHelper.getDurationInMinutes(duration);
        if (durationInMinutes > 10) {
            if(video.getBrightcoveProperties() != null && video.getBrightcoveProperties().get("m3u8") != null){
                videoUrl = (String) video.getBrightcoveProperties().get("m3u8");
                videoUrl = Utilities.cleanVideoUrl(videoUrl);
            }
        }else{
            if(video.getBrightcoveProperties() != null && video.getBrightcoveProperties().get("videoUrl") != null){
                videoUrl = (String) video.getBrightcoveProperties().get("videoUrl");
                videoUrl = Utilities.cleanVideoUrl(videoUrl);
            }
        }
        jsonArray.put(videoUrl);

        /* set publication date */
        Calendar publicationDate = note.getFirstPubDate();
        if(publicationDate == null && note.getPubDate() != null){
            publicationDate = note.getPubDate();
        }
        if (publicationDate == null) {
            value = "";
        } else {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            value = format.format(publicationDate.getTime());
        }
        jsonArray.put(value);

        /* duration is defined above as it is used to define the video url */
        jsonArray.put(duration);

        return jsonArray.toString();
    }

    /**
     * Render a note as a feed item.
     *
     * @param rendering the type of rendering to render
     * @param note the note to render as a feed item
     * @return a string representing a JSON feed item
     */
    protected String getProgramFeed(SlingHttpServletRequest request, String rendering, Note note, Map<String, Object> additional) {
        JSONArray jsonArray = new JSONArray();

        jsonArray.put(note.getTitle());

        return jsonArray.toString();
    }

}

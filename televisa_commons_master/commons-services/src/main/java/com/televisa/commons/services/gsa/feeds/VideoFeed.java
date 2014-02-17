package com.televisa.commons.services.gsa.feeds;

import com.day.cq.tagging.Tag;
import com.televisa.commons.services.datamodel.Video;
import com.televisa.commons.services.gsa.Constants;
import com.televisa.commons.services.gsa.GsaUtilities;
import com.televisa.commons.services.services.GsaService;
import com.televisa.commons.services.utilities.Utilities;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class VideoFeed {

    private VideoFeed() {
    }

    public static String getXML(Video video) {
        String xml;

        int index = 1;
        StringBuilder keywords = null;
        if (video.getKeywords().length > 0) {
            keywords = new StringBuilder();
            for (Tag tag : video.getKeywords()) {
                if (index < video.getKeywords().length) {
                    keywords.append(tag.getTitle()).append(", ");
                    index++;
                } else {
                    keywords.append(tag.getTitle().trim());
                }
            }
        }
        String pubDate;
        String pubDateTime;
        if (video.getPubDate() == null) {
            pubDate = GsaUtilities.getDateToString("yyyy-MM-dd", video.getCrDate().getTime());
            pubDateTime = GsaUtilities.getDateToString("yyyy-MM-dd HH:mm:ss", video.getCrDate().getTime());
        } else {
            pubDate = GsaUtilities.getDateToString("yyyy-MM-dd", video.getPubDate().getTime());
            pubDateTime = GsaUtilities.getDateToString("yyyy-MM-dd HH:mm:ss", video.getPubDate().getTime());
        }

        String thumbnailUrl = null;
        if (video.getVideoImageAsset() != null && video.getVideoImageAsset().getRendition(136, 102) != null) {
            thumbnailUrl = GsaUtilities.getExternalizedURL(video.getVideoImageAsset().getRendition(136, 102).getPath(), GsaService.STATIC_DOMAIN);
        }

        String brightcoveId = null;
        if ("8".equals(video.getVideoPlayer()) && video.getVideoId() != null) {
            brightcoveId = video.getVideoId();
        }

        String m3u81url = null;
        if (video.getBrightcoveProperties() != null && video.getBrightcoveProperties().containsKey("m3u8")) {
            m3u81url = video.getBrightcoveProperties().get("m3u8", String.class);
        }

        Map<String, String> videoMetadata = new HashMap<String, String>();
        videoMetadata.put("category", Utilities.normalizeString(video.getCategory()));
        videoMetadata.put("categoryURL", GsaUtilities.getExternalizedURL(video.getCategoryUrl(), GsaService.HTML_DOMAIN));
        videoMetadata.put("title", video.getTitle());
        videoMetadata.put("URL", GsaUtilities.getExternalizedURL(video.getUrl(), GsaService.HTML_DOMAIN));
        videoMetadata.put("IdBrightcove", brightcoveId);
        videoMetadata.put("thumbnail_url", thumbnailUrl);
        videoMetadata.put("keywords", keywords.toString());
        videoMetadata.put("pubDate", pubDate);
        videoMetadata.put("pubDateTime", pubDateTime);
        videoMetadata.put("channel", video.getChannel());
        videoMetadata.put("programa", video.getVideoProgram());
        videoMetadata.put("nombrePrograma", video.getVideoProgramName());
        videoMetadata.put("summary", video.getSummary());
        videoMetadata.put("duration", video.getVideoDuration());
        videoMetadata.put("chapter", video.getVideoChapter());
        videoMetadata.put("season", video.getVideoSeason());
        videoMetadata.put("programaUrl", video.getVideoProgramUrl());
        videoMetadata.put("tipo", Constants.VIDEO_TYPE);
        videoMetadata.put("id_video", video.getVideoId());
        videoMetadata.put("videotype", video.getVideoType());
        videoMetadata.put("m3u81url", m3u81url);
        videoMetadata.put("tags", keywords.toString().toLowerCase());

        Element record     = new Element(Constants.RECORD_ELEMENT);
        Element metadata   = new Element(Constants.METADATA_ELEMENT);
        Element content    = new Element(Constants.CONTENT_ELEMENT);
        Element meta;

        record.addAttribute(new Attribute(Constants.URL_ATTRIBUTE, GsaUtilities.getExternalizedURL(video.getUrl(), GsaService.HTML_DOMAIN)));
        record.addAttribute(new Attribute(Constants.ACTION_ATTRIBUTE, Constants.ACTION_ADD));
        record.addAttribute(new Attribute(Constants.MIMETYPE_ATTRIBUTE, Constants.TEXTHTML));
        record.addAttribute(new Attribute(Constants.LOCK_ATTRIBUTE, Constants.TRUE));
        record.addAttribute(new Attribute(Constants.LAST_MODIFIED_ATTRIBUTE, GsaUtilities.getDateToString("EEE, dd MMM yyyy HH:mm:ss z", video.getPubDate() == null ? new Date() : video.getCrDate().getTime() )));

        for (Map.Entry<String, String> videoMeta : videoMetadata.entrySet()) {
            if(videoMeta.getValue() != null && !videoMeta.getValue().isEmpty()){
                meta = new Element(Constants.META_ELEMENT);
                meta.addAttribute(new Attribute(Constants.NAME_ATTRIBUTE, videoMeta.getKey()));
                meta.addAttribute(new Attribute(Constants.CONTENT_ATTRIBUTE,videoMeta.getValue()));
                metadata.appendChild(meta);
            }
        }

        record.appendChild(metadata);

        content.appendChild(Constants.CONTENT_KEY);
        record.appendChild(content);

        Document doc = new Document(record);

        xml = doc.toXML();
        xml = xml.replace("<?xml version=\"1.0\"?>", "");

        xml = xml.replace(Constants.CONTENT_KEY, GsaUtilities.wrapWithCdata(video.getTitle()));


        return xml;

    }


}

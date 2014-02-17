package com.televisa.commons.services.gsa.feeds;

import com.day.cq.tagging.Tag;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.gsa.Constants;
import com.televisa.commons.services.services.GsaService;
import com.televisa.commons.services.gsa.GsaUtilities;
import com.televisa.commons.services.utilities.Utilities;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: palecio
 * Date: 7/3/13
 * Time: 09:03
 */
public final class ArticleFeed {

    private ArticleFeed() {
    }

    public static String getXML(Note note) {

        Element record   = new Element(Constants.RECORD_ELEMENT);
        Element metadata = new Element(Constants.METADATA_ELEMENT);
        Element content   = new Element(Constants.CONTENT_ELEMENT);
        Element meta;

        String xml;

        int index = 1;
        StringBuilder keywords = new StringBuilder();
        for (Tag tag : note.getKeywords()) {
            if (index < note.getKeywords().length) {
                keywords.append(tag.getName().trim()).append(", ");
                index++;
            } else {
                keywords.append(tag.getName().trim());
            }
        }

        String thumbnailUrl = null;
        if (note.getNoteImageAsset() != null && note.getNoteImageAsset().getRendition(136, 102) != null){
            thumbnailUrl = GsaUtilities.getExternalizedURL(note.getNoteImageAsset().getRendition(136, 102).getPath(), GsaService.STATIC_DOMAIN);
        }

        String rendition63x63 = null;
        if (note.getNoteImageAsset() != null && note.getNoteImageAsset().getRendition(63, 63) != null){
            rendition63x63 = GsaUtilities.getExternalizedURL(note.getNoteImageAsset().getRendition(63, 63).getPath(), GsaService.STATIC_DOMAIN);
        }

        String rendition136x76 = null;
        if (note.getNoteImageAsset() != null && note.getNoteImageAsset().getRendition(136, 76) != null){
            rendition136x76 = GsaUtilities.getExternalizedURL(note.getNoteImageAsset().getRendition(136, 76).getPath(), GsaService.STATIC_DOMAIN);
        }

        String rendition140x100 = null;
        if (note.getNoteImageAsset() != null && note.getNoteImageAsset().getRendition(140, 100) != null){
            rendition140x100 = GsaUtilities.getExternalizedURL(note.getNoteImageAsset().getRendition(140, 100).getPath(), GsaService.STATIC_DOMAIN);
        }
        String rendition300x168 = null;
        if (note.getNoteImageAsset() != null && note.getNoteImageAsset().getRendition(300, 168) != null){
            rendition300x168 = GsaUtilities.getExternalizedURL(note.getNoteImageAsset().getRendition(300, 168).getPath(), GsaService.STATIC_DOMAIN);
        }
        String rendition624x468 = null;
        if (note.getNoteImageAsset() != null && note.getNoteImageAsset().getRendition(624, 468) != null){
            rendition624x468 = GsaUtilities.getExternalizedURL(note.getNoteImageAsset().getRendition(624, 468).getPath(), GsaService.STATIC_DOMAIN);
        }
        String rendition624x351 = null;
        if (note.getNoteImageAsset() != null && note.getNoteImageAsset().getRendition(624, 351) != null){
            rendition624x351 = GsaUtilities.getExternalizedURL(note.getNoteImageAsset().getRendition(624, 351).getPath(), GsaService.STATIC_DOMAIN);
        }
        String rendition319x319 = null;
        if (note.getNoteImageAsset() != null && note.getNoteImageAsset().getRendition(319, 319) != null){
            rendition319x319 = GsaUtilities.getExternalizedURL(note.getNoteImageAsset().getRendition(319, 319).getPath(), GsaService.STATIC_DOMAIN);
        }
        String pubDate;
        String modificationHour;
        if(note.getPubDate() == null) {
            pubDate = GsaUtilities.getDateToString(Constants.FORMAT_DATE, note.getCrDate().getTime());
            modificationHour = GsaUtilities.getDateToString(Constants.FORMAT_TIME, note.getCrDate().getTime());
        } else {
            pubDate = GsaUtilities.getDateToString(Constants.FORMAT_DATE, note.getPubDate().getTime());
            modificationHour = GsaUtilities.getDateToString(Constants.FORMAT_TIME, note.getPubDate().getTime());
        }

        String creationDate = GsaUtilities.getDateToString(Constants.FORMAT_DATE, note.getCrDate().getTime());
        String creationHour = GsaUtilities.getDateToString(Constants.FORMAT_TIME, note.getCrDate().getTime());

        Map<String, String> articleMetadata = new HashMap<String, String>();

        articleMetadata.put("category", Utilities.normalizeString(note.getCategory()));
        articleMetadata.put("categoryURL", GsaUtilities.getExternalizedURL(note.getCategoryUrl(), GsaService.HTML_DOMAIN));
        articleMetadata.put("title", note.getTitle());
        articleMetadata.put("URL", GsaUtilities.getExternalizedURL(note.getUrl(), GsaService.HTML_DOMAIN));
        articleMetadata.put("thumbnail_url", thumbnailUrl);
        articleMetadata.put("63x63", rendition63x63);
        articleMetadata.put("136x76", rendition136x76);
        articleMetadata.put("140x100", rendition140x100);
        articleMetadata.put("300x168", rendition300x168);
        articleMetadata.put("624x468", rendition624x468);
        articleMetadata.put("624x351", rendition624x351);
        articleMetadata.put("319x319", rendition319x319);
        articleMetadata.put("keywords", keywords.toString());
        articleMetadata.put("pubDate", pubDate);
        articleMetadata.put("crDate", creationDate);
        articleMetadata.put("horaCreacion", creationHour);
        articleMetadata.put("horaModificacion", modificationHour);
        articleMetadata.put("channel", note.getChannel());
        articleMetadata.put("summary", note.getSummary());
        articleMetadata.put("tipo", Constants.ARTICLE_TYPE);
        articleMetadata.put("tags", keywords.toString().toLowerCase());

        record.addAttribute(new Attribute(Constants.URL_ATTRIBUTE, GsaUtilities.getExternalizedURL(note.getUrl(), GsaService.HTML_DOMAIN)));
        record.addAttribute(new Attribute(Constants.ACTION_ATTRIBUTE, Constants.ACTION_ADD));
        record.addAttribute(new Attribute(Constants.MIMETYPE_ATTRIBUTE, Constants.TEXTHTML));
        record.addAttribute(new Attribute(Constants.LOCK_ATTRIBUTE, Constants.TRUE));
        record.addAttribute(new Attribute(Constants.LAST_MODIFIED_ATTRIBUTE, GsaUtilities.getDateToString("EEE, dd MMM yyyy HH:mm:ss z", note.getPubDate() == null ? new Date() : note.getCrDate().getTime() )));

        for (Map.Entry<String, String> articleMeta : articleMetadata.entrySet()) {
            if(articleMeta.getValue() != null && !articleMeta.getValue().isEmpty()){
                meta = new Element(Constants.META_ELEMENT);
                meta.addAttribute(new Attribute(Constants.NAME_ATTRIBUTE, articleMeta.getKey()));
                meta.addAttribute(new Attribute(Constants.CONTENT_ATTRIBUTE,articleMeta.getValue()));
                metadata.appendChild(meta);
            }
        }

        record.appendChild(metadata);

        content.appendChild(Constants.CONTENT_KEY);
        record.appendChild(content);

        Document doc = new Document(record);

        xml = doc.toXML();
        xml = xml.replace("<?xml version=\"1.0\"?>", "");
        xml = xml.replace(Constants.CONTENT_KEY, GsaUtilities.wrapWithCdata(note.getContent()));

        return xml;


    }
}

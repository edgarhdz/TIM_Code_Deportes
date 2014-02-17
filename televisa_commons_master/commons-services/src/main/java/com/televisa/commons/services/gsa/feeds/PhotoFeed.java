package com.televisa.commons.services.gsa.feeds;

import com.day.cq.tagging.Tag;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.datamodel.Photo;
import com.televisa.commons.services.gsa.Constants;
import com.televisa.commons.services.gsa.GsaUtilities;
import com.televisa.commons.services.services.GsaService;
import com.televisa.commons.services.datamodel.objects.ImageAsset;
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
 * Time: 09:05
 */
public final class PhotoFeed {

    private PhotoFeed() {
    }

    public static String getXML(Note note) {

        Photo photo = (Photo)note;
        String xml;

        Element record    = new Element(Constants.RECORD_ELEMENT);
        Element metadata  = new Element(Constants.METADATA_ELEMENT);
        Element content   = new Element(Constants.CONTENT_ELEMENT);
        Element meta;

        int index = 1;
        StringBuilder keywords = new StringBuilder();
        for (Tag tag : photo.getKeywords()) {
            if (index < photo.getKeywords().length) {
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

        String pubDate;
        if (note.getPubDate() == null) {
            pubDate = GsaUtilities.getDateToString("yyyy-MM-dd", note.getCrDate().getTime());
        } else {
            pubDate = GsaUtilities.getDateToString("yyyy-MM-dd", note.getPubDate().getTime());
        }

        String channelUrl = null;
        if(photo.getPage().getAbsoluteParent(2) != null){
            channelUrl = photo.getPage().getAbsoluteParent(2).getPath();
            channelUrl = GsaUtilities.getExternalizedURL(channelUrl, GsaService.HTML_DOMAIN);
        }

        Map<String, String> photoMetadata = new HashMap<String, String>();

        photoMetadata.put("category", Utilities.normalizeString(photo.getCategory()));
        photoMetadata.put("title", photo.getTitle());
        photoMetadata.put("URL", photo.getUrl());
        photoMetadata.put("thumbnail_url", thumbnailUrl);
        photoMetadata.put("keywords", keywords.toString());
        photoMetadata.put("pubDate", pubDate);
        photoMetadata.put("channel", photo.getChannel());
        photoMetadata.put("url_canal", channelUrl);
        index = 1;
        if(photo.getImageAssets() != null){
            for (ImageAsset image : photo.getImageAssets()) {
                photoMetadata.put("foto" + index++, GsaUtilities.getExternalizedURL(image.getPath(), GsaService.STATIC_DOMAIN));
            }
        }
        photoMetadata.put("tipo", Constants.PHOTO_TYPE);


        record.addAttribute(new Attribute(Constants.URL_ATTRIBUTE, GsaUtilities.getExternalizedURL(photo.getUrl(), GsaService.STATIC_DOMAIN)));
        record.addAttribute(new Attribute(Constants.ACTION_ATTRIBUTE, Constants.ACTION_ADD));
        record.addAttribute(new Attribute(Constants.MIMETYPE_ATTRIBUTE, Constants.TEXTHTML));
        record.addAttribute(new Attribute(Constants.LOCK_ATTRIBUTE, Constants.TRUE));
        record.addAttribute(new Attribute(Constants.LAST_MODIFIED_ATTRIBUTE, GsaUtilities.getDateToString("EEE, dd MMM yyyy HH:mm:ss z", photo.getPubDate() == null ? new Date() : photo.getCrDate().getTime() )));

        for (Map.Entry<String, String> photoMeta : photoMetadata.entrySet()) {
            if(photoMeta.getValue() != null && !photoMeta.getValue().isEmpty()){
                meta = new Element(Constants.META_ELEMENT);
                meta.addAttribute(new Attribute(Constants.NAME_ATTRIBUTE, photoMeta.getKey()));
                meta.addAttribute(new Attribute(Constants.CONTENT_ATTRIBUTE,photoMeta.getValue()));
                metadata.appendChild(meta);
            }
        }

        record.appendChild(metadata);

        content.appendChild(Constants.CONTENT_KEY);
        record.appendChild(content);


        Document doc = new Document(record);

        xml = doc.toXML();
        xml = xml.replace(Constants.CONTENT_KEY, GsaUtilities.wrapWithCdata(photo.getDescription()));
        xml = xml.replace("<?xml version=\"1.0\"?>", "");


        return xml;

    }
}

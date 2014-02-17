package com.televisa.commons.services.gsa;

import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.datamodel.Video;
import com.televisa.commons.services.gsa.feeds.ArticleFeed;
import com.televisa.commons.services.gsa.feeds.PhotoFeed;
import com.televisa.commons.services.gsa.feeds.VideoFeed;
import com.televisa.commons.services.utilities.Utilities;
import nu.xom.Document;
import nu.xom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GsaFeedProvider extends FeedXML {

    private static final Logger LOGGER = LoggerFactory.getLogger(GsaFeedProvider.class);

    public GsaFeedProvider(Note note, String action) {
        super(note, action);
    }

    public GsaFeedProvider(Note[] notes, String action) {
        super(notes, action);
    }


    protected String getGSAMultipleXMLAction() {

        LOGGER.info("this is getGSAMultipleXMLAction");

        String category = null;

        LOGGER.info("size: " + getNotes().length);

        if (getNotes().length > 0) {
            category = getNotes()[0].getCategory();
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>");
        stringBuilder.append(Constants.DOCTYPE);
        stringBuilder.append("<gsafeed>");
        stringBuilder.append(this.getGSAFeedHeaderActionAdd(category));
        stringBuilder.append(this.getGSAFeedGroupActionAdd());
        stringBuilder.append("</gsafeed>");

        return stringBuilder.toString();
    }


    protected String getGSAFeedHeaderActionAdd(String category) {
        String xml;
        Element header     = new Element("header");
        Element datasource = new Element("datasource");
        Element feedtype   = new Element("feedtype");

        datasource.appendChild(Utilities.normalizeString(category));
        feedtype.appendChild("incremental");

        header.appendChild(datasource);
        header.appendChild(feedtype);

        Document doc = new Document(header);
        xml = doc.toXML();

        xml = xml.replace("<?xml version=\"1.0\"?>", "");

        return xml;
    }

    protected String getGSAFeedGroupActionAdd() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<group>");
        for (Note note : getNotes()) {
            if (note.isArticle()) {
                stringBuilder.append(ArticleFeed.getXML(note));
            } else if (note.isPhoto()) {
                stringBuilder.append(PhotoFeed.getXML(note));
            } else if (note.isVideo()){
                stringBuilder.append(VideoFeed.getXML((Video) note));
            }
        }
        stringBuilder.append("</group>");
        return stringBuilder.toString();
    }


}

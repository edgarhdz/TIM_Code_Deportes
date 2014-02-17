/**
 * GSA Utility class
 *
 * This class help GSAWorkflowProcess to create xml feed.
 *
 * Changes History:
 *
 *         2013-01-17 Initial Development
 *
 * @author jesquivel@xumak.com
 * @version 1.0
 */

package com.televisa.commons.services.gsa;

import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.services.GsaService;
import com.televisa.commons.services.utilities.Utilities;
import nu.xom.Attribute;
import nu.xom.DocType;
import nu.xom.Document;
import nu.xom.Element;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class GsaUtilities {

    private static final Logger LOGGER = LoggerFactory.getLogger(GsaUtilities.class);
    private static GsaService gsaService;

    private GsaUtilities() {
    }

    public static void setGsaService( GsaService gsaService ) {
        GsaUtilities.gsaService = gsaService;
    }

    public static String getDateToString(String dateFormat, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    public static String getExternalizedURL(String url, String domain) {
        return gsaService.buildUrl( url , domain);
    }

    public static String wrapWithCdata(String text){
        return text == null || text.isEmpty() ? ""  : "<![CDATA[" + text + "]]>";
    }

    public static String getXMLGSAFeed(Note[] noteArray, String action) {

        LOGGER.info("getXMLGSAFeed - action: " + action);

        FeedXML feed;
        String xml  = null;

        feed = new GsaFeedProvider(noteArray, action);

        if (action.equals(Constants.ACTION_ADD)) {

            LOGGER.info("note.length "+ noteArray.length);

            xml = feed.getGSAMultipleXMLAction();

        } else if (action.equals(Constants.ACTION_DELETE)) {
            if(noteArray.length == 1){
                xml = getDeleteGSAFeed(noteArray[0]);
            }else {
                xml = getDeletionsGSAFeed( noteArray );
                     /* TODO implement deactivate tree functionality*/
                     /* LOGGER.warn("Cowardly refusing to delete more than 1 GSA record at a time."); */
            }

        }

        return xml;

    }


    public static String getDeleteGSAFeed(Note note){
        /* TODO move to a new class*/
        /* Example XML feed:
            <?xml version="1.0" encoding="iso-8859-1"?>
            <!DOCTYPE gsafeed PUBLIC "-//Google//DTD GSA Feeds//EN" "">
            <gsafeed>
                <header>
                    <datasource>USA</datasource>
                    <feedtype>metadata-and-url</feedtype>
                </header>
                <group action="delete">
                    <record url="http://tvolucion.esmas.com/telenovelas/drama/como-dice-el-dicho/194860/como-dice-dicho-capitulo-139/"   mimetype="text/plain"/>
                </group>
            </gsafeed>
        */

        String recordURL = GsaUtilities.getExternalizedURL(note.getUrl(), GsaService.HTML_DOMAIN);

        /* Define feed elements */
        Element gsafeed = new Element(Constants.GSAFEED_ELEMENT);
        Element header = new Element(Constants.HEADER_ELEMENT);
        Element datasource = new Element(Constants.DATASOURCE_ELEMENT);
        Element feedtype = new Element(Constants.FEEDTYPE_ELEMENT);
        Element group = new Element(Constants.GROUP_ELEMENT);
        Element record = new Element(Constants.RECORD_ELEMENT);

        /* Create XML Node structure*/
        gsafeed.appendChild(header);
        header.appendChild(datasource);
        header.appendChild(feedtype);
        gsafeed.appendChild(group);
        group.appendChild(record);

        /* Add attibutes to nodes */
        Attribute action = new Attribute(Constants.ACTION_ATTRIBUTE, Constants.ACTION_DELETE);
        group.addAttribute(action);

        Attribute url = new Attribute(Constants.URL_ATTRIBUTE, recordURL);
        record.addAttribute(url);

        Attribute mimeType = new Attribute(Constants.MIMETYPE_ATTRIBUTE, Constants.TEXTPLAIN);
        record.addAttribute(mimeType);

        /*Remove accents and convert to lower case*/
        String category = note.getCategory();
        category = Utilities.normalizeString(category);
        
        /* Add values to nodes */
        datasource.appendChild(category);
        feedtype.appendChild("metadata-and-url");

        Document doc = new Document(gsafeed);
        DocType docType = new DocType(Constants.GSAFEED_ELEMENT, "-//Google//DTD GSA Feeds//EN", "");
        doc.setDocType(docType);

        String xml = doc.toXML();
        xml = xml.replace("<?xml version=\"1.0\"?>", "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>");

        return xml;

    }


    public static String getDeletionsGSAFeed(Note[] note){
		/* TODO move to a new class*/
		/* Example XML feed:
		    <?xml version="1.0" encoding="iso-8859-1"?>
		    <!DOCTYPE gsafeed PUBLIC "-//Google//DTD GSA Feeds//EN" "">
		    <gsafeed>
		        <header>
		            <datasource>USA</datasource>
		            <feedtype>metadata-and-url</feedtype>
		        </header>
		        <group action="delete">
		            	<record url="http://tvolucion.esmas.com/telenovelas/drama/como-dice-el-dicho/194860/como-dice-dicho-capitulo-139/"   mimetype="text/plain"/>
				<record url="http://tvolucion.esmas.com/telenovelas/drama/como-dice-el-dicho/194860/como-dice-dicho-capitulo-139/"   mimetype="text/plain"/>
				<record url="http://tvolucion.esmas.com/telenovelas/drama/como-dice-el-dicho/194860/como-dice-dicho-capitulo-139/"   mimetype="text/plain"/>
				<record url="http://tvolucion.esmas.com/telenovelas/drama/como-dice-el-dicho/194860/como-dice-dicho-capitulo-139/"   mimetype="text/plain"/>
				<record url="http://tvolucion.esmas.com/telenovelas/drama/como-dice-el-dicho/194860/como-dice-dicho-capitulo-139/"   mimetype="text/plain"/>
		        </group>
		    </gsafeed>
		*/

		

		/* Define feed elements */
        Element gsafeed = new Element(Constants.GSAFEED_ELEMENT);
        Element header = new Element(Constants.HEADER_ELEMENT);
        Element datasource = new Element(Constants.DATASOURCE_ELEMENT);
        Element feedtype = new Element(Constants.FEEDTYPE_ELEMENT);
        Element group = new Element(Constants.GROUP_ELEMENT);
		

		
		

		/* Create XML Node structure*/
        gsafeed.appendChild(header);
        header.appendChild(datasource);
        header.appendChild(feedtype);
        gsafeed.appendChild(group);

        for(Note noteItem : note ){
            String recordURL = GsaUtilities.getExternalizedURL(noteItem.getUrl(), GsaService.HTML_DOMAIN);
            Element record = new Element(Constants.RECORD_ELEMENT);
            group.appendChild(record);
            Attribute url = new Attribute(Constants.URL_ATTRIBUTE, recordURL);
            record.addAttribute(url);
            Attribute mimeType = new Attribute(Constants.MIMETYPE_ATTRIBUTE, Constants.TEXTPLAIN);
            record.addAttribute(mimeType);

        }
		

		/* Add attibutes to nodes */
        Attribute action = new Attribute(Constants.ACTION_ATTRIBUTE, Constants.ACTION_DELETE);
        group.addAttribute(action);

		/* Add values to nodes */
        String category = Utilities.normalizeString(note[0].getCategory());
        datasource.appendChild(category);
        feedtype.appendChild("metadata-and-url");

        Document doc = new Document(gsafeed);
        DocType docType = new DocType(Constants.GSAFEED_ELEMENT, "-//Google//DTD GSA Feeds//EN", "");
        doc.setDocType(docType);

        String xml = doc.toXML();
        xml = xml.replace("<?xml version=\"1.0\"?>", "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>");

        return xml;

    }



    public static void postRequest(String gsaurl, String xml, String datasource) throws IOException {

        HttpClient client;
        InputStream inputStream = null;
        PostMethod method = null;

        try {

            client = new HttpClient();
            method = new PostMethod(gsaurl);

            LOGGER.info("Datasource = " + Utilities.normalizeString(datasource));

            method.addParameter(Constants.DATASOURCE_ELEMENT, Utilities.normalizeString(datasource));
            method.addParameter(Constants.FEEDTYPE_ELEMENT, Constants.FEEDTYPE);
            method.addParameter("data", xml);

            int statusCode = client.executeMethod(method);

            if (statusCode != -1) {
                inputStream = method.getResponseBodyAsStream();
            }

            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer, "utf-8");
            LOGGER.info("post request - status code: " + writer.toString());

        } catch(IOException e) {
            LOGGER.error("post request exception: " + e);
            throw new IOException(e);

        } finally {
            if(method != null){
                method.releaseConnection();
            }
        }

    }
}

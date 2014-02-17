package com.local.deportes.services.servlet;

import com.day.cq.wcm.api.PageManager;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.sling.jcr.resource.JcrResourceConstants;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.Comment;


import java.io.*;
import java.util.*;

/**
 * User: llopez
 * Date: 12/5/13
 * Time: 9:35 AM
 */

@SlingServlet(
        label = "Video Multistream Servlet",
        methods = {"GET" },
        extensions = { "html" },
        paths = {"/bin/generics/multistream/xml"},
        metatype = false
)
public class VideoMultistreamServlet extends SlingAllMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoMultistreamServlet.class);

    private Session session;
    @Reference
    private ResourceResolverFactory resourceResolverFactory;
    @Reference
    private SlingRepository repository;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        response.setContentType("text/html");

        LOGGER.info("******** MULTISTREAM STARTING PROCESS");

        String nodePath = request.getParameter("currentNode").toString();

        LOGGER.info("******** MULTISTREAM nodePath: "+ nodePath);

        //create configuration files
        createStatusFile(nodePath);
        createContentXML(nodePath);


    }

    public void createStatusFile(String nodePath){
        try{
            String status = "off";
            session = repository.loginAdministrative(null);

            if (session.nodeExists(nodePath)) {
                Node componentNode = session.getNode(nodePath);

                //STATUS
                if(componentNode.hasProperty("status")){
                    Value value = componentNode.getProperty("status").getValue();
                    if(value != null && value.getString() != null){
                        status = componentNode.getProperty("status").getString();

                        Node jcrContentText = null;
                        if (!componentNode.hasNode("playerstate.txt")) {
                            Node newNode = componentNode.addNode("playerstate.txt","nt:file");
                            jcrContentText = newNode.addNode("jcr:content","nt:resource");
                            jcrContentText.setProperty("jcr:mimeType","application/application/octet-stream");
                        } else {
                            jcrContentText = componentNode.getNode("playerstate.txt/jcr:content");
                        }

                        jcrContentText.setProperty("jcr:data",status);
                        session.save();
                    }
                }
                session.logout();
            }

        }catch(Exception e) {
            LOGGER.info("******** ERROR MULTISTREAM FILE TXT: " + e.getMessage());
        }
    }

    public void createContentXML(String nodePath) {
        boolean hasSignals = false;
        try {
            session = repository.loginAdministrative(null);
            Map<String,String> map = new LinkedHashMap<String, String>();

            if (session.nodeExists(nodePath)) {
                Node componentNode = session.getNode(nodePath);

                if(componentNode != null){
                    if (componentNode.hasProperty("signalTitle") && componentNode.hasProperty("signalStatus") && componentNode.hasProperty("signalURL")) {
                        if (componentNode.getProperty("signalURL").isMultiple()) {
                            Value[] titles = componentNode.getProperty("signalTitle").getValues();
                            Value[] status = componentNode.getProperty("signalStatus").getValues();
                            Value[] urls = componentNode.getProperty("signalURL").getValues();
                            int index = 0;
                            while (index < urls.length){
                                if(status[index].getString().equals("activated")){
                                    map.put(urls[index].getString().toString(),titles[index].getString().toString());
                                }
                                index++;
                            }
                            hasSignals = true;
                        } else {
                            map.put(componentNode.getProperty("signalURL").getString(),componentNode.getProperty("signalTitle").getString());
                            hasSignals = true;
                        }
                    }
                }
            }

            if (hasSignals) {
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
                //creating a new instance of a DOM to build a DOM tree.
                Document doc = docBuilder.newDocument();

                Element rss = doc.createElement("rss");
                rss.setAttribute("xmlns:itunes", "http://www.itunes.com/dtds/podcast-1.0.dtd");
                rss.setAttribute("xmlns:media", "http://search.yahoo.com/mrss/");
                rss.setAttribute("xmlns:dc", "http://purl.org/dc/elements/1.1/");
                rss.setAttribute("xmlns:atom", "http://www.w3.org/2005/Atom");
                rss.setAttribute("version", "2.0");
                doc.appendChild(rss);

                Element channel = doc.createElement("channel");
                rss.appendChild(channel);

                Element title = doc.createElement("title");
                channel.appendChild(title);
                Text texttitle = doc.createTextNode("Multistream");
                title.appendChild(texttitle);

                Element atomlink = doc.createElement("atom:link");
                channel.appendChild(atomlink);
                atomlink.setAttribute("href", "");
                atomlink.setAttribute("rel", "self");
                atomlink.setAttribute("type", "application/rss+xml");

                Element link = doc.createElement("link");
                channel.appendChild(link);

                Element description = doc.createElement("description");
                channel.appendChild(description);

                Element image = doc.createElement("image");
                channel.appendChild(image);

                Element itunesimage = doc.createElement("itunes:image");
                itunesimage.setAttribute("href", "");
                channel.appendChild(itunesimage);

                Element itunesexplicit = doc.createElement("itunes:explicit");
                channel.appendChild(itunesexplicit);
                Text textitunesexplicit = doc.createTextNode("clean");
                itunesexplicit.appendChild(textitunesexplicit);

                Element category = doc.createElement("category");
                channel.appendChild(category);
                Text textcategory = doc.createTextNode("Rama prueba");
                category.appendChild(textcategory);

                Element itunescategory = doc.createElement("itunes:category");
                itunescategory.setAttribute("text", "Category");
                channel.appendChild(itunescategory);

                Element pubDate = doc.createElement("pubDate");
                channel.appendChild(pubDate);
                Text textpubDate = doc.createTextNode("Wed, 31 Dec 1969 19:00:00 -0500");
                pubDate.appendChild(textpubDate);

                Element dcdate = doc.createElement("dc:date");
                channel.appendChild(dcdate);
                Text textdcdate = doc.createTextNode("1969-12-31T19:00:00-05:00");
                dcdate.appendChild(textdcdate);

                Element language = doc.createElement("language");
                channel.appendChild(language);
                Text textlanguage = doc.createTextNode("en");
                language.appendChild(textlanguage);

                Element dclanguage = doc.createElement("dc:language");
                channel.appendChild(dclanguage);
                Text textdclanguage = doc.createTextNode("en");
                dclanguage.appendChild(textdclanguage);

                Element ttl = doc.createElement("ttl");
                channel.appendChild(ttl);
                Text textttl = doc.createTextNode("10");
                ttl.appendChild(textttl);

                int idRandom = 1;

                for (Map.Entry<String, String> entry : map.entrySet()){

                    Element item1 = doc.createElement("item");
                    channel.appendChild(item1);

                    Element matchName = doc.createElement("matchName");
                    item1.appendChild(matchName);
                    Text textMatchName = doc.createTextNode("Signal");
                    matchName.appendChild(textMatchName);

                    Element cameraAngle = doc.createElement("cameraAngle");
                    item1.appendChild(cameraAngle);
                    Text textCameraAngle = doc.createTextNode(entry.getValue());
                    cameraAngle.appendChild(textCameraAngle);

                    Element itemtitle = doc.createElement("title");
                    item1.appendChild(itemtitle);
                    Text textitemtitle = doc.createTextNode(entry.getValue());
                    itemtitle.appendChild(textitemtitle);

                    Element IDGALAXY = doc.createElement("IDGALAXY");
                    item1.appendChild(IDGALAXY);
                    Text textIDGALAXY = doc.createTextNode("238404");
                    IDGALAXY.appendChild(textIDGALAXY);

                    Element idtv = doc.createElement("idtv");
                    item1.appendChild(idtv);
                    Text textIdtv = doc.createTextNode(entry.getValue()+"-238404");
                    idtv.appendChild(textIdtv);

                    Element descriptionitem = doc.createElement("idtv");
                    item1.appendChild(descriptionitem);
                    Text textDescription = doc.createTextNode("VOD Trailer");
                    descriptionitem.appendChild(textDescription);

                    Element itemcategory = doc.createElement("category");
                    item1.appendChild(itemcategory);
                    Text textCategory = doc.createTextNode("live_stream");
                    itemcategory.appendChild(textCategory);

                    Element itempubDate = doc.createElement("category");
                    item1.appendChild(itempubDate);
                    Text textPubDate = doc.createTextNode("Wed, 31 Dec 1969 19:00:00 -0500");
                    itempubDate.appendChild(textPubDate);

                    Element guid = doc.createElement("guid");
                    guid.setAttribute("isPermaLink", "false");
                    item1.appendChild(guid);
                    String id = "124118654600";
                    id = id + String.valueOf(idRandom);
                    idRandom++;

                    Text textGuid = doc.createTextNode(id);
                    guid.appendChild(textGuid);

                    Element date = doc.createElement("dc:date");
                    item1.appendChild(date);
                    Text textDate = doc.createTextNode("1969-12-31T19:00:00-05:00");
                    date.appendChild(textDate);

                    Element dcsource = doc.createElement("dc:source");
                    item1.appendChild(dcsource);
                    Text textSource = doc.createTextNode("VOD Trailer");
                    dcsource.appendChild(textSource);

                    Element dccreator = doc.createElement("dc:creator");
                    item1.appendChild(dccreator);

                    Element dccontributor = doc.createElement("dc:contributor");
                    item1.appendChild(dccontributor);

                    Element itunesauthor = doc.createElement("itunes:author");
                    item1.appendChild(itunesauthor);
                    Text textItunesAuthor = doc.createTextNode(entry.getValue());
                    itunesauthor.appendChild(textItunesAuthor);

                    Element itunessummary = doc.createElement("itunes:summary");
                    item1.appendChild(itunessummary);
                    Text textItunesSummary = doc.createTextNode(entry.getValue());
                    itunessummary.appendChild(textItunesSummary);

                    Element itunesExplicit = doc.createElement("itunes:explicit");
                    item1.appendChild(itunesExplicit);
                    Text textItunesExplicit = doc.createTextNode("clean");
                    itunesExplicit.appendChild(textItunesExplicit);

                    Element itunesduration = doc.createElement("itunes:duration");
                    item1.appendChild(itunesduration);
                    Text textItunesDuration = doc.createTextNode("00:00");
                    itunesduration.appendChild(textItunesDuration);

                    Element enclosure = doc.createElement("enclosure");
                    item1.appendChild(enclosure);
                    enclosure.setAttribute("url", "");
                    enclosure.setAttribute("length", "0");
                    enclosure.setAttribute("type", "video/mp4");

                    Element mediagroup = doc.createElement("media:group");
                    item1.appendChild(mediagroup);

                    Element mediacontent = doc.createElement("media:content");
                    mediagroup.appendChild(mediacontent);
                    mediacontent.setAttribute("isDefault", "true");
                    mediacontent.setAttribute("framerate", "0");
                    mediacontent.setAttribute("medium", "video");
                    mediacontent.setAttribute("url", entry.getKey());
                    mediacontent.setAttribute("type", "application/smil+xml");
                    mediacontent.setAttribute("expression", "full");
                    mediacontent.setAttribute("bitrate", "0");
                    mediacontent.setAttribute("lang", "en-us");
                    mediacontent.setAttribute("height", "268");
                    mediacontent.setAttribute("duration", "1887");
                    mediacontent.setAttribute("width", "480");

                    Element mediatitle = doc.createElement("media:title");
                    mediagroup.appendChild(mediatitle);
                    mediatitle.setAttribute("type", "plain");
                    Text textmediatitle = doc.createTextNode(entry.getValue());
                    mediatitle.appendChild(textmediatitle);

                    Element mediadescription = doc.createElement("media:description");
                    mediagroup.appendChild(mediadescription);
                    mediadescription.setAttribute("type", "plain");
                    Text textmediadescription = doc.createTextNode(entry.getValue());
                    mediadescription.appendChild(textmediadescription);

                    Element mediaplayer = doc.createElement("media:player");
                    mediagroup.appendChild(mediaplayer);
                    mediaplayer.setAttribute("url", "");
                    mediaplayer.setAttribute("width", "640");
                    mediaplayer.setAttribute("height", "360");

                    Element mediathumbnail = doc.createElement("media:thumbnail");
                    mediagroup.appendChild(mediathumbnail);
                    mediathumbnail.setAttribute("url", "");
                    mediathumbnail.setAttribute("width", "320");
                    mediathumbnail.setAttribute("height", "240");
                }

                //TransformerFactory instance is used to create Transformer objects.
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();

                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                // create string from xml tree
                StringWriter sw = new StringWriter();
                StreamResult result = new StreamResult(sw);
                DOMSource source = new DOMSource(doc);
                transformer.transform(source, result);
                String xmlString = sw.toString();
                createConfigXML(nodePath, xmlString);
            }

        } catch(Exception e) {
            LOGGER.info("******** ERROR EN MULTISTREAM: " + e.getMessage());
        }
    }

    private void createConfigXML(String nodePath, String xmlString) {
        LOGGER.info("******** MULTISTREAM");

        List<String> imageStatus = new LinkedList<String>();
        List<String> imagesStatusUrls = new LinkedList<String>();

        String contentXMLPath = nodePath.replace("jcr:content","_jcr_content") + "/content-list-multi-vods.xml";
        String playerStatePath = nodePath.replace("jcr:content","_jcr_content") + "/playerstate.txt";

        boolean existConfigXML = false;

        try {

            session = repository.loginAdministrative(null);

            // get images and url for each status
            if (session.nodeExists(nodePath)) {
                Node componentNode = session.getNode(nodePath);

                imageStatus.add(getGenericProperty(componentNode, "prev", "fileReference"));
                imageStatus.add(getGenericProperty(componentNode, "post", "fileReference"));
                imageStatus.add(getGenericProperty(componentNode, "error", "fileReference"));
                imageStatus.add(getGenericProperty(componentNode, "off", "fileReference"));

                imagesStatusUrls.add(getGenericProperty(componentNode, "off", "offUrl"));
                imagesStatusUrls.add(getGenericProperty(componentNode, "prev", "prevUrl"));
                imagesStatusUrls.add(getGenericProperty(componentNode, "post", "postUrl"));
                imagesStatusUrls.add(getGenericProperty(componentNode, "error", "errorUrl"));

            }

            Node jcrContent = null;
            if (!session.nodeExists(nodePath+"/content-list-multi-vods.xml/jcr:content/")){
                Node node = session.getNode(nodePath);
                Node xmlNode = node.addNode("content-list-multi-vods.xml", "nt:file");
                jcrContent = xmlNode.addNode("jcr:content","nt:resource");
            } else {
                jcrContent = session.getNode(nodePath+"/content-list-multi-vods.xml/jcr:content/");
            }
            if(jcrContent != null && !xmlString.isEmpty()){
                jcrContent.setProperty("jcr:data", xmlString);
                session.save();
                existConfigXML = true;
            }

            // Create the config-multi-vods.xml file
            String config = "";
            if (existConfigXML) {
                config = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
                config += "<application>";
                config += "<player multi_feed_url=\""+contentXMLPath+"\" multi_feed_interval=\"86400\" multi_allow_duplicate_streams=\"false\" multi_startup_streams=\"1241186546001;\" multi_startup_view_mode=\"1\" locale_setting=\"es\" core_ads_enabled=\"true\" dfp_tag_url=\"http://ad.doubleclick.net/adx/es.esmas.video_embed/usa;tile=9;sz=1x1;ord=5208797005470842;dcmt=text/html;zone0=usa;roll=pre;pais=mex;estado=df;ciudad=mexico%20city;id_video=182018;status=nooficial?callback=cbResponse\" eventmanagementstates_enabled=\"true\" eventmanagementstates_status_url=\""+ playerStatePath +"\" eventmanagementstates_status_interval=\"60\" live_timestamp_mode=\"1\" live_timestamp_source=\"stream\" live_timestamp_timezone_offset=\"-4\" live_timestamp_timezone_string=\"EDT\" branding_preload=\"none\" core_player_name=\"multi\" domain=\"projects.mediadev.edgesuite.net\" embed_domain=\"projects.mediadev.edgesuite.net\" auto_play=\"true\" fullscreen_enabled=\"true\" volume=\"75\" show_sharing_overlay=\"false\" show_email_overlay=\"false\" show_autoplay_overlay=\"false\" use_netsession_client=\"false\" netsession_install_prompt_frequency_secs=\"-1\" auto_play_list=\"true\" ad_autoplay_btn_enabled=\"false\" ad_control_enabled=\"false\" live_stream_flag=\"live\" ad_server_timeout=\"10\" scale_view_to_fit=\"true\" pip_enabled=\"true\" js_callback=\"AEP.jsCallbackHandler\"></player>";
                config += "<plugins>";
                config += "<plugin host=\"osmf\" type=\"dynamic\" absolute=\"true\" id=\"AkamaiAdvancedStreamingPlugin\">";
                config += "http://players.edgesuite.net/flash/plugins/osmf/advanced-streaming-plugin/v2.10/osmf1.6/AkamaiAdvancedStreamingPlugin.swf";
                config += "</plugin>";
                config += "<plugin host=\"osmf\" type=\"static\" id=\"OSMFCSMALoader\">com.akamai.playeranalytics.osmf.OSMFCSMALoaderInfo</plugin>";
                config += "</plugins>";
                config += "<view>";
                config += "<!-- VIEW ITEMS DISABLED FOR THIS PLAYER -->";
                config += "<element id=\"infoOverlay\" style=\"bottom: 0px;text-align:right;\" adEnabled=\"true\"/>";
                config += "</view>";
                config += "<share></share>";
                config += "<admedia>";
                config += "<property key=\"AD_SITE\">televisa</property>";
                config += "<vendor id=\"dfp\">";
                config += "<!--  FULLY FORMED BASE URL FOR TESTING.  -->";
                config += "<property key=\"DFP_BASE_URL\">";
                config += "http://ad.doubleclick.net/adx/es.esmas.video_embed/usa;tile=9;sz=1x1;ord=5208797005470842;dcmt=text/html;zone0=usa;roll=pre;pais=mex;estado=df;ciudad=mexico%20city;id_video=182018;status=nooficial?callback=cbResponse";
                config += "</property>";
                config += "<property key=\"DFP_DELIMITER\">;</property>";
                config += "<property key=\"DFP_SIZE\">300x240</property>";
                config += "</vendor>";
                config += "</admedia>";
                config += "<metrics>";
                config += "<vendor id=\"akamai\">";
                config += "<property key=\"MEDIA_ANALYTICS_BEACON\">";
                config += "http://ma277-r.analytics.edgesuite.net/config/beacon-3527.xml";
                config += "</property>";
                config += "<dimensions default=\"N/A\">";
                config += "<property key=\"category\" mapType=\"key\">matchName</property>";
                config += "<property key=\"eventName\" mapType=\"key\">cameraAngle</property>";
                config += "</dimensions>";
                config += "</vendor>";
                config += "<vendor id=\"comscore\">";
                config += "<event key=\"adStart\">";
                config += "<property key=\"COMSCORE_CSURL\">http://b.scorecardresearch.com/b</property>";
                config += "<property key=\"COMSCORE_CSC1\">1</property>";
                config += "<property key=\"COMSCORE_CSC2\">0000000</property>";
                config += "<property key=\"COMSCORE_CSC3\">Customer</property>";
                config += "<property key=\"COMSCORE_CSC4\"/>";
                config += "<property key=\"COMSCORE_CSC5\">09</property>";
                config += "<property key=\"COMSCORE_CSC6\"/>";
                config += "<property key=\"COMSCORE_CSC7\"/>";
                config += "<property key=\"COMSCORE_CSC8\"/>";
                config += "<property key=\"COMSCORE_CSC9\"/>";
                config += "<property key=\"COMSCORE_CSC10\"/>";
                config += "<property key=\"COMSCORE_CSC14\"/>";
                config += "</event>";
                config += "<event key=\"videoStart\">";
                config += "<property key=\"COMSCORE_CSURL\">http://b.scorecardresearch.com/b</property>";
                config += "<property key=\"COMSCORE_CSC1\">1</property>";
                config += "<property key=\"COMSCORE_CSC2\">0000000</property>";
                config += "<property key=\"COMSCORE_CSC3\">Customer</property>";
                config += "<property key=\"COMSCORE_CSC4\"/>";
                config += "<property key=\"COMSCORE_CSC5\">02</property>";
                config += "<property key=\"COMSCORE_CSC6\"/>";
                config += "<property key=\"COMSCORE_CSC7\"/>";
                config += "<property key=\"COMSCORE_CSC8\"/>";
                config += "<property key=\"COMSCORE_CSC9\"/>";
                config += "<property key=\"COMSCORE_CSC10\"/>";
                config += "<property key=\"COMSCORE_CSC14\"/>";
                config += "</event>";
                config += "</vendor>";
                config += "</metrics>";
                config += "<locales>";
                config += "<locale id=\"en\">";
                config += "<property key=\"MSG_EMAIL_TO\">To</property>";
                config += "<property key=\"MSG_EMAIL_FROM\">From</property>";
                config += "<property key=\"MSG_EMAIL_VIDEO\">Email this video</property>";
                config += "<property key=\"MSG_EMAIL_MESSAGE_DEFAULT\">Check out this video from xxx</property>";
                config += "<property key=\"MSG_EMAIL_MESSAGE\">Message</property>";
                config += "<property key=\"MSG_EMAIL_ADDRESS_INVALID\">Invalid Email Address</property>";
                config += "<property key=\"MSG_EMAIL_MESSAGE_INVALID\">";
                config += "Please limit your message to 500 characters or less.";
                config += "</property>";
                config += "<property key=\"MSG_EMAIL_CHARACTERS_REMAINING_TEXT\">characters left</property>";
                config += "<property key=\"MSG_EMAIL_SEND_FAILURE\">Message could not be sent.</property>";
                config += "<property key=\"MSG_EMAIL_SEND_SUCCESS_MESSAGE\">Your email has been sent!</property>";
                config += "<property key=\"MSG_SHARE_VIDEO_TEXT\">Share this video...</property>";
                config += "<property key=\"MSG_POST_TEXT\">Post</property>";
                config += "<property key=\"MSG_EMBED_TEXT\">Embed</property>";
                config += "<property key=\"MSG_LINK_TEXT\">Link</property>";
                config += "<property key=\"MSG_SHARE_CONNECT_FAILURE\">Unable to connect. Please try again.</property>";
                config += "<property key=\"MSG_SHARE_CONTENT_DISABLED\">Share and embed are disabled.</property>";
                config += "<property key=\"MSG_VERSION_TEXT\">Version</property>";
                config += "<property key=\"MSG_BUFFERING_TEXT\">buffering</property>";
                config += "<property key=\"MSG_CUSTOMIZE_CLIP_POINTS\">Customize the start and end point of the video.</property>";
                config += "<property key=\"MSG_PAUSE\">Pause</property>";
                config += "<property key=\"MSG_PREVIEW\">Preview</property>";
                config += "<property key=\"MSG_CURRENT\">Currrent</property>";
                config += "<property key=\"MSG_SEEK_TO\">Seek to</property>";
                config += "<property key=\"MSG_LIVE\">LIVE</property>";
                config += "<property key=\"MSG_DEFAULT_ERROR_MESSAGE\">";
                config += "Sorry, we were unable to play the media you selected. Please try again, or select alternate media.";
                config += "</property>";
                config += "<property key=\"MSG_ERROR_PREFIX\">Error encountered:</property>";
                config += "<property key=\"MSG_STREAM_NOT_FOUND\">Stream not found</property>";
                config += "<property key=\"MSG_CURRENT_WORKING_BANDWIDTH\">Current working bandwidth</property>";
                config += "<property key=\"MSG_CURRENT_BITRATE_PLAYING\">Current bitrate playing</property>";
                config += "<property key=\"MSG_MAX_BITRATE_AVAILABLE\">Max bitrate available</property>";
                config += "<property key=\"MSG_NOT_AVAILABLE\">Not Available</property>";
                config += "<property key=\"MSG_GO_LIVE\">GO LIVE</property>";
                config += "<property key=\"MSG_REPLAY\">Replay</property>";
                config += "<property key=\"MSG_MEDIA_ITEM_INVALID\">Media item invalid</property>";
                config += "<property key=\"MSG_STREAM_ENCOUNTERED_ERROR\">This stream has encountered an error.</property>";
                config += "<property key=\"MSG_STREAM_ENDED\">This stream has ended.</property>";
                config += "<property key=\"MSG_SELECT_STREAM\">Click here to select a stream.</property>";
                config += "<property key=\"MSG_EMAIL_CHARACTERS_REMAINING_TEXT\">characters left</property>";
                config += "<property key=\"MSG_NEXT_VIDEO_STARTS\">Next video starts in:</property>";
                config += "<property key=\"MSG_TOKEN_SERVICE_NOT_AVAILABLE\">token service not available</property>";
                config += "<property key=\"MSG_ERROR\">error:</property>";
                config += "<property key=\"MSG_SELECT_CHANNEL\">Select Channel</property>";
                config += "<property key=\"MSG_DRAG\">Drag</property>";
                config += "<property key=\"MSG_SWAP\">Swap</property>";
                config += "<property key=\"MSG_CHANGE\">Change</property>";
                config += "<property key=\"MSG_LIVE\">Live</property>";
                config += "<property key=\"MSG_GOLIVE\">Go Live</property>";
                config += "<property key=\"MSG_ADDPIP\">Add PIP</property>";
                config += "<property key=\"MSG_VIEW\">View</property>";
                config += "</locale>";
                config += "<locale id=\"es\">";
                config += "<property key=\"MSG_EMAIL_TO\">a</property>";
                config += "<property key=\"MSG_EMAIL_FROM\">de</property>";
                config += "<property key=\"MSG_EMAIL_VIDEO\">Enviar este vídeo</property>";
                config += "<property key=\"MSG_EMAIL_MESSAGE_DEFAULT\">Echa un vistazo a este video de xxx</property>";
                config += "<property key=\"MSG_EMAIL_MESSAGE\">mensaje</property>";
                config += "<property key=\"MSG_EMAIL_ADDRESS_INVALID\">Dirección de correo electrónico no válida</property>";
                config += "<property key=\"MSG_EMAIL_MESSAGE_INVALID\">";
                config += "Por favor limite su mensaje a 500 caracteres o menos.";
                config += "</property>";
                config += "<property key=\"MSG_EMAIL_CHARACTERS_REMAINING_TEXT\">personajes de la izquierda</property>";
                config += "<property key=\"MSG_EMAIL_SEND_FAILURE\">El mensaje no pudo ser enviado.</property>";
                config += "<property key=\"MSG_EMAIL_SEND_SUCCESS_MESSAGE\">Tu email ha sido enviado!</property>";
                config += "<property key=\"MSG_SHARE_VIDEO_TEXT\">Comparte este vídeo...</property>";
                config += "<property key=\"MSG_POST_TEXT\">enviar</property>";
                config += "<property key=\"MSG_EMBED_TEXT\">incrustar</property>";
                config += "<property key=\"MSG_LINK_TEXT\">enlace</property>";
                config += "<property key=\"MSG_SHARE_CONNECT_FAILURE\">";
                config += "No se puede conectar. Por favor, inténtelo de nuevo.";
                config += "</property>";
                config += "<property key=\"MSG_SHARE_CONTENT_DISABLED\">Compartir e incrustar están desactivados.</property>";
                config += "<property key=\"MSG_VERSION_TEXT\">versión</property>";
                config += "<property key=\"MSG_BUFFERING_TEXT\">Buffering</property>";
                config += "<property key=\"MSG_CUSTOMIZE_CLIP_POINTS\">Personalizar el inicio y el punto final del video.</property>";
                config += "<property key=\"MSG_PAUSE\">romper</property>";
                config += "<property key=\"MSG_PREVIEW\">vista previa</property>";
                config += "<property key=\"MSG_CURRENT\">corriente</property>";
                config += "<property key=\"MSG_SEEK_TO\">Tratar de</property>";
                config += "<property key=\"MSG_LIVE\">EN VIVO</property>";
                config += "<property key=\"MSG_DEFAULT_ERROR_MESSAGE\">";
                config += "Lo sentimos, no hemos podido jugar los medios de comunicación seleccionados. Por favor, inténtelo de nuevo, o seleccionar los medios de comunicación alternativos.";
                config += "</property>";
                config += "<property key=\"MSG_ERROR_PREFIX\">Se produjo un error:</property>";
                config += "<property key=\"MSG_STREAM_NOT_FOUND\">Stream que no se encuentra</property>";
                config += "<property key=\"MSG_CURRENT_WORKING_BANDWIDTH\">Ancho de banda actual de trabajo</property>";
                config += "<property key=\"MSG_CURRENT_BITRATE_PLAYING\">Tasa de bits de reproducción actual</property>";
                config += "<property key=\"MSG_MAX_BITRATE_AVAILABLE\">Tasa de bits máxima disponible</property>";
                config += "<property key=\"MSG_NOT_AVAILABLE\">No está disponible</property>";
                config += "<property key=\"MSG_GO_LIVE\">IR A EN VIVO</property>";
                config += "<property key=\"MSG_REPLAY\">Repetir</property>";
                config += "<property key=\"MSG_MEDIA_ITEM_INVALID\">Medios elemento no válido</property>";
                config += "<property key=\"MSG_STREAM_ENCOUNTERED_ERROR\">Esta señal ha encontrado un error.</property>";
                config += "<property key=\"MSG_STREAM_ENDED\">Esta corriente ha terminado.</property>";
                config += "<property key=\"MSG_SELECT_STREAM\">Haga clic aquí para elegir una señal.</property>";
                config += "<property key=\"MSG_EMAIL_CHARACTERS_REMAINING_TEXT\">personajes de la izquierda</property>";
                config += "<property key=\"MSG_NEXT_VIDEO_STARTS\">Siguiente video comienza en:</property>";
                config += "<property key=\"MSG_TOKEN_SERVICE_NOT_AVAILABLE\">servicio de token no está disponible</property>";
                config += "<property key=\"MSG_ERROR\">error:</property>";
                config += "<property key=\"MSG_SELECT_CHANNEL\">Seleccione el canal</property>";
                config += "<property key=\"MSG_DRAG\">Arrastrar</property>";
                config += "<property key=\"MSG_SWAP\">Intercambiar</property>";
                config += "<property key=\"MSG_CHANGE\">Cambiar</property>";
                config += "<property key=\"MSG_LIVE\">EN VIVO</property>";
                config += "<property key=\"MSG_GOLIVE\">IR A EN VIVO</property>";
                config += "<property key=\"MSG_VIEW\">Ver</property>";
                config += "<property key=\"MSG_ADDPIP\">Añadir Señal</property>";
                config += "</locale>";
                config += "</locales>";
                config += "<eventmanagementstates>";
                config += "<states>";

                config += "<state>";
                config += "<property key=\"ID\">pre</property>";
                config += "<property key=\"POSTER_SRC\">";
                config += imageStatus.get(0).toString();
                config += "</property>";
                config += "<property key=\""+imagesStatusUrls.get(0).toString()+"\"/>";
                config += "</state>";

                config += "<state>";
                config += "<property key=\"ID\">post</property>";
                config += "<property key=\"POSTER_SRC\">";
                config += imageStatus.get(1).toString();
                config += "</property>";
                config += "<property key=\""+imagesStatusUrls.get(1).toString()+"\"/>";
                config += "</state>";

                config += "<state>";
                config += "<property key=\"ID\">error</property>";
                config += "<property key=\"POSTER_SRC\">";
                config += imageStatus.get(2).toString();
                config += "</property>";
                config += "<property key=\""+imagesStatusUrls.get(2).toString()+"\"/>";
                config += "</state>";

                config += "<state>";
                config += "<property key=\"ID\">off</property>";
                config += "<property key=\"POSTER_SRC\">";
                config += imageStatus.get(3).toString();
                config += "</property>";
                config += "<property key=\""+imagesStatusUrls.get(3).toString()+"\"/>";
                config += "</state>";

                config += "</states>";
                config += "</eventmanagementstates>";
                config += "</application>";
            }

            if (existConfigXML) {
                Node configjcrContent = null;
                if (!session.nodeExists(nodePath+"/config-multi-vods.xml/jcr:content/")){
                    Node node = session.getNode(nodePath);
                    Node xmlNode = node.addNode("config-multi-vods.xml", "nt:file");
                    configjcrContent = xmlNode.addNode("jcr:content","nt:resource");
                } else {
                    configjcrContent = session.getNode(nodePath+"/config-multi-vods.xml/jcr:content/");
                }
                if(configjcrContent != null){
                    configjcrContent.setProperty("jcr:data",config);
                    session.save();
                }
            }
            // End creating config-multi-vods.xml file
            session.logout();
        }catch(Exception e) {
            LOGGER.info("******** ERROR EN MULTISTREAM: " + e.getMessage());
        }

    }

    public String getGenericProperty(Node node, String childrenNodeName, String property){

        String value = "";
        try{
            if(node.hasNode(childrenNodeName)){
                Node childrenNode = node.getNode(childrenNodeName);
                if(childrenNode.hasProperty(property)){
                    value = childrenNode.getProperty(property).getString();
                }
            }
        } catch (RepositoryException e){
            LOGGER.error(e.getMessage());
        }
        return value;
    }


}
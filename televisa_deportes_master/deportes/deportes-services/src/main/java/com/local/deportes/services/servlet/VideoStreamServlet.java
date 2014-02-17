package com.local.deportes.services.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.foundation.Image;

/**
 * Created with IntelliJ IDEA.
 * User: jdiaz86
 * Date: 12/5/13
 * Time: 9:35 AM
 * To change this template use File | Settings | File Templates.
 */


/**
 * This servlet is used for get the meeting rooms availability by day.
 *
 * Author: jbarrera
 * Date:   30.07.2013
 * @scr.component
 * @scr.service
 * @scr.property name="sling.servlet.methods" value="GET"
 * @scr.property name="sling.servlet.paths" value="/bin/generics/videostream/xml"
 */
public class VideoStreamServlet extends SlingAllMethodsServlet {

	protected final Logger LOG = LoggerFactory.getLogger(VideoSearchServlet.class);

	/** @scr.reference */
	SlingRepository repository;

	/** @scr.reference */
	ResourceResolverFactory resourceResolverFactory;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

		LOG.info("INICIA DOGET");
		response.setContentType("text/html");

		try{
			Session session = repository.loginAdministrative(null);

			LOG.info("Session " + session);

			Map authInfo = new HashMap();
			authInfo.put(JcrResourceConstants.AUTHENTICATION_INFO_SESSION,session);
			ResourceResolver resourceResolver = resourceResolverFactory.getResourceResolver(authInfo);

			LOG.info("resourceResolver " + resourceResolver);

			// get page manager
			PageManager pageManager =  resourceResolver.adaptTo(PageManager.class);

			LOG.info("pageManager " + pageManager);

			String pagePath = request.getParameter("currentPage").toString();
			String nodePath = request.getParameter("currentNode").toString();
			
			String[] posters = new String[4];
			String[] urls = new String[4];
			String status = "off";
			boolean modifyPlayerState = false;

			if (session.nodeExists(nodePath)){
				Node componentNode = session.getNode(nodePath);

				Image imgOff;
				Image imgPrev;
				Image imgPost;
				Image imgError;

				String imgOffSrc = "#";
			    String imgPrevSrc = "#";
			    String imgPostSrc = "#";
			    String imgErrorSrc = "#";

			    //get the resources
			    //POSTER PRE
			    Resource resImgPrev = resourceResolver.resolve(nodePath + "/prev");
			    if (resImgPrev != null) {
			        imgPrev= new Image(resImgPrev);
			        if (imgPrev != null && imgPrev.hasContent()) {
						imgPrev.setSelector(".img");
						imgPrevSrc = imgPrev.getFileReference();
			        }
			    }
			    posters[0] = imgPrevSrc;
			    
			    //POSTER POST
			    Resource resImgPost = resourceResolver.resolve(nodePath + "/post");
			    if (resImgPost != null) {
			        imgPost= new Image(resImgPost);
			        if (imgPost != null && imgPost.hasContent()) {
						imgPost.setSelector(".img");
						imgPostSrc = imgPost.getFileReference();
			        }
			    }
			    posters[1] = imgPostSrc;
			    
			    //POSTER ERROR
			    Resource resImgError = resourceResolver.resolve(nodePath + "/error");
			    if (resImgError != null) {
			        imgError= new Image(resImgError);
			        if (imgError != null && imgError.hasContent()) {
						imgError.setSelector(".img");
						imgErrorSrc = imgError.getFileReference();
			        }
			    }
			    posters[2] = imgErrorSrc;
			    
			    //POSTER OFF
				Resource resImgOff = resourceResolver.resolve(nodePath + "/off");
			    if (resImgOff != null) {
			        imgOff= new Image(resImgOff);
			        if (imgOff != null && imgOff.hasContent()) {
						imgOff.setSelector(".img");
						imgOffSrc = imgOff.getFileReference();
			        }
			    }
			    posters[3] = imgOffSrc;
				
			    
			    //URL PREV
			    String urlPrev = "#";
				if(componentNode.hasProperty("prevUrl")){
					Value value = componentNode.getProperty("prevUrl").getValue();
					if(value != null && value.getString() != null){
						urlPrev = value.getString();
					}
				}
				urls[0] = urlPrev;
				
				//URL POST 
				String urlPost = "#";
				if(componentNode.hasProperty("postUrl")){
					Value value = componentNode.getProperty("postUrl").getValue();
					if(value != null && value.getString() != null){
						urlPost	= value.getString();
					}
				}
				urls[1] = urlPost;
				
				//URL ERROR
				String urlError = "#";
				if(componentNode.hasProperty("errorUrl")){
					Value value = componentNode.getProperty("errorUrl").getValue();
					if(value != null && value.getString() != null){
						urlError = value.getString();
					}
				}
				urls[2] = urlError;
				
				//URL OFF
				String urlOff = "#";
				if(componentNode.hasProperty("offUrl")){
					Value value = componentNode.getProperty("offUrl").getValue();
					if(value != null && value.getString() != null){
						urlOff = value.getString();
					}
				}
				urls[3] = urlOff;
				
				//STATUS
				if(componentNode.hasProperty("status")){
					Value value = componentNode.getProperty("status").getValue();
					if(value != null && value.getString() != null){
						status = componentNode.getProperty("status").getString();
						modifyPlayerState = true;	
					}
				}
			}

			Node node = null;
			if (session.nodeExists(pagePath)){
				node = session.getNode(pagePath + "/jcr:content/livestream");

				LOG.info("Node: " + node.getPath());

				Node jcrContent = null;
				if (!node.hasNode("config_hds.xml")) {
					Node newNode = node.addNode("config_hds.xml","nt:file");
					jcrContent = newNode.addNode("jcr:content","nt:resource");
					jcrContent.setProperty("jcr:mimeType","application/xml");
				} else {
					jcrContent = node.getNode("config_hds.xml/jcr:content");
				}

				Node jcrContentText = null;
				if (!node.hasNode("playerstate.txt")) {
					Node newNode = node.addNode("playerstate.txt","nt:file");
					jcrContentText = newNode.addNode("jcr:content","nt:resource");
					jcrContentText.setProperty("jcr:mimeType","application/application/octet-stream");
				} else {
					jcrContentText = node.getNode("playerstate.txt/jcr:content");
				}

				DocumentBuilderFactory builderFactory =DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
				//creating a new instance of a DOM to build a DOM tree.
				Document doc = docBuilder.newDocument();

				String data = fillXml(doc,posters, urls, node.getPath().replaceAll("jcr:content", "_jcr_content"));
				jcrContent.setProperty("jcr:data",data);
				if(modifyPlayerState){
					jcrContentText.setProperty("jcr:data",status);	
				}
				session.save();
				session.logout();
			}
		}catch(javax.xml.transform.TransformerException e){
			LOG.info("TransExce " + e.getMessage());
			e.printStackTrace();
		}catch(FileNotFoundException e){
			LOG.info("FileNotFound " + e.getMessage());
			e.printStackTrace();
		}catch(IOException e){
			LOG.info("IOExcep " + e.getMessage());
			e.printStackTrace();
		}catch(RepositoryException e){
			LOG.info("RepoExp " + e.getMessage());
			LOG.info("RepoExp2 " , e);
			e.printStackTrace();
		}catch(ParserConfigurationException e){
			LOG.info("ParserConf " + e.getMessage());
			e.printStackTrace();
		}catch(LoginException e){
			LOG.info("LoginEx " + e.getMessage());
			e.printStackTrace();
		}
	}

	public String fillXml(Document doc, String[] urls, String[] posters, String nodePath) throws javax.xml.transform.TransformerException, FileNotFoundException, IOException {
		//This method creates an element node
		Element application = doc.createElement("application");
		//adding a node after the last child node of the specified node.
		doc.appendChild(application);

		//Application childs
		Element player = doc.createElement("player");
		player.setAttribute("auto_play", "true");
		player.setAttribute("volume", "50");
		player.setAttribute("start_bitrate_index", "-1");
		player.setAttribute("auto_replay", "false");
		player.setAttribute("dvr_enabled", "0");
		player.setAttribute("rewind_interval", "15");
		player.setAttribute("media_analytics_logging_enabled", "false");
		player.setAttribute("set_resume_dvr_at_live", "false");
		player.setAttribute("js_callback", "AEP.jsCallbackHandler");
		player.setAttribute("eventmanagementstates_enabled", "true");
		player.setAttribute("eventmanagementstates_status_url", nodePath + "/playerstate.txt");
		player.setAttribute("eventmanagementstates_status_interval", "300");
		player.setAttribute("locale_setting", "es");

		application.appendChild(player);

		Element view = doc.createElement("view");
		application.appendChild(view);

		//View childs
		Element element = doc.createElement("element");
		element.setAttribute("id", "controls");
		element.setAttribute("visible", "true");
		element.setAttribute("autoHide", "5");
		element.setAttribute("height", "5");
		view.appendChild(element);

		//Element childs
		Element elementChild = doc.createElement("element");
		elementChild.setAttribute("id", "replay");
		element.appendChild(elementChild);
		elementChild = doc.createElement("element");
		elementChild.setAttribute("id", "progress");
		elementChild.setAttribute("color", "#CCCCCC");
		element.appendChild(elementChild);
		elementChild = doc.createElement("element");
		elementChild.setAttribute("id", "loaded");
		elementChild.setAttribute("color", "#003366");
		element.appendChild(elementChild);
		elementChild = doc.createElement("element");
		elementChild.setAttribute("id", "scrubber");
		element.appendChild(elementChild);
		elementChild = doc.createElement("element");
		elementChild.setAttribute("id", "stats");
		element.appendChild(elementChild);
		elementChild = doc.createElement("element");
		elementChild.setAttribute("id", "hdclient");
		element.appendChild(elementChild);
		elementChild = doc.createElement("element");
		elementChild.setAttribute("id", "volume");
		elementChild.setAttribute("color", "#CCCCCC");
		element.appendChild(elementChild);
		elementChild = doc.createElement("element");
		elementChild.setAttribute("id", "fullscreen");
		element.appendChild(elementChild);


		Element plugins = doc.createElement("plugins");
		Comment comment = doc.createComment("CONSULT AKAMAI REGARDING PLUG-IN MODIFICATIONS. DO NOT MODIFY WITHOUT SUPPORT FROM AKAMAI");
		plugins.appendChild(comment);
		application.appendChild(plugins);

		//plugin Childs
		Element plugin = doc.createElement("plugin");
		plugin.setAttribute("host", "osmf");
		plugin.setAttribute("type", "dynamic");
		plugin.setAttribute("absolute", "true");
		plugin.setAttribute("id", "AkamaiAdvancedStreamingPlugin");
		Text pluginText = doc.createTextNode("http://players.edgesuite.net/flash/plugins/osmf/advanced-streaming-plugin/v2.11/osmf1.6/AkamaiAdvancedStreamingPlugin.swf");
		plugin.appendChild(pluginText);
		plugins.appendChild(plugin);
		plugin = doc.createElement("plugin");
		plugin.setAttribute("host", "osmf");
		plugin.setAttribute("type", "static");
		plugin.setAttribute("id", "OSMFCSMALoader");
		pluginText = doc.createTextNode("com.akamai.playeranalytics.osmf.OSMFCSMALoaderInfo");
		plugin.appendChild(pluginText);
		plugins.appendChild(plugin);

		Element eventmanagementstates = doc.createElement("eventmanagementstates");
		application.appendChild(eventmanagementstates);

		Element states = doc.createElement("states");
		eventmanagementstates.appendChild(states);

		//state pre
		Element state = doc.createElement("state");
		states.appendChild(state);

		Element property = doc.createElement("property");
		property.setAttribute("key", "ID");
		Text propertyText = doc.createTextNode("pre");
		property.appendChild(propertyText);
		state.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","POSTER_SRC");
		propertyText = doc.createTextNode(urls[0]);   //author
		property.appendChild(propertyText);
		state.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","URL");
		propertyText = doc.createTextNode(posters[0]);   //author
		property.appendChild(propertyText);
		state.appendChild(property);

		//state post
		state = doc.createElement("state");
		states.appendChild(state);

		property = doc.createElement("property");
		property.setAttribute("key", "ID");
		propertyText = doc.createTextNode("post");
		property.appendChild(propertyText);
		state.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","POSTER_SRC");
		propertyText = doc.createTextNode(urls[1]);   //author
		property.appendChild(propertyText);
		state.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","URL");
		propertyText = doc.createTextNode(posters[1]);   //author
		property.appendChild(propertyText);
		state.appendChild(property);

		//state error
		state = doc.createElement("state");
		states.appendChild(state);

		property = doc.createElement("property");
		property.setAttribute("key", "ID");
		propertyText = doc.createTextNode("error");
		property.appendChild(propertyText);
		state.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","POSTER_SRC");
		propertyText = doc.createTextNode(urls[2]);   //author
		property.appendChild(propertyText);
		state.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","URL");
		propertyText = doc.createTextNode(posters[2]);   //author
		property.appendChild(propertyText);
		state.appendChild(property);

		//state off
		state = doc.createElement("state");
		states.appendChild(state);

		property = doc.createElement("property");
		property.setAttribute("key", "ID");
		propertyText = doc.createTextNode("off");
		property.appendChild(propertyText);
		state.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","POSTER_SRC");
		propertyText = doc.createTextNode(urls[3]);   //author
		property.appendChild(propertyText);
		state.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","URL");
		propertyText = doc.createTextNode(posters[3]);   //author
		property.appendChild(propertyText);
		state.appendChild(property);

		Element locales = doc.createElement("locales");
		application.appendChild(locales);

		Element locale = doc.createElement("locale");
		locale.setAttribute("id","en");
		locales.appendChild(locale);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_TO");
		propertyText = doc.createTextNode("To");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_FROM");
		propertyText = doc.createTextNode("From");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_VIDEO");
		propertyText = doc.createTextNode("Email this video");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_MESSAGE_DEFAULT");
		propertyText = doc.createTextNode("Check out this video from xxx");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_MESSAGE");
		propertyText = doc.createTextNode("Message");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_ADDRESS_INVALID");
		propertyText = doc.createTextNode("Invalid Email Address");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_MESSAGE_INVALID");
		propertyText = doc.createTextNode("Please limit your message to 500 characters or less.");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_CHARACTERS_REMAINING_TEXT");
		propertyText = doc.createTextNode("characters left");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_SEND_FAILURE");
		propertyText = doc.createTextNode("Message could not be sent.");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_SEND_SUCCESS_MESSAGE");
		propertyText = doc.createTextNode("Your email has been sent!");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_SHARE_VIDEO_TEXT");
		propertyText = doc.createTextNode("Share this video...");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_POST_TEXT");
		propertyText = doc.createTextNode("Post");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMBED_TEXT");
		propertyText = doc.createTextNode("Embed");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_LINK_TEXT");
		propertyText = doc.createTextNode("Link");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_SHARE_CONNECT_FAILURE");
		propertyText = doc.createTextNode("Unable to connect. Please try again.");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_SHARE_CONTENT_DISABLED");
		propertyText = doc.createTextNode("Share and embed are disabled.");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_VERSION_TEXT");
		propertyText = doc.createTextNode("Version");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_BUFFERING_TEXT");
		propertyText = doc.createTextNode("buffering");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_CUSTOMIZE_CLIP_POINTS");
		propertyText = doc.createTextNode("Customize the start and end point of the video.");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_PAUSE");
		propertyText = doc.createTextNode("Pause");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_PREVIEW");
		propertyText = doc.createTextNode("Preview");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_CURRENT");
		propertyText = doc.createTextNode("Currrent");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_SEEK_TO");
		propertyText = doc.createTextNode("Seek to");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_LIVE");
		propertyText = doc.createTextNode("LIVE");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_DEFAULT_ERROR_MESSAGE");
		propertyText = doc.createTextNode("Sorry, we were unable to play the media you selected. Please try again, or select alternate media.");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_ERROR_PREFIX");
		propertyText = doc.createTextNode("Error encountered:");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_STREAM_NOT_FOUND");
		propertyText = doc.createTextNode("Stream not found");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_CURRENT_WORKING_BANDWIDTH");
		propertyText = doc.createTextNode("Current working bandwidth");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_CURRENT_BITRATE_PLAYING");
		propertyText = doc.createTextNode("Current bitrate playing");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_MAX_BITRATE_AVAILABLE");
		propertyText = doc.createTextNode("Max bitrate available");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_NOT_AVAILABLE");
		propertyText = doc.createTextNode("Not Available");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_REPLAY");
		propertyText = doc.createTextNode("Replay");
		property.appendChild(propertyText);
		locale.appendChild(property);

		locale = doc.createElement("locale");
		locale.setAttribute("id","es");
		locales.appendChild(locale);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_TO");
		propertyText = doc.createTextNode("A");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_FROM");
		propertyText = doc.createTextNode("De");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_VIDEO");
		propertyText = doc.createTextNode("Enviar este vídeo");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_MESSAGE_DEFAULT");
		propertyText = doc.createTextNode("Echa un vistazo al vídeo que te envía xxx");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_MESSAGE");
		propertyText = doc.createTextNode("Mensaje");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_ADDRESS_INVALID");
		propertyText = doc.createTextNode("Dirección de correo electrónico no válida");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_MESSAGE_INVALID");
		propertyText = doc.createTextNode("Por favor limite su mensaje a 500 caracteres o menos.");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_CHARACTERS_REMAINING_TEXT");
		propertyText = doc.createTextNode("caracteres disponibles");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_SEND_FAILURE");
		propertyText = doc.createTextNode("El mensaje no pudo ser enviado.");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMAIL_SEND_SUCCESS_MESSAGE");
		propertyText = doc.createTextNode("Tu email ha sido enviado!");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_SHARE_VIDEO_TEXT");
		propertyText = doc.createTextNode("Comparte este vídeo...");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_POST_TEXT");
		propertyText = doc.createTextNode("Enviar");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_EMBED_TEXT");
		propertyText = doc.createTextNode("Incrustar");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_LINK_TEXT");
		propertyText = doc.createTextNode("Enlazar");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_SHARE_CONNECT_FAILURE");
		propertyText = doc.createTextNode("No se puede conectar. Por favor, inténtelo de nuevo.");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_SHARE_CONTENT_DISABLED");
		propertyText = doc.createTextNode("Las opciones Compartir e Incrustar están desactivadas.");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_VERSION_TEXT");
		propertyText = doc.createTextNode("Versión");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_BUFFERING_TEXT");
		propertyText = doc.createTextNode("Cargando");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_CUSTOMIZE_CLIP_POINTS");
		propertyText = doc.createTextNode("Personalizar el tiempo de comienzo y finalización del video.");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_PAUSE");
		propertyText = doc.createTextNode("Pausa");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_PREVIEW");
		propertyText = doc.createTextNode("Vista previa");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_CURRENT");
		propertyText = doc.createTextNode("Actual");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_SEEK_TO");
		propertyText = doc.createTextNode("Ir a");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_LIVE");
		propertyText = doc.createTextNode("En Vivo");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_DEFAULT_ERROR_MESSAGE");
		propertyText = doc.createTextNode("Lo sentimos, no hemos podido reproducir los elementos seleccionados. Por favor, inténtelo de nuevo, o seleccione un fichero/stream alternativo.");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_ERROR_PREFIX");
		propertyText = doc.createTextNode("Se produjo un error:");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_STREAM_NOT_FOUND");
		propertyText = doc.createTextNode("Stream no encontrado");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_CURRENT_WORKING_BANDWIDTH");
		propertyText = doc.createTextNode("Ancho de banda actual");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_CURRENT_BITRATE_PLAYING");
		propertyText = doc.createTextNode("Bitrate actual");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_MAX_BITRATE_AVAILABLE");
		propertyText = doc.createTextNode("Máximo bitrate disponible");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_NOT_AVAILABLE");
		propertyText = doc.createTextNode("No está disponible");
		property.appendChild(propertyText);
		locale.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","MSG_REPLAY");
		propertyText = doc.createTextNode("Repetir");
		property.appendChild(propertyText);
		locale.appendChild(property);

		Element metrics = doc.createElement("metrics");
		application.appendChild(metrics);

		Element vendor = doc.createElement("vendor");
		vendor.setAttribute("id","akamai");
		metrics.appendChild(vendor);
		property = doc.createElement("property");
		property.setAttribute("key","MEDIA_ANALYTICS_BEACON");
		propertyText = doc.createTextNode("http://ma277-r.analytics.edgesuite.net/config/beacon-3837.xml");
		property.appendChild(propertyText);
		vendor.appendChild(property);

		Element dimensions = doc.createElement("dimensions");
		dimensions.setAttribute("default","N/A");

		property = doc.createElement("property");
		property.setAttribute("key","IDGalaxy");
		property.setAttribute("mapType","value");
		propertyText = doc.createTextNode("N/A");
		property.appendChild(propertyText);
		dimensions.appendChild(property);

		property = doc.createElement("property");
		property.setAttribute("key","IDTV");
		property.setAttribute("mapType","value");
		propertyText = doc.createTextNode("N/A");
		property.appendChild(propertyText);
		dimensions.appendChild(property);
		vendor.appendChild(dimensions);

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

		return xmlString;
		/***********************************************************************************/
	}
}
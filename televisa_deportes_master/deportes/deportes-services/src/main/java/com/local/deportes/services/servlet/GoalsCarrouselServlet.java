package com.local.deportes.services.servlet;

import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.WCMMode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.datamodel.objects.InfoPage;
import com.televisa.commons.services.datamodel.objects.impl.FilterVideoCarouselImpl;
import com.televisa.commons.services.services.GsaService;
import com.televisa.commons.services.services.InfoPageManagerService;
import com.televisa.commons.services.utilities.Utilities;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@SlingServlet(
        label = "Video Search Servlet",
        methods = {"GET" },
        extensions = { ".json" },
        paths = {"/bin/generics/goalscarrousel/query"},
        metatype = false
)
public class GoalsCarrouselServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = -4580767148997581744L;

   	protected final Logger LOG = LoggerFactory.getLogger(GoalsCarrouselServlet.class);

       @Reference
       private InfoPageManagerService infoPageManagerService;

       @Reference
       GsaService gsaService;

       WCMMode wcmMode;

   	@Override
   	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        this.wcmMode = WCMMode.fromRequest(request);
        response.setContentType("application/json");
   		response.setCharacterEncoding("UTF-8");

        /* Get and validate selectors */
        boolean valid = false;
        String[] selectors = request.getRequestPathInfo().getSelectors();
        List<String> tags = new LinkedList<String>();
        String type = "video";
        String path = "/content/televisa/deportes";
        int page = 1;
        boolean createTeamsCombo = false;
        valid = true;
           if(selectors.length == 3) {
               createTeamsCombo = false;
               path = selectors[0].replaceAll("__", "/");
               if(isNumber(selectors[1])) {
                   page = Integer.parseInt(selectors[1]);
               }
               String tournamentSelector = selectors[2].replaceAll("__","/");
               if(tournamentSelector.contains("televisa:deportes/torneos")) {
                   tags.add(tournamentSelector);
               }
           } else if(selectors.length == 4) {
            path = selectors[0].replaceAll("__", "/");
            if(isNumber(selectors[1])) {
                page = Integer.parseInt(selectors[1]);
            }
            String tournamentSelector = selectors[2].replaceAll("__","/");
            String jornadaSelector = selectors[3].replaceAll("__","/");
            if(tournamentSelector.contains("televisa:deportes/torneos") && jornadaSelector.contains("televisa:deportes/torneos")) {
                createTeamsCombo = true;
                tags.add(tournamentSelector);
                tags.add(jornadaSelector);
            }
        } else if(selectors.length > 4){
            path = selectors[0].replaceAll("__", "/");
            if(isNumber(selectors[1])) {
                page = Integer.parseInt(selectors[1]);
            }
            for(int index = 2; index < selectors.length; index++){
                if(!selectors[index].equals("") && selectors[index] != null) {
                    String rawTagString = selectors[index];
                    tags.add(rawTagString.replaceAll("__","/"));
                }
            }
        }

   		Map<String, String> htmlMap = new LinkedHashMap<String, String>();
        LinkedList<Tag> tagList = new LinkedList<Tag>();
   		if(valid){
   			FilterVideoCarouselImpl filter = new FilterVideoCarouselImpl(path,type,tags.toArray(new String[tags.size()]),"","",12,3,page);
   			InfoPage infoPage = infoPageManagerService.getVideoNotesByFilter(request, filter);
   			List<Note> notes = new ArrayList<Note>();
   			if((infoPage != null) && (infoPage.getNotes() != null) && (infoPage.getNotes().size() > 0)){
   				notes = infoPage.getNotes();
   			}

   			if(notes != null && notes.size() > 0){
   				htmlMap.put("body", createBodyHTML(notes));
               if(createTeamsCombo) {
                   htmlMap.put("teams", createComboHTML(notes));
               }

   			}
   		}
   		response.getWriter().write(createJSON(htmlMap));
   	}

   	   /**
        *
        * @param notes
        * @return
        */
       private String createBodyHTML(List<Note> notes) {
           StringBuffer bodyHTML = new StringBuffer();
           for (int i = 0; (i < notes.size()); i++) {
               Note note = notes.get(i);
               String rendition = null;
               if (note.getNoteImageAsset() != null && note.getNoteImageAsset().getRendition(84, 47) != null) {
                   rendition = note.getNoteImageAsset().getRendition(84, 47).getPath();
               }
               if (rendition == null && note.getNoteImageAsset() != null) {
                   rendition = note.getNoteImageAsset().getPath();
               }

               if (wcmMode.equals(WCMMode.DISABLED) && note.getNoteImageAsset() != null) {
                   rendition = Utilities.getCompleteURL(gsaService, rendition, GsaService.STATIC_DOMAIN);
               }
               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
               String formattedDate = "";
               if(note.getFirstPubDate() != null) {
                   formattedDate = formatter.format(note.getFirstPubDate().getTime());
               }
               bodyHTML = bodyHTML.append("<li class='color'>");
               bodyHTML = bodyHTML.append("<div class='img_div left'>");
               //bodyHTML = bodyHTML.append("<div class='overlay'></div>");
               //bodyHTML = bodyHTML.append("<i class='tvsa-videocamera tvsa-1x'></i>");
               bodyHTML = bodyHTML.append("<img src='" + rendition + "' alt='" + note.getTitle() + "'>");
               bodyHTML = bodyHTML.append("<span class=\"wdg_goals_carru_01_sprite video-corner\"></span>");
               bodyHTML = bodyHTML.append("</div>");
               bodyHTML = bodyHTML.append("<a href='" + externalizePath(note.getUrl()) + "' title='" + note.getTitle() + "'>");
               bodyHTML = bodyHTML.append("<span class='program'>" + note.getTitle() + "</span>");
               bodyHTML = bodyHTML.append("<span class='channel textcolor-title4'> <span class='textcolor-title2'>  -  </span>" + formattedDate + "</span>");
               bodyHTML = bodyHTML.append("</a>");
               bodyHTML = bodyHTML.append("</li>");
           }
           return bodyHTML.toString();
       }


    /**
    *
    * @param notes
    * @return
    */
    private String createComboHTML(List<Note> notes) {
        StringBuffer comboHTML = new StringBuffer();
        List<Tag> teamsList = new LinkedList<Tag>();
        for (Note note:notes) {
            Tag[] tags = note.getTags();
            for(Tag tag:tags){
                if(tag.getTagID().contains("televisa:deportes/equipos")){
                    if(!teamsList.contains(tag)) {
                        teamsList.add(tag);
                    }
                }
            }
        }
        comboHTML = comboHTML.append("<li><p>Todos</p></li>");
        if(teamsList != null && teamsList.size() > 0) {
           for(Tag tag:teamsList) {
               comboHTML = comboHTML.append("<li><p name='" + tag.getTagID() +"'>" + tag.getTitle() + "</p></li>");
           }

        }
        return comboHTML.toString();
    }
   	/**
   	 *
   	 * @param parameter
   	 * @return
   	 */
   	private boolean isNumber(String parameter){
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

       private boolean isValidPath(String path){
           return  (path.split("/").length > 0);
       }

   	/**
   	 *
   	 * @param htmlMap
   	 * @return
   	 */
   	private String createJSON(Map<String, String> htmlMap){
           GsonBuilder builder = new GsonBuilder();
           builder.disableHtmlEscaping();
           Gson gson = builder.create();
           return gson.toJson(htmlMap).toString();
   	}

    private String externalizePath(String path){
       if(wcmMode.equals(WCMMode.DISABLED)){
           path = Utilities.getCompleteURL(gsaService, path, GsaService.HTML_DOMAIN);
       }else{
           path += ".html";
       }
       return path;
    }
}

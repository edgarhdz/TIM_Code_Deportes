package com.televisa.commons.services.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.datamodel.objects.InfoPage;
import com.televisa.commons.services.datamodel.objects.impl.FilterIndexByTagsImpl;
import com.televisa.commons.services.services.InfoPageManagerService;
import com.televisa.commons.services.utilities.Base64Encoding;
import com.televisa.commons.services.utilities.Utilities;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SlingServlet(
        label = "Gallery History Servlet",
        methods = {"GET" },
        extensions = { "json" },
        paths = {"/bin/commons/superClic/query"},
        metatype = true
)
public class SuperClicServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = -4580767148997581744L;

	protected final Logger LOG = LoggerFactory.getLogger(SuperClicServlet.class);

    @Reference
    private InfoPageManagerService infoPageManagerService;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");

        /* Get and validate selectors */
        boolean valid = false;

        String[] selectors = request.getRequestPathInfo().getSelectors();
        String path = "";
        String[] tags = null;
        Boolean showarticles = false;
        Boolean showvideos = false;
        Boolean showphotogalleries = false;
        Boolean showpartidos = false;
        int pageNumber = 1;

        if(selectors.length == 6){
            if(Utilities.isValidPath(selectors[0])){

                //get path
                path = Base64Encoding.decoding(selectors[0]);

                //get tags
                String strTags = Base64Encoding.decoding(selectors[1]);
                strTags = strTags.replace("[","");
                strTags = strTags.replace("]","");
                tags = strTags.split(",");
                
                // get note filters
                showarticles = Boolean.valueOf(selectors[2]);
                showvideos = Boolean.valueOf(selectors[3]);
                showphotogalleries = Boolean.valueOf(selectors[4]);

                // get page number
                if(Utilities.isNumber(selectors[5])){
                    pageNumber = Integer.parseInt(selectors[5]);

                }
                valid = true;
            }
        }

	Map<String, String> htmlMap = new LinkedHashMap<String, String>();
        if(valid){
            
            InfoPage infoPage;
            FilterIndexByTagsImpl filter;
            //if there are no tags
            if ( (tags.length==1) && (tags[0].equals("")) ) {
                filter = new FilterIndexByTagsImpl(path, null, showarticles, showvideos, showphotogalleries, showpartidos, 12, 1, pageNumber);
               infoPage = infoPageManagerService.getNotesByPath(request, filter);                
            } else {
                filter = new FilterIndexByTagsImpl(path, tags, showarticles, showvideos, showphotogalleries, showpartidos, 12, 1, pageNumber);
                infoPage = infoPageManagerService.getNotesByPathAndTags(request, filter);        
            }

            
			List<Note> notes = new ArrayList<Note>();
			if((infoPage != null) && (infoPage.getNotes() != null) && (infoPage.getNotes().size() > 0)){
				notes = infoPage.getNotes();
			}
			if(notes != null && notes.size() > 0){
				htmlMap.put("body", createBodyHTML(notes));
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
        bodyHTML = bodyHTML.append("<ul>");

        for (int i = 0; (i < notes.size()); i++) {

            Note note = notes.get(i);

            String rendition = null;
            if (note.getNoteImageAsset() != null && note.getNoteImageAsset().getRendition(136, 102) != null) {
                rendition = note.getNoteImageAsset().getRendition(136, 102).getPath();
            }
            if (rendition == null && note.getNoteImageAsset() != null) {
                rendition = note.getNoteImageAsset().getPath();
            }

            bodyHTML = bodyHTML.append("<li>");
            bodyHTML = bodyHTML.append("<a href='" + note.getUrl() +".html"+ "' title='" + note.getTitle() + "'>");
            bodyHTML = bodyHTML.append("<img src='" + rendition + "' alt='" + note.getTitle() + "'>");
            bodyHTML = bodyHTML.append("</a>");
            bodyHTML = bodyHTML.append("<h3>");
            bodyHTML = bodyHTML.append("<a href='" +  note.getUrl() + ".html" + "' title='" + note.getTitle() + "'>");
            bodyHTML = bodyHTML.append(note.getTitle());
            bodyHTML = bodyHTML.append("</a>");
            bodyHTML = bodyHTML.append("</h3>");
            bodyHTML = bodyHTML.append("<p class=textcolor-title2'></p>");
            bodyHTML = bodyHTML.append("</li>");

        }
        bodyHTML = bodyHTML.append("</ul>");

        return bodyHTML.toString();
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
}
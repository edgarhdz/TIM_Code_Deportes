package com.televisa.commons.services.servlet;

import java.io.IOException;
import java.util.List;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.datamodel.objects.InfoPage;
import com.televisa.commons.services.datamodel.objects.impl.FilterIndexByTagsImpl;

import com.televisa.commons.services.services.InfoPageManagerService;
import com.televisa.commons.services.utilities.Base64Encoding;
import com.televisa.commons.services.utilities.Utilities;

@SlingServlet(
		label = "Search Elements Servlet",
		methods = {"GET" },
		extensions = { "json" },
		paths = {"/bin/commons/search/query"},
		metatype = true
		)
public class SearchElementsServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = -4580767148997581744L;

	protected final Logger LOG = LoggerFactory.getLogger(SearchElementsServlet.class);

	@Reference
	private InfoPageManagerService infoPageService;	


	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String path = "#";
		String[] tags = {};
		int itemsPerPage = 0;
		int totalPages = 1;
		int page = 1;
		boolean article = false, video = false, gallery = false, partidos = false;

		String[] selectors = request.getRequestPathInfo().getSelectors();
		if(selectors.length == 6){

			String typeOfNote = selectors[0];
				if("article".equals(typeOfNote)){
					article = true;
				}else if("video".equals(typeOfNote)){
					video = true;
				}else if("gallery".equals(typeOfNote)){
					gallery = true;
				}
			
			path = Base64Encoding.decoding(selectors[1]);	
			
			String strTags = Base64Encoding.decoding(selectors[2]);

			if(strTags != null){
				if(!"[]".equals(strTags)){
					strTags = strTags.replaceAll("\\[", "");
					strTags = strTags.replaceAll("\\]", "");
					if(strTags.indexOf(",") > -1){
						tags = strTags.split(",");	
					}else{
						tags = new String[]{strTags};
					}
				}else{
					tags = null;
				}	
			}
			
			if(Utilities.isNumber(selectors[3])){
				itemsPerPage = Integer.parseInt(selectors[3]);
			}
			
			if(Utilities.isNumber(selectors[4])){
				totalPages = Integer.parseInt(selectors[4]);
			}
			
			if(Utilities.isNumber(selectors[5])){
				page = Integer.parseInt(selectors[5]);
			}
		}

		String finalJSON = "";
		JSONObject json = new JSONObject();
		List<Note> notes = null;	    
		if(!path.trim().equals("")){
			FilterIndexByTagsImpl filter = new FilterIndexByTagsImpl(path, tags, article, video, gallery, partidos, itemsPerPage, totalPages, page);
			InfoPage infoPage = infoPageService.getNotesByPath(request, filter);
			
			if(infoPage != null && infoPage.getNotes().size() > 0){
				notes = infoPage.getNotes();
			}
			
			if(notes != null){
				try {
					json.put("results", notes.size());
					for (int i = 0; i < notes.size(); i++) {
						Note note = notes.get(i);
						if(note != null){
							JSONObject noteJson = new JSONObject();
							if(note.getTitle() != null && note.getTitle().length() > 0){
								noteJson.put("title", note.getTitle());	
							}else{
								noteJson.put("title", "");	
							}
							
							if(note.getUrl() != null && note.getUrl().length() > 0){
								noteJson.put("url",  note.getUrl());	
							}else{
								noteJson.put("url", "#");
							}
							
							String rendition = null;
							if (note.getNoteImageAsset() != null && note.getNoteImageAsset().getRendition(136, 104) != null) {
								rendition = note.getNoteImageAsset().getRendition(136, 104).getPath();
							}
							if (rendition == null && note.getNoteImageAsset() != null) {
								rendition = note.getNoteImageAsset().getPath();
							}
							
							if(rendition != null && rendition.length() > 0){
								noteJson.put("image", rendition);	
							}else{
								noteJson.put("image", "#");
							}
							
							json.accumulate("note", noteJson);	
						}
					}
					finalJSON = json.toString(2);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		response.getWriter().write(finalJSON);
	}
}

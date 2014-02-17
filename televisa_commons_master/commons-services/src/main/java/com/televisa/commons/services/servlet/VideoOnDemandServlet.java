/**
 *  DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Servlet to request videos for Video On Demand
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date             | Developer      		 | Changes
 * 1.0     | Oct 12, 2013     | bchavez@xumak.com      	 | Initial Creation
 * -----------------------------------------------------------------------------
 */

package com.televisa.commons.services.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.televisa.commons.services.datamodel.Video;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.datamodel.objects.InfoPage;
import com.televisa.commons.services.datamodel.objects.impl.FilterIndexByTagsImpl;
import com.televisa.commons.services.brightcove.BrightcoveProperties;
import com.televisa.commons.services.services.InfoPageManagerService;
import com.televisa.commons.services.services.GsaService;
import com.televisa.commons.services.utilities.Base64Encoding;
import com.televisa.commons.services.utilities.Utilities;


/**
 * Servelt to request videos for Video On Demand
 *
 * @author bchavez@xumak.com
 *
 */

@SuppressWarnings("serial")
@SlingServlet(
		label = "Video On Demand Servlet",
		methods = {"GET" },
		extensions = { "json" },
		paths = {"/bin/commons/video/on-demand"},
		metatype = true
		)
public class VideoOnDemandServlet extends SlingAllMethodsServlet {

	protected final Logger LOG = LoggerFactory.getLogger(VideoOnDemandServlet.class);

	@Reference
	private InfoPageManagerService infoPageService;

	@Reference
	private GsaService gsaService;


	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String path = "#";
		String[] tags = {};
		int itemsPerPage = 0;
		int totalPages = 1;
		int page = 1;

		String[] selectors = request.getRequestPathInfo().getSelectors();
		if(selectors.length == 5){

			path = Base64Encoding.decoding(selectors[0]);	
			
			String strTags = Base64Encoding.decoding(selectors[1]);
			
			if(strTags != null){
				strTags = strTags.replaceAll("\\[", "");
				strTags = strTags.replaceAll("\\]", "");
				if(strTags.indexOf(",") > -1){
					tags = strTags.split(",");	
				}else{
					tags = new String[]{strTags};
				}	
			}
			
			if(Utilities.isNumber(selectors[2])){
				itemsPerPage = Integer.parseInt(selectors[2]);
			}
			
			if(Utilities.isNumber(selectors[3])){
				totalPages = Integer.parseInt(selectors[3]);
			}
			
			if(Utilities.isNumber(selectors[4])){
				page = Integer.parseInt(selectors[4]);
			}
		}

		String finalJSON = "";
		JSONObject json = new JSONObject();
		List<Note> notes = null;	    
		if(!path.trim().equals("")){
			FilterIndexByTagsImpl filter = new FilterIndexByTagsImpl(path, tags, false, true, false, false, itemsPerPage, totalPages, page);
			InfoPage infoPage = infoPageService.getNotesByPathAndTags(request, filter);
			
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
							if (note.getNoteImageAsset() != null && note.getNoteImageAsset().getRendition(136, 77) != null) {
								rendition = note.getNoteImageAsset().getRendition(136, 77).getPath();
							}
							if (rendition == null && note.getNoteImageAsset() != null) {
								rendition = note.getNoteImageAsset().getPath();
							}
							
							if(rendition != null && rendition.length() > 0){
								noteJson.put("image", rendition);	
							}else{
								noteJson.put("image", "#");
							}

							BrightcoveProperties brightcoveHTML5Properties = new BrightcoveProperties((Video)note, gsaService);
		    					Map<String,String> brightcoveProperties = brightcoveHTML5Properties.getProperties();
							noteJson.put("brightcove", new JSONObject(brightcoveProperties));

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

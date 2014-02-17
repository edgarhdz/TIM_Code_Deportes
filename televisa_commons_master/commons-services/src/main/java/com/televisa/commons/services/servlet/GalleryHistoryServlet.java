package com.televisa.commons.services.servlet;

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

import com.day.cq.wcm.api.WCMMode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.datamodel.objects.InfoPage;
import com.televisa.commons.services.datamodel.objects.impl.FilterGalleryHistoryImpl;
import com.televisa.commons.services.services.GsaService;
import com.televisa.commons.services.services.InfoPageManagerService;
import com.televisa.commons.services.utilities.Utilities;

@SlingServlet(
        label = "Gallery History Servlet",
        methods = {"GET" },
        extensions = { "json" },
        paths = {"/bin/generics/galleryHistory/query"},
        metatype = true
)
public class GalleryHistoryServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = -4580767148997581744L;

	protected final Logger LOG = LoggerFactory.getLogger(GalleryHistoryServlet.class);

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
        String type = "";
        String path = "";
        String actualPath = "";
        String year = "";
        int page = 0;

        if(selectors.length == 5){
            if(Utilities.isValidPath(selectors[1])){
            	path = selectors[1].replaceAll("__","/");
            	if(Utilities.isValidPath(selectors[2])){
            		actualPath = selectors[2].replaceAll("__","/");
	                if(Utilities.isNumber(selectors[3])){
	                    if(Utilities.isNumber(selectors[4])){
	                        type = selectors[0];
	                        year = selectors[3];
	                        page = Integer.parseInt(selectors[4]);
	                        valid = true;
	                    }
	                }
            	}
            }
        }

		Map<String, String> htmlMap = new LinkedHashMap<String, String>();
		if(valid){
			FilterGalleryHistoryImpl filter = new FilterGalleryHistoryImpl(path, year, type, 12, 15, page);
			InfoPage infoPage = infoPageManagerService.getNotesByPathAndYear(request, filter);
			List<Note> notes = new ArrayList<Note>();

			if((infoPage != null) && (infoPage.getNotes() != null) && (infoPage.getNotes().size() > 0)){
				notes = infoPage.getNotes();
				notes = Utilities.eliminateRepatedNote(notes, actualPath);
			}

			if(notes != null && notes.size() > 0){
				htmlMap.put("body", createBodyHTML(notes));

		        String URL = request.getRequestURI();
		        int totalPages = infoPage.getTotalPages();
		        int actualPage = infoPage.getActualPage();

				htmlMap.put("paginator", createPaginatorHTML(notes, URL, totalPages, actualPage));

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
            if (i == 6) {
                bodyHTML = bodyHTML.append("<ul class='dotted-ad'>");
            }
            Note note = notes.get(i);

            String rendition = null;
            if (note.getNoteImageAsset() != null && note.getNoteImageAsset().getRendition(136, 102) != null) {
                rendition = note.getNoteImageAsset().getRendition(136, 102).getPath();
            }
            if (rendition == null && note.getNoteImageAsset() != null) {
                rendition = note.getNoteImageAsset().getPath();
            }

            if (wcmMode.equals(WCMMode.DISABLED) && note.getNoteImageAsset() != null) {
                rendition = Utilities.getCompleteURL(gsaService, rendition, GsaService.STATIC_DOMAIN);
            }

            bodyHTML = bodyHTML.append("<li>");
            bodyHTML = bodyHTML.append("<a href='" + note.getUrl() +".html"+ "' title='" + note.getTitle() + "'>");
            bodyHTML = bodyHTML.append("<img src='" + rendition + "' alt='" + note.getTitle() + "'>");
            bodyHTML = bodyHTML.append("</a>");
            bodyHTML = bodyHTML.append("<h2>");
            bodyHTML = bodyHTML.append("<a href='" +  note.getUrl() + ".html" + "' title='" + note.getTitle() + "'>");
            bodyHTML = bodyHTML.append(note.getTitle());
            bodyHTML = bodyHTML.append("</a>");
            bodyHTML = bodyHTML.append("</h2>");
            bodyHTML = bodyHTML.append("<p></p>");
            bodyHTML = bodyHTML.append("</li>");

            if (i == 5 && notes.size() > 5) {
                bodyHTML = bodyHTML.append("</ul>");
            }
        }
        bodyHTML = bodyHTML.append("</ul>");

        return bodyHTML.toString();
    }

	/**
	 * 
	 * @param notes
	 * @param URL
	 * @param totalPages
	 * @param actualPage
	 * @return
	 */
    private String createPaginatorHTML(List<Note> notes, String URL, int totalPages, int actualPage){
        StringBuffer paginatorHTML = new StringBuffer();
        paginatorHTML = paginatorHTML.append("<ul>");

        for(int page = 1; page < (totalPages + 1); page++ ){
            if(totalPages > 4){
                if((page ==  1) && (actualPage > 1)){
                    paginatorHTML = paginatorHTML.append("<li class='dotted-right'>");
                    paginatorHTML = paginatorHTML.append("<a href='#left' class='retorna' title='" + (actualPage - 1) + "'>");
                    paginatorHTML = paginatorHTML.append("<i class='tvsa-double-caret-left'></i>");
                    paginatorHTML = paginatorHTML.append("</a>");
                    paginatorHTML = paginatorHTML.append("</li>");

                    if((page == 1) && (actualPage > 2)){
                        paginatorHTML = paginatorHTML.append("<li><a title='" + page + "'>" + page + "</a></li>");
                        paginatorHTML = paginatorHTML.append("<li>...</li>");
                    }
                }

                if((page >=  1) && (page <= totalPages)){
                    if((page >= 1 && page <= 3) && (actualPage == 1)){
                        if(actualPage <= page){
                            if(page == actualPage){
                                paginatorHTML = paginatorHTML.append("<li><span class='selected'>" + page + "</span></li>");
                            }else{
                                paginatorHTML = paginatorHTML.append("<li ><a title='" + page + "'>" + page + "</a></li>");
                            }
                        }
                    }
                }

                if(((page >= 1) && (page <= totalPages)) && (actualPage != 1 && actualPage != totalPages)){
                    if((actualPage >= (page - 1)) && (actualPage <= (page + 1))){
                        if(page == actualPage){
                            paginatorHTML = paginatorHTML.append("<li><span class='selected'>" + page + "</span></li>");
                        }else{
                            paginatorHTML = paginatorHTML.append("<li ><a title='" + page + "'>" + page + "</a></li>");
                        }
                    }
                }

                if((page >= (totalPages - 2) && page <= totalPages) && (actualPage == totalPages)){
                    if(actualPage >= page){
                        if(page == actualPage){
                            paginatorHTML = paginatorHTML.append("<li><span class='selected'>" + page + "</span></li>");
                        }else{
                            paginatorHTML = paginatorHTML.append("<li ><a title='" + page + "'>" + page + "</a></li>");
                        }
                    }
                }

                if((page == totalPages) && (actualPage < totalPages)){
                    if((page == totalPages) && (actualPage < (totalPages - 1))){
                        paginatorHTML = paginatorHTML.append("<li>...</li>");
                        paginatorHTML = paginatorHTML.append("<li><a title='" + totalPages + "'>" + totalPages + "</a></li>");
                    }

                    paginatorHTML = paginatorHTML.append("<li class='dotted-left'>");
                    paginatorHTML = paginatorHTML.append("<a href='#right' class='final' title='" + (actualPage + 1) + "'>");
                    paginatorHTML = paginatorHTML.append("<i class='tvsa-double-caret-right'></i>");
                    paginatorHTML = paginatorHTML.append("</a>");
                    paginatorHTML = paginatorHTML.append("</li>");
                }
            }else{

                if((page ==  1) && (actualPage > 1)){
                    paginatorHTML = paginatorHTML.append("<li class='dotted-right'>");
                    paginatorHTML = paginatorHTML.append("<a href='#left' class='retorna' title='" + (actualPage - 1) + "'>");
                    paginatorHTML = paginatorHTML.append("<i class='tvsa-double-caret-left'></i>");
                    paginatorHTML = paginatorHTML.append("</a>");
                    paginatorHTML = paginatorHTML.append("</li>");
                }

                if(page == actualPage){
                    paginatorHTML = paginatorHTML.append("<li><span class='selected'>" + page + "</span></li>");
                }else{
                    paginatorHTML = paginatorHTML.append("<li ><a title='" + page + "'>" + page + "</a></li>");
                }

                if((page == totalPages) && (actualPage < totalPages)){
                    paginatorHTML = paginatorHTML.append("<li class='dotted-left'>");
                    paginatorHTML = paginatorHTML.append("<a href='#right' class='final' title='" + (actualPage + 1) + "'>");
                    paginatorHTML = paginatorHTML.append("<i class='tvsa-double-caret-right'></i>");
                    paginatorHTML = paginatorHTML.append("</a>");
                    paginatorHTML = paginatorHTML.append("</li>");
                }

            }
        }
        paginatorHTML = paginatorHTML.append("</ul>");
        return paginatorHTML.toString();
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
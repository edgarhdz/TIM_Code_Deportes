package com.televisa.commons.services.servlet;

import com.televisa.commons.services.brightcove.BrightcoveVideo;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: palecio
 * Date: 10/4/13
 * Time: 09:46
 */
@SlingServlet(
        label = "Externalizer Servlet",
        methods = {"GET" },
        extensions = { "json" },
        paths = {"/bin/brightcoveVideo"},
        metatype = false
)
/**
 * Receives a brightcove id as a a parameter, fetches the video info from the brightcove server
 * and returns a json with the values fetched.
 */
public class BrightcoveVideoServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(BrightcoveVideoServlet.class);

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        Writer writer = response.getWriter();
        response.setContentType("application/json; charset=UTF-8");
        String brightcoveId = request.getParameter("id");
        BrightcoveVideo brightcoveVideo = null;
        if(brightcoveId != null){
            brightcoveVideo = new BrightcoveVideo(request, brightcoveId);
        }

        try {
            if(brightcoveVideo == null) {
                new JSONObject().write(writer);
            } else {
                brightcoveVideo.toJson().write(writer);
            }
        } catch (JSONException je) {
            LOGGER.error(je.getMessage());
        }
    }


}

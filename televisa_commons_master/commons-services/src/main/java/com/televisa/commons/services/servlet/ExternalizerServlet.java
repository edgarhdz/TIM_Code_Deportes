package com.televisa.commons.services.servlet;

import com.televisa.commons.services.services.Externalizer;
import com.televisa.commons.services.services.GsaService;
import org.apache.felix.scr.annotations.Reference;
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


@SlingServlet(
        label = "Externalizer Servlet",
        methods = {"GET" },
        extensions = { "json" },
        paths = {"/bin/externalizer"},
        metatype = false
)
/**
 * Receives a path an returns a json with the externalized url with the format:
 * {"url":"http://localhost:4503/content/televisa/noticieros/mundo/1304/example.html"}
 */
public class ExternalizerServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(VideoCarouselServlet.class);

    @Reference
    private GsaService gsaService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        Writer writer = response.getWriter();
        String path = request.getParameter("path"); //get the path parameter e.g. /content/televisa/noticieros/mundo/1304/example.html
        if (path == null) {
            path = "";
        }

        String url = this.gsaService.buildUrl(path, Externalizer.HTML_DOMAIN);
        JSONObject jsonObject = new JSONObject(); //create a jsonObject
        try {
            jsonObject.put("url",url); //put the externalized url in the json object
        } catch (JSONException e) {
            LOG.error(e.getMessage());
        }
        writer.write(jsonObject.toString()); //write the jsonObject to the response

    }


}

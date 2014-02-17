package com.televisa.commons.services.servlet;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
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
import java.util.Iterator;

@SlingServlet(
        label = "Video Carousel Servlet",
        methods = {"GET" },
        extensions = { "json" },
        paths = {"/bin/video/carousel"},
        metatype = false
)

/**
 * Created with IntelliJ IDEA.
 * User: jdiaz86
 * Date: 8/21/13
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class VideoCarouselServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(VideoCarouselServlet.class);

    private TagManager tagManager;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        Writer writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject json_item = null;
        String lastItemKey = "";
        int count = 0;
        String fullPath = "";
        JSONObject jsonObject = new JSONObject(); //create a jsonObject
        this.tagManager = request.getResourceResolver().adaptTo(TagManager.class);

        String[] selectors = request.getRequestPathInfo().getSelectors();
        String tagPath = "";
        String tag = "";

        if (selectors.length == 2) {
            tagPath = selectors[0].replaceAll("__", "/");
            tag = selectors[1];

        }

        try {
            fullPath = tagPath.concat("/".concat(tag));
            Tag themeNameSpace = this.tagManager.resolve(fullPath);
            if (null!=themeNameSpace) {
                Iterator<Tag> themeNameSpaceIterator = themeNameSpace.listChildren();
                while (themeNameSpaceIterator.hasNext()) {
                    Tag nameSpaceItem = themeNameSpaceIterator.next();
                    json_item = new JSONObject();
                    json_item.put("title", nameSpaceItem.getTitle());
                    json_item.put("name", nameSpaceItem.getName());
                    jsonObject.put("note" + count, json_item);
                    count++;
                }
                lastItemKey = "note".concat(String.valueOf(--count));
                json_item = (JSONObject)jsonObject.get(lastItemKey);
                jsonObject.remove(lastItemKey);
                jsonObject.put("last",json_item);

                count = 0;
            }
        } catch (JSONException e) {
            LOG.error(e.getMessage());
        }

        writer.write(jsonObject.toString()); //write the jsonObject to the response
    }
}

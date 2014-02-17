package com.local.deportes.services.servlet;

import com.day.cq.tagging.TagManager;
import com.day.cq.tagging.Tag;
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
import java.lang.Override;import java.lang.String;import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: antonio
 * Date: 8/7/13
 * Time: 10:45 AM
 * To change this template use File | Settings | File Templates.
 */
@SlingServlet(
        label = "Tag List Servlet",
        paths = {"/bin/televisa/tags/list"},
        methods = {"GET", "POST"},
        extensions = {"json"},
        metatype = false
)
public class TagListServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = -1749870401142597298L;
    private static final Logger LOGGER = LoggerFactory.getLogger(TagListServlet.class);
    private static final String PARENT_NAMESPACE = "stockphotography:";
   /* @Reference
    private JcrTagManagerFactory tagManagerFactory;*/

    @Override
    protected void doGet(
            final SlingHttpServletRequest request,
            final SlingHttpServletResponse response) throws ServletException,
            IOException {
        doPost(request,response);
    }


    @Override
    protected void doPost(
            final SlingHttpServletRequest request,
            final SlingHttpServletResponse response) throws ServletException,
            IOException {
        //response.getWriter().write("test");
        TagManager tagManager = request.getResourceResolver().adaptTo(TagManager.class);
        String test = "";
        JSONObject jsonArray = new JSONObject();
        Writer writer = response.getWriter();
        try {

            Tag parentTag = tagManager.resolve(PARENT_NAMESPACE);
            Iterator<Tag> tagIter = parentTag.listChildren();
            //JSONObject jsonArray = new JSONObject();
            //jsonArray.put(PARENT_NAMESPACE, new JsonObject());
            while(tagIter.hasNext()) {
                Tag currentTag = tagIter.next();
                if(currentTag.listChildren() != null && currentTag.listChildren().hasNext()) {
                    Iterator<Tag> subTagIter = currentTag.listChildren();
                    JSONObject subArray = new JSONObject();
                    while(subTagIter.hasNext()){
                        Tag currentSubTag = subTagIter.next();
                        subArray.accumulate(currentTag.getTagID(), currentSubTag.getTagID());
                    }
                   jsonArray.accumulate(PARENT_NAMESPACE,subArray);
                } else {
                    jsonArray.accumulate(PARENT_NAMESPACE, currentTag.getTagID());
                }
            }
            jsonArray.write(writer);
        } catch (JSONException e) {
            LOGGER.error(e.getMessage());
        }
        //response.getWriter().write(jsonArray. + "******");

    }

}

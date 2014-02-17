package com.televisa.commons.services.servlet;


import com.televisa.commons.services.services.TwitterService;
import com.televisa.commons.services.utilities.Utilities;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;

@SlingServlet(
        label = "Twitter Search",
        methods = {"GET" },
        extensions = { "json" },
        paths = {"/bin/twitter/search"},
        metatype = true
)
public class TwitterServlet extends SlingAllMethodsServlet {

    private static final String TWITTER_API_URL = "https://api.twitter.com";
    private static final String TWITTER_SEARCH_PATH = "/1.1/search/tweets.json";

    @Reference
    private TwitterService twitterService;

    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        Writer writer = response.getWriter();
        String[] selectors = request.getRequestPathInfo().getSelectors();

        if(selectors != null && selectors.length == 2){
            String accessToken = this.twitterService.getAccessToken();
            String rpp = selectors[1];
            String query = selectors[0];
            query = URLEncoder.encode(query, "UTF-8");
            String resultType = "recent";
            String twitterUrl = TWITTER_API_URL + TWITTER_SEARCH_PATH + "?" + "result_type=" + resultType + "&q=" + query +"&rpp=" + rpp;
            if(Utilities.isInteger(rpp)){
                HttpClient client = new HttpClient();
                GetMethod method = new GetMethod(twitterUrl);
                method.addRequestHeader("Authorization", "Bearer " + accessToken);
                client.executeMethod(method);
                writer.write(method.getResponseBodyAsString());
            }else{
                writer.write("Invalid request, second selector must be a number");
            }
        }else{
            writer.write("Invalid request");
        }
    }
}

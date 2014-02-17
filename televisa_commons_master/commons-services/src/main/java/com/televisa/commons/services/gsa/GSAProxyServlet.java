package com.televisa.commons.services.gsa;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;


@SlingServlet(
        label = "GSA Proxy Servlet",
        methods = { "GET" },
        extensions = { "json" },
        paths = {"/bin/gsaproxy"},
        metatype = false
)

public class GSAProxyServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;



    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {
        response.setHeader("Content-Type", "application/json; charset=utf-8");

        String limit   = request.getParameter( "limit" );
        String charset = request.getParameter( "_charset_" );
        String query   = request.getParameter( "query" );
        URL url = new URL("http://googleak.esmas.com/search?btnG=Google+Search&spell=1&output=xml&proxystylesheet=json_cq&oe=latin1&ie=latin1&ud=1&getfields=*&num=" + limit +"&requiredfields=tipo:articulo&q=" + query + "&site=cqnoticieros&sort=date%3AD%3AS%3Ad1&_charset_=" + charset );
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream prin = connection.getInputStream();

        PrintWriter out = response.getWriter();
        while ( prin.available() > 0 ) {
            out.write( prin.read() );
        }
        out.flush();
        connection.disconnect();

    }
}

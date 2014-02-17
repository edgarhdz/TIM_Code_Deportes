package com.televisa.commons.services.servlet;
/**
 *  DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Servlet use to manage all search functionality
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date             | Developer      			 | Changes
 * 1.0     | Mar 11, 2013     | jdiaz@xumak.com         | Initial Creation
 * -----------------------------------------------------------------------------
 */

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

/**
 * Servlet to manage search functionality
 *
 * @author jdiaz@xumak.com
 *
 */
@SuppressWarnings("serial")
@SlingServlet(paths = "/bin/commons/search", methods = "POST", extensions = ".html")
public class SearchServlet extends SlingAllMethodsServlet {

    @Override
    protected void doPost(SlingHttpServletRequest request,
                          SlingHttpServletResponse response) throws ServletException,
            IOException {

        String query = request.getParameter("q").trim();
        String url = request.getParameter("url").trim();

        if ((null != query) && (!"".equals(query))) {
            String[] arrayQ = query.split(" ");
            query = "";
            for (String s : arrayQ) {
                if (!"".equals(s)) {
                    query = new StringBuffer(query).append(s).append("+").toString();
                }
            }
            query = query.substring(0, query.lastIndexOf("+"));

        }

        if ((null != url) && (!url.isEmpty())) {
            response.sendRedirect(url+"?q="+query);
        }
    }

}
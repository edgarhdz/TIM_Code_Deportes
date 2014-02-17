package com.televisa.commons.services.servlet;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import java.io.IOException;

@SlingServlet(
        label = "Respond JS Proxy",
        methods = {"GET" },
        extensions = { "html" },
        paths = {"/bin/respondProxy"},
        metatype = false
)
public class RespondJSProxyServlet extends SlingAllMethodsServlet {

    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        String htmlResponse = null;
        String javascriptCode = null;

        javascriptCode = "\t\t(function () {\n" +
                "\t\tvar domain, css, query, getQueryString, ajax, xmlHttp;\n" +
                "\n" +
                "\t\t/*\n" +
                "\t\t\thttp://stackoverflow.com/questions/4963673/get-url-array-variables-in-javascript-jquery/4963817#4963817\n" +
                "\t\t*/\n" +
                "\t\tgetQueryString = function() {\n" +
                "\t\t\tvar ret = {}, parts, i, p;\n" +
                "\n" +
                "\t\t\tparts = (document.location.toString().split(\"?\")[1]).split(\"&\");\n" +
                "\n" +
                "\t\t\tfor (i = 0; i < parts.length; i++) {\n" +
                "\n" +
                "\t\t\t\tp = parts[i].split(\"=\");\n" +
                "\t\t\t\t// so strings will be correctly parsed:\n" +
                "\t\t\t\tp[1] = decodeURIComponent(p[1].replace(/\\+/g, \" \"));\n" +
                "\n" +
                "\t\t\t\tif (p[0].search(/\\[\\]/) >= 0) { // then it\"s an array\n" +
                "\t\t\t\t\tp[0] = p[0].replace(\"[]\", \"\");\n" +
                "\n" +
                "\t\t\t\t\tif (typeof ret[p[0]] != \"object\") {\n" +
                "\t\t\t\t\t\tret[p[0]] = [];\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t\tret[p[0]].push(p[1]);\n" +
                "\t\t\t\t} else {\n" +
                "\t\t\t\t\tret[p[0]] = p[1];\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t\treturn ret;\n" +
                "\t\t};\n" +
                "\n" +
                "\t\tajax = function( url, callback ) {\n" +
                "\t\t\tvar req = xmlHttp();\n" +
                "\t\t\tif (!req){\n" +
                "\t\t\t\treturn;\n" +
                "\t\t\t}\n" +
                "\t\t\treq.open( \"GET\", url, true );\n" +
                "\t\t\treq.onreadystatechange = function () {\n" +
                "\t\t\t\tif ( req.readyState != 4 || req.status != 200 && req.status != 304 ){\n" +
                "\t\t\t\t\treturn;\n" +
                "\t\t\t\t}\n" +
                "\t\t\t\tcallback( req.responseText );\n" +
                "\t\t\t};\n" +
                "\t\t\tif ( req.readyState == 4 ){\n" +
                "\t\t\t\treturn;\n" +
                "\t\t\t}\n" +
                "\t\t\treq.send();\n" +
                "\t\t};\n" +
                "\n" +
                "\t\t//define ajax obj \n" +
                "\t\txmlHttp = (function() {\n" +
                "\t\t\tvar xmlhttpmethod = false,\n" +
                "\t\t\t\tattempts = [\n" +
                "\t\t\t\t\tfunction(){ return new XMLHttpRequest(); },\n" +
                "\t\t\t\t\tfunction(){ return new ActiveXObject(\"Microsoft.XMLHTTP\"); },\n" +
                "\t\t\t\t\tfunction(){ return new ActiveXObject(\"MSXML2.XMLHTTP.3.0\"); }\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\tal = attempts.length;\n" +
                "\n" +
                "\t\t\twhile( al-- ){\n" +
                "\t\t\t\ttry {\n" +
                "\t\t\t\t\txmlhttpmethod = attempts[ al ]();\n" +
                "\t\t\t\t}\n" +
                "\t\t\t\tcatch(e) {\n" +
                "\t\t\t\t\tcontinue;\n" +
                "\t\t\t\t}\n" +
                "\t\t\t\tbreak;\n" +
                "\t\t\t}\n" +
                "\t\t\treturn function(){\n" +
                "\t\t\t\treturn xmlhttpmethod;\n" +
                "\t\t\t};\n" +
                "\t\t})();\n" +
                "\n" +
                "\t\tquery = getQueryString();\n" +
                "\t\tcss = query[\"css\"];\n" +
                "\t\tdomain = query[\"url\"];\n" +
                "\n" +
                "\t\tif (css && domain) {\n" +
                "\t\t\tajax(css, function (response) {\n" +
                "\t\t\t\twindow.name = response;\n" +
                "\t\t\t\twindow.location.href = domain;\n" +
                "\t\t\t});\n" +
                "\t\t}\n" +
                "\t\t}());";

        htmlResponse = "<!DOCTYPE HTML>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title>Respond JS Proxy</title>\n" +
                "\t<meta charset=\"utf-8\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<script>\n" +
                "\t\t" + javascriptCode + "\n" +
                "\t</script>\n" +
                "</body>\n" +
                "</html>";

        response.setContentType("text/html");
        response.getWriter().write(htmlResponse);

    }
}

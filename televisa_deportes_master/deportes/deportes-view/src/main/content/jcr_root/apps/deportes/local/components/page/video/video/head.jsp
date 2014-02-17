<%--
  Copyright 1997-2010 Day Management AG
  Barfuesserplatz 6, 4001 Basel, Switzerland
  All Rights Reserved.

  This software is the confidential and proprietary information of
  Day Management AG, ("Confidential Information"). You shall not
  disclose such Confidential Information and shall use it only in
  accordance with the terms of the license agreement you entered into
  with Day.

  ==============================================================================

  Default head script.

  Draws the HTML head with some default content:
  - includes the WCML init script
  - includes the head libs script
  - includes the favicons
  - sets the HTML title
  - sets some meta data

  ==============================================================================

--%>
<%@include file="/apps/deportes/local/components/global.jsp"%>
<%@ page import="com.day.cq.commons.Doctype,
                   org.apache.commons.lang3.StringEscapeUtils,
				   com.day.cq.tagging.Tag,
				   javax.jcr.Node,
				   javax.jcr.NodeIterator,
				   javax.jcr.Session" %>
<%@page import="com.televisa.commons.services.services.GsaService"%>
<%
    GsaService gsaService = sling.getService(GsaService.class);
    String xs = Doctype.isXHTML(request) ? "/" : "";
    String favIcon = currentDesign.getPath() + "/favicon.ico";
    if (resourceResolver.getResource(favIcon) == null) {
        favIcon = null;
    }

    Tag[] tags = currentPage.getTags();


    String pageUrl = currentPage.getPath()+".html";
    if((WCMMode.fromRequest(request) != WCMMode.EDIT) && (WCMMode.fromRequest(request) != WCMMode.DESIGN)){
        pageUrl = gsaService.buildUrl(currentPage.getPath(),"html");
    }



    String pgDescription = "";
    String pgShort = "";

    String propertyDescription = currentPage.getDescription();
    String propertyDescShort = "";
    String finPropy ="";

    if(propertyDescription!=null){
        if(propertyDescription.length()>160){
            propertyDescShort = propertyDescription.substring(0,159)+"...";
            finPropy = propertyDescShort;
        }else{
            finPropy = propertyDescription;
        }

    }else{
        try{
            Node currentDescription = currentNode.getNode("video");
            pgDescription = currentDescription.getProperty("summary").getString();
            if(pgDescription.length()>160){
                pgShort = pgDescription.substring(0,159)+"...";
                pgDescription = pgShort;
                finPropy = pgDescription;
            }else{
                finPropy = pgDescription;
            }
        }catch(Exception e){
            finPropy = "";
        }

    }

%>

<head>
    <c:set var="tags" value="<%= tags %>"/>
    <c:set var="tagsnum" value="<%= tags.length > 0 ? tags.length - 1 : 0 %>"/>
    <c:set var="pageDescription" value="<%= finPropy%>"/>

    <title><%= currentStyle.get( "title/templateTitle", properties.get( "jcr:title", "Deportes | Televisa" ) ) %></title>

    <meta name="Description" content="${fn:escapeXml(pageDescription)}">


    <meta name="Keywords" content="<c:if test="${not empty properties.keywords}">${properties.keywords}</c:if><c:if test="${empty properties.keywords}"><c:forEach var="i" begin="0" end="${tagsnum}"><c:if test="${i>0}">, </c:if>${tags[i].title}</c:forEach></c:if>">

    <link rel="canonical" href="<%= pageUrl%>"/>

    <meta http-equiv="X-UA-Compatible" content="IE=10,IE=9,IE=8,IE=7,IE=6"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta http-equiv="imagetoolbar" content="no">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="Pragma" content="no-cache, must-revalidate, no-sztore"/>

    <!-- Robots -->
    <meta name="robots" content="index,follow">
    <meta name="robots" content="noodp,noydir">
    <meta name="googlebot" content="index,follow">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <meta name="HandheldFriendly" content="true">
    <meta name="format-detection" content="telephone=no">
    <!-- link: CSS IE-->

    <tg:noteProvider path="<%= currentPage.getPath() %>"/>
    <c:if test = "${!empty note.topic}">
        <meta name="topico" content="${note.topic}">
    </c:if>
    <meta name="icon" content="video"/>

    <!--[if IE]>
    <script type="text/javascript">
        function noError(){return true;}
        window.onerror = noError;

    </script>
    <![endif]-->


    <cq:include script="/libs/wcm/core/components/init/init.jsp"/>
    <cq:include script="headlibs.jsp"/>

    <!-- Respond Proxy  -->
    <%
        if((WCMMode.fromRequest(request) != WCMMode.EDIT) && (WCMMode.fromRequest(request) != WCMMode.DESIGN)){
    %>
    <link href="<%= gsaService.buildUrl("/bin/respondProxy.html", "static") %>" id="respond-proxy" rel="respond-proxy" />
    <link href="<%=gsaService.buildUrl("/etc/designs/televisa/generics/clientlibs/base/img/respond.proxy.gif", "html") %>" id="respond-redirect" rel="respond-redirect" />
    <script src="<%= gsaService.buildUrl("/etc/designs/televisa/generics/clientlibs/base/js/respond.js", "html") %>" ></script>
    <script src="<%=gsaService.buildUrl("/etc/designs/televisa/generics/clientlibs/base/js/respond.proxy.js", "html") %>"></script>

    <% } %>

    <cq:include resourceType="televisa/components/content/generics/ads/banner" path="banner" />
    <cq:include script="stats.jsp"/>
</head>
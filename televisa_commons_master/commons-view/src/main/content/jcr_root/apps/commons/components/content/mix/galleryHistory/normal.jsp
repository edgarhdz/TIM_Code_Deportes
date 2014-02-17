
<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Index By Tags component.
 *  This component is used for define how 'n' numbers of twitterFeed components is going to manage the component

 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer               | Changes
 * 1.0     | 28/01/2013  | Luis Jose Sztul         | Initial Creation.
 * 1.1     | 10/06/2013  | Pablo Alecio            | Fix to allow two of these components on the same page.
 * 1.1     | 25/07/2013  | Pablo Alecio            | Made the ajax call async, changed the path to the servlet and changed the style of the component when it hasn't loaded anything
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>

<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<%@page import="com.televisa.commons.services.services.InfoPageManagerService"%>
<%@page import="com.televisa.commons.services.datamodel.Note"%>
<%@page import="com.televisa.commons.services.datamodel.RenditionSize"%>
<%@page import="com.televisa.commons.services.datamodel.objects.impl.FilterGalleryHistoryImpl"%>
<%@page import="com.televisa.commons.services.datamodel.objects.InfoPage"%>
<%@page import="com.televisa.commons.services.utilities.Utilities"%>


<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.day.cq.wcm.api.WCMMode" %>

<fmt:message key="commons.galleryHistory.placeholder" var="placeholder"/>

<c:set var="mode" value="<%= WCMMode.fromRequest(request) %>"/>
<c:set var="modeEdit" value="<%= WCMMode.EDIT %>"/>

<%
    String actualPath = (String)request.getAttribute("embedPath");
    if(actualPath == null){
        if (currentPage != null && currentPage.getPath() != null && currentPage.getPath().length() > 0){
            actualPath = currentPage.getPath().replaceAll("/","__");
        }
    }else{
        actualPath = actualPath.replaceAll("/","__");
    }
    pageContext.setAttribute("actualPath", actualPath);
    pageContext.setAttribute("path", properties.get("path", "").replaceAll("/","__"));
    pageContext.setAttribute("year", properties.get("year", ""));
    pageContext.setAttribute("linkTarget", properties.get("linkTarget", "_self"));

    pageContext.setAttribute("show", properties.get("show", ""));
    pageContext.setAttribute("showtitle", properties.get("showtitle", ""));
    pageContext.setAttribute("hide", properties.get("hide", ""));
    pageContext.setAttribute("hidetitle", properties.get("hidetitle", ""));

    pageContext.setAttribute("titleLinkText", properties.get("titleLinkText", ""));
    pageContext.setAttribute("titleLinkType", properties.get("titleLinkType", "internal"));
    pageContext.setAttribute("titleInternalLink", properties.get("titleInternalLink", ""));
    pageContext.setAttribute("titleExternalLink", properties.get("titleExternalLink", ""));
    pageContext.setAttribute("titleLinkTarget", properties.get("titleLinkTarget", "_blank"));
    pageContext.setAttribute("titleTitle", properties.get("titleTitle", ""));

    pageContext.setAttribute("typeOfNote", properties.get("typeOfNote", "genericNote"));
    pageContext.setAttribute("random", Math.abs((new Date()).hashCode()));

%>

<c:choose>
    <c:when test="${titleLinkType == 'internal'}">
        <c:if test="${not empty titleInternalLink}">
            <c:set var="titlelink" value="${titleInternalLink}" />
            <c:if test= "${not empty titlelink}">
                <c:set var="titlelink" value="href='${titleInternalLink}.html'" />
            </c:if>
        </c:if>
    </c:when>
    <c:otherwise>
        <c:set var="titlelink" value="href='${titleExternalLink}'" />
    </c:otherwise>
</c:choose>

<c:if test="${not empty titleLinkTarget && titleLinkTarget != 'select'}">
    <c:set var="titleLinkTarget" value="target= '${titleLinkTarget}'"/>
</c:if>
<c:if test="${not empty titleTitle}">
    <c:set var="titleLinkTitle" value="title= '${titleTitle}'" />
</c:if>
<c:set var="titleLinkText" value="${titleLinkText}"/>
<c:if test="${empty titleLinkText}">
    <c:set var="titleLinkText" value=""/>
</c:if>

<c:set var="plecaVisible" value="true" />
<c:if test="${not empty properties.linkHide && properties.linkHide == true}">
    <c:set var="plecaVisible" value="false" />
</c:if>

<!-- mix_12arts_02 -->
<c:choose>
    <c:when test="${not empty path && not empty year}">
        <!-- Add styles to devices -->
        <c:if test="${not empty properties.hideDevice}">
            <div class="${properties.hideDevice}">
        </c:if>

        <div class="mix_12arts_02" data-enhance="false">

                <!-- BEGIN: str_pleca_01 -->
                <div class="str_pleca_01">
                    <div class="str_pleca_01_title">
                        <c:if test="${plecaVisible}">
                            <h3 class="background-color-pleca1">
                                <a ${titlelink} ${titleLinkTarget} ${titleLinkTitle} class="textcolor-title3">
                                    ${titleLinkText}
                                    <span class="str_pleca_01_arrowa selected"></span>
                                    <span class="str_pleca_01_arrowb"></span>
                                </a>
                            </h3>
                        </c:if>
                        <div class="ocultar">
                            <a href="#" class="textcolor-title4" title="${hidetitle}" >${hide}<i class="tvsa-caret-up"></i></a>
                        </div>
                        <div class="mostrar">
                            <a href="#" class="textcolor-title4" title="${showtitle}" >${show}<i class="tvsa-caret-down"></i></a>
                        </div>
                    </div>
                </div>
                <!-- END: str_pleca_01 -->

            <div class="type1a_">
                <div class="carrusel" id="${random}carousel"></div>
            </div>

            <div class="nav_paginator_01" id="${random}paginator">
                <div class="nav_paginator_01">
                </div>
            </div>
        </div>

        <!-- close div of styles to devices -->
        <c:if test="${not empty properties.hideDevice}">
            </div>
        </c:if>

    </c:when>
    <c:otherwise>
        <h4 class="cq-texthint-placeholder">${placeholder}</h4>
    </c:otherwise>
</c:choose>

<c:if test = "${authorMode}">
    <div style="clear:both;"></div>
</c:if>



<script type="text/javascript">
    var triggerClick = function (){
        $(".nav_paginator_01 ul li a").click(function(e) {
            var numberOfPage = $(this).attr('title');
            if(numberOfPage != undefined){
                loadNotes${random}(numberOfPage);
            }
        });
    }

    function loadNotes${random}(numberOfPage){

        if(numberOfPage == undefined || numberOfPage <= 0){
            numberOfPage = 1;
        }
        var htmlBody = '';
        var htmlPaginator = '';
        var htmlResult = [];
        var path = "${path}";
        $.ajax({
            dataType: "json",
            type: "GET",
            url: "/bin/generics/galleryHistory/query.${typeOfNote}."+ path +".${actualPath}"  + ".${year}." + numberOfPage + ".json",
            success: function(result){
                htmlResult = result;
                $.each(htmlResult, function(key, val) {
                    if(key == 'body'){
                        htmlBody = val;
                    }else if(key == 'paginator'){
                        htmlPaginator = val;
                    }
                });
                $('#${random}carousel').html(htmlBody);
                $('#${random}paginator').html(htmlPaginator);
                triggerClick();
                if ($(window).width() > 960){
                    element_Margins(6);
                }
                if ( $(window).width() > 624 && $(window).width() <= 960){
                    element_Margins(4);
                }
            },
            error: function(result){
                console.log("Unable to get data in the Gallery History");
            }
        });
    }

    jQuery(document).ready(function ($) {
        (function($,T){
            loadNotes${random}(1); //Load notes in the first page
        })($,Televisa);
    });
</script>
<!-- END: mix_6x2arts_01 -->

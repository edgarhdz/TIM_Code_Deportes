<%--
/**
 *  DESCRIPTION
 * -----------------------------------------------------------------------------
 *  
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date           | Developer          	| Changes
 * 1.0     | 25/11/2013     | Bryan Gerhard Chávez	| Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%><%
%><%@include file="/apps/commons/components/global.jsp"%>

<%@ page import="com.televisa.commons.services.utilities.Base64Encoding"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>

<fmt:message key="commons.recentVideos.editMessage" var="editMessage"/>

<%
    String actualPath = (String) request.getAttribute("embedPath");
    if(actualPath == null){
        if(currentPage != null && currentPage.getPath() != null && currentPage.getPath().length() > 0){
            actualPath = currentPage.getPath();
        }
    }

    pageContext.setAttribute("actualPath", actualPath);

    String path = properties.get("path", "#");
    pageContext.setAttribute("path", Base64Encoding.encoding(path));
    String[] tags = properties.get("cq:tags", new String[0]);
    pageContext.setAttribute("tags", Base64Encoding.encoding(Arrays.toString(tags)));
    String typeOfNote = properties.get("typeOfNote", "video");
    pageContext.setAttribute("typeOfNote", typeOfNote);
    pageContext.setAttribute("linkTargetVideo", properties.get("linkTargetVideo", "_blank"));

    pageContext.setAttribute("random", Math.abs((new Date()).hashCode()));

%>

<%-- PLECA --%>

<%-- Adding validation for the content of the text --%>
    <c:set var="text" value="${properties.linkText}"/>
    <c:if test="${empty text}">
		<c:if test = "${authorMode}"> 
        	<c:set var="text" value="${placeholder} ${componentname}"/>
    	</c:if>
    </c:if>
     
    <c:set var="plecaVisible" value="true"/>
    <c:if test="${not empty properties.linkHide && properties.linkHide == true && !authorMode}">
    	<c:set var="plecaVisible" value="false"/>
    </c:if>
    

    <c:choose>
        <c:when test="${properties.linkType == 'internal'}">
            <c:if test="${not empty properties.internalLink}">
                <c:set var="link" value="href='${properties.internalLink}.html'" />  
            </c:if>    
        </c:when>
        <c:otherwise>
            <c:if test="${not empty properties.externalLink}">
                <c:set var="link" value="href='${properties.externalLink}'" />
            </c:if>        
        </c:otherwise>
    </c:choose>    
    
    <%-- adding target propety to the link --%>
    <c:if test="${not empty properties.linkText}">
        <c:set var="target" value="target='${properties.linkTarget}'"/>
    </c:if>
    <%-- adding title propety to the link --%>
    <c:if test="${not empty properties.linkTitle}">
        <c:set var="title" value="title= '${properties.linkTitle}'" />
    </c:if>

<%-- /PLECA --%>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

<!-- BEGIN: vid_4vids_04-->
	<div class="vid_4vids_04" data-enhance="false">
        <div class="wdg_mix_carrousa showArrows">
            <div class="vid_4vids_04_title">
			<c:if test="${plecaVisible}">
				<div class="one background-color-pleca1">
					<a ${title} ${link} ${target}> ${text}<span class="icon mobile"></span>
					</a>
				</div>
			</c:if>


			<ul class="arrows">
                    <li>
                    	<a href="#left" class="wdg_carousa_left carouselArrowLeft">
                        	<i class="tvsa-double-caret-left inactive"></i>
                        </a>
                    </li>
                    <li>
                    	<a href="#right" class="wdg_carousa_right carouselArrowRight">
                        	<i class="tvsa-double-caret-right"></i>
                        </a>
                    </li>
                </ul>
            </div>

                    <div class="general">

    					<div id="${random}" class="carrusel-sizes">
        				</div>

                    </div><!--General-->

        </div><!--wdg_mix_carrousa showArrows-->
            <div class="bullets">
                <div class="background-color1"></div>
                <div></div>
                <div></div>
            </div>
            <div class="degraded"></div>


    </div>
<!-- END: vid_4vids_04 -->

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>


<script type="text/javascript">
    $(document).ready(function ($) {
        (function($,T){
            var path = "${path}";
            var actualPath = "${actualPath}";
            $.ajax({
                dataType: "json",
                type: "GET",
                url: "/bin/commons/search/query.${typeOfNote}."+ path + ".${tags}" + ".12" + ".1" + ".1" + ".json",
                success: function(result){
                    if(result != null){
                    	refreshRecentVideos${random}(result, actualPath);
                    }else{
                        console.log("Recent Videos: Not enough information to get json object.");
                    }
                },
                error: function(result){
                    console.log("Unable to get data in the Video Carousel");
                }
            });
        })($,Televisa);
    });
    function refreshRecentVideos${random}(result, actualPath){
    	var containerDiv = $("#${random}").addClass("showArrows");
	var divCont4 = $("<div>").addClass("cont4");
	var counter = 0;
        var toogle = true;
        var cont4_toogle = true;
	var notes = result.note;
	//to make the single element an array
	if(!Array.isArray(result.note)){ 
		notes = [notes];
	}     
	notes.forEach(
	        function(entry) {
	            var strUrl = entry.url;
	            if(strUrl.indexOf(".html") != -1){
	                strUrl = strUrl.replace(".html", "");
	            }

	            if(strUrl.length > 0){
	                var lastCharacter = strUrl.substring(strUrl.length, strUrl.length - 1);
	                if(lastCharacter.indexOf(".html") != -1){
	                    strUrl= strUrl.substring(0, strUrl.length - 1);
	                }
	            }

	            var strActualPath = actualPath;
	            if(strActualPath.indexOf(".html") != -1){
	                strActualPath = strActualPath.replace(".html", "");
	            }

	            if(strActualPath.length > 0){
	                var lastCharacter = strActualPath.substring(strActualPath.length, strActualPath.length - 1);
	                if(lastCharacter.indexOf(".html") != -1){
	                    strActualPath= strActualPath.substring(0, strActualPath.length - 1);
	                }
	            }

	            if(strActualPath != strUrl){
	                counter = counter + 1;


	                if(toogle){
	                    var divContainer =  $("<div>").addClass("izq1");
	                    toogle = false;
	                }else{
			    var divContainer =  $("<div>").addClass("izq");
	                    toogle = true
	                }

	                var videoRef =  $("<a>").addClass("ventana").addClass("galleryLink");
	                    videoRef.attr("href",entry.url + ".html");
	                    videoRef.attr("title",entry.title);
	                    videoRef.attr("target","${linkTargetVideo}");
	                    videoRef.attr("data-index",counter);
	                var i = $("<i>").addClass("tvsa-camera");
	                var divOverlay = $("<div>").addClass("overlay").append(i);
	                var img =  $("<img>");
	                    img.attr("src",entry.image);
	                    img.attr("alt",entry.title);
	
	                videoRef.append(divOverlay).append(img);
	
	                var divTwo = $("<div>").addClass("two");
	                var titleRef = $("<a>").addClass("galleryLink");
	                    titleRef.attr("href",entry.url + ".html");
	                    titleRef.attr("title",entry.title);
	                    titleRef.attr("target","${linkTargetVideo}");
	                    titleRef.append(entry.title);
	
	                divTwo.append(titleRef);
	
	                divContainer.append(videoRef).append(divTwo);

	                divCont4.append(divContainer);

	                if((counter%4)==0){
	                    containerDiv.append(divCont4);
	                    divCont4 = $("<div>").addClass("cont4");
	                }

	            }
	        }
	);


    	if((counter%4)!=0){
            containerDiv.append(divCont4);
        }

    $("div#${random} > div.cont4").last().addClass("last");
    }
</script>

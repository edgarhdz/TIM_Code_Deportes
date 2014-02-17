<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  
 *  
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 7/1/2013    | Bryan Gerhard Chávez   | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>  
<%@include file="/apps/deportes/local/components/global.jsp"%>

<%@ page import="com.televisa.commons.services.utilities.Base64Encoding"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Arrays" %>

<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@page session="false" %>


<fmt:message key="deportes.vid4vids03.empty.label" var="emptyVideo"/>
<fmt:message key="commons.onDemand.placeholder" var="placeholdercomponent"/>

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
    pageContext.setAttribute("linkTargetVideo", properties.get("linkTargetVideo", "_blank"));

	pageContext.setAttribute("playVideo", properties.get("playVideo", "play"));

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

	<c:set var="plecaVisible" value="true" />
	<c:if
		test="${not empty properties.linkHide && properties.linkHide == true}">
		<c:set var="plecaVisible" value="false" />
	</c:if>


<%-- /PLECA --%>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

	<!-- BEGIN: vid_4vids_03-->
    <div class="vid_4vids_03" data-enhance="false">
        <div class="wdg_mix_carrousa showArrows">
            <div class="vid_4vids_03_title">
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
            <c:if test="${empty properties.path && authorMode}">
                <h4 class="cq-texthint-placeholder">${placeholdercomponent}</h4>
            </c:if>
            <div class="general">

                <div class="carrusel-sizes">
                </div><!-- Contenedor -->

            </div><!--General-->
          </div><!--wdg_mix_carrousa showArrows-->
        <div class="bullets">
            <div class="background-color1"></div>
            <div></div>
            <div></div>
        </div>
        <div class="degraded"></div>
         <!-- <div class="vid_4vids_03_verMas">
       		<a class="textcolor-title4" href="#">Ver Más</a>
    	 </div> -->
    </div>
    <!-- END: vid_4vids_03 -->

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>

<c:if test="${playVideo == 'play'}">
    <%-- Scripts --%>
    <script type="text/javascript" src="http://www.googletagservices.com/tag/js/gpt.js"></script>   
    <script language="javascript" src="http://i2.esmas.com/tvolucion/js/Tim-HTML5-Player-1.0.0.js"></script> 
    <script language="JavaScript" type="text/javascript" src="http://admin.brightcove.com/js/BrightcoveExperiences.js"></script>
    <script language="JavaScript" type="text/javascript" src="http://admin.brightcove.com/js/APIModules_all.js"></script>
    <script type="text/javascript" src="http://gksrv.esmas.com/esmasgeo?js=1"></script>   
    <%-- /Scripts --%>
</c:if>

<script type="text/javascript">

    var globalResult;
    
	var mobileDimensions = Vid3Utils.getMobilePlayerSize();
   	var playerHeight;
    var playerWidth;
    if(mobileDimensions.height != null){
        playerHeight = mobileDimensions.height;
        playerWidth = mobileDimensions.width;
    }

    var galleryVideos = [];
	
	if("${playVideo}" == "play"){
        var ipadConfig = {
            thumbnail: "",
            title: "",               
            id: "",
            cat: "",
            profile: "bca",
            gbs: "",
            permalink: "",
            duration: "00:00:00",
            origin: 8,
            ism3: false,
            m3u8: "",
            width : playerWidth || 624,
            height: playerHeight || 380
        };
    
        var brightcoveConfig = {
            bgcolor:"#000000",
            width:"624",
            height:"380",
            playerID:"",
            publisherID:"74091787001",
            playerKey:"AQ~~,AAAAEUA28vk~,ZZqXLYtFw-ADB2SpeHfBR3cyrCkvIrAe",
            isVid:"true",
            isUI:"true",
            dynamicStreaming:"false",
            videoId:"",
            wmode:"transparent",
            linkBaseURL:"",
            autoStart:"false",
            profile:"bca",
            quality:"high"
        };
    
        //Create brightcove player
        BrightCoveVid3GalleryManager.initialize(galleryVideos,brightcoveConfig,ipadConfig);
    }

	//AJAX call
    runAjax($);
    function runAjax($){
        var timeElapsed = false;
        setTimeout(function(){timeElapsed = true}, 60000);


        $.ajax({
            dataType: "json",
            type: "GET",
            url: "/bin/commons/video/on-demand.${path}.${tags}" + ".12" + ".1" + ".1" + ".json",
            success: function(result){
                //This is possible since the json object will be cached.
                if(JSON.stringify(globalResult) != JSON.stringify(result)){
					if(result != null){
                        globalResult = result;
                        refreshRecentVideos${random}(result, "${actualPath}");
                    }else{
                        console.log("On Demand: Not enough information to get json object.");
                    }
                }
            },
            error: function(result){
                console.log("Unable to get json object from /bin/commons/video/on-demand");
            }
        }).done(function(){
            var timer = setInterval(function(){
                if(timeElapsed){
                    clearInterval(timer);
                    runAjax($);
                }
            },30000);
     });
    }

    function refreshRecentVideos${random}(result, actualPath){
        var excludeVideo = actualPath;
        var containerDiv = $("<div>").addClass("carrusel-sizes");
		var toogle = true;
        var counter = 0;
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
					galleryBrightcove(entry.brightcove);
                    var divContainer;
                        if(toogle){
                            divContainer =  $("<div>").addClass("izq1");
                            toogle = false;
                        }else{
                            divContainer =  $("<div>").addClass("izq");
                            toogle = true
                        }
                    var videoRef =  $("<a>").addClass("galleryLink-OnDemand").addClass("ventana");
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
                    var titleRef = $("<a>").addClass("galleryLink-OnDemand");
                        titleRef.attr("href",entry.url + ".html");
                        titleRef.attr("title",entry.title);
                        titleRef.attr("target","${linkTargetVideo}");
                    	titleRef.attr("data-index",counter);
                        titleRef.append(entry.title);

                    divTwo.append(titleRef);
                    divContainer.append(videoRef).append(divTwo);
                    containerDiv.append(divContainer);
                    counter = counter + 1;
                }
            }
		);
        containerDiv.replaceAll($(".carrusel-sizes"));

        if("${playVideo}" == "play"){
            BrightCoveVid3GalleryManager.onClickItem();
        }

    }
    
    function galleryBrightcove(videoProperties){
    	galleryVideos.push({
            thumbnail: "",
            title: videoProperties.title,               
            id: videoProperties.id,
            videoId: videoProperties.videoId,
            cat: videoProperties.cat,
            profile: videoProperties.profile, 
            gbs: videoProperties.gbs,
            permalink: videoProperties.publicUrl,
            duration: videoProperties.duration,
            origin: parseInt(videoProperties.origin),
            ism3: (videoProperties.isM3u8 === 'true'),
            m3u8: videoProperties.m3u8,
            width : playerWidth  || parseInt(videoProperties.html5PlayerWidth),
            height: playerHeight || parseInt(videoProperties.html5PlayerHeight)
        });
    }

	//variable that have an EventListener when the video ends
	var mediaComplete = function(){
        //Function to play stream
        Vid3Utils.hideVideo();
        //Show akamai
	}

</script>

<c:if test="${playVideo == 'play'}">
	<script type="text/javascript" src="http://www.esmas.com/scripts/esmas_stats.js"></script>
	<script type="text/javascript">doStats('esmas');</script>
</c:if>

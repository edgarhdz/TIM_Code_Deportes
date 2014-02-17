<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Akamai script.
 *  This script renders the akamai video player.
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 27/02/2013  | Pablo Alecio           | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>  
<%@include file="/apps/televisa/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page session="false" %>
<cq:includeClientLib categories="apps.noticieros.vid_player_01" />
<fmt:message key="generics.akamai.empty.label" var="emptyVideo"/>

<c:set var="display" value="false"/>
<c:if test="${not empty properties.akamaiId}">
   <c:set var="akamaiId" value="id=${properties.akamaiId}&"/>
   <c:set var="display" value="true"/>
</c:if>
<c:set var="smavp" value="smavp=false"/>
<c:if test="${not empty properties.smavp}">
   <c:set var="smavp" value="smavp=true"/>
</c:if>

<c:choose>
    <c:when test="${(not empty properties.height) && (not empty properties.width)}">
        <c:set var="height" value="${properties.height}"/>
        <c:set var="width" value="${properties.width}"/>
    </c:when>
    <c:otherwise>
        <c:set var="height" value="343"/>
        <c:set var="width" value="613"/>
    </c:otherwise>
</c:choose>

<c:set var="dimensions" value="&h=${height}&w=${width}"/>

<c:choose>
    <c:when test="${display}">
        <iframe 
            id="akamaiPlayer"
            src="http://tvolucion.esmas.com/embed/embed.php?${akamaiId}${smavp}${dimensions}"
            width="${width}" 
            height="${height}" 
            frameborder="0" 
            scrolling="no" 
            class="vid_black" 
            style="position: relative;
                overflow: hidden;">
        </iframe>
    </c:when>
    <c:otherwise>
        <p>${emptyVideo}</p>
    </c:otherwise>
</c:choose>

<script type="text/javascript">

    $(document).ready(function(){
       
        resizeAkamaiPlayer();
        
        $(window).resize(function() {
           
           resizeAkamaiPlayer();
        });    
        
    });
    
    function resizeAkamaiPlayer(){
        var dimensions = getWindowSize();
        if(dimensions.type == "mobile"){
            var desktopHeight = $("#akamaiPlayer").attr('height');
            var desktopWidth = $("#akamaiPlayer").attr('width');
            
            var mobileHeight = (dimensions.width * desktopHeight / desktopWidth); //keeps the ratio w:h
            mobileHeight = Math.round(mobileHeight);
            var src = $("#akamaiPlayer").attr('src');
            var hIndex = $("#akamaiPlayer").attr('src').indexOf('&h=');
            
            src = src.substring(0, hIndex);            
            src += "&h=" + mobileHeight + "&w=" + dimensions.width;
            $("#akamaiPlayer").attr('src', src);
            $("#akamaiPlayer").attr('width', dimensions.width);
            $("#akamaiPlayer").attr('height', mobileHeight);
            
        }        
    }
    
        
</script>
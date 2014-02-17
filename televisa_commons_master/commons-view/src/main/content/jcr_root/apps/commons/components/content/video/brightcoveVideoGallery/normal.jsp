<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Brightcove Video Gallery component.
 *  This component displays a brightcove video gallery. It uses references to video pages to populate the video list.
 *  See /etc/designs/televisa/noticieros/clientlibs/vid_4vids_01/js/vid_4vids_01.js
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 04/03/2013  | Pablo Alecio           | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>  
<%@include file="/apps/commons/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page session="false" %>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

<cq:include script="/apps/commons/components/content/video/layouts/gallery.jsp" />
<cq:include script="/apps/commons/components/content/video/players/brightcove.jsp" />

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>
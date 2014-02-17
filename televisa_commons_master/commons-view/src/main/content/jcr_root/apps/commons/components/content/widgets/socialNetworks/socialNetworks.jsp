<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  
 *  
 *  component id = wdg_social_01
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 13/02/2013  | Juan Jose Pol.         | Initial Creation.
 * 2.0     | 26/02/2013  | Leonel Orozco          | JS file now loaded in footerlibs.jsp
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>

<%@include file="/apps/commons/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>

    <!-- BEGIN: Widget Redes Sociales -->
    <div class="wdg_social_01">
    	<div id="widgetSocialShare"></div>
	</div>
    <!-- END: Widget Redes Sociales -->

    <%--  adding a new div to move the parsys   --%>
    <c:if test = "${authorMode}"> 
        <div style="clear:both;"></div>
    </c:if>  
    
<%--script type="text/javascript" src="http://comentarios.esmas.com/js/communities.js"></script--%>


           
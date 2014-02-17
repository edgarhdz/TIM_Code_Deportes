<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Primary Three Shorts Teaser Container Component
 *
 *  Displays a Primary Teaser Component with Three Shorts  Teaser in a Container.
 *
 *  component id = mix_4arts_01
 *
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 15/02/2013  | Gerardo Escobar        | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
    <%@include file="/apps/deportes/generics/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

    <!-- BEGIN: mix_4arts_01 -->
    <%--  adding  div tag only the container is a parsys --%>
    <c:if test="${componentContext.parent.component.name == 'parsys'}">
        <div class="mix_4arts_01">
    </c:if> 
    
        <div class="mix_4arts_01_top">
    
            <cq:include path="mix_1arts_08" resourceType="/apps/deportes/generics/components/content/mix/mix1arts08" />
    
        </div>
        <div class="mix_4arts_01_bottom">
    
            <cq:include path="mix_1arts_07_1" resourceType="/apps/deportes/generics/components/content/mix/mix1arts07" />
            <cq:include path="mix_1arts_07_2" resourceType="/apps/deportes/generics/components/content/mix/mix1arts07" />
            <cq:include path="mix_1arts_07_3" resourceType="/apps/deportes/generics/components/content/mix/mix1arts07" />
    
        </div>
        
    <%--  adding  div tag only the container is a parsys --%>
    <c:if test="${componentContext.parent.component.name == 'parsys'}">
        </div>
    </c:if>     
    <!-- END: mix_4arts_01 -->

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>
 
    <%--  adding a new div to move the parsys  --%>
    <c:if test = "${authorMode}"> 
        <div style="clear:both;"></div>
    </c:if>  
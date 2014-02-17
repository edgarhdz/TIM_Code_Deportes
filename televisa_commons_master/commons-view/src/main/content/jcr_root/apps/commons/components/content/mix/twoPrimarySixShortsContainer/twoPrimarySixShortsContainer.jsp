<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Generics Two Primary Six Shorts Container Component.
 *
 *  Displays a generic primary four shorts container component.
 *
 *  component id = mix_8arts_01
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
--%><%
%><%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%><%
%><%@page session="false" %>

<div class="mix_8arts_01">
    <div class="cont-left">

        <cq:include path="str_pleca_01_1" resourceType="/apps/commons/components/content/structure/singleTitleBox" />

        <cq:include path="mix_4arts_01_1" resourceType="/apps/commons/components/content/mix/primaryThreeShortsTeaserContainer" />

    </div>

    <div class="cont-left">

        <cq:include path="str_pleca_01_2" resourceType="/apps/commons/components/content/structure/singleTitleBox" />

        <cq:include path="mix_4arts_01_2" resourceType="/apps/commons/components/content/mix/primaryThreeShortsTeaserContainer" />

    </div>

    <%-- Clear the previous float so it will not overlap the next container. --%>
    <div style="float: none; clear: both;">&nbsp;</div>

</div>
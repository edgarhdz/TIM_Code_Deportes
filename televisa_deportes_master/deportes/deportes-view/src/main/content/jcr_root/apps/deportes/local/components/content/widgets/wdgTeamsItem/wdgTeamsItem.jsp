<%--
.

--%><%
%><%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>

<c:set var="iconPath">
    <tg:getImageAsset name="./fileReference" />
</c:set>

<c:choose>
    <c:when test="${empty iconPath}">
        <div class="equipo textcolor-title4">
            <img src="http://placehold.it/25x25"> TDN
        </div>
    </c:when>
    <c:otherwise>
        <div class="equipo textcolor-title4">
                <img class="toolsUtilsIcon" src="${iconPath}" border="0" height="25px" width="25px" />${properties.imgTitle}
        </div>
    </c:otherwise>
</c:choose>





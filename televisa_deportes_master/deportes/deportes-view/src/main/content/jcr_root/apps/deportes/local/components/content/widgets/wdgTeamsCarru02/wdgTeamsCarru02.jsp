
<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Teams Carousel Widget 02
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 09/07/2013  | Mario Taracena         | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/deportes/local/components/global.jsp"%>
<%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%


%>
<fmt:message key="deportes.wdgteamscarru02.title.label" var="altTitle" />
<fmt:message key="deportes.wdgteamscarru02.btn.text" var="altbtnText1" />
<fmt:message key="deportes.wdgteamscarru02.btn.text2" var="altbtnText2" />
<fmt:message key="deportes.wdgteamscarru02.league.label" var="altleague" />
<!-- BEGIN: wdg_teams_carru_02 -->
<div class="wdg_teams_carru_02">
    <div class="titulo_bar">
        <c:choose>
            <c:when test="${empty currentStyle.title}">
                <div class="titulo">[${altTitle}]</div>
            </c:when>
            <c:otherwise>
                <div class="titulo">${currentStyle.title}</div>
            </c:otherwise>
        </c:choose>

        <div class="ocultar">
            <c:choose>
                <c:when test="${empty currentStyle.btnText1}">
                    <a href="#"><i class="tvsa-caret-up"></i><span class="ocultar-todos">[${altbtnText1}]</span></a>
                </c:when>
                <c:otherwise>
                    <a href="#"><i class="tvsa-caret-up"></i><span class="ocultar-todos"> ${currentStyle.btnText1}</span></a>
                </c:otherwise>
            </c:choose>

        </div>
        <div class="mostrar">
            <c:choose>
                <c:when test="${empty currentStyle.btnText2}">
                    <a href="#"><i class="tvsa-caret-down"></i><span class="mostrar-todos">[${altbtnText2}]</span></a>
                </c:when>
                <c:otherwise>
                    <a href="#"><i class="tvsa-caret-down"></i><span class="mostrar-todos">${currentStyle.btnText2}</span></a>
                </c:otherwise>
            </c:choose>

        </div>
    </div>

    <div class="striked">
        <div class="line">
        <c:choose>
            <c:when test="${empty currentStyle.league}">
                <span class="up">[${altleague}]</span>
            </c:when>
            <c:otherwise>
                <span class="up">${currentStyle.league}</span>
            </c:otherwise>
        </c:choose>
        </div>
    </div>


    <div class="wrapper" >
        <div class="row" >
            <div class="equipos">
                <cq:include path="imgTeamsCarru02ItemsContainer" resourceType="foundation/components/parsys" />
            </div>
        </div>
    </div>
</div>


<!-- END: wdg_teams_carru_02 -->
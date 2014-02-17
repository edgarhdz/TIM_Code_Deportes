<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  wdgGoalsCarru01
 *  This component is based of the libs/foundation/components/reference.

 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer               | Changes
 * 1.0     | 08/20/2013  | jdiaz@xumak.com         | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */

--%>
<%@include file="/apps/deportes/local/components/global.jsp"%>

<%@page import="com.televisa.commons.services.services.InfoPageManagerService"%>

<%@page import="com.televisa.commons.services.datamodel.Note"%>
<%@page import="com.televisa.commons.services.datamodel.RenditionSize"%>
<%@page import="com.televisa.commons.services.datamodel.objects.impl.FilterVideoCarouselImpl"%>
<%@page import="com.televisa.commons.services.datamodel.objects.InfoPage"%>
<%@page import="com.televisa.commons.services.utilities.Utilities"%>

<%@page import="com.day.cq.tagging.TagManager"%>
<%@page import="com.day.cq.tagging.Tag"%>
<%@page import="java.util.Iterator"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.StringBuilder" %>


<fmt:message key="deportes.wdgGoalsCarru01.matchday.placeholder" var="placeholdermatchday"/>
<fmt:message key="deportes.wdgGoalsCarru01.tournament.placeholder" var="placeholdertournament"/>
<fmt:message key="deportes.wdgGoalsCarru01.team.placeholder" var="placeholderteam"/>
<fmt:message key="deportes.wdgGoalsCarru01.seeall.placeholder" var="placeholderseeall"/>

<%@page import="com.day.cq.wcm.api.WCMMode, com.day.cq.wcm.api.components.DropTarget" %>


<%
    TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
    List<Tag> tagsOne = new ArrayList<Tag>();
    Tag current;

    String tagpath = "";

    String[] tagsTourn = properties.get("cq:tournTags", String[].class);
    String[] currentTag = properties.get("cq:currentTourn", String[].class);

    if ( (null!=tagsTourn) && (null!=currentTag) ) {
        if ( (tagsTourn.length>0) && (currentTag.length>0) ) {
            Tag themeNameSpace = tagManager.resolve(tagsTourn[0]);
            current = tagManager.resolve(currentTag[0]);
            if (null!=themeNameSpace) {
                Iterator<Tag> themeNameSpaceIterator = themeNameSpace.listChildren();

                while(themeNameSpaceIterator.hasNext()){
                    tagsOne.add(themeNameSpaceIterator.next());
                }
            }
            tagpath = tagsTourn[0];
            pageContext.setAttribute("tagsOne", tagsOne);
            pageContext.setAttribute("tagpath", tagpath);
            pageContext.setAttribute("current", current);
        }
    }
%>
<c:set var="path" value="/content/televisa/deportes"/>
<c:set var="path" value="${fn:replace(path, '/', '__')}"/>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

<div class="wdg_goals_carru_01" data-enhance="false">

    <!-- BEGIN: str_pleca_01 -->
    <cq:include path="str_pleca" resourceType="/apps/deportes/generics/components/content/structure/strPleca01" />
    <!-- END: str_pleca_01 -->

    <div class="filterResultado">
        <div class="lineaResultado result1">
            <div class="filter">
                <div class="wdg_goals_carru_012_dropdown drop1">
                    <div class="wdg_goals_carru_012_dropdowncontent content1">
                        <p name="${current.name}">${current.title}</p>
                        <span class="tvsa-caret-down dropdown-gray"></span>
                    </div>
                    <div class="wdg_goals_carru_012_listcontainer list1">
                        <ul class="wdg_goals_carru_012_dropdownlist list1">
                            <c:forEach var="tags" items="${tagsOne}">
                                <li><p name="${tags.name}">${tags.title}</p></li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <h3>
                <c:choose>
                    <c:when test="${empty properties.tournament}">
                        ${placeholdertournament}
                    </c:when>
                    <c:otherwise>
                        ${properties.tournament}
                    </c:otherwise>
                </c:choose>
            </h3>
        </div>
        <div class="lineaResultado result2">
            <div class="filter">
                <div class="wdg_goals_carru_012_dropdown drop2">
                    <div class="wdg_goals_carru_012_dropdowncontent content2">


                    </div>
                    <div class="wdg_goals_carru_012_listcontainer list2">
                        <ul class="wdg_goals_carru_012_dropdownlist list2">

                        </ul>
                    </div>
                </div>
            </div>
            <h3>
                <c:choose>
                    <c:when test="${empty properties.matchday}">
                        ${placeholdermatchday}
                    </c:when>
                    <c:otherwise>
                        ${properties.matchday}
                    </c:otherwise>
                </c:choose>
            </h3>
        </div>
        <div class="lineaResultado result3">
            <div class="filter">
                <div class="wdg_goals_carru_012_dropdown drop3">
                    <div class="wdg_goals_carru_012_dropdowncontent content3">
                        <p>Todos los equipos</p>
                        <span class="tvsa-caret-down dropdown-gray"></span>
                    </div>
                    <div class="wdg_goals_carru_012_listcontainer list3">
                        <ul class="wdg_goals_carru_012_dropdownlist list3">

                        </ul>
                    </div>
                </div>
            </div>
            <h3>
                <c:choose>
                    <c:when test="${empty properties.team}">
                        ${placeholderteam}
                    </c:when>
                    <c:otherwise>
                        ${properties.team}
                    </c:otherwise>
                </c:choose>
            </h3>
        </div>
    </div>

    <ul id="deg">
        <div class="goalscontent"></div>
    </ul>

    <div> <a class="prev bginactive" title="Link Description" href="#"><span class="tvsa-caret-up"></span></a>
        <a class="next bgactive" title="Link Description" href="#"><span class="tvsa-caret-down"></span></a>
        <a class="full-timetable" href="#"> <span>
                        <c:choose>
                            <c:when test="${empty properties.seeall}">
                                ${placeholderseeall}
                            </c:when>
                            <c:otherwise>
                                ${properties.seeall}
                            </c:otherwise>
                        </c:choose>

        </span> </a> </div>
    <div class="degraded"></div>
</div>

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>

<script type="text/javascript">
    GoalCarru.initialize('${tagpath}', '${path}');
</script>
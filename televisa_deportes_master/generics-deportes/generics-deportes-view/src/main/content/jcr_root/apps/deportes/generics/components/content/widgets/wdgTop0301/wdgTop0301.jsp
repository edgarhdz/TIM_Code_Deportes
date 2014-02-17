<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Top 3 Component, this component will be display a list of notes,
 *  that can be entering manually or automatic using tags or parent path to show
 *  the most recents.
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 24/07/2013  | jbarrera               | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="genericsdeportes.wdgTop0301.title" var="componentname"/>
<fmt:message key="genericsdeportes.wdgTop0301.message.placeholder" var="placeholder"/>

<%
    pageContext.setAttribute("numberDisplay",properties.get("numberDisplay",3));
    pageContext.setAttribute("numberofitems",properties.get("numberofitems",0));
%>
<%-- Adding validation for the content --%>
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

<%-- adding target propety to the links --%>
<c:if test="${not empty properties.linkText}">
    <c:set var="target" value="target= '${properties.linkTarget}'"/>
</c:if>
<%-- adding title propety to the links --%>
<c:if test="${not empty properties.linkTitle}">
    <c:set var="title" value="title= '${properties.linkTitle}'" />
</c:if>
<%-- validation to adding link text --%>
<c:if test="${not empty properties.linkText}">
    <c:set var="text" value="${properties.linkText}" />
</c:if>


<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>

<!-- begin wdg_top03_01-->
<div class="wdg_top03_01"  data-enhance="false">
    <div class="wdg_carousa showArrows">
        <c:if test = "${authorMode}">
            <c:if test="${empty properties.mode || properties.mode == null}">
                ${placeholder }&nbsp;${componentname}
            </c:if>
            <c:if test="${properties.mode=='manual' &&  (numberofitems == null || numberofitems ==0)}">
                ${placeholder }&nbsp;${componentname}
            </c:if>
        </c:if>

        <!-- manual mode -->
        <c:if test="${(properties.mode=='manual' && numberofitems != null && numberofitems > 0) }">

            <div class="wdg_top03_01_title">
                <em><a ${link} ${target} ${title}> ${text} <span class="icon mobile"></span></a></em>
                <%-- adding validation to show the arrows buttons --%>
                <c:if test="${numberofitems > numberDisplay}">
                    <ul class="arrows">
                        <li><a href="#left" class="wdg_carousa_left carouselArrowLeft"><i class="tvsa-double-caret-left inactive"></i></a></li>
                        <li class="dotted-left"><a href="#right" class="wdg_carousa_right carouselArrowRight"><i class="tvsa-double-caret-right active"></i></a></li>
                    </ul>
                </c:if>
            </div>
            <div class="wdg_top03_01_carousel">
                <ul>
                    <c:set var="index" value="${0}" />
                    <c:set var="indexList" value="${0}" />
                    <c:forEach var="item" items ="${properties.path}" varStatus="loop">

                        <c:if test="${index==indexList}">
                            <li>
                                <c:set var="indexList" value="${indexList+numberDisplay}" />
                        </c:if>

                        <%-- adding NoteManagerService service --%>
                        <c:if test="${numberofitems==1}">
                            <tg:noteProvider path="${properties.path}"/>
                        </c:if>
                        <c:if test="${numberofitems > 1}">
                            <tg:noteProvider path="${properties.path[index]}"/>
                        </c:if>

                        <c:set var="rendition">
                            <tg:renditionSelector image="${note.noteImageAsset}" width="84" height="63"/>
                        </c:set>

                        <c:if test="${empty note.externalUrl}">
                            <c:set var="noteUrl" value="${note.url}"/>
                        </c:if>
                        <c:if test="${not empty note.externalUrl}">
                            <c:set var="noteUrl" value="${note.externalUrl}"/>
                        </c:if>

                        <div class="entry">
                            <a href="${noteUrl}.html" title="${note.title}" ${target}>
                                <img src="${rendition}" alt="${note.title}"/>
                                <span class="category"><c:out value="${note.topic}"/></span>
                                <h2><c:out value="${note.title}"/></h2>
                            </a>
                        </div>
                        <c:if test="${index==indexList-1}">
                            </li>
                        </c:if>
                        <c:set var="index" value="${index + 1}" />
                    </c:forEach>
                    <c:if test="${index!=listIndex}">
                        </li>
                    </c:if>
                </ul>
            </div>
        </c:if>

        <!-- mode auto -->
        <c:if test="${properties.mode == 'automatic'}">
            <c:choose>
                <c:when test="${not empty properties.parentPath && not empty properties.topic}">
                    <tg:getNotesFromPathAndTags name="notes" path="${properties.parentPath}" limit="${properties.limit}" tag="${properties.topic[0]}" noteType="genericNote" asList="true" index="0"/>
                </c:when>
                <c:when test="${empty properties.parentPath && not empty properties.topic}">
                    <tg:getNotesFromTags name="notes" tags="${properties.topic}" limit="${properties.limit}" asList="true" index="0"/>
                </c:when>
                <c:when test="${not empty properties.parentPath && empty properties.topic}">
                    <tg:getLatestNotes path="${properties.parentPath}"  name="notes" limit="${properties.limit}" noteType="genericNote" asList="true" index ="0"/>
                </c:when>
                <c:otherwise>
                    <tg:getLatestNotes path="${properties.parentPath}"  name="notes" limit="${properties.limit}" noteType="genericNote" asList="true" index="0"/>
                </c:otherwise>
            </c:choose>
            <div class="wdg_top03_01_title">
                <em><a ${link} ${target} ${title}> ${text} <span class="icon mobile"></span></a></em>
                <%-- adding validation to show the arrows buttons --%>
                <c:if test="${properties.limit != 'null' && properties.limit > numberDisplay }">
                    <ul class="arrows">
                        <li><a href="#left" class="wdg_carousa_left carouselArrowLeft"><i class="tvsa-double-caret-left inactive"></i></a></li>
                        <li class="dotted-left"><a href="#right" class="wdg_carousa_right carouselArrowRight"><i class="tvsa-double-caret-right active"></i></a></li>
                    </ul>
                </c:if>
            </div>


            <div class="wdg_top03_01_carousel">
                <ul>
                    <c:set var="index" value="${0}" />
                    <c:set var="indexList" value="${0}" />
                    <c:if test="${notes!=null}">
                        <c:forEach var="note" items="${notes}">
                            <c:if test="${index==indexList}">
                                <li>
                                    <c:set var="indexList" value="${indexList+numberDisplay}" />
                            </c:if>
                            <c:set var="rendition">
                                <tg:renditionSelector image="${note.noteImageAsset}" width="84" height="63"/>
                            </c:set>
                            <c:if test="${empty note.externalUrl}">
                                <c:set var="noteUrl" value="${note.url}"/>
                            </c:if>
                            <c:if test="${not empty note.externalUrl}">
                                <c:set var="noteUrl" value="${note.externalUrl}"/>
                            </c:if>
                            <div class="entry">
                                <a href="${noteUrl}.html" title="${note.title}" ${target}>
                                    <img src="${rendition}" alt="${note.title}"/>
                                    <span class="category"><c:out value="${note.topic}"/></span>
                                    <h2><c:out value="${note.title}"/></h2>
                                </a>
                            </div>
                            <c:if test="${index==indexList-1}">
                                </li>
                            </c:if>
                            <c:set var="index" value="${index + 1}" />
                        </c:forEach>
                        <c:if test="${index!=listIndex}">
                            </li>
                        </c:if>
                    </c:if>
                </ul>
            </div>
        </c:if>
        <!-- end auto -->
    </div>
    <div class="bullets">
        <div class="background-color1"></div><div></div><div></div>
    </div>
</div>
<!-- end wdg_top03_01 -->

<!-- end div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>
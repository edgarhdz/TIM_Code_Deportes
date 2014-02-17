<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Secondary Teaser component.
 *  This component is used to retrieved the image rendition of 192 * 108, the
 *  title and the description of a generic article, you can hide the Topic,
 *  the image, or the description using the checkbox options of the dialog.
 * 
 *  component id = mix_1arts_09
 *  
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 24/01/2013  | Juan Jose Pol.         | Initial Creation.
 * -----------------------------------------------------------------------------
 */
  ==============================================================================
--%>

<%@include file="/apps/deportes/generics/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="commons.editcomponent.placeholder" var="placeholdercomponentmessage"/>
<fmt:message key="commons.secondaryteaser.title" var="componentname"/>
<fmt:message key="commons.secondaryteaser.messageuser" var="messageuser"/>

<%-- adding NoteManagerService service --%>
<c:choose>
    <c:when test="${properties.mode == 'manual'}">
        <c:if test="${not empty properties.patharticle}">
            <tg:noteProvider path="${properties.patharticle}"/>
        </c:if>
    </c:when>
    <c:when test="${properties.mode == 'automatic'}">
        <c:choose>
            <c:when test="${not empty properties.parentPath && not empty properties.topic}">
                <tg:getNotesFromPathAndTags name="note" path="${properties.parentPath}" limit="1" index="${properties.noteIndex}" tag="${properties.topic[0]}" noteType="genericNote" asList="false"/>
            </c:when>
            <c:when test="${empty properties.parentPath && not empty properties.topic}">
                <tg:getNotesFromTags name="note" tags="${properties.topic}" limit="1" index="${properties.noteIndex}" asList="false"/>
            </c:when>
            <c:when test="${not empty properties.parentPath && empty properties.topic}">
                <tg:getLatestNotes path="${properties.parentPath}"  name="note" limit="1" noteType="genericNote" index="${properties.noteIndex}" asList="false"/>
            </c:when>
            <c:otherwise>
                <tg:getLatestNotes path="${properties.parentPath}"  name="note" limit="1" noteType="genericNote" index="${properties.noteIndex}" asList="false"/>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <tg:noteProvider path="${properties.patharticle}"/>
    </c:otherwise>
</c:choose>

<%-- adding validation to show the image context in the main image depending of the template --%>
<c:if test="${note != null}">
    <c:set var="rendition">
        <tg:renditionSelector image="${note.noteImageAsset}" width="192" height="108"/>
    </c:set>
    <c:if test="${note.photo == true }">
        <c:set var="imageContext" value="mix_1arts_09_sprite photo-corner" />
    </c:if>
    <c:if test="${note.video == true}">
        <c:set var="imageContext" value="mix_1arts_09_sprite video-corner" />
    </c:if>
</c:if>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>

<%--  Merge hmtl and css for the mix_1arts_09 Component --%>
<c:choose>

    <c:when test="${note != null}">

        <!-- BEGIN:mix_1arts_09 -->
        <%--  adding the div tag only the container is a parsys --%>
        <c:if test="${componentContext.parent.component.name == 'parsys'}">
            <div class="mix_1arts_09">
        </c:if>


        <div>
            <ul class="mix_1arts_09_thumb">
                <li>
                    <a href="${note.url}.html" title="${note.title}"  target="${properties.linkTarget}" >

                        <%-- validation to show the image if exist the rendition--%>
                        <c:if test="${not empty rendition}">

                            <c:if test="${properties.showimage == true}">
                                <div class="imgWrapper">
                                    <img src="${rendition}" alt="${note.title}"/>
                                </div>
                                <div class="lineseparator"></div>
                                <%-- adding validation to show the image context --%>
                                <c:if test="${properties.showcontext == true}">
                                    <span class="${imageContext}"></span>
                                </c:if>

                            </c:if>

                        </c:if>

                        <%-- adding validation to show the topic--%>
                        <c:if test="${properties.showtopic == true}">
                            <strong class="category"> <c:out value="${note.topic}"/></strong>
                        </c:if>


                        <%-- adding validation to show the description--%>
                        <c:if test="${properties.showtitle == true}">
                            <h3><c:out value="${note.title}"/></h3>
                        </c:if>

                        <c:if test="${properties.showimage != 'true' && properties.showtopic != 'true' &&  properties.showtitle != 'true' }">
                            <h3 class="cq-texthint-placeholder" id="cq-gen96"> <c:out value="${messageuser}"/></h3>
                        </c:if>

                        <i class="icon tvsa-caret-right"></i>

                    </a>
                </li>
            </ul>
        </div>

        <c:if test="${componentContext.parent.component.name == 'parsys'}">
            </div>
        </c:if>
        <!--END:mix_1arts_09-->

    </c:when>
    <c:otherwise>

        <%--  show message only if this is author mode --%>
        <c:if test = "${authorMode}">
            <div class="mix_1arts_09">
                <h3 class="cq-texthint-placeholder" id="cq-gen96"><c:out value="${placeholdercomponentmessage}"/> <c:out value="${componentname}"/></h3>
                <img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="" id="cq-gen97">
            </div>
        </c:if>

    </c:otherwise>
</c:choose>

<%--  adding a new div to move the parsys --%>
<c:if test = "${authorMode}">
    <div style="clear:both;"></div>
</c:if>


<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>


<%--  remove note object in pageContext when add two or more primaryTeaser in a container --%>
<%pageContext.removeAttribute("note");%>
<%--
   /*
    * DESCRIPTION
    * -----------------------------------------------------------------------------
    *  Detailed Primary Teaser component.
    *  This component is used to retrieved the image rendition of 300 * 168, and
    *  the description of a generic article, and get the template used in the generic
    *  article, also the author can set a topic in the dialog of this component,
    *  the user can choose between show and not show the image context...
    *
    *
    *  component id = mix_1arts_10
    *
    * -----------------------------------------------------------------------------
    *
    * CHANGE HISTORY
    * -----------------------------------------------------------------------------
    * Version | Date        | Developer              | Changes
    * 1.0     | 07/23/2013  | Antonio Oliva          | Initial Creation.
    * -----------------------------------------------------------------------------
    *
    * ====================================================================================
    */
--%>
<%@include file="/apps/deportes/generics/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="commons.editcomponent.placeholder" var="placeholdercomponentmessage"/>
<fmt:message key="genericsdeportes.mix1arts10.title" var="componentname"/>
<tg:noteRemover name="note"/>

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
                    <tg:getNotesFromPathAndTags name="note" path="${properties.parentPath}" limit="1" index="${properties.noteIndex}" noteType="genericNote"  tag="${properties.topic[0]}" asList="false"/>
                </c:when>
				<c:when test="${empty properties.parentPath && not empty properties.topic}">
					<tg:getNotesFromTags name="note" tags="${properties.topic}" limit="1" index="${properties.noteIndex}" asList="false"/>
                </c:when>
				<c:when test="${not empty properties.parentPath && empty properties.topic}">
                    <tg:getLatestNotes path="${properties.parentPath}"  name="note" limit="1" index="${properties.noteIndex}" noteType="genericNote"  asList="false"/>
                </c:when>
                <c:otherwise>
                    <tg:getLatestNotes path="${properties.parentPath}"  name="note" limit="1" index="${properties.noteIndex}" noteType="genericNote"  asList="false"/>
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
        <tg:renditionSelector image="${note.noteImageAsset}" width="300" height="169"/>
    </c:set>
    <c:choose>
        <c:when test="${fn:contains(rendition, '169')}">
            <c:set var="renditionAlpha" value="${rendition}" />
        </c:when>
        <c:otherwise>
            <c:set var="renditionAlpha">
                <tg:renditionSelector image="${note.noteImageAsset}" width="300" height="168"/>
            </c:set>
        </c:otherwise>
    </c:choose>


    <c:if test="${note.photo == true }">
        <c:set var="imageContext" value="sprite photo-corner" />
    </c:if>
    <c:if test="${note.video == true}">
        <c:set var="imageContext" value="sprite video-corner" />
    </c:if>
</c:if>

 <%-- Date Published --%>
 <c:set var="date" value="${note.pubDate.time}" />

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>

<!-- BEGIN: mix_1arts_10 -->

<%-- Merge HTML and CSS for the mix_1arts_10 Component--%>
<c:choose>
    <c:when test="${note != null}">
        <div class="mix_1arts_10">
            <div class="mix_1arts_10_title">
                <a href="${note.url}.html"  target="${properties.linkTarget}" title="${note.title}">
                    <h1 class="textcolor-title1"><c:out value="${note.author}"/></h1>
                </a>
            </div>
            <div>
                <article class="articleMedium">


                    <a class="mix_1arts_10_imagecontainer" href="${note.url}.html"  target="${properties.linkTarget}"  title="${note.title}">
                        <c:if test="${not empty renditionAlpha}">
                            <img src="${renditionAlpha}" alt="${note.title}"/>

                            <c:if test="${properties.showcontext == 'true' && note.video == true}">
                                <div class="playersprite play background-color1" id="videobtn"></div>
                            </c:if>

                            <%-- show image context --%>
                           <c:if test="${properties.showcontext == 'true'}">
                                <span class="${imageContext}"></span>
                            </c:if>
                        </c:if>

                    </a>

                    <div class="mix_1arts_10_textcontainer">
                        <h1><a class="textcolor-title1" href="${note.url}.html" target="${properties.linkTarget}" title="${note.title}"><c:out value="${note.author}"/></a></h1>
                        <h2><a class="textcolor-title2" href="${note.url}.html"  target="${properties.linkTarget}" title="${note.title}"><c:out value="${note.topic}"/></a></h2>
                        <h3><a class="textcolor-title6" href="${note.url}.html"  target="${properties.linkTarget}"  title="Link Description"><fmt:formatDate pattern="yyyy-MM-dd" value="${date}" /></a></h3>
                        <h4><a href="${note.url}.html"  target="${properties.linkTarget}"  title="Link Description"><c:out value="${note.title}"/></a></h4>
                        <h5><a href="${note.url}.html"  target="${properties.linkTarget}"  title="Link Description"><c:out value="${note.description}"/></a></h5>
                        <span class="icon"></span>
                    </div>
                </article>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <%-- show message only if this is in author mode --%>
        <c:if test = "${authorMode}">
            <div class="mix_1arts_10">
                <div>
                    <article class="articleMedium">
                        <h3 class="cq-texthint-placeholder" id="cq-gen96"><c:out value="${placeholdercomponentmessage}"/> <c:out value="${componentname}"/></h3>
                        <img src="/libs/cq/ui/resources/0.gif" class="cq-teaser-placeholder" alt="" id="cq-gen97" style="width:99%; height: 80%;">
                    </article>

                </div>
            </div>
        </c:if>
    </c:otherwise>
</c:choose>
<%--  adding a new div to move the parsys  --%>
<c:if test = "${authorMode}">
    <div style="clear:both;"></div>
</c:if>

<!-- END: mix_1arts_10 -->
 <!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>




<%--  remove note object in pageContext when add two or more primaryTeaser in a container --%>
<%pageContext.removeAttribute("note");%>
<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Feed Generator Component
 *
 *  This component generates the feed from a note.
 *
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 28/02/2013  | Gerardo Escobar        | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page import="com.day.cq.wcm.api.components.IncludeOptions"%><%
%><%@page import="com.day.cq.wcm.api.WCMMode"%><%
%><%@page session="false" %><%
    response.setContentType("text/xml");
    IncludeOptions.getOptions(request, true).forceSameContext(Boolean.TRUE);
    WCMMode.PREVIEW.toRequest(request);
%>

<c:if test="${!empty properties.pathProgram}">
	<c:set var="path" value="${properties.pathProgram}" />
	<tg:namedNoteProvider name="note" path="${path}" />
	<c:if test="${!empty note}">

<item>
	<title><![CDATA[${note.title}]]></title>
	<link>${note.url}</link>
	<author><![CDATA[${note.author}]]></author>
	<description><![CDATA[${note.description}]]></description>
	<pubDate><fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${note.pubDate.time}" /></pubDate>
	<media:thumbnail url='<tg:renditionSelector image="${note.noteImageAsset}" width="120" height="90" />'/>
	<media:group>
		<media:content url='<tg:renditionSelector image="${note.noteImageAsset}" width="70"  height="70"  />' medium='image' height='70'  width='70'  />
		<media:content url='<tg:renditionSelector image="${note.noteImageAsset}" width="90"  height="120" />' medium='image' height='90'  width='120' />
		<media:content url='<tg:renditionSelector image="${note.noteImageAsset}" width="100" height="270" />' medium='image' height='100' width='270' />
		<media:content url='<tg:renditionSelector image="${note.noteImageAsset}" width="100" height="298" />' medium='image' height='100' width='298' />
		<media:content url='<tg:renditionSelector image="${note.noteImageAsset}" width="150" height="300" />' medium='image' height='150' width='300' />
		<media:content url='<tg:renditionSelector image="${note.noteImageAsset}" width="250" height="400" />' medium='image' height='250' width='400' />
		<media:content url='<tg:renditionSelector image="${note.noteImageAsset}" width="348" height="619" />' medium='image' height='348' width='619' />
	</media:group>
	<content:encoded><![CDATA[<c:out value="${note.content}" escapeXml="false" />]]></content:encoded>
	<guid isPermaLink='false'>${note.identifier}</guid>
	<media:keywords><![CDATA[${note.stringTags}]]></media:keywords>
	<comments>http://comentarios.esmas.com/feeds/comentarios.php?url=${note.url}.html</comments>
	<category><![CDATA[${note.category}]]></category>
</item>

	</c:if>
</c:if>

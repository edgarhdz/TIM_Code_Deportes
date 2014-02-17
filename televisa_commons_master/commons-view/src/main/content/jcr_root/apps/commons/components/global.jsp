<%--

This is the 'Televisa Generics' project global JSP.
This JSP should be included at the top of all custom components in the 'Televisa Generics' project.

--%>

<%@include file="/libs/foundation/global.jsp" %><%
%><%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@page import="com.day.cq.wcm.api.WCMMode" import="com.day.cq.i18n.I18n"%>
<%@ taglib prefix="tg" uri="http://commons.televisa.com/taglibs/commons-taglib" %>
<cq:setContentBundle/>

<c:set var="authorMode" value="<%=WCMMode.fromRequest(request) == WCMMode.EDIT%>" scope="request"/>
<c:set var="designMode" value="<%=WCMMode.fromRequest(request) == WCMMode.DESIGN%>" scope="request"/>
<c:set var="previewMode" value="<%=(WCMMode.fromRequest(request) != WCMMode.EDIT) && (WCMMode.fromRequest(request) != WCMMode.DESIGN)%>" scope="request"/>
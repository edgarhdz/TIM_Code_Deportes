<%@include file="/apps/deportes/local/components/global.jsp"%>
<%
    slingRequest.setAttribute( "configuration", "true" );
%>

<fmt:message key="commons.menulist.title" var="placeholderMainList"/>
<fmt:message key="commons.header.title" var="placeholderHeader"/>
<fmt:message key="commons.alternatelist.title" var="placeholderAlternateList"/>
<fmt:message key="commons.headerfilter.title" var="placeholderHeaderFilter"/>
<fmt:message key="commons.paragraphselection.title" var="placeholderParagraph"/>
<fmt:message key="commons.headercontent.title" var="placeholderHeaderContent"/>
<fmt:message key="commons.footercontent.title" var="placeholderFooterContent"/>


<div>
    <h2><%=currentPage.getTitle()%></h2>

    <div style="width: 80%; margin: 1%; height: 100px">
        <div style="float:left;">
        <h4>${placeholderMainList} </h4>
        <cq:include path="mainlist"
            resourceType="/apps/commons/components/content/base/linklist" />
        </div>
        <div style="float:right;">
        <h4> ${placeholderAlternateList} </h4>
        <cq:include path="altlist"
            resourceType="/apps/commons/components/content/base/linklist" />
        </div>
        <div style="clear:both"></div>
    </div>

    <div class="sideright" style="width: 80%; margin: 1%;">

        <h4>${placeholderHeader}</h4>
        <cq:include path="header" resourceType="foundation/components/parsys" />

        <div class="containerSpace" style="padding: 25px;"></div>

        <h4>${placeholderHeaderFilter}</h4>
        <cq:include path="headerFiltering" resourceType="/apps/commons/components/content/navigation/navHeader01Filtering" />


        <h4>${placeholderParagraph}</h4>
        <cq:include path="paragraphselection" resourceType="foundation/components/parsys" />


        <div class="containerSpace" style="padding: 25px;"></div>

        <h4>${placeholderHeaderContent}</h4>
        <cq:include path="headercontent" resourceType="foundation/components/parsys" />
        
        <div class="containerSpace" style="padding: 100px; margin-bottom:100px;"></div>

        <h3>${placeholderFooterContent}</h3>
        <cq:include path="footer" resourceType="foundation/components/parsys"/>
    </div>
</div>

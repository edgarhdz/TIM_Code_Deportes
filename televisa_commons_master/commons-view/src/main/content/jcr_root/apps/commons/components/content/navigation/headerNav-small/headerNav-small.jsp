
<%--
 * DESCRIPTION
 *  Header smaller component for Commons Televisa
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 14/01/2013  | jdiaz@xumak.com        | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%><%
%><%@include file="/apps/commons/components/global.jsp"%><%
%>
<fmt:message key="commons.navheader01.description" var="placeholdermaintitle" />

<!-- BEGIN: nav_header_01 -->
<header class="main-header">  

<header class="nav_header_01">
    <div class="nav_header_01_top">
        <a href="#menu" class="nav_header_01_mobile">
            <span class="nav_header_01_sprite nav_header_01_menu">Men&uacute; Principal</span>
        </a>
        <a href="http://www.televisa.com" class="nav_header_01_logotv">
            <span class="nav_header_01_sprite nav_header_01_logotv"></span>
        </a>

    </div>

    <div class="nav_header_01_toggleable">
        <div class="nav_header_01_wrapper">
            <div class="nav_header_01_news">
               
            </div>
        </div>
    </div>

    <div class="nav_header_01_section nav_header_01_news">
        <c:if test="${not empty properties.mainTitle}">
            <h2><a href="#"> ${properties.mainTitle} <span></span></a></h2>                        
        </c:if>
        <c:if test="${empty properties.mainTitle}">
            <h2><a href="#"> ${placeholdermaintitle} <span></span></a></h2>
        </c:if>
        
    </div>
</header>

 </header> 
<!-- END: nav_header_01 -->
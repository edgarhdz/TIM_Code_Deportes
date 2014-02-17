
<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Twitter Feed component.
 *  This component is the reponsible for communicate to twitter for recover the content.

 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer               | Changes
 * 1.0     | 28/01/2013  | Luis Jose Sztul         | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.day.cq.wcm.foundation.Image"%>    

<fmt:message key="commons.twitterList.placeholder" var="placeholder"/>

<%
    ArrayList<String> queryTerms = new ArrayList<String>();
    
    if(properties.containsKey("twitterList")) {
        String[] twitterList = properties.get("twitterList", String[].class);
        
        for (int i = 0; i < twitterList.length; i++) {
            twitterList[i] = "from:" + twitterList[i];
            queryTerms.add(twitterList[i]);
        }   
    }

    // Build the query string by joining all query terms with " OR "
    String queryString = StringUtils.join(queryTerms.toArray(new String[0]), " OR ");   
%>

<c:set var="numberOfTweets" value="${properties.numberOfTweets}"/>
<c:if test="${empty numberOfTweets}">
    <c:set var="numberOfTweets" value="1"/>
</c:if>

<c:set var="dynamicId" value="<%= (int)(Math.random()*999999) %>"/>
<c:if test="${empty dynamicId}">
    <c:set var="dynamicId" value="0"/>
</c:if>

<c:choose>
    <c:when test="${not empty properties.numberOfTweets && not empty properties.twitterList}">
        <ul id="${dynamicId}"></ul>
        <script>        
            jQuery(document).ready(function ($) {
                    var data = {q: "<%= queryString %>",rpp: "${numberOfTweets}"};
                    components.twitterListFeed.initTwitterFeed(data, $("#${dynamicId}"));    
            });
        </script>        
    </c:when>
    <c:otherwise>
        <h2>${placeholder}</h2>
    </c:otherwise>
</c:choose>
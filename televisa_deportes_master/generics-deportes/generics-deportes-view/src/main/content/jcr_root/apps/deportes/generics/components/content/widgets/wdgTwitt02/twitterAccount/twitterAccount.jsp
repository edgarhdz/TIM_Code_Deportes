
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

<fmt:message key="commons.twitterAccount.placeholder" var="placeholder"/>

<%
    ArrayList<String> queryTerms = new ArrayList<String>();
    String[] twitterAccounts = new String[0];
    if(properties.containsKey("twitterAccounts")) {
        twitterAccounts = properties.get("twitterAccounts", String[].class);
        
        for (int i = 0; i < twitterAccounts.length; i++) {
            twitterAccounts[i] = "from:" + twitterAccounts[i];
            queryTerms.add(twitterAccounts[i]);
        }   
        pageContext.setAttribute("twitterAccounts", twitterAccounts);
    }

    // Build the query string by joining all query terms with " OR "
    String queryString = StringUtils.join(queryTerms.toArray(new String[0]), " OR ");   
%>

<c:set var="twitterAccountsLength" value="<%= twitterAccounts.length %>"/>

<c:set var="numberOfTweets" value="0"/>

<c:choose>
    <c:when test="${not empty twitterAccountsLength}">
        <c:choose>
            <c:when test="${twitterAccountsLength <= 6}">
                <c:set var="numberOfTweets" value="2"/>
            </c:when>
            <c:otherwise>
                <c:set var="numberOfTweets" value="1"/>
            </c:otherwise>
        </c:choose>        
    </c:when>
    <c:otherwise>
        <c:set var="twitterAccountsLength" value="0"/>
    </c:otherwise>
</c:choose>

<c:set var="dynamicId" value="<%= (int)(Math.random()*999999) %>"/>
<c:if test="${empty dynamicId}">
    <c:set var="dynamicId" value="0"/>
</c:if>

<c:choose>
    <c:when test="${(numberOfTweets > 0) && (twitterAccountsLength > 0)}">
        <ul id="${dynamicId}"></ul>
        <script>        
            jQuery(document).ready(function ($) {            
                    var paramTweets = [];
                    <c:forEach var="tweet" begin="0" end="${twitterAccountsLength}">
                        paramTweets.push({q: '${twitterAccounts[tweet]}',rpp: "${numberOfTweets}"});
                    </c:forEach>
                    components.twitterAccountFeed.initTwitterFeed(paramTweets, $("#${dynamicId}"));    
            });
        </script>            
    </c:when>
    <c:otherwise>
        <h4 class="cq-texthint-placeholder" >${placeholder}</h4>
    </c:otherwise>
</c:choose>
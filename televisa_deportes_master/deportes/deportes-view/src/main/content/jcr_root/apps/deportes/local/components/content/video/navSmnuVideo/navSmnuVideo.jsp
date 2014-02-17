<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  nav_smnu_video component.
 *  Contains both navSmnuVideo01 and navSmnuVideo02.
 *  navigation components for livestream akamai templates
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 06/11/2013  | Jorge Diaz             | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/deportes/local/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page session="false" %>
<cq:includeClientLib categories="deportes.navSmnuVideo02" />
<cq:includeClientLib categories="deportes.navSmnuVideo01" />


<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>

<!-- BEGIN: nav_smnu_video-->

<c:choose>
    <c:when test="${properties.navVideoType=='nav01'||empty properties.navVideoType}">
        <div class="nav_smnu_video_01">
            <img src="${properties.fileReference}" class="tdlogo">
    </c:when>
    <c:when test="${properties.navVideoType=='nav02'}">
        <img src="${properties.fileReference}" class="tdlogo">
        <div class="nav_smnu_video_02">
    </c:when>
</c:choose>


<div class="sings">Señales</div>
<div class="cont">
    <div class="navarrowleft">
        <a class="wdg_matchesresult_navleft" href="#left"> <span
                class="navlefticon"><i class="tvsa-double-caret-left inactivenav"></i></span>
        </a>
    </div>
    <div class="container">
        <c:choose>
            <c:when test="${properties.navVideoType=='nav01'||empty properties.navVideoType}">
                <div class="nav_smnu_video_01_bar type1a">
            </c:when>
            <c:when test="${properties.navVideoType=='nav02'}">
                <div class="nav_smnu_video_02_bar type1a">
            </c:when>
        </c:choose>

        <div class="degraded_darked_left"></div>
        <ul class="deg">


            <c:if test="${properties.numberofitems>0}">

                <c:choose>
                    <c:when test="${properties.numberofitems==1}">
                        <li class="current" m3u8="${properties.m3u8}" video="${properties.videoUrl}"><a href="#" title="${properties.description}"> ${properties.name} </a></li>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="i" begin="0" end="${properties.numberofitems-1}" varStatus="loop">

                            <c:if test="${i==0}">
                                <li class="current" m3u8="${properties.m3u8[i]}" video="${properties.videoUrl[i]}"><a href="#" title="${properties.description[i]}"> ${properties.name[i]} </a></li>
                            </c:if>
                            <c:if test="${i>0}">
                                <li m3u8="${properties.m3u8[i]}" video="${properties.videoUrl[i]}"><a href="#" title="${properties.description[i]}">${properties.name[i]}</a></li>
                            </c:if>
                        </c:forEach>

                    </c:otherwise>

                </c:choose>


            </c:if>
        </ul>
        <div class="degraded_darked_right"></div>
    </div>
</div>
<div class="navarrowright">
    <a class="wdg_matchesresult_navright" href="#right">
        <span class="navrighticon"><i class="tvsa-double-caret-right"></i></span>
    </a>
</div>
</div>
</div>
<!-- END: nav_smnu_video_02 -->

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>
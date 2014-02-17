<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 * Teams Carousel Widget 01
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 11/07/2013  | Mario Taracena		  | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%><%
    %><%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" %><%
%><%
	// TODO add you code here
%>
<fmt:message key="deportes.wdgteamscarru02.title.label" var="altTitle" />
<fmt:message key="deportes.wdgteamscarru02.btn.text" var="altbtnText1" />
<fmt:message key="deportes.wdgteamscarru02.btn.text2" var="altbtnText2" />
<fmt:message key="deportes.wdgteamscarru01.tab2.title" var="altleague1Name" />
<fmt:message key="deportes.wdgteamscarru01.tab3.title" var="altleague2Name" />
<fmt:message key="deportes.wdgteamscarru01.tag1.label" var="altleague1tag1" />
<fmt:message key="deportes.wdgteamscarru01.tag2.label" var="altleague1tag2" />
<fmt:message key="deportes.wdgteamscarru01.tag3.label" var="altleague1tag3" />
<fmt:message key="deportes.wdgteamscarru03.tag4.label" var="altleague1tag4" />

<!-- Add styles to devices -->
<c:if test="${not empty currentStyle.hideDevice}">
    <div class="${currentStyle.hideDevice}">
</c:if>
<!-- BEGIN: wdg_teams_carru_03 -->
<c:choose>
    <c:when test="${authorMode}">
        <div class="wdg_teams_carru_03" style="overflow: scroll;">
    </c:when>
    <c:otherwise>
        <div class="wdg_teams_carru_03">
    </c:otherwise>
</c:choose>



    <div class="titulo_bar">

        <c:choose>
            <c:when test="${empty currentStyle.title}">
                <div class="titulo textcolor-title1">[${altTitle}]</div>
            </c:when>
            <c:otherwise>
                <div class="titulo textcolor-title1">${currentStyle.title}</div>
            </c:otherwise>
        </c:choose>

        <div class="ocultar">
            <c:choose>
                <c:when test="${empty currentStyle.btnText1}">
                    <a href="#" class="textcolor-title1"><i class="tvsa-caret-up"></i>[${altbtnText1}]</a>
                </c:when>
                <c:otherwise>
                    <a href="#" class="textcolor-title1"><i class="tvsa-caret-up"></i> ${currentStyle.btnText1}</a>
                </c:otherwise>
            </c:choose>

        </div>
        <div class="mostrar">
            <c:choose>
                <c:when test="${empty currentStyle.btnText2}">
                    <a href="#" class="textcolor-title1"><i class="tvsa-caret-down"></i>[${altbtnText2}]</a>
                </c:when>
                <c:otherwise>
                    <a href="#" class="textcolor-title1"><i class="tvsa-caret-down"></i>${currentStyle.btnText2}</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="wrapper">

        <div class="row">

            <c:choose>
                <c:when test="${empty currentStyle.league1Name}">
                    <div class="liga_name textcolor-title4">[${altleague1Name}]</div>
                </c:when>
                <c:otherwise>
                    <div class="liga_name textcolor-title4">${currentStyle.league1Name}</div>
                </c:otherwise>
            </c:choose>

            <div class="equipos">
                <c:choose>
                    <c:when test="${empty currentStyle.league1tag1}">
                        <div class="striked"><span>[${altleague1tag1}]</span></div>
                    </c:when>
                    <c:otherwise>
                        <div class="striked"><span>${currentStyle.league1tag1}</span></div>
                    </c:otherwise>
                </c:choose>

                <cq:include path="imgTeamsCarru03ItemsContainer ItemHolderone" resourceType="foundation/components/parsys" />
            </div>

            <div class="vseparator"></div>

            <div class="equipos">

                <c:choose>
                    <c:when test="${empty currentStyle.league1tag2}">
                        <div class="striked"><span>[${altleague1tag2}]</span></div>
                    </c:when>
                    <c:otherwise>
                        <div class="striked"><span>${currentStyle.league1tag2}</span></div>
                    </c:otherwise>
                </c:choose>
                <cq:include path="imgTeamsCarru03ItemsContainer ItemHoldertwoo" resourceType="foundation/components/parsys" />
            </div>

            <div class="vseparator"></div>

            <div class="equipos">

                <c:choose>
                    <c:when test="${empty currentStyle.league1tag3}">
                        <div class="striked"><span>[${altleague1tag3}]</span></div>
                    </c:when>
                    <c:otherwise>
                        <div class="striked"><span>${currentStyle.league1tag3}</span></div>
                    </c:otherwise>
                </c:choose>
                <cq:include path="imgTeamsCarru03ItemsContainer ItemHolderthree" resourceType="foundation/components/parsys" />
            </div>

            <div class="vseparator"></div>

            <div class="equipos">

                <c:choose>
                    <c:when test="${empty currentStyle.league1tag4}">
                        <div class="striked"><span>[${altleague1tag4}]</span></div>
                    </c:when>
                    <c:otherwise>
                        <div class="striked"><span>${currentStyle.league1tag4}</span></div>
                    </c:otherwise>
                </c:choose>
                <cq:include path="imgTeamsCarru03ItemsContainer ItemHolderfour" resourceType="foundation/components/parsys" />
            </div>

        </div>

        <div class="row">
            <c:choose>
                <c:when test="${empty currentStyle.league2Name}">
                    <div class="liga_name textcolor-title4">[${altleague2Name}]</div>
                </c:when>
                <c:otherwise>
                    <div class="liga_name textcolor-title4">${currentStyle.league2Name}</div>
                </c:otherwise>
            </c:choose>

            <div class="equipos">

                <c:choose>
                    <c:when test="${empty currentStyle.league2tag1}">
                        <div class="striked"><span>[${altleague1tag1}]</span></div>
                    </c:when>
                    <c:otherwise>
                        <div class="striked"><span>${currentStyle.league2tag1}</span></div>
                    </c:otherwise>
                </c:choose>
                <cq:include path="imgTeamsCarru03ItemsContainer ItemHolderfive" resourceType="foundation/components/parsys" />
            </div>

            <div class="vseparator"></div>

            <div class="equipos">

                <c:choose>
                    <c:when test="${empty currentStyle.league2tag2}">
                        <div class="striked"><span>[${altleague1tag2}]</span></div>
                    </c:when>
                    <c:otherwise>
                        <div class="striked"><span>${currentStyle.league2tag2}</span></div>
                    </c:otherwise>
                </c:choose>
                <cq:include path="imgTeamsCarru03ItemsContainer ItemHoldersix" resourceType="foundation/components/parsys" />
            </div>

            <div class="vseparator"></div>

            <div class="equipos">

                <c:choose>
                    <c:when test="${empty currentStyle.league2tag3}">
                        <div class="striked"><span>[${altleague1tag3}]</span></div>
                    </c:when>
                    <c:otherwise>
                        <div class="striked"><span>${currentStyle.league2tag3}</span></div>
                    </c:otherwise>
                </c:choose>
                <cq:include path="imgTeamsCarru03ItemsContainer ItemHolderseven" resourceType="foundation/components/parsys" />
            </div>
            <div class="vseparator"></div>

            <div class="equipos">

                <c:choose>
                    <c:when test="${empty currentStyle.league2tag4}">
                        <div class="striked"><span>[${altleague1tag4}]</span></div>
                    </c:when>
                    <c:otherwise>
                        <div class="striked"><span>${currentStyle.league2tag4}</span></div>
                    </c:otherwise>
                </c:choose>
                <cq:include path="imgTeamsCarru03ItemsContainer ItemHoldereight" resourceType="foundation/components/parsys" />
            </div>

        </div>



    </div>
</div>
 <!-- close div of styles to devices -->
<c:if test="${not empty currentStyle.hideDevice}">
    </div>
</c:if>
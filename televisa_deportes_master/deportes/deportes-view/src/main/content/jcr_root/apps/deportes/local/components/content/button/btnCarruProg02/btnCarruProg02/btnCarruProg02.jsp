<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 * btn_carru_prog_02
 *
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 12/08/2013  | jbarrera               | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="deportes.btnCarruProg02.placeholder" var="placeholder"/>

<% pageContext.setAttribute("numberofitems",properties.get("numberofitems",0));%>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>


<c:set var="plecaVisible" value="true" />
<c:if
	test="${not empty properties.linkHide && properties.linkHide == true}">
	<c:set var="plecaVisible" value="false" />
</c:if>

<c:if test="${numberofitems == null || numberofitems == 0 }">
    <c:if test="${authorMode}">
        <h4 class="cq-texthint-placeholder">${placeholder}</h4>
    </c:if>
</c:if>

<div class="btn_carru_prog_02" data-enhance="false">

    <c:if test="${numberofitems > 0 }">
        <div class="pleca">
            <!-- BEGIN: wdg_scroll_dropdown -->
            <div class="wdg_scroll_dropdown">
             <c:if test="${plecaVisible}">
             	  <div class="wdg_tennisresult_01_pleca background-color-pleca1">
                    <p class="textcolor-title3">${properties.title}</p>
                </div>
             </c:if>
                <div class="wdg_scroll_list background-gradient-content2">
                    <p class="wdg_scroll_value"></p>
                    <div class="wdg_tennisresult_01_click background-color-pleca1">
                        <span class="tvsa-caret-down"></span>
                    </div>
                    <div class="wdg_scroll_events">
                        <ul class="wdg_scroll_elements">
                            <c:if test="${numberofitems != null && numberofitems > 0 }">
                                <c:set var="index" value="${0}" />
                                <c:forEach var="item" items ="${properties.path}" varStatus="loop">
                                    <c:set var="pageTitle">
                                        <tg:getPageTitleFromPath path="${item}" />
                                    </c:set>
                                    <li><p path="${item}">${pageTitle}</p></li>
                                    <c:set var="index" value="${index + 1}" />
                                </c:forEach>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- END: wdg_scroll_dropdown -->
        </div>

        <div class="typecarruse">
            <c:if test="${numberofitems > 0 }">
                <div class="title">
                    <ul class="flechas">
                        <li><a href="#left" title="Link Description" class="left"><i class="tvsa-double-caret-left"></i></a></li>
                        <li><a class="right" href="#right" title="Link Description"><i class="tvsa-double-caret-right"></i></a></li>
                    </ul>
                </div>
            </c:if>
            <!-- END:PLECA -->
            <!-- add reference -->
            <div class="carrusel">
                <ul class="carrousa">
                    <cq:include path="refEvent" resourceType="/apps/deportes/local/components/content/button/btnCarruProg02/btnReferenceCarruProg02"/>
                </ul>
            </div>
        </div>
        <div class="bullets" id="b_tablet"></div>
        <div class="bullets" id="b_mobile"></div>
    </c:if>
</div>

<!-- end div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>
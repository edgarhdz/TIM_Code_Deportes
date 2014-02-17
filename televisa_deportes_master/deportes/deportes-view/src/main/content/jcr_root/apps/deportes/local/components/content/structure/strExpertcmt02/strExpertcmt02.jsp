<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Expert Comments Component
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 11/06/2013  | Antonio Oliva          | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/deportes/local/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="deportes.component.message.placeholder" var="placeholder" />
<fmt:message key="deportes.strexpertcmt02.jcr.title" var="componentname" />

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>
<!-- BEGIN: str_expertcmt_02-->
<div class="str_expertcmt_02" data-enhance="false">
    <c:choose>
        <c:when test="${empty properties.maintitle}">
            <div class="str_expertcmt_02_title"><h3 class="textcolor-title1">[${placeholder}&nbsp;${componentname}]</h3></div>
        </c:when>
        <c:otherwise>
            <div class="str_expertcmt_02_title"><h3 class="textcolor-title1">${properties.maintitle}</h3></div>
        </c:otherwise>
    </c:choose>

    <div class="scroll">
        <div class="alto">


            <!-- First row of the structure -->
            <c:if test="${not empty properties.titletext1 || not empty properties.bodytext1}">
                <div class="expert_container dotted-bottom-right expert_container_right">
                    <h2 class="textcolor-title6 tab">1&nbsp;<span class="subtitle textcolor-title2">${properties.titletext1}</span></h2>
                    <div class="underlined"></div>
                    <p class="textcolor-title4 tab4">${properties.bodytext1}</p>
                </div>
            </c:if>
            <c:if test="${not empty properties.titletext2 || not empty properties.bodytext2}">
                <div class="str_expertcmt_02_separator dotted-left"></div>
                <div class="expert_container dotted-bottom">
                    <h2 class="textcolor-title6">2&nbsp;<span class="subtitle textcolor-title2">${properties.titletext2}</span></h2>
                    <div class="underlined"></div>
                    <p class="textcolor-title4">${properties.bodytext2}</p>
                </div>
            </c:if>


            <!-- Second row of the structure -->
            <c:if test="${not empty properties.titletext3 || not empty properties.bodytext3}">
                <div class="expert_container_block dotted-bottom-right expert_container_right">
                    <h2 class="textcolor-title6 tab">3&nbsp;<span class="subtitle textcolor-title2">${properties.titletext3}</span></h2>
                    <div class="underlined"></div>
                    <p class="textcolor-title4 tab2">${properties.bodytext3}</p>
                </div>
            </c:if>
            <c:if test="${not empty properties.titletext4 || not empty properties.bodytext4}">
                <div class="str_expertcmt_02_separator dotted-left"></div>
                <div class="expert_container_block dotted-bottom">
                    <h2 class="textcolor-title6">4&nbsp;<span class="subtitle textcolor-title2">${properties.titletext4}</span></h2>
                    <div class="underlined"></div>
                    <p class="textcolor-title4">${properties.bodytext4}</p>
                </div>
            </c:if>


            <!-- Third row of the structure -->
            <c:if test="${not empty properties.titletext5 || not empty properties.bodytext5}">
                <div class="expert_container_block expert_container_right r-special">
                    <h2 class="textcolor-title6 tab">5&nbsp;<span class="subtitle textcolor-title2">${properties.titletext5}</span></h2>
                    <div class="underlined"></div>
                    <p class="textcolor-title4 tab2">${properties.bodytext5}</p>
                </div>
            </c:if>

                <div class="str_expertcmt_02_separator dotted-left"></div>
                <div class="expert_container_block">
                    <c:if test="${not empty properties.titletext6 || not empty properties.bodytext6}">
                        <h2 class="textcolor-title6">6&nbsp;<span class="subtitle textcolor-title2">${properties.titletext6}</span></h2>
                        <div class="underlined"></div>
                        <p class="textcolor-title4">${properties.bodytext6}</p>
                    </c:if>
                </div>

            <div class="more">
                <p class="textcolor-title4">Ver más</p>
            </div>
            <div class="see_less textcolor-title4">Ocultar</div>

         </div>
     </div>
    <div class="degradado"></div>
</div>
<!-- END: str_expertcmt_02 -->
 <!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>
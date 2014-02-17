<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  
 *  
 *  component id = art_latestnews_01
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 27/02/2013  | Leonel Orozco          | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>

<%
    pageContext.setAttribute("textMinutes", properties.get("textMinutes", "Última hora"));;
    pageContext.setAttribute("titleMinutes", properties.get("titleMinutes", ""));
    
    pageContext.setAttribute("textMoreRead", properties.get("textMoreRead", "Más leídos"));;
    pageContext.setAttribute("titleMoreRead", properties.get("titleMoreRead", ""));    
%>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>
<!-- BEGIN:art_latestnews_01 -->
<div class="art_latestnews_01">
    <div>
        <div class="art_latestnews_01_lasth selected">
            <c:if test="${not empty titleMinutes}">
                <c:set var="titleMinutes" value="title= '${titleMinutes}'" />
            </c:if>
            <c:set var="textMinutes" value="${textMinutes}"/>
            <c:if test="${empty textMinutes}">
                <c:set var="textMinutes" value=""/>
            </c:if>     
                           
            <a href="#firstab" ${titleMinutes}>${textMinutes}</a>
            <div class="art_latestnews_01_arrow"></div>
        </div>
        <div class="art_latestnews_01_morev">
        
            <c:if test="${not empty titleMoreRead}">
                <c:set var="titleMoreRead" value="title= '${titleMoreRead}'" />
            </c:if>
            <c:set var="textMoreRead" value="${textMoreRead}"/>
            <c:if test="${empty textMoreRead}">
                <c:set var="textMoreRead" value=""/>
            </c:if>     
                           
            <a href="#secondtab" ${titleMoreRead}>${textMoreRead}</a>
            <div class="art_latestnews_01_arrow"></div>
        </div>
    </div>
    
    <ul class="art_latestnews_01_list1" id="minutos">
        <li class="minuto [[class_hidden]] mxm_item" style="display: none;" data-template id="[[txt_hash]]">
            <div class="art_latestnews_01_elements">
                <a href="http://www.televisa.com" title="[[title]]">
                    <span>[[js_date|date 'HH:mm']]</span>
                    <p>[[title]]</p>
                </a> 
            </div>
            <div class="art_latestnews_01_over">
                <div class="art_latestnews_01_boxbg1"> </div>
                <div class="art_latestnews_01_boxbg2">                
                    <a href="#">
                        <p class="art_latestnews_01_text">[[title]]</p>
                        <p class="art_latestnews_01_link">Ver Más</p>
                    </a> 
                </div>
                <div class="art_latestnews_01_boxbg3"> </div>
            </div>
        </li>        
    </ul>
    
    <ul class="art_latestnews_01_list2" style="display:none;" id="masleido">
        <li class="art_latestnews_01_liz" style="display: none;" data-template>
            <!-- BEGIN: mix_1arts_07 -->
            <div class="mix_1arts_07">
                <ul class="articles">
                    <li>
                        <!--<a href="[[url]]" rel="nofollow" title="[[title]]" class="link">-->
                        <a href="http://www.televisa.com/" rel="nofollow" title="[[title]]" class="link">
                            <h3>[[title]]</h3>
                            <p>[[description]]</p>
                            <span class="icon"></span> 
                        </a>
                    </li>
                </ul>
            </div>
            <!-- END: mix_1arts_07 -->
        </li>
    </ul>  
</div>
<!-- END:art_latestnews_01 -->

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>
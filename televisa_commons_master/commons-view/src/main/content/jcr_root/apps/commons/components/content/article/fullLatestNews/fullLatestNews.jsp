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

<!-- BEGIN: art_20itemspag_01art_20itemspag_01 -->
<div class="art_20itemspag_01">
    <ul id="minutos">
        <li class="minuto [[class_hidden]] mxm_item" style="display: none;" data-template id="[[txt_hash]]">
            <span>[[js_date|date 'HH:mm']]</span>
            <div class="comText">
                <h3>[[title]]</h3>
                <p>[[description]]</p>
            </div>
        </li>
    </ul>
    <div class="art_20itemspag_01_paginacion" id="mxm_pagging"></div> 
</div>
<!-- END: art_20itemspag_01 -->
<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  
 *  
 *  component id = wdg_commen_01
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 26/02/2013  | Leonel Orozco          | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%>

<fmt:message key="commons.wdgcomment01.comments.label" var="comments"/>


<div class="str_pleca_01_title">
    <h3>
        <a>
            <span class="str_pleca_01_arrowa selected"></span>
            <span class="str_pleca_01_arrowb"></span>
        </a>
    </h3>
</div>

<!-- BEGIN: wdg_comen_01 -->
<div id="COMM_comments_social"></div>

<!-- END: wdg_comen_01-->

<%--  adding a new div to move the parsys --%>
<c:if test = "${authorMode}">
    <div style="clear:both;"></div>
</c:if>  
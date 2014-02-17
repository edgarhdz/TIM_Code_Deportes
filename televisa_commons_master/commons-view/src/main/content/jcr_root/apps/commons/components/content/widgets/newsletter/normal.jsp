<%--
/**
 *  DESCRIPTION
 * -----------------------------------------------------------------------------
 *  newsletter component use to suscribe
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date             | Developer          | Changes
 * 1.0     | Feb 26st, 2013   | jdiaz@xumak.com    | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%>

<script type="text/javascript">

    <c:if test="${not empty properties.message}">
        config_mailing.msgs.text = '${properties.message}';
    </c:if>
    <c:if test="${not empty properties.buttonText}">
        config_mailing.msgs.button_text = '${properties.buttonText}';
    </c:if>
    <c:if test="${not empty properties.thanksText}">
        config_mailing.msgs.thanks_text = '${properties.thanksText}';
    </c:if>
    <c:if test="${not empty properties.emailText}">
        config_mailing.msgs.input_text = '${properties.emailText}';
    </c:if>
    <c:if test="${not empty properties.messageWorking}">
        config_mailing.msgs.working_msg = '${properties.messageWorking}';
    </c:if>
</script>


<div class="hide-tablet">

<!-- BEGIN: wdg_nwslt_01 -->
<div class="wdg_nwslt_01" data-enhance="false">
    <!-- BEGIN: str_pleca_01 -->
        <cq:include path="str_pleca_01" resourceType="/apps/commons/components/content/structure/singleTitleBox" />
    <!-- END: str_pleca_01 -->

    <div id="mailing"></div>
</div>
<!-- END: wdg_nwslt_01 -->

</div>



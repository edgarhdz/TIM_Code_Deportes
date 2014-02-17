<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  mix2arts07
 *  This script contains 2 mix1arts08 components.
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 21/11/2013  | Luis Emilio Lopez      | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/deportes/local/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@page session="false" %>

<!-- BEGIN: mix_2arts_07 -->
<cq:include path="strPleca01" resourceType="/apps/deportes/generics/components/content/structure/strPleca01" />
<div class="mix_2arts_07" data-enhance="false">
    <div class="container separator">
        <div class="cont-left">

           <!-- BEGIN: mix_1arts_08 -->
            <cq:include path="mix1arts081" resourceType="/apps/deportes/generics/components/content/mix/mix1arts08" />
        <!-- END: mix_1arts_08 -->

       </div>

        <div class="cont-right">

           <!-- BEGIN: mix_1arts_08 -->
            <cq:include path="mix1arts082" resourceType="/apps/deportes/generics/components/content/mix/mix1arts08" />
            <!-- END: mix_1arts_08 -->

        </div>
	</div>
</div>
<!-- END: mix_2arts_07 -->
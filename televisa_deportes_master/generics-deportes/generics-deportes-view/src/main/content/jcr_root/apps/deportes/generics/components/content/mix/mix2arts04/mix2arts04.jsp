<%--
  /*
   *
   * ====================================================================================
   */
--%>
<%@include file="/apps/deportes/generics/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>
<!-- BEGIN: mix_2arts_04 -->
<div class="mix_2arts_04">
    <div class="mix_2arts_04_line">
        <div class="mix_2arts_04_left">
            <!-- BEGIN: mix_1arts_10 -->
            <cq:include path="articleLeft" resourceType="/apps/deportes/generics/components/content/mix/mix1arts10"  />
            <!-- END: mix_1arts_10 -->
        </div>
        <div class="mix_2arts_04_right">
            <!-- BEGIN: mix_1arts_10 -->
            <cq:include path="articleRight" resourceType="/apps/deportes/generics/components/content/mix/mix1arts10"  />
            <!-- END: mix_1arts_10 -->
        </div>
    </div>
</div>
 <!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>
<!-- END: mix_2arts_04 -->
<%--  adding a new div to move the parsys --%>
<c:if test = "${authorMode}">
    <div style="clear:both;"></div>
</c:if>

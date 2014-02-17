<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<fmt:message key="deportes.wdgfacebook01.message" var="message" />

<!-- hide in tablet and mobile -->
<div class="hide-tablet">
    <!-- wdg_facebook_01 -->
    <div class="wdg_facebook_01">
        <div id="fb-root"></div>
        <script>(function(d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) return;
            js = d.createElement(s); js.id = id;
            js.src = "//connect.facebook.net/es_LA/all.js#xfbml=1";
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));</script>
        <c:if test="${authorMode}">
            <h4 class="cq-texthint-placeholder">${message}</h4>
        </c:if>

        <div class="fb-like-box" data-href="${properties.firstpath}" data-width="300" data-show-faces="true" data-header="true" data-stream="false" data-show-border="true"></div>
    </div>
<!-- close div of styles to devices -->
</div>

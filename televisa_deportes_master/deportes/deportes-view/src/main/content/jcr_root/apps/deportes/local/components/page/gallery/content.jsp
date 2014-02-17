<%@include file="/apps/deportes/local/components/global.jsp"%><%
%><%@page import="com.day.cq.wcm.api.WCMMode" pageEncoding="utf-8" %><%
%>

<!-- begin template -->
<section class="td_gallery_detail_01">
    <header class="header1 top_space">
        <cq:include path="navHeader" resourceType="/apps/commons/components/content/base/reference"/>
    </header>

    <!-- main -->
    <div class="main">
        <div class="c1">
            <div class="c1_1">
                <!-- img_fotofalfull_01 component -->
                <cq:include path="imagegallerycontainer" resourceType="/apps/commons/components/content/image/imageFotoGalleryFull" />
            </div>
         </div>
     </div>

     <!-- footer -->
    <footer class="footerPleca">
        <div class="footer" style="clear: both">
            <cq:include path="corporateFooter" resourceType="/apps/commons/components/content/base/reference" />
        </div>
    </footer>
</section> 
<!-- end template -->
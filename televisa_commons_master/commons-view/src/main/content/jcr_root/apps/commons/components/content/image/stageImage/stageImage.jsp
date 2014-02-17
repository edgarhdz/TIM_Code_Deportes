<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  State Image Component
 *  This component is used to configure the image of an article , where 
 *  the author can select any image from the DAM and add an author and a text
 *  alternative like a property of the image..
 *  
 *  component id = img_stage_01
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 06/02/2013  | Juan Jose Pol.         | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>

<%@include file="/apps/commons/components/global.jsp"%>

    <%-- adding image rendition of 624 * 351--%>
    <c:set var="RENDITION_624_351">
      <tg:renditionFromImageAsset name="fileReference" width="624" height="351"/>
    </c:set>

    <%--  Validation Author  --%> 
    <c:if test="${properties.author != null}">
        <c:set var="author" value="Foto: ${properties.author}" />  
    </c:if>        
    
    <c:if test="${properties.description != null}">
        <c:set var="shortdescription" value="${properties.description}" />    
    </c:if>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>

    <%-- Merge hmtl and css for the img_stage_01 Component    --%>   
    <!-- BEGIN: img_stage_01-->
    <div class="img_stage_01">
    
        <div class="img_stage_01_image">
            <c:choose>
                <c:when test="${not empty RENDITION_624_351}">        
                    <img src="${RENDITION_624_351}" alt="${properties.alt}" id="img_stage_01_IMG">
                </c:when>
                <c:otherwise>
                    <c:if test = "${authorMode}">
                        <img class="cq-dd-image cq-image-placeholder"  src="/etc/designs/default/0.gif" >
                    </c:if>
                </c:otherwise>        
            </c:choose>
        </div>
        <c:if test="${not empty properties.description || not empty author}">     
            <a class="img_stage_01_whtbkg" href="${RENDITION_624_351}" target="_blank">
                <p class="img_stage_01_black"><c:out value="${shortdescription}"/></p>
                <p class="img_stage_01_gray"> <c:out value="${author}" /></p>
            </a> 
        </c:if>
        
    </div>
    <!-- BEGIN: img_stage_01-->

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>

    <%--  adding a new div to move the parsys   --%>
    <c:if test = "${authorMode}"> 
        <div style="clear:both;"></div>
    </c:if>  
           
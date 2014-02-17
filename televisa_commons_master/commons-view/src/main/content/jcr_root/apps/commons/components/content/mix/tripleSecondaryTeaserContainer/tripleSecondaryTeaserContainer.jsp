<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Triple Secondary Teaser Container component.
 *  This components is used like a container composed of three mix_1arts_09 components.
 *
 *  component id = mix_3arts_02
 *  
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 28/01/2013  | Juan Jose Pol.         | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%>


    <%-- adding change in attribute border-right to 'none' only previewMode  --%> 
    <c:if test="${authorMode}">
        <style>
            div.mix_3arts_02 div.mix_1arts_09 {
                float: left;
                padding: 0px 12px 0 12px;
                border-right: none;
            }
    </style>
    </c:if>
    
    
    <%-- Merge HTML and CSS for the mix_3arts_03 Component   --%>
    <!-- BEGIN:mix_3arts_02 -->
    
    <div class="mix_3arts_02">
        
        <div class="mix_3arts_02_first">
            <cq:include path="secondaryTeaser1" resourceType="/apps/commons/components/content/mix/secondaryTeaser" />
        </div>
        
        <div>
        <cq:include path="secondaryTeaser2" resourceType="/apps/commons/components/content/mix/secondaryTeaser" />
        </div>    
                           
        <div class="mix_3arts_02_last">
            <cq:include path="secondaryTeaser3" resourceType="/apps/commons/components/content/mix/secondaryTeaser" />
        </div>
        <div class="clearBoth"></div>

    </div>
    <!--END:mix_3arts_02-->
    
    
    <%--  adding a new div to move the parsys --%>
    <c:if test = "${authorMode}"> 
        <div style="clear:both;"></div>
    </c:if>   
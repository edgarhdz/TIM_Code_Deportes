<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  primaryFourShorts Teaser Container component.
 *  
 * 
 *
 *  component id = mix_5arts_01
 *  
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 04/02/2013  | Julio Esquivel         | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%><%
%><%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%><%
%>
<%@ page import="com.day.cq.wcm.api.WCMMode" %>



    <!-- BEGIN: mix_5arts_01 -->
    <div class="mix_5arts_01">    
        
        <div class="left">
           <cq:include path="mix_1arts_08" resourceType="/apps/commons/components/content/mix/primaryTeaser" />
        </div>        
        
        <div class="left" id="segundo">
            <div class="bloque">
              <cq:include path="mix_1arts_07_1" resourceType="/apps/commons/components/content/mix/shortTeaser" />
            </div>
            
            <div class="bloque">
              <cq:include path="mix_1arts_07_2" resourceType="/apps/commons/components/content/mix/shortTeaser" />
            </div>   
            
            <div class="bloque">
              <cq:include path="mix_1arts_07_3" resourceType="/apps/commons/components/content/mix/shortTeaser" />
            </div>   
            
            <div class="bloque" id="finalelem">
              <cq:include path="mix_1arts_07_4" resourceType="/apps/commons/components/content/mix/shortTeaser" />
            </div>     
        </div>
        
    </div>


    <%--  adding a new div to move the parsys  --%>
    <c:if test = "${authorMode}"> 
        <div style="clear:both;"></div>
    </c:if>  
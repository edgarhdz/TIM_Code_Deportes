<%--

 ADOBE CONFIDENTIAL
 __________________

  Copyright 2012 Adobe Systems Incorporated
  All Rights Reserved.

 NOTICE:  All information contained herein is, and remains
 the property of Adobe Systems Incorporated and its suppliers,
 if any.  The intellectual and technical concepts contained
 herein are proprietary to Adobe Systems Incorporated and its
 suppliers and are protected by trade secret or copyright law.
 Dissemination of this information or reproduction of this material
 is strictly forbidden unless prior written permission is obtained
 from Adobe Systems Incorporated.

  ==============================================================================

  Tag It component.

  Component to add tags

  Created: sukamat@adobe.com June 8th 2012

--%><%
%><%@page session="false" import="com.day.cq.wcm.foundation.forms.FormsConstants,
        com.day.cq.wcm.foundation.forms.FormsHelper,
        com.day.cq.wcm.foundation.forms.LayoutHelper,
        com.day.cq.i18n.I18n,
        com.adobe.cq.social.commons.CollabUtil,
        java.util.ResourceBundle" %>
<%@include file="/libs/foundation/global.jsp"%><%
%>
<cq:includeClientLib categories="cq.social.tagging"/>

<%
    final ResourceBundle resourceBundle = slingRequest.getResourceBundle(null);
    I18n i18n = new I18n(resourceBundle);

    final String name = properties.get(FormsConstants.ELEMENT_PROPERTY_NAME, "cq:tags");
    final String id = FormsHelper.getFieldId(slingRequest, resource);
    final String title = FormsHelper.getTitle(resource, i18n.get("Tags"));
    final String description = FormsHelper.getDescription(resource, "");
    final String cid = properties.get("componentid", "tagpicker") + CollabUtil.generateRandomString(4);

    final boolean required = FormsHelper.isRequired(resource);
    final boolean readOnly = FormsHelper.isReadOnly(slingRequest, resource);
    final boolean hideTitle = properties.get("hideTitle", false);
    final boolean hideDescription = properties.get("hideDescription", false);

    String[] tagFilterArr = properties.get("./filterVal", String[].class);
    String tagFilters = "";
    if (tagFilterArr == null) {
        tagFilters = "/etc/tags";
    } else {
        StringBuilder sb = new StringBuilder();
        for (String tf : tagFilterArr) {
            sb.append(tf).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        tagFilters = sb.toString();
    }
%>

<script type="text/javascript">
    $CQ(document).ready(function() {
        $CQ("#"+"<%=cid%>").tagit({
            itemName: '<%=name%>',
            fieldName: '',
            allowSpaces: false,
            animate: false,
            minLength: 2,
            removeConfirmation: true,
            tagSource: function(request,response) {
                $CQ.ajax({
                    url:"<%=request.getContextPath()%>" + "/services/tagfilter",
                    data: {term:request.term, tagfilter:'<%=tagFilters%>'},
                    dataType:"json",
                    success: function(data){
                        response ($CQ.map (data, function(item) {
                            return {
                                label: item.label,
                                value: item.value,
                                id: "+"+item.tagid
                            }
                        }));
                    }
                });
            },
            onTagClicked: function(evt, tag) {
                alert($CQ("#"+"<%=cid%>").tagit('tagLabel', tag));
            }
        });

        $CQ(".form_button_submit").click(function() {
              $CQ("#"+"<%=cid%>").find(':input').each(function() {
                    $CQ(this).attr("name","<%=name%>");
              });
        });

       $CQ(".form_button_reset").click(function() {
             $CQ("#"+"<%=cid%>").tagit("removeAll");
       });
    });
</script>

<!--div class="forum-box-wrapper-tags"-->
<div class="form_row">

    <% LayoutHelper.printTitle(id, title, required, hideTitle, out); %>
    <div class="form_rightcol CQjquery">
        <ul id="<%=cid%>">
            <%-- Don't remove. Existing list items will be pre-added to the tags --%>
        </ul>
    </div>
</div>
<%
LayoutHelper.printDescription(FormsHelper.getDescription(resource, ""), out);
LayoutHelper.printErrors(slingRequest, name, hideTitle, out);
%>

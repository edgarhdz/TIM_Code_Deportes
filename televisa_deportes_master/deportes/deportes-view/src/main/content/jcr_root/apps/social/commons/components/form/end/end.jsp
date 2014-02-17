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

  Form 'end' component

  Draws the end of a form

--%><%@include file="/libs/foundation/global.jsp"%><%
%><%@ page session="false" import="com.day.cq.i18n.I18n,
                   com.day.cq.wcm.api.WCMMode,
                   com.day.cq.wcm.foundation.forms.FormsHelper,
                   com.day.cq.wcm.foundation.forms.LayoutHelper,
                   org.apache.jackrabbit.util.Text,
                   java.util.Locale,
                   java.util.ResourceBundle" %>

<%

    final Locale pageLocale = currentPage.getLanguage(true);
    final ResourceBundle resourceBundle = slingRequest.getResourceBundle(pageLocale);
    final I18n i18n = new I18n(resourceBundle);
    boolean isSubmitDisabled = !properties.get("anonymousSubmit", Boolean.FALSE);
    String denyText = properties.get("denyText", i18n.get("Sign in in order to post to this forum."));
    if(isSubmitDisabled ){
        isSubmitDisabled= ("anonymous".equals(resource.getResourceResolver().adaptTo(Session.class).getUserID()));
    }
%>
<div class="form_row">
   <div class="form_leftcol"></div>
   <div class="form_rightcol">
<%
    final WCMMode wcmMode = WCMMode.fromRequest(request);
    final boolean isEditMode = (wcmMode == WCMMode.EDIT) || (wcmMode == WCMMode.DESIGN);
    final boolean hasSubmit = properties.get("submit", Boolean.FALSE);
    if ( hasSubmit ) {
        final boolean isSubmittable = FormsHelper.checkRule(resource, slingRequest,
                pageContext, "submittableRule");
        if (isSubmittable || isEditMode) {
            final String name = properties.get("name", "Submit");
            final String title = FormsHelper.getTitle(resource, I18n.get(slingRequest, "Submit", "Default form end submit button text"));
            boolean clientValidation = FormsHelper.doClientValidation(slingRequest);
            out.write("<input class=\"form_button_submit\" type=\"" + (clientValidation ? "button" : "submit") + "\"");
            if (FormsHelper.isReadOnly(slingRequest) || isSubmitDisabled) {
                out.write(" disabled=\"disabled\"");
            }
            if ( name.length() > 0 ) {
                out.write(" name=\"");
                out.write(Text.encodeIllegalXMLCharacters(name));
                out.write("\"");
            }
            if ( title.length() > 0 ) {
                out.write(" value=\"");
                out.write(Text.encodeIllegalXMLCharacters(title));
                out.write("\"");
            }
            if (clientValidation) {
                out.write(" onclick=\"if (");
                out.write(FormsHelper.getFormsPreCheckMethodName(slingRequest));
                out.write("('");
                if ( name.length() > 0 ) {
                    out.write(name);
                }
                out.write("')) { document.forms['");
                out.write(FormsHelper.getFormId(slingRequest));
                out.write("'].submit();} else return false;\"");
            }
            if (!isSubmittable) {
                out.write(" disabled=\"\"");
            }
            out.write(">");
        }
    }
    final boolean hasReset = properties.get("reset", Boolean.FALSE);
    if ( hasReset ) {
        %>&nbsp;&nbsp;&nbsp;<%
        String resetTitle = properties.get("resetTitle", "");
        out.write("<input class=\"form_button_reset\" type=\"reset\"");
        if (FormsHelper.isReadOnly(slingRequest)|| isSubmitDisabled) {
            out.write(" disabled=\"disabled\"");
        }
        if ( resetTitle.length() > 0 ) {
            out.write(" value=\"");
            out.write(Text.encodeIllegalXMLCharacters(resetTitle));
            out.write("\"");
        }
        out.write(">");
    }

    final boolean hasCancel = properties.get("cancel", Boolean.FALSE);
    if ( hasCancel ) {
        %>&nbsp;&nbsp;&nbsp;<%
        String cancelTitle = properties.get("cancelTitle",  I18n.get(slingRequest, "Cancel"));
        out.write("<input class=\"form_button_cancel\" type=\"button\" onclick=\"history.go(-1)\"");
        if ( cancelTitle.length() > 0 ) {
            out.write(" value=\"");
            out.write(Text.encodeIllegalXMLCharacters(cancelTitle));
            out.write("\"");
        }
        out.write(">");
    }

    if(isSubmitDisabled){
        %>&nbsp;&nbsp;&nbsp;<%= xssAPI.encodeForHTML(denyText ) %><%
    }
    %></div>
  </div><%
    LayoutHelper.printDescription(FormsHelper.getDescription(resource, ""), out);
    FormsHelper.endForm(slingRequest);

    // turn of decoration and close the decorating DIV
    componentContext.setDecorate(false);
    %></div><%

    // draw the edit bar
    if (editContext != null) {
        editContext.includeEpilog(slingRequest, slingResponse, wcmMode);
    }
%></form>

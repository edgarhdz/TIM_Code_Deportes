<%--
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Camera Selection
 *  This script allows authors to add cameras configurations into the soccer
 *	field.
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 18/11/2013  | Luis Emilio Lopez      | Initial Creation
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/deportes/local/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page session="false" %>
<%@ page import="com.day.cq.wcm.api.WCMMode" %>

<c:if test = "${authorMode}">

    <fmt:message key="deportes.cameraselection.component.camerabtn.title" var="cameraTitle" />
    <fmt:message key="deportes.cameraselection.component.index.title" var="indexTitle" />
    <fmt:message key="deportes.cameraselection.component.camid.title" var="cameIdTitle" />
    <fmt:message key="deportes.cameraselection.component.label.title" var="labelTitle" />
    <fmt:message key="deportes.cameraselection.component.dialog.title" var="dialogTitle" />
    <fmt:message key="deportes.cameraselection.component.m3u8.title" var="m3u8" />
    <fmt:message key="deportes.cameraselection.component.video.title" var="video" />

    <button id="add01" type="button" value="add camera 01" class="add-camera ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">${cameraTitle}</button>
    <button id="add02" type="button" value="add camera 02" class="add-camera ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">${cameraTitle}</button>
    <button id="add03" type="button" value="add camera 03" class="add-camera ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">${cameraTitle}</button>
    <button id="add04" type="button" value="add camera 04" class="add-camera ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">${cameraTitle}</button>
    <button id="add05" type="button" value="add camera 05" class="add-camera ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">${cameraTitle}</button>
    <button id="add06" type="button" value="add camera 06" class="add-camera ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">${cameraTitle}</button>
    <button id="add07" type="button" value="add camera 07" class="add-camera ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">${cameraTitle}</button>
    <button id="add08" type="button" value="add camera 08" class="add-camera ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">${cameraTitle}</button>

    <div id="dialog" title="${dialogTitle}">
        <form>
            <fieldset>
                <label class="camera-dialog-label" for="index">${indexTitle}</label>
                <input type="text" name="index" id="index" class="camera-dialog-input text ui-widget-content ui-corner-all" />
                <label class="camera-dialog-label" for="label">${labelTitle}</label>
                <input type="text" name="label" id="label" value="" class="camera-dialog-input text ui-widget-content ui-corner-all" />
                <label class="camera-dialog-label" for="m3u8">${m3u8}</label>
                <input type="text" name="m3u8" id="m3u8" value="" class="camera-dialog-input text ui-widget-content ui-corner-all" />
                <label class="camera-dialog-label" for="video">${video}</label>
                <input type="text" name="video" id="video" value="" class="camera-dialog-input text ui-widget-content ui-corner-all" />
                <input type="hidden" id="ID" value="" />
            </fieldset>
        </form>
    </div>

</c:if>



<!-- Tu diriges-->
<div class="cameras">
    <div class="cameras_field hide-mobile">
        <!--Fondo del campo fut/box-->



        <div class="game_field" >
            <%
                Node pageNode = currentPage.adaptTo(Node.class);
                if (pageNode != null) {
                    if (pageNode.hasNodes()) {
                        NodeIterator ni = pageNode.getNodes();


                        while (ni.hasNext()) {
                            Node n = ni.nextNode();
                            if (n.getName().equals("cameras")) {
                                int count = 0;
                                NodeIterator iterator = n.getNodes();
                                while (iterator.hasNext()) {
                                    count++;
                                    Node camera = iterator.nextNode();

                                    String index = camera.getProperty("index").getString();
                                    String label = camera.getProperty("label").getString();
                                    String m3u8 = camera.getProperty("m3u8").getString();
                                    String video = camera.getProperty("video").getString();
                                    String x = camera.getProperty("x").getString();
                                    String y = camera.getProperty("y").getString();
                                    String camclass = camera.getProperty("class").getString();
                                    camclass = camclass.replace("selected", "");

            %> <div id="<%= count %>" name="<%= label %>" index="<%= index %>" m3u8="<%= m3u8 %>" video="<%= video %>" data-x="<%= x %>" data-y="<%= y %>" class="<%= camclass %>"></div> <%
                            }
                        }
                    }
                }
            }
        %>
        </div>



    </div>

    <div class="cameras_index">
        <h5>Selecciona tu Camara</h5>

        <ul>
            <%
                if (pageNode != null) {
                    if (pageNode.hasNodes()) {
                        NodeIterator ni = pageNode.getNodes();


                        while (ni.hasNext()) {
                            Node n = ni.nextNode();
                            if (n.getName().equals("cameras")) {
                                int count = 0;
                                NodeIterator iterator = n.getNodes();
                                while (iterator.hasNext()) {
                                    count++;
                                    Node camera = iterator.nextNode();
                                    String index = camera.getProperty("index").getString();
                                    String label = camera.getProperty("label").getString();
                                    String m3u8 = camera.getProperty("m3u8").getString();
                                    String video = camera.getProperty("video").getString();

            %> <li><a class="indice" href="#"  m3u8="<%= m3u8 %>" video="<%= video %>" id="<%= count %>"><%= index %></a><div id="l<%= count %>"><%= label %></div></li> <%
                            }
                        }
                    }
                }
            }
        %>

        </ul>
    </div>

</div>
<!--// Tu diriges-->


<% if (pageNode != null) { %>
<c:if test = "${authorMode}">

    <fmt:message key="deportes.cameraselection.component.savebtn.title" var="saveTitle" />

    <form id="cameras_data" method="POST" action="<%= pageNode.getPath() %>" enctype="multipart/form-data">
        <input type="hidden" name=":operation" value="import" />
        <input type="hidden" name=":contentType" value="json" />
        <input type="hidden" name=":replace" value="true">
        <input type="hidden" name=":replaceProperties" value="true">
        <input type="hidden" name=":redirect" value="<%= currentPage.getPath() %>.html">
        <input type="Submit" id="save" value="${saveTitle}" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" />
    </form>

</c:if>
<% } %>

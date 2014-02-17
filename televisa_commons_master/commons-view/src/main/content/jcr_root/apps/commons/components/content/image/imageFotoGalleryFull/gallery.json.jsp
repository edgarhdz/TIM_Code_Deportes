<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Image Gallery json.
 *  This is container to create an image gallery page, the user can add the path
 *  image files to add images that the user need.
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 18/02/2013  | Pablo Alecio.          | Initial Creation.
 * 1.1     | 13/05/2013  | Pablo Alecio           | Externalized image paths
 * 2.0     | 03/08/2013  | Pablo Alecio           | Complete refactoring

 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%>
<%@page import="com.televisa.commons.services.services.GsaService"%>
<%@page import="java.util.Iterator"%>
<%
    GsaService gsaService = sling.getService(GsaService.class);
    pageContext.setAttribute("gsaService", gsaService);
%>

<%-- Set title --%>
<c:set var="title" value ="${fn:escapeXml(currentPage.title)}"/>

<%-- Set description --%>
<c:set var="desc" value ="${fn:escapeXml(properties.shortDescription)}"/>
<c:set var="desc" value ="${tg:jsonEscape(properties.shortDescription)}"/>

<%-- Set url --%>
<c:set var="url" value ="${currentPage.path}.html"/>
<c:if test="${previewMode == 'true'}">
    <c:set var="url" value="${tg:getCompleteURL(gsaService, url, 'static')}" />
</c:if>

<%-- Set thumbnail --%>
<c:set var="firstChildNode" value="${tg:getFirstChildNode(currentNode)}" />
<c:if test="${not empty firstChildNode}">  
        <c:set var="imagePrefix" value="${firstChildNode.name}/" />
        
        <%-- First Image --%>
             <c:set var="firstImage">
            <tg:renditionFromImageAsset name="${imagePrefix}fileReference" width="136" height="102" />
        </c:set>

    </c:if>
<c:if test="${not empty firstImage}">
    <c:set var="thumbnail" value ="${firstImage}"/>

    <c:if test="${previewMode == 'true'}">
        <c:set var="thumbnail" value="${tg:getCompleteURL(gsaService, thumbnail, 'static')}" />
    </c:if>
</c:if>

{
"id": "${currentPage.path}",
"title": "${title}",
"description": "${desc}", 
"url":"${url}",
"photos": [

<%
       Iterator<Node> imageItemsNode = currentNode.getNodes();
        while(imageItemsNode.hasNext()){
            Node imageNode = imageItemsNode.next();
                String strImageDescription = "";
                String strFileReference = "";

                try{
                    Property imageDescriptionProperty = imageNode.getProperty("imgDescription");
                    if(imageDescriptionProperty != null){
                        Value valueImageDesc = imageDescriptionProperty.getValue();
                        if(valueImageDesc != null && valueImageDesc.getString() != null){
                            pageContext.setAttribute("imgDescription", valueImageDesc.getString());
                        }
                    }
                }catch(PathNotFoundException e){
					pageContext.setAttribute("imgDescription", "");
                }

                try{
                    Property fileReferenceProperty = imageNode.getProperty("fileReference");
                    if(fileReferenceProperty != null){
                        Value valueFileReference = fileReferenceProperty.getValue();
                        if(valueFileReference != null && valueFileReference.getString() != null){
                            pageContext.setAttribute("fileReference", valueFileReference.getString());
                        }
                    }
                }catch(PathNotFoundException e){
					pageContext.setAttribute("fileReference", "");
                }
%>
    <c:set var="imgDescription" value="${imgDescription}" />
    <c:set var="imagePath" value="${fileReference}" />

        <%-- Set small images --%>
        <c:set var="smallImage">
            <tg:getAssetPathBySize path="${imagePath}" width="63" height="63"/>
        </c:set>
        <c:if test="${previewMode == 'true'}">
            <c:set var="smallImage" value="${tg:getCompleteURL(gsaService, smallImage, 'static')}" />
        </c:if>

        <%-- Set large images --%>   
        <c:set var="largeImage">
            <tg:getAssetPathBySize path="${imagePath}" width="624" height="468"/>
        </c:set>
        <c:if test="${previewMode == 'true'}">
            <c:set var="largeImage" value="${tg:getCompleteURL(gsaService, largeImage, 'static')}" />
        </c:if>

        <c:if test="${not empty index}">
            ,
        </c:if> 
        <c:if test="${empty index}">
             <c:set var="index" value="0"/>
        </c:if> 

        {
            "id": ${index},
            "title": "${imgDescription}",
            "description": "${imgDescription}",
            "thumb": "${smallImage}",
            "image": "${largeImage}"
        }
    	<c:set var="index" value="${index + 1}" />
<%

       }
%>
],
"thumb": "${thumbnail}"
}
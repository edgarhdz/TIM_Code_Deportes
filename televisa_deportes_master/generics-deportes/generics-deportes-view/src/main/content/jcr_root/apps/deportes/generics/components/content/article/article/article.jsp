<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Article Note Component
 *  This is used like a container to create an article.
 *  basically include  the following components: articleBranchTitle, articleTitle,
 *  relatedArticles, socialNetworks, stageImage | photoGallery | videoGallery, articleBullet and a parsys.
 *
 *
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 12/02/2013  | Juan Jose Pol.         | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>

<%@include file="/apps/deportes/generics/components/global.jsp"%>

<%
    Resource ressss = resourceResolver.getResource(currentPage.getPath());
    Node pageNode = ressss.adaptTo(Node.class);

    if(!pageNode.hasNode("jcr:content/article") ){
        pageNode.addNode("jcr:content/article");
        pageNode.save();
    }

    if(WCMMode.fromRequest(request) == WCMMode.EDIT){
        if(!pageNode.hasNode("jcr:content/article/relatedArticles") ){
            Node newNode = pageNode.addNode("jcr:content/article/relatedArticles" );
            newNode.setProperty( "sling:resourceType","deportes/generics/components/content/article/artreltd01");
            pageNode.save();
        }
    }
%>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>

<div>
    <%-- cq:include path="articleBranchTitle" resourceType="/apps/televisa/components/content/generics/article/articleBranchTitle" /> --%>
    <cq:include script="articleTitle.jsp"/>
    <c:if test="${properties.socialRelated == 'true'}">
        <div class="mix_2arts_06">

            <div class="separator dotted-right">
                <cq:include path="relatedArticles" resourceType="/apps/deportes/generics/components/content/article/artreltd01" />
            </div>
            <%-- <cq:include path="socialNetworks" resourceType="/apps/deportes/generics/components/content/article/socialNetworks" /> --%>
        </div>
    </c:if>




    <%-- validation to add the stageImage | videoGallery | photoGallery--%>
    <c:choose>
        <c:when test="${properties.showcomponents == 'stageimage'}">
            <cq:include path="image" resourceType="/apps/deportes/generics/components/content/image/imgStage01" />
        </c:when>
        <c:when test="${properties.showcomponents == 'photogallery'}">
           <cq:include path="imageGallery" resourceType="/apps/deportes/generics/components/content/image/imgGalry01" />
        </c:when>
        <c:otherwise>
            <%-- <cq:include path="image" resourceType="/apps/deportes/generics/components/content/image/imgStage01" /> --%>
        </c:otherwise>
    </c:choose>


    <cq:include path="description" resourceType="/apps/deportes/generics/components/content/article/artBullet01" />

    <%-- adding a simple parsys like container for others components --%>
    <cq:include path="containerOne" resourceType="foundation/components/parsys" />

</div>

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>
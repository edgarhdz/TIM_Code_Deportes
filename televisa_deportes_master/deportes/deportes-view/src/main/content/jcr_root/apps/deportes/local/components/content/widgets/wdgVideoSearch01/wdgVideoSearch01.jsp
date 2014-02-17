<%@include file="/apps/deportes/local/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>

<%@page import="com.televisa.commons.services.datamodel.Note"%>
<%@page import="com.televisa.commons.services.datamodel.RenditionSize"%>
<%@page import="com.televisa.commons.services.datamodel.objects.impl.FilterVideoCarouselImpl"%>
<%@page import="com.televisa.commons.services.datamodel.objects.InfoPage"%>
<%@page import="com.televisa.commons.services.utilities.Utilities"%>

<%@page import="com.day.cq.tagging.TagManager"%>
<%@page import="com.day.cq.tagging.Tag"%>
<%@page import="java.util.Iterator"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.StringBuilder" %>
<%@ page import="java.util.LinkedList" %>


<%
    TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
    List<Tag> tagsOne = new ArrayList<Tag>();
    Tag current;

    String tagpath = "";

    String[] tagsTourn = properties.get("cq:tournTags", String[].class);
    String[] currentTag = properties.get("cq:currentTourn", String[].class);
    String[] tagsTypes = currentStyle.get("cq:typeTags", String[].class);

    if ( (null!=tagsTourn) && (null!=currentTag) ) {
        if ( (tagsTourn.length>0) && (currentTag.length>0) ) {
            Tag themeNameSpace = tagManager.resolve(tagsTourn[0]);
            current = tagManager.resolve(currentTag[0]);
            if (null!=themeNameSpace) {
                Iterator<Tag> themeNameSpaceIterator = themeNameSpace.listChildren();

                while(themeNameSpaceIterator.hasNext()){
                    tagsOne.add(themeNameSpaceIterator.next());
                }
            }
            tagpath = tagsTourn[0];
            pageContext.setAttribute("tagsOne", tagsOne);
            pageContext.setAttribute("tagpath", tagpath);
            pageContext.setAttribute("current", current);
        }
    }
    List<Tag> typetagList = new LinkedList<Tag>();
    if(null != tagsTypes) {
        for(String typeTagString:tagsTypes) {
            Tag typeTag = tagManager.resolve(typeTagString);
            typetagList.add(typeTag);
        }
        pageContext.setAttribute("typeTags", typetagList);
    }
%>

<c:set var="path" value="${fn:replace(properties.path,'/','__')}"/>
<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    <div class="${properties.hideDevice}">
</c:if>

<!-- BEGIN: wdg_video_search_01 -->
<div class="wdg_video_search_01" id="carousel_vid_search" data-enhance="false">

    <!-- BEGIN: filterResultado -->
    <div class="contenFilter">
        <div class="filterResultado">
            <div class="lineaResultado result1">
                <div class="filter">
                    <div class="wdg_video_search_01_dropdown drop1">
                        <div class="wdg_video_search_01_dropdowncontent content1">
                            <p name="${current.name}">${current.title}</p>
                            <span class="tvsa-caret-down"></span>
                        </div>
                        <div class="wdg_video_search_01_listcontainer list1">
                            <ul class="wdg_video_search_01_dropdownlist list1">
                                <c:forEach var="tags" items="${tagsOne}">
                                    <li><p name="${tags.name}">${tags.title}</p></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                <h3>Torneo</h3>
            </div>
            <div class="lineaResultado result2">
                <div class="filter">
                    <div class="wdg_video_search_01_dropdown drop2">
                        <div class="wdg_video_search_01_dropdowncontent content2">
                            <p>Todos</p>
                            <span class="tvsa-caret-down"></span>
                        </div>
                        <div class="wdg_video_search_01_listcontainer list2">
                            <ul class="wdg_video_search_01_dropdownlist list2">

                            </ul>
                        </div>
                    </div>
                </div>
                <h3>Jornada</h3>
            </div>
        </div>
        <!-- Fila -2-   -->
        <div class="filterResultado filterResultado2">
            <div class="lineaResultado result3">
                <div class="filter">
                    <div class="wdg_video_search_01_dropdown drop3">
                        <div class="wdg_video_search_01_dropdowncontent content3">
                            <p>Todos</p>
                            <span class="tvsa-caret-down"></span>
                        </div>
                        <div class="wdg_video_search_01_listcontainer list3">
                            <ul class="wdg_video_search_01_dropdownlist list3">


                            </ul>
                        </div>
                    </div>
                </div>
                <h3>Equipo</h3>
            </div>




            <div class="lineaResultado result4">
                <div class="filter">
                    <div class="wdg_video_search_01_dropdown drop4">
                        <div class="wdg_video_search_01_dropdowncontent content4">
                            <p>Todos</p>
                            <span class="tvsa-caret-down"></span>
                        </div>
                        <div class="wdg_video_search_01_listcontainer list4">
                            <ul class="wdg_video_search_01_dropdownlist list4">
                                <c:forEach var="tags" items="${typeTags}">
                                    <li><p name="${tags.tagID }">${tags.title}</p></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                <h3>Tipo</h3>
            </div>
        </div>
    </div>
    <!-- END: filterResultado -->

    <div class="scroll"></div>
    <!-- BEGIN: mix_1arts_13 -->

    <!-- END: mix_1arts_13 -->

    <!-- BEGIN: nav_paginator_01-->

    <div class="nav_paginator_01 dotted-top">
    </div>

    <!-- END: nav_paginator_01 -->

    <!-- BEGIN:  wdg_video_search_01_verMas -->
    <div class="wdg_video_search_01_ver_mas">
        <a href="#" class="textcolor-title4">Ver M&aacute;s</a>
    </div>
    <!-- END: wdg_video_search_01_verMas -->

    <div class="degraded"></div>

</div>
<!-- END: wdg_video_search_01 -->
<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
    </div>
</c:if>


<script type="text/javascript">
    VideoSearch.initialize('${tagpath}', '${path}');
</script>
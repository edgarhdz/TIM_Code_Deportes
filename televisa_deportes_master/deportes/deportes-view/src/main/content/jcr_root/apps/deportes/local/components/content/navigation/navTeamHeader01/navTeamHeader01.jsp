<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Bookmark Navigation Component
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 01/07/2013  | Otto Giron             | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/deportes/local/components/global.jsp"%>
<%@page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>
<c:set var="iconPath" value="${properties.fileReference}" ></c:set>
<div class="nav_team_header_01" data-enhance="false">
		<!--In the data-color attr you need to setup the link color-->
	 		<div class="link_color" data-color="#63C"></div><!-- END color -->
	<div class="fondo">
    	<div class="gradiente_head"></div>
    	<div class="degradado_head"></div>
            <div class="logo">
                <div class="texto textcolor-title3">${properties.teamName}</div>
				<img src="${iconPath}" width="108" height="68" alt="" />
            </div>
    </div>
    <div class="sombra"></div>
    <div class="nav_team_header_01_container">
        <div class="nav_team_header_01_right">
            <div class="nav_team_header_01_navcontainer">
                <div class="wdg_matchesresult_navarrowleft">
                    <a class="wdg_matchesresult_navleft" href="#left">
                        <span class="wdg_matchesresult_navlefticon"><i class="tvsa-double-caret-left"></i></span>
                    </a> 
                </div>
                <div class="box">
                    <div class="nav_team_header_01_nav">
                        <ul class="nav_team_header_01_theme">
                            <c:choose>
							<c:when test="${properties.numberofitems==1}">
								<c:choose>
									<c:when test="${properties.linkType=='internal'}">
										<c:set var="linkOne" value="${properties.path}" />
										<c:if test="${not empty linkOne}">
											<c:set var="linkOne" value="${properties.path}.html" />
										</c:if>
									</c:when>
									<c:when test="${properties.linkType=='external'}">
										<c:set var="linkOne" value="${properties.externalLink}" />

										<c:if test="${not empty linkOne}">
											<c:choose>
												<c:when test="${fn:contains(linkOne, 'http://')}">
												</c:when>
												<c:otherwise>
													<c:set var="linkOne" value="http://${linkOne}" />
												</c:otherwise>
											</c:choose>
										</c:if>
									</c:when>
								</c:choose>
								<li class="border-bottom2"><a class="textcolor-title2"
									href="${linkOne}" target="${properties.target}"
									title="${properties.title}"> <p>${properties.text}</p> </a></li>
							</c:when>
							<c:when test="${properties.numberofitems>1}">
								<c:forEach var="i" begin="0" end="${properties.numberofitems-1}">
									<c:choose>
										<c:when test="${properties.linkType[i]=='internal'}">
											<c:set var="link" value="${properties.path[i]}" />
											<c:if test="${not empty link}">
												<c:set var="link" value="${properties.path[i]}.html" />
											</c:if>
										</c:when>
										<c:when test="${properties.linkType[i]=='external'}">
											<c:set var="link" value="${properties.externalLink[i]}" />
											<c:if test="${not empty link}">
												<c:choose>
													<c:when test="${fn:contains(link, 'http://')}">
													</c:when>
													<c:otherwise>
														<c:set var="link" value="http://${link}" />
													</c:otherwise>
												</c:choose>
											</c:if>
										</c:when>
									</c:choose>
									<c:set var="currentPageLink" value="${currentPage.path}.html"/>
									<c:if test="${currentPageLink==link}">
										<c:set var="colorTitleClass" value="class='textcolor-title2'" />
										<c:set var="borderBottomClass" value="class='border-bottom2'"/>
									</c:if>
									
									<li ${borderBottomClass}><a href="${link}" target="${properties.target[i]}"
										title="${properties.title[i]}"><p ${colorTitleClass}>${properties.text[i]}</p> </a></li>
									<c:set var="colorTitleClass" value="" />
									<c:set var="borderBottomClass" value=""/>
								</c:forEach>
							</c:when>
						</c:choose>
                        </ul>
                    </div>
                </div>
                <div class="wdg_matchesresult_navarrowright">
                    <a class="wdg_matchesresult_navright" href="#right">
                        <span lass="wdg_matchesresult_navrighticon"><i class="tvsa-double-caret-right"></i></span>
                    </a>
                </div>
            </div>
        </div>
    </div>
   </div>
<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>
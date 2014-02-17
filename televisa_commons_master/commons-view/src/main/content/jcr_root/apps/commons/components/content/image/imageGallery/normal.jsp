<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Image Gallery component.....
 *  
 * 
 *
 *  component id = img_galry_01
 *  
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 12/02/2013  | Julio Esquivel         | Initial Creation.
 * 1.1     | 21/03/2013  | Juan Jose Pol          | UX updated
 * 1.2     | 21/03/2013  | Jose Alejandro Urizar  | Maximize function.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>

<%@include file="/apps/commons/components/global.jsp"%><%
%>
<%@page import="com.televisa.commons.services.services.GsaService"%>
<fmt:message key="commons.editcomponent.placeholder" var="placeholder"/>
<fmt:message key="commons.imagegallery.title" var="imagegallerytitle"/>
<%
    GsaService gsaService = sling.getService(GsaService.class);
    pageContext.setAttribute("gsaService", gsaService);
    String[] internal = properties.get("internallink",String[].class);
    
    Page pageGallery = null;
    if (null!=internal) {
     pageGallery = pageManager.getPage(internal[0]);
    }
    pageContext.setAttribute("pageGallery",pageGallery);
%>

<!-- Add styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	<div class="${properties.hideDevice}">
</c:if>

<c:choose>
    <c:when test="${properties.numberofitems == 0 || properties.numberofitems == null}"> 
        <c:if test="${authorMode}">

            <!-- BEGIN: str_pleca_01 -->
                <cq:include path="str_pleca_01" resourceType="/apps/commons/components/content/structure/singleTitleBox" />
            <!-- END: str_pleca_01 -->

			<div class="img_galry_01" data-current="0" data-enhance="false">
                <div id="image-container">
                  <a href="#" class="twitter td_bg lnk_twitter back_overlay" title="Tweet this!" target="_blank"><i class="tvsa-twitter"></i></a>
                  <a href="#" class="facebook td_bg lnk_facebook back_overlay" title="Share on Facebook" target="_blank"><i class="tvsa-facebook"></i></a>
                  <a href="#" title="Pin it!" class="td_bg pinterest pin-it-button back_overlay"><i class="tvsa-pinterest"></i></a>

                  <img class="mainimage" src="http://dummyimage.com/624x468" alt="#1 Soy la descripcion de este imagen. No tengo muchas palabras." width="624" height="468" />
                  <span class="loading"><i class="tvsa-spinner tvsa-spin tvsa-3x"></i></span>
                  <div class="overlay">
                     <p class="imageDescription">${placeholder} ${imagegallerytitle}</p>
                  </div>
                  <div class="controls">
                     <a class="td_bg play back_overlay" href="#"><i class="tvsa-play"></i></a>
                     <a class="td_bg expand back_overlay" href="#link-of-first-image-in-first-gallery"><i class="tvsa-expand"></i></a>
                  </div>
                </div>

			   <div class="gallery-controls">
                   <div class="type1a_ carrusel1 dotted-bottom">
                     <div class="arrowLeft"><a href="#left" class="left" title="Anterior"><i class="tvsa-double-caret-left inactive"></i></a></div>
                     <div class="arrowRight"><a href="#right" class="right" title="Siguiente" id="rightC1"><i class="tvsa-double-caret-right"></i></a></div>

                       <div class="carruselcontainer">
                        <ul class="ulcarrusel">
                           <li>
                              <!-- in case there are detail pages (expand icon) add the real links here -->
                              <a href="#" class="image active" data-pos="0" title="${placeholder} ${imagegallerytitle}">
                              <img src="http://dummyimage.com/85x66" width="85" height="66" alt="${placeholder} ${imagegallerytitle}" class="" />
                              <span class="border"></span>
                              <span class="title">${placeholder} ${imagegallerytitle}</span>
                              </a>
                           </li>
                         </ul>
                       </div>
                   </div>

                   <div class="type1a_ carrusel2">
                     <div class="arrowLeft"><a href="#left" class="left" title="Link Description"><i class="tvsa-double-caret-left inactive"></i></a></div>
                     <div class="arrowRight"><a href="#right" class="right" title="Link Description"><i class="tvsa-double-caret-right"></i></a></div>
                     <div class="carruselcontainer">
                        <ul id="carrusel2_ul" class="ulcarrusel">
                           <li>
                              <a href="#" title="${placeholder} ${imagegallerytitle}" class="album active" data-pos="0">
                              <img src="http://dummyimage.com/136x104" alt="${placeholder} ${imagegallerytitle}" width="136" height="103" />
                              <span class="icon"><i class="tvsa-camera"></i></span>
                              <span class="overlay"><i class="tvsa-images tvsa-4x"></i></span>
                              <span class="description">
                              <span class="topic td">${placeholder} ${imagegallerytitle}</span>
                              <span class="title">${placeholder} ${imagegallerytitle}</span>
                              </span>
                              </a>
                           </li>
                        </ul>
                     </div>

                      <div class="degradado"></div>
		           </div>
                <!-- END: overlay -->
            </div>
          </div>
        </c:if>
    </c:when>

    <c:otherwise>
        <div class="img_galry_01" data-current="0" data-enhance="false">
           <div class="image-container">
              <a href="#" class="twitter td_bg lnk_twitter back_overlay" title="Tweet this!" target="_blank"><i class="tvsa-twitter"></i></a>
              <a href="#" class="facebook td_bg lnk_facebook back_overlay" title="Share on Facebook" target="_blank"><i class="tvsa-facebook"></i></a>
              <a href="javascript:void((function()%7Bvar%20e=document.createElement(&apos;script&apos;);e.setAttribute(&apos;type&apos;,&apos;text/javascript&apos;);e.setAttribute(&apos;charset&apos;,&apos;UTF-8&apos;);e.setAttribute(&apos;src&apos;,&apos;http://assets.pinterest.com/js/pinmarklet.js?r=&apos;+Math.random()*99999999);document.body.appendChild(e)%7D)());" title="Pin it!" class="td_bg pinterest pin-it-button back_overlay"><i class="tvsa-pinterest"></i></a>

              <img class="mainimage" src="http://dummyimage.com/624x468" alt="${placeholder} ${imagegallerytitle}" width="624" height="468" />
              <span class="loading"><i class="tvsa-spinner tvsa-spin tvsa-3x"></i></span>
              <div class="overlay">
                 <p class="imageDescription">${placeholder} ${imagegallerytitle}</p>
              </div>
              <div class="controls">
                 <a class="td_bg play back_overlay" href="#"><i class="tvsa-play"></i></a>
                 <a class="td_bg expand back_overlay" href="#link-of-first-image-in-first-gallery"><i class="tvsa-expand"></i></a>
              </div>
           </div>
           <div class="gallery-controls">
               <c:choose>
                   <c:when test="${not empty properties.showgalleries || not empty properties.showinfo}">
                      <div class="type1a_ carrusel1 dotted-bottom">        
                   </c:when>
                   <c:otherwise>
		              <div class="type1a_ carrusel1">
                   </c:otherwise>
               </c:choose>

                 <div class="arrowLeft"><a href="#left" class="left" title="Anterior"><i class="tvsa-double-caret-left inactive"></i></a></div>
                 <div class="arrowRight"><a href="#right" class="right" title="Siguiente" id="rightC1"><i class="tvsa-double-caret-right"></i></a></div>
                 <div class="carruselcontainer">
                    <ul class="ulcarrusel">
                    </ul>
                 </div>
              </div>
               <c:choose>
                   <c:when test="${not empty properties.showgalleries}">
                      <div class="type1a_ carrusel2">
                   </c:when>
                   <c:otherwise>
                       <div class="type1a_ carrusel2" style="display:none;">
                   </c:otherwise>
               </c:choose>

                 <div class="arrowLeft"><a href="#left" class="left" title="Link Description"><i class="tvsa-double-caret-left inactive"></i></a></div>
                 <div class="arrowRight"><a href="#right" class="right" title="Link Description"><i class="tvsa-double-caret-right"></i></a></div>
                 <div class="carruselcontainer">
                    <ul id="carrusel2_ul" class="ulcarrusel">
                        <c:forEach var="element" items="${properties.internallink}" varStatus="loop">
                            <tg:noteProvider path="${element}"/>

                            <c:set var="rendition">
                                <tg:renditionSelector image="${note.noteImageAsset}" width="136" height="102"/>                                
                            </c:set>

                            <li>         
                                <a href="javascript: void(0);" title="${note.imageShortDescription}" galleryid="${element}/jcr:content/imagegallerycontainer.gallery.json" class="album" data-pos="${loop.count-1}">

                                    <c:if test="${not empty properties.showgalleries}">
                                        <img src="${rendition}" alt="" width="136" height="102">

                                        <span class="icon"><i class="tvsa-camera"></i></span>
                                        <span class="overlay"><i class="tvsa-images tvsa-4x"></i></span>
    
                                        <span class="description">
                                            <span class="topic td">${note.imageTopic}</span>
                                            <span class="title">${note.imageShortDescription}</span>
                                        </span>
                                    </c:if>
                                </a>
                            </li>
                        </c:forEach>  
                    </ul>
                 </div>
                 <div class="degradado"></div>
              </div>
              <c:if test="${properties.showinfo == 'true'}" >
	            <div id="img_galry_01_txts">
	                <div class="txt1 dotted-right">
	                </div>
	                <div class="txt2 dotted-bottom">
	
	                        <h3>Temas relacionados</h3>
	                        <h4>
	                            <c:set var="index" value="${1}" />
	                            <c:forEach var="i" items ="${pageGallery.tags}" varStatus="loop">
	                                <c:set var="keyword" value="${fn:toUpperCase(fn:substring(i.title, 0, 1))}${fn:toLowerCase(fn:substring(i.title, 1,fn:length(i.title)))}" />
	                                <c:choose>
	                                    <c:when test="${index % 3 != 0}">
	                                        <a class="art_related_01_black" href="<tg:searchByTagUrl tag='${i.name}'/>">${keyword}
	                                            ${!loop.last ? ', ' : ''}
	                                        </a>
	                                    </c:when>
	                                    <c:otherwise>
	                                        <a class="art_related_01_black" href="<tg:searchByTagUrl tag='${i.name}'/>">${keyword}</a>
	                                        <br/>
	                                    </c:otherwise>
	                                </c:choose>
	                                <c:set var="index" value="${index + 1}" />
	                            </c:forEach>
	                        </h4>
	
	                </div>
	    
	                <div class="txt3">
	                    <!-- BEGIN: Widget Redes Sociales -->
	                    <div class="wdg_social_01">
	                        <div id="widgetSocialShare"></div>
	                    </div>
	                    <!-- END: Widget Redes Sociales -->
	                </div>
	            </div>
	        </c:if>
           </div>
           <!-- END: overlay -->
        </div>
    </c:otherwise>
</c:choose>

<!-- close div of styles to devices -->
<c:if test="${not empty properties.hideDevice}">
	</div>
</c:if>

<%--  adding a new div to move the parsys  --%>
<c:if test = "${authorMode}"> 
    <div style="clear:both;"></div>
</c:if>  

<%--  remove note object in pageContext when add two or more primaryTeaser in a container --%>
<%pageContext.removeAttribute("note");%>

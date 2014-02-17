
<%-- Merge HTML and CSS for the mix_carru_01 Component  --%>
    <c:choose>        
        <c:when test="${firstNote != null}">  
            <!-- BEGIN: Carrusel -->
            <div class="mix_carru_01">                
                <div class="type2b">
                    <article class="articleBigModule">
            
                        <div class="galleryImages">
                            <ul>
                                <li ${active}>
                                    <div id="bigImgA">
                    					<div class="resize">
                    						 <img src="${firstImage_624_351}" alt="${firstNote.title}" id="bigImg"/>
	                                        <c:if test="${properties.showcontext == true}">
	                                            <span id="icon_type" class="${firstNoteImageContext}"></span>
	                                        </c:if>
                    					</div>       
                                         
                                    </div>
                                </li>
                            </ul>
                        </div>
            
                        <div class="galleryCaption">
							<div class="bullets">
								<ul>
									<li class="selected"></li>
									<li></li>
									<li></li>
									<li></li>
									<li></li>
									<li></li>
								</ul>
							</div>
							<h2 class="itemSection">
                                <c:if test="${properties.showtopic == true}">                                    
                                    <a href="${firstNote.url}.html" title="${firstNote.title}" ${target} >
                                        ${firstNote.topic}
                                    </a>
                                </c:if>     
                            </h2>
            
                            <h4 class="itemCaption">
                                <c:if test="${properties.showtitle == true}"> 
                                    <a href="${firstNote.url}.html" title="${firstNote.title}" ${target} >
                                        ${firstNote.title}
                                    </a>
                                </c:if>
                            </h4>
                           
                        </div>
                        
                        <ul class="galleryThumbs">
                            <c:if test="${not empty firstNote.url}">
                                <li ${active} image ="${firstImage_624_351}" seccion="${firstNote.topic}" urlSeccion="${firstNote.categoryUrl}.html" title="${firstNote.title}" urlTitle="${firstNote.url}.html" ${target}  <c:if test="${properties.showcontext == true}"> type="${firstNoteImageContext}" </c:if>>
                                    <img src="${firstImage_63_63}" alt=""/>
                                    <span class="border"></span>
                                </li>
                            </c:if>
                            <c:if test="${not empty secondNote.url}">
                                <li  image ="${secondImage_624_351}" seccion="${secondNote.topic}" urlSeccion="${secondNote.categoryUrl}.html" title="${secondNote.title}" urlTitle="${secondNote.url}.html"  <c:if test="${properties.showcontext == true}">type="${secondNoteImageContext}"</c:if>>
                                    <img src="${secondImage_63_63}" alt=""/>
                                    <span class="border"></span>
                                </li>
                            </c:if>
                            <c:if test="${not empty thirdNote.url}">
                                <li image ="${thirdImage_624_351}" seccion="${thirdNote.topic}" urlSeccion="${thirdNote.categoryUrl}.html" title="${thirdNote.title}" urlTitle="${thirdNote.url}.html" <c:if test="${properties.showcontext == true}">type="${thirdNoteImageContext}"</c:if>>
                                    <img src="${thirdImage_63_63}" alt=""/>
                                    <span class="border"></span>
                                </li>
                            </c:if>
                            <c:if test="${not empty fourthNote.url}">
                                <li image ="${fourthImage_624_351}" seccion="${fourthNote.topic}" urlSeccion="${fourthNote.categoryUrl}.html" title="${fourthNote.title}" urlTitle="${fourthNote.url}.html" <c:if test="${properties.showcontext == true}">type="${fourthNoteImageContext}"</c:if>>
                                    <img src="${fourthImage_63_63}" alt=""/>
                                    <span class="border"></span>
                                </li>
                            </c:if>
                            <c:if test="${not empty fifthNote.url}">
                                <li image ="${fifthImage_624_351}" seccion="${fifthNote.topic}" urlSeccion="${fifthNote.categoryUrl}.html" title="${fifthNote.title}" urlTitle="${fifthNote.url}.html" <c:if test="${properties.showcontext == true}">type="${fifthNoteImageContext}"</c:if>>
                                    <img src="${fifthImage_63_63}" alt=""/>
                                    <span class="border"></span>
                                </li>
                            </c:if>
                            <c:if test="${not empty sixthNote.url}">
                                <li image ="${sixthImage_624_351}" seccion="${sixthNote.topic}" urlSeccion="${sixthNote.categoryUrl}.html" title="${sixthNote.title}" urlTitle="${sixthNote.url}.html" <c:if test="${properties.showcontext == true}">type="${sixthNoteImageContext}"</c:if>>
                                    <img src="${sixthImage_63_63}" alt=""/>
                                    <span class="border"></span>
                                </li>
                            </c:if>
                        </ul>
                                             
                    </article>
                </div>
            </div>
            <!-- END: Carrusel -->
      
    </c:when>
        <c:otherwise>
        
            <%--   show message only if this is in author mode --%>
            <c:if test = "${authorMode}"> 
               
                <h3 class="cq-texthint-placeholder" id="cq-gen96"><c:out value="${placeholdercomponentmessage}"/> <c:out value="${componentname}"/></h3>
                <img src="/libs/cq/ui/resources/0.gif" class="cq-carousel-placeholder" alt="" id="cq-gen97">  
                
            </c:if>
           
        </c:otherwise>
    </c:choose>
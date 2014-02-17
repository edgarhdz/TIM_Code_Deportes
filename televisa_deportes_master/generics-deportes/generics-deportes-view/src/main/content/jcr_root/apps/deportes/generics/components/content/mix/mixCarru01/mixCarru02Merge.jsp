
<c:choose>
	<c:when test="${firstNote != null}">
		<!-- BEGIN: Carrusel -->

		<div class="mix_carru_02 autoplay">
			<div class="type2b2_02">
				<article class="articleBigModule_02">

					<div class="galleryImages_02">
						<ul>
							<li ${active}>					
								<a href="${firstNote.url}.html">
									<div class="resize">
										<img 
									src="${firstImage_624_351}"
									 alt="${firstNote.title}"
										 />
									</div>									
								</a>
                                    <c:if test="${properties.showcontext == true}">
                                        <span class="${firstNoteImageContext}" style="display: block;"></span>
                                    </c:if>
                                    <c:if test="${properties.showcontext == false}">
                                        <span class="${firstNoteImageContext}" style="display: none;"></span>
                                    </c:if>
							</li>
							<li >					
								<a href="${secondNote.url}.html">
									<div class="resize">
										<img 
									src="${secondImage_624_351}"
									 alt="${secondNote.title}"
										 />
									</div>									
								</a>
                                    <c:if test="${properties.showcontext == true}">
                                        <span class="${secondNoteImageContext}" style="display: block;"></span>
                                    </c:if>
                                    <c:if test="${properties.showcontext == false}">
                                        <span class="${secondNoteImageContext}" style="display: none;"></span>
                                    </c:if>
							</li>
							<li>					
								<a href="${thirdNote.url}.html">
									<div class="resize">
										<img 
									src="${thirdImage_624_351}"
									 alt="${thirdNote.title}"
										 />
									</div>									
								</a>
                                    <c:if test="${properties.showcontext == true}">
                                        <span class="${thirdNoteImageContext}" style="display: block;"></span>
                                    </c:if>
                                    <c:if test="${properties.showcontext == false}">
                                        <span class="${thirdNoteImageContext}" style="display: none;"></span>
                                    </c:if>
							</li>
						</ul>
					</div>

					<div class="galleryCaption_02">
						<div class="bullets_02">
							<ul>
								<li class="background-color1"></li>
								<li></li>
								<li></li>
							</ul>
						</div>
						<h2 class="topic">
							<c:if test="${properties.showtopic == true}">                                    
                                    <a class="textcolor-title2"  href="${firstNote.url}.html" title="${firstNote.title}" ${target} >
                                        ${firstNote.topic}
                                    </a>
                            </c:if> 
						</h2>

						<h4 class="caption">
							<c:if test="${properties.showtitle == true}"> 
                                    <a href="${firstNote.url}.html" title="${firstNote.title}" ${target} >
                                        ${firstNote.title}
                                    </a>
                            </c:if>
						</h4>
					</div>


					<ul class="galleryThumbs">
                        <c:if test="${not empty firstNote.url}">
                            <li ${active}><a href="#" data-position="1"
                                data-image="${firstImage_624_351}"
                                data-topic="${firstNote.topic}"
                                data-urltopic="${firstNote.categoryUrl}.html"
                                data-title="${firstNote.title}"
                                data-urltitle="${firstNote.url}.html"
                                <c:if test="${properties.showcontext == true}"> data-type="${firstNoteImageContext}" </c:if>>
                                    <img src="${firstImage_63_63}" alt="" /> <span class="border"></span>
                            </a></li>
                        </c:if>
                        <c:if test="${not empty secondNote.url}">
                            <li>
                                <a href="#"
                                    data-position="2"
                                    data-image ="${secondImage_624_351}"
                                    data-topic="${secondNote.topic}"
                                    data-urltopic="${secondNote.categoryUrl}.html"
                                    data-title="${secondNote.title}"
                                    data-urltitle="${secondNote.url}.html"
                                    <c:if test="${properties.showcontext == true}"> data-type="${secondNoteImageContext}" </c:if>
                                >
                                    <img
                                    src="${secondImage_63_63}" alt="" />
                                    <span class="border"></span>
                                </a>

                            </li>
                        </c:if>
                        <c:if test="${not empty thirdNote.url}">
                            <li>
                                <a href="#"
                                    data-position="3"
                                    data-image ="${thirdImage_624_351}"
                                    data-topic="${thirdNote.topic}"
                                    data-urltopic="${thirdNote.categoryUrl}.html"
                                    data-title="${thirdNote.title}"
                                    data-urltitle="${thirdNote.url}.html"
                                    <c:if test="${properties.showcontext == true}"> data-type="${thirdNoteImageContext}" </c:if>
                                    >
                                    <img	src="${thirdImage_63_63}" alt="" />
                                    <span class="border"></span>
                                </a>
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
		<c:if test="${authorMode}">

			<h3 class="cq-texthint-placeholder" id="cq-gen96">
				<c:out value="${placeholdercomponentmessage}" />
				<c:out value="${componentname}" />
			</h3>
			<img src="/libs/cq/ui/resources/0.gif"
				class="cq-carousel-placeholder" alt="" id="cq-gen97">
		</c:if>

	</c:otherwise>
</c:choose>
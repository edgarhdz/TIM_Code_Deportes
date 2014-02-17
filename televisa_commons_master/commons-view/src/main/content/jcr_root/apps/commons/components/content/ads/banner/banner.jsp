<%--
 *
 * DESCRIPTION
 * -----------------------------------------------------------------------------
 *  
 *  
 *  component id = ads_banner_01
 *
 * -----------------------------------------------------------------------------
 * 
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        | Developer              | Changes
 * 1.0     | 27/02/2013  | Leonel Orozco          | Initial Creation.
 * -----------------------------------------------------------------------------
 *
 */
  ==============================================================================
--%>
<%@include file="/apps/commons/components/global.jsp"%>

<%@page import="com.televisa.commons.services.services.NoteManagerService"%>
<%@ page import="com.day.cq.wcm.api.WCMMode" %>

<fmt:message key="commons.banner.desktop.placeholder" var="placeholderDesktop"/>
<fmt:message key="commons.banner.tablet.placeholder" var="placeholderTablet"/>
<fmt:message key="commons.banner.mobile.placeholder" var="placeholderMobile"/>

<c:set var="mode" value="<%= WCMMode.fromRequest(request) %>"/>
<c:set var="modeEdit" value="<%= WCMMode.EDIT %>"/>
<c:set var="isEditOrDesignMode" value="${mode == 'EDIT' or mode == 'DESIGN'}"/>

<c:set var="hasBannersDesktop" value='<%= currentStyle.get("hasBannersDesktop", "false") %>'/>
<c:set var="networkCodeDesktop" value='<%= currentStyle.get("networkCodeDesktop", "") %>'/>
<c:set var="siteDesktop" value='<%= currentStyle.get("siteDesktop", "") %>'/>
<c:set var="hasPlecaDesktop" value='<%= currentStyle.get("hasPlecaDesktop", "false") %>'/>
<c:set var="plecaWidthDesktop" value='<%= currentStyle.get("plecaWidthDesktop", "0") %>'/>
<c:set var="plecaHeightDesktop" value='<%= currentStyle.get("plecaHeightDesktop", "0") %>'/>
<c:set var="hasBoxDesktop" value='<%= currentStyle.get("hasBoxDesktop", "false") %>'/>
<c:set var="boxWidthDesktop" value='<%= currentStyle.get("boxWidthDesktop", "0") %>'/>
<c:set var="boxHeightDesktop" value='<%= currentStyle.get("boxHeightDesktop", "0") %>'/>
<c:set var="hasComboDesktop" value='<%= currentStyle.get("hasComboDesktop", "false") %>'/>
<c:set var="superWidthDesktop" value='<%= currentStyle.get("superWidthDesktop", "0") %>'/>
<c:set var="superHeightDesktop" value='<%= currentStyle.get("superHeightDesktop", "0") %>'/>
<c:set var="bannerWidthDesktop" value='<%= currentStyle.get("bannerWidthDesktop", "0") %>'/>
<c:set var="bannerHeightDesktop" value='<%= currentStyle.get("bannerHeightDesktop", "0") %>'/>

<c:set var="hasBannersTablet" value='<%= currentStyle.get("hasBannersTablet", "false") %>'/>
<c:set var="networkCodeTablet" value='<%= currentStyle.get("networkCodeTablet", "") %>'/>
<c:set var="siteTablet" value='<%= currentStyle.get("siteTablet", "") %>'/>
<c:set var="hasPlecaTablet" value='<%= currentStyle.get("hasPlecaTablet", "false") %>'/>
<c:set var="plecaWidthTablet" value='<%= currentStyle.get("plecaWidthTablet", "0") %>'/>
<c:set var="plecaHeightTablet" value='<%= currentStyle.get("plecaHeightTablet", "0") %>'/>
<c:set var="hasBoxTablet" value='<%= currentStyle.get("hasBoxTablet", "false") %>'/>
<c:set var="boxWidthTablet" value='<%= currentStyle.get("boxWidthTablet", "0") %>'/>
<c:set var="boxHeightTablet" value='<%= currentStyle.get("boxHeightTablet", "0") %>'/>
<c:set var="hasComboTablet" value='<%= currentStyle.get("hasComboTablet", "false") %>'/>
<c:set var="superWidthTablet" value='<%= currentStyle.get("superWidthTablet", "0") %>'/>
<c:set var="superHeightTablet" value='<%= currentStyle.get("superHeightTablet", "0") %>'/>
<c:set var="bannerWidthTablet" value='<%= currentStyle.get("bannerWidthTablet", "0") %>'/>
<c:set var="bannerHeightTablet" value='<%= currentStyle.get("bannerHeightTablet", "0") %>'/>

<c:set var="hasBannersMobile" value='<%= currentStyle.get("hasBannersMobile", "false") %>'/>
<c:set var="networkCodeMobile" value='<%= currentStyle.get("networkCodeMobile", "") %>'/>
<c:set var="siteMobile" value='<%= currentStyle.get("siteMobile", "") %>'/>
<c:set var="hasPlecaMobile" value='<%= currentStyle.get("hasPlecaMobile", "false") %>'/>
<c:set var="plecaWidthMobile" value='<%= currentStyle.get("plecaWidthMobile", "0") %>'/>
<c:set var="plecaHeightMobile" value='<%= currentStyle.get("plecaHeightMobile", "0") %>'/>
<c:set var="hasBoxMobile" value='<%= currentStyle.get("hasBoxMobile", "false") %>'/>
<c:set var="boxWidthMobile" value='<%= currentStyle.get("boxWidthMobile", "0") %>'/>
<c:set var="boxHeightMobile" value='<%= currentStyle.get("boxHeightMobile", "0") %>'/>
<c:set var="hasComboMobile" value='<%= currentStyle.get("hasComboMobile", "false") %>'/>
<c:set var="superWidthMobile" value='<%= currentStyle.get("superWidthMobile", "0") %>'/>
<c:set var="superHeightMobile" value='<%= currentStyle.get("superHeightMobile", "0") %>'/>
<c:set var="bannerWidthMobile" value='<%= currentStyle.get("bannerWidthMobile", "0") %>'/>
<c:set var="bannerHeightMobile" value='<%= currentStyle.get("bannerHeightMobile", "0") %>'/>
<c:set var="hideOutOfPage" value='<%= currentStyle.get("hideOutOfPage", "false") %>' />
<c:set var="DFPTag" value="${fn:toLowerCase(DFPTag)}" />

<%--
    NoteManagerService service = (NoteManagerService) sling.getService(NoteManagerService.class);
    String template = service.getTemplateName(currentPage.getPath());
    if(template == null || (template.trim().length() <= 0)){
        template = "televisa";
    }
    pageContext.setAttribute("template", template);
--%>

<%--
 *
 * The variables DFPTag, DFPTagSeoUrl and setSeoUrlTargeting are seted in the headlibs.jsp 
 * of each template to the request object.
 * ------------------------------------------------------------------------------------------
 *
--%>
<script type="text/javascript">

// DESKTOP
var large_banner_desktop;
var short_banner_desktop;

var large_banner_desktop2;
var short_banner_desktop2;

var desktop_box_banner;
var desktop_box_banner2;

//TABLET
var tablet_box_banner;
var tablet_box_banner2;

var tablet_banner_portrait_large;
var tablet_banner_portrait_large2;

var tablet_large_banner;
var tablet_large_banner2;

var tablet_box_banner_landscape;
var tablet_box_banner_landscape2;

var tablet_short_banner;
var tablet_short_banner2;

//MOBILE
var mobile_banner_large;

//Out Of Page Banners
var dfpSlotOop;
var dfpSlotSkin;

//Flags
var bannersDisplayed = false;
var setSeoUrlTargeting = ${setSeoUrlTargeting == "true" ? true : false};
var loadDesktop = false;
var loadMobile = false;
var loadTablet_landscape = false;
var loadTablet_portrait = false;
var isEditMode = ${isEditOrDesignMode};

var Televisa = Televisa || {};
var setSeoUrlTargetSlots = setSeoUrlTargetSlots || [];
var desktopBannersSlots = desktopBannersSlots || [];
var portraitBannersSlots = portraitBannersSlots || [];
var landscapeBannersSlots = landscapeBannersSlots || [];
var mobileBannersSlots = mobileBannersSlots || [];

var googletag = googletag || {};
googletag.cmd = googletag.cmd || [];

(function() {

    var gads = document.createElement('script');
    gads.id = "googletag";
    gads.async = true;
    gads.type = 'text/javascript';

    var useSSL = 'https:' == document.location.protocol;
    gads.src = (useSSL ? 'https:' : 'http:') + '//www.googletagservices.com/tag/js/gpt.js';

    var node = document.getElementsByTagName('script')[0];
    node.parentNode.insertBefore(gads, node);
})();

var initBanners = function(){
    <c:if test="${not empty hasBannersDesktop || not empty hasBannersTablet || not empty hasBannersMobile}">
            googletag.cmd.push(function() {

                <%--  DESKTOP --%>
                <c:choose>
                <c:when test="${(not empty hasBannersDesktop) && (hasBannersDesktop == 'true')}">

                        <%-- PlecaDesktop --%>
                        <c:if test="${((not empty hasPlecaDesktop) && (hasPlecaDesktop == 'true')) && ((empty hasComboDesktop) || (hasComboDesktop == 'false'))}">
                        <c:if test="${(not empty plecaWidthDesktop) && (not empty plecaHeightDesktop) && (plecaWidthDesktop != '0') && (plecaHeightDesktop != '0')}">
                        <%--
                            pleca_desktop_primary_var = googletag.defineSlot('/${networkCodeDesktop}/${siteDesktop}/${DFPTag}', [${plecaWidthDesktop}, ${plecaHeightDesktop}], '').addService(googletag.pubads()).setTargeting("pos", "middle");
                        --%>
                        </c:if>
                        </c:if>

                        <c:if test="${((not empty hasPlecaDesktop) && (hasPlecaDesktop == 'true'))}">
                        <c:if test="${(not empty plecaWidthDesktop) && (not empty plecaHeightDesktop) && (plecaWidthDesktop != '0') && (plecaHeightDesktop != '0')}">
                        <%--
                            pleca_desktop_secondary_var = googletag.defineSlot('/${networkCodeDesktop}/${siteDesktop}/${DFPTag}', [${plecaWidthDesktop}, ${plecaHeightDesktop}], 'bannerNews1').addService(googletag.pubads()).setTargeting("pos", "middle");
                        --%>
                        </c:if>
                        </c:if>      

                        <%-- BoxDesktop --%>
                        <c:if test="${(not empty hasBoxDesktop) && (hasBoxDesktop == 'true')}">
                        <c:if test="${(not empty boxWidthDesktop) && (not empty boxHeightDesktop) && (boxWidthDesktop != '0') && (boxHeightDesktop != '0')}">

                        desktop_box_banner = googletag.defineSlot('/${networkCodeDesktop}/${siteDesktop}/${DFPTag}', [${boxWidthDesktop}, ${boxHeightDesktop}], 'desktop_box_banner').addService(googletag.pubads()).setTargeting("pos", "top");
                desktop_box_banner2 = googletag.defineSlot('/${networkCodeDesktop}/${siteDesktop}/${DFPTag}', [${boxWidthDesktop}, ${boxHeightDesktop}], 'desktop_box_banner2').addService(googletag.pubads()).setTargeting("pos", "middle");
                setSeoUrlTargetSlots.push(desktop_box_banner, desktop_box_banner2);
                desktopBannersSlots.push(desktop_box_banner, desktop_box_banner2);

                </c:if>
                        </c:if>

                <%-- ComboDesktop --%>
                <c:if test="${((not empty hasComboDesktop) && (hasComboDesktop == 'true'))}">
                        <c:if test="${(not empty superWidthDesktop) && (not empty superHeightDesktop) && (superWidthDesktop != '0') && (superHeightDesktop != '0')}">

                        large_banner_desktop = googletag.defineSlot('/${networkCodeDesktop}/${siteDesktop}/${DFPTag}', [${superWidthDesktop}, ${superHeightDesktop}], 'large_banner').addService(googletag.pubads()).setTargeting("pos", "top");
                large_banner_desktop2 = googletag.defineSlot('/${networkCodeDesktop}/${siteDesktop}/${DFPTag}', [${superWidthDesktop}, ${superHeightDesktop}], 'large_banner2').addService(googletag.pubads()).setTargeting("pos", "middle");
                setSeoUrlTargetSlots.push(large_banner_desktop, large_banner_desktop2);
                desktopBannersSlots.push(large_banner_desktop, large_banner_desktop2);

                </c:if>

                <c:if test="${(not empty bannerWidthDesktop) && (not empty bannerHeightDesktop) && (bannerWidthDesktop != '0') && (bannerHeightDesktop != '0')}">

                        short_banner_desktop = googletag.defineSlot('/${networkCodeDesktop}/${siteDesktop}/${DFPTag}', [${bannerWidthDesktop}, ${bannerHeightDesktop}], 'short_banner').addService(googletag.pubads()).setTargeting("pos", "top");
                short_banner_desktop2 = googletag.defineSlot('/${networkCodeDesktop}/${siteDesktop}/${DFPTag}', [${bannerWidthDesktop}, ${bannerHeightDesktop}], 'short_banner2').addService(googletag.pubads()).setTargeting("pos", "middle");
                setSeoUrlTargetSlots.push(short_banner_desktop, short_banner_desktop2);
                desktopBannersSlots.push(short_banner_desktop, short_banner_desktop2);

                </c:if>
                        </c:if>

                        </c:when>
                        </c:choose>

                <%--  TABLET --%>
                <c:choose>
                <c:when test="${(not empty hasBannersTablet) && (hasBannersTablet == 'true')}">

                        <%-- plecaWidthTablet --%>
                        <c:if test="${(not empty hasPlecaTablet) && (hasPlecaTablet == 'true')}">
                        <c:if test="${(not empty plecaWidthTablet) && (not empty plecaHeightTablet) && (plecaWidthTablet != '0') && (plecaHeightTablet != '0')}">

                        <%-- tablet_banner_portrait --%>
                        tablet_banner_portrait_large = googletag.defineSlot('/${networkCodeTablet}/${siteTablet}/${DFPTag}', [${plecaWidthTablet}, ${plecaHeightTablet}], 'tablet_banner_portrait').addService(googletag.pubads()).setTargeting("pos", "top");
                tablet_banner_portrait_large2 = googletag.defineSlot('/${networkCodeTablet}/${siteTablet}/${DFPTag}', [${plecaWidthTablet}, ${plecaHeightTablet}], 'tablet_banner_portrait2').addService(googletag.pubads()).setTargeting("pos", "middle");
                setSeoUrlTargetSlots.push(tablet_banner_portrait_large, tablet_banner_portrait_large2);
                portraitBannersSlots.push(tablet_banner_portrait_large, tablet_banner_portrait_large2);

                </c:if>
                        </c:if>

                <%-- ComboTablet --%>
                <c:if test="${(not empty hasComboTablet) && (hasComboTablet == 'true')}">

                        <%-- tablet_banner_landscape --%>
                        <c:if test="${(not empty superWidthTablet) && (not empty superHeightTablet) && (superWidthTablet != '0') && (superHeightTablet != '0')}">

                        tablet_large_banner = googletag.defineSlot('/${networkCodeTablet}/${siteTablet}/${DFPTag}', [${superWidthTablet}, ${superHeightTablet}], 'tablet_large_banner').addService(googletag.pubads()).setTargeting("pos", "top");
                setSeoUrlTargetSlots.push(tablet_large_banner);
                landscapeBannersSlots.push(tablet_large_banner);

                </c:if>

                <c:if test="${(not empty bannerWidthTablet) && (not empty bannerHeightTablet) && (bannerWidthTablet != '0') && (bannerHeightTablet != '0')}">

                        tablet_short_banner = googletag.defineSlot('/${networkCodeTablet}/${siteTablet}/${DFPTag}', [${bannerWidthTablet}, ${bannerHeightTablet}], 'tablet_short_banner').addService(googletag.pubads()).setTargeting("pos", "top");
                tablet_short_banner2 = googletag.defineSlot('/${networkCodeTablet}/${siteTablet}/${DFPTag}', [${bannerWidthTablet}, ${bannerHeightTablet}], 'tablet_short_banner2').addService(googletag.pubads()).setTargeting("pos", "middle");
                setSeoUrlTargetSlots.push(tablet_short_banner, tablet_short_banner2);
                landscapeBannersSlots.push(tablet_short_banner, tablet_short_banner2);

                </c:if>

                <c:if test="${(not empty superWidthTablet) && (not empty superHeightTablet) && (superWidthTablet != '0') && (superHeightTablet != '0')}">

                        tablet_large_banner2 = googletag.defineSlot('/${networkCodeTablet}/${siteTablet}/${DFPTag}', [${superWidthTablet}, ${superHeightTablet}], 'tablet_large_banner2').addService(googletag.pubads()).setTargeting("pos", "middle");
                setSeoUrlTargetSlots.push(tablet_large_banner2);
                landscapeBannersSlots.push(tablet_large_banner2);

                </c:if>

                        </c:if>

                <%-- BoxTablet --%>
                <c:if test="${(not empty hasBoxTablet) && (hasBoxTablet == 'true')}">
                        <c:if test="${(not empty boxWidthTablet) && (not empty boxHeightTablet) && (boxWidthTablet != '0') && (boxHeightTablet != '0')}">

                        tablet_box_banner = googletag.defineSlot('/${networkCodeTablet}/${siteTablet}/${DFPTag}', [${boxWidthTablet}, ${boxHeightTablet}], 'tablet_box_banner').addService(googletag.pubads()).setTargeting("pos", "top");
                tablet_box_banner2 = googletag.defineSlot('/${networkCodeTablet}/${siteTablet}/${DFPTag}', [${boxWidthTablet}, ${boxHeightTablet}], 'tablet_box_banner2').addService(googletag.pubads()).setTargeting("pos", "middle");
                portraitBannersSlots.push(tablet_box_banner, tablet_box_banner2);

                <%--  Tablet Box Banner LandScape--%>
                tablet_box_banner_landscape = googletag.defineSlot('/${networkCodeTablet}/${siteTablet}/${DFPTag}', [${boxWidthTablet}, ${boxHeightTablet}], 'tablet_box_banner_landscape').addService(googletag.pubads()).setTargeting("pos", "top");
                tablet_box_banner_landscape2 = googletag.defineSlot('/${networkCodeTablet}/${siteTablet}/${DFPTag}', [${boxWidthTablet}, ${boxHeightTablet}], 'tablet_box_banner_landscape2').addService(googletag.pubads()).setTargeting("pos", "middle");
                setSeoUrlTargetSlots.push(tablet_box_banner, tablet_box_banner2, tablet_box_banner_landscape, tablet_box_banner_landscape2);
                landscapeBannersSlots.push(tablet_box_banner_landscape, tablet_box_banner_landscape2);

                </c:if>
                        </c:if>

                        </c:when>
                        </c:choose>

                <%--  MOBiLE --%>
                <c:choose>
                <c:when test="${(not empty hasBannersMobile) && (hasBannersMobile == 'true')}">

                        <%-- PlecaMobile --%>
                        <c:if test="${(not empty hasPlecaMobile) && (hasPlecaMobile == 'true')}">
                        <c:if test="${(not empty plecaWidthMobile) && (not empty plecaHeightMobile) && (plecaWidthMobile != '0') && (plecaHeightMobile != '0')}">

                        mobile_banner_large =  googletag.defineSlot('/${networkCodeMobile}/${siteMobile}/${DFPTag}', [[3, 3], [6, 1],[320,480], [${plecaWidthMobile}, ${plecaHeightMobile}]], 'mobile_banner').addService(googletag.pubads()).setTargeting("pos", "top");
                setSeoUrlTargetSlots.push(mobile_banner_large);
                mobileBannersSlots.push(mobile_banner_large);

                </c:if>
                        </c:if>
                        </c:when>
                        </c:choose>

                <%-- Out of Page Banners --%>
                <c:if test="${hideOutOfPage == 'false'}">
                        dfpSlotOop = googletag.defineOutOfPageSlot('/${networkCodeDesktop}/${siteDesktop}/${DFPTag}', 'dfp_slot_oop').addService(googletag.pubads()).setTargeting("intertype", "layer");
                dfpSlotSkin = googletag.defineOutOfPageSlot('/${networkCodeDesktop}/${siteDesktop}/${DFPTag}', 'dfp_slot_skin').addService(googletag.pubads()).setTargeting("intertype", "skin");
                setSeoUrlTargetSlots.push(dfpSlotOop, dfpSlotSkin);

                if (setSeoUrlTargeting) {
                    setSeoUrlTargetingToSlots("${DFPTagSeoUrl}");
                }
                </c:if>

                googletag.pubads().collapseEmptyDivs();
                googletag.pubads().enableSingleRequest();
                googletag.pubads().disableInitialLoad();

                //Show banners only when isn't Edit or Design mode
                if (isEditMode) {
                    googletag.pubads().noFetch();
                }

                googletag.enableServices();

            });
    </c:if>
}

initBanners();

//To avoid calls while the windows is resizing, instead do it at the end of the resizing
(function($, sr) {

    // debouncing function from John Hann
    // http://unscriptable.com/index.php/2009/03/20/debouncing-javascript-methods/
    var debounce = function(func, threshold, execAsap) {
        var timeout;

        return function debounced() {
            var obj = this,
                    args = arguments;

            function delayed() {
                if (!execAsap)
                    func.apply(obj, args);
                timeout = null;
            };

            if (timeout)
                clearTimeout(timeout);
            else if (execAsap)
                func.apply(obj, args);

            timeout = setTimeout(delayed, threshold || 200);
        };
    }

    // smartresize
    jQuery.fn[sr] = function(fn) {
        return fn ? this.bind('resize', debounce(fn)) : this.trigger(sr);
    };

})(jQuery, 'smartresize');

$(function() {
    $(document).bind('responsive', function(e, params) {
        loadDesktop = false;
        loadTablet_portrait = false;
        loadTablet_landscape = false;
        loadMobile = false;

        switch (params.type) {
            case "mobile":
                loadMobile = true;
                break;

            case "tablet_landscape":
                loadTablet_landscape = true;
                break;

            case "tablet_portrait":
                loadTablet_portrait = true;
                break;

            case "desktop":
                loadDesktop = true;
                break;
        }

        loadBanners();

    });
});

(function () {

    Televisa.responsive = {
        type: '',
        width: 0,
        height: 0,

        fireEvent: function () {
            var me = this;
            if ($.browser.msie && parseInt($.browser.version, 10) <= 7) {
                me.width = document.body.offsetWidth;
                me.height = document.body.offsetHeight;
            } else {
                me.width = $(window).width();
                me.height = $(window).height();
            }


            if (me.width > 1024) {
                me.type = 'desktop';


                $('.tablet_box_banner_landscape').removeClass('show');
                $('.tablet_box_banner_portrait').removeClass('show');
                $('.desktop_box_banner').addClass('show');


            } else if (me.width <= 1024 && me.width > 768) {
                me.type = 'tablet_landscape';


                $('.tablet_box_banner_portrait').removeClass('show');
                $('.tablet_box_banner_landscape').addClass('show');
                $('.desktop_box_banner').removeClass('show');


            } else if (me.width <= 768 && me.width >= 624){
                me.type = 'tablet_portrait';

                $('.tablet_box_banner_landscape').removeClass('show');
                $('.tablet_box_banner_portrait').addClass('show');
                $('.desktop_box_banner').removeClass('show');

            } else {
                me.type = 'mobile';
            }

            $(document).trigger('responsive', {
                type: me.type,
                width: me.width,
                height: me.height
            });
        },

        listener: function () {
            var me = this;
            $(document).ready(function () {
                if (!bannersDisplayed) {
                    me.fireEvent();
                }
            });

            $(window).on("load", function () {
                if (!bannersDisplayed) {
                    me.fireEvent();
                }
            });

            if ("onorientationchange" in window) {
                $(window).on("orientationchange", function(){
                    me.fireEvent();
                });
            } else {
                $(window).smartresize(function(){
                    me.fireEvent();
                });
            }

        }
    };
    Televisa.responsive.listener();
})();

var loadBanners = function () {

    if (!bannersDisplayed && Televisa.responsive.type === "") {
        setTimeout(function() {
            Televisa.responsive.fireEvent();
            return;
        }, 200);
    }

    if (loadMobile) {

        googletag.cmd.push(function() {
            if (mobileBannersSlots.length > 0) {
                googletag.pubads().refresh(mobileBannersSlots);
            }
        });
        bannersDisplayed = true;
    }

    if (loadTablet_portrait) {

        googletag.cmd.push(function() {
            if (portraitBannersSlots.length > 0) {
                googletag.pubads().refresh(portraitBannersSlots);
            }
        });
        bannersDisplayed = true;
    }

    if (loadTablet_landscape) {

        googletag.cmd.push(function() {
            if (landscapeBannersSlots.length > 0) {
                googletag.pubads().refresh(landscapeBannersSlots);
            }
        });
        bannersDisplayed = true;
    }

    if (loadDesktop) {

        //Only display Out Of Page the first time that the page is loaded
        //and only when is desktop
        if (!bannersDisplayed) {
            googletag.cmd.push(function () {
                googletag.pubads().refresh([
                    dfpSlotOop,
                    dfpSlotSkin
                ]);
            });
        }

        googletag.cmd.push(function () {
            if (desktopBannersSlots.length > 0) {
                googletag.pubads().refresh(desktopBannersSlots);
            }
        });

        bannersDisplayed = true;
    }
}

var setSeoUrlTargetingToSlots = function (seoUrl) {

    for (var i = 0; i < setSeoUrlTargetSlots.length; i++) {
        setSeoUrlTargetSlots[i].setTargeting("seo-url", seoUrl);
    }
}

</script>
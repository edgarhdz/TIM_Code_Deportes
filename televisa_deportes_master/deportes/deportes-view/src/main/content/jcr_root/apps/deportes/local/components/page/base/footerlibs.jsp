<%@include file="/apps/deportes/local/components/global.jsp"%>

<script type="text/javascript">
    $(document).ready(function() {
        <%-- include js file if a commentsLike, socialNetworks or latestNews component exists --%>
        if($("#COMM_comments_social").length > 0 || $(".wdg_social_01").length > 0 || $(".art_latestnews_01")[0] || $(".art_20itemspag_01")[0] || $(".wdg_nwslt_01")) {
            head.js("http://comentarios.esmas.com/js/communities.js");
            head.js("/etc/designs/deportes/local/clientlibs/templates/base/js/altershare.js");
        }
        /* poll */
        if ($(".esmas_safe_simple_poll_box")[0]) {
            head.js("http://i2.esmas.com/encuestas/public/js/esmas_safe_iloader.js");
        }

    });
</script>
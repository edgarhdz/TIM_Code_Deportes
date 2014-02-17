<%@include file="/apps/deportes/local/components/global.jsp"%>

<% currentDesign.writeCssIncludes(pageContext); %>

<cq:includeClientLib categories="deportes.jquery"/>
<cq:includeClientLib categories="deportes.tdVideoDetalle01"/>
<cq:includeClientLib categories="deportes.groups.mix"/>
<cq:includeClientLib categories="deportes.groups.navigation"/>
<cq:includeClientLib categories="deportes.groups.structure"/>
<cq:includeClientLib categories="deportes.groups.ads"/>
<cq:includeClientLib categories="deportes.groups.widgets"/>
<cq:includeClientLib categories="deportes.groups.video"/>
<cq:includeClientLib categories="deportes.groups.articleAndImage"/>
<cq:includeClientLib categories="deportes.groups.button"/>
<cq:includeClientLib categories="deportes.base"/>

<%

        String[] tags = properties.get( "daxtags", new String[]{} );
        String flattags = "";        
        for( int n = 0; n < tags.length && n < 1; n++ ){
            if( n > 0 ) {
                flattags += "-";
            }        
            String[] slashless = tags[ n ].split( "/" );
            String[] pointless = slashless[ slashless.length - 1 ].split( ":" );
            flattags += pointless[ pointless.length - 1 ];
        }
        
        String opttags = properties.get( "optionaltags", "no-tag");

        request.setAttribute( "DAXName","deportes." + currentPage.getParent().getParent().getName() + "." + flattags +"." + opttags + ".video." + currentPage.getParent().getName() + "-" + currentPage.getName() );
		request.setAttribute( "DFPTag", flattags + "/" + currentPage.getParent().getParent().getName() + "/video" );
		request.setAttribute( "setSeoUrlTargeting", "true" );
		request.setAttribute( "DFPTagSeoUrl", currentPage.getName() );
    
%>
package com.televisa.commons.services.services.impl;

import com.day.cq.wcm.api.Page;
import com.televisa.commons.services.datamodel.Note;
import com.televisa.commons.services.gsa.GsaUtilities;
import com.televisa.commons.services.gsa.UrlMapping;
import com.televisa.commons.services.services.Externalizer;
import com.televisa.commons.services.services.GsaService;
import com.televisa.commons.services.services.NoteManagerService;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

@Component(immediate=true,metatype=true,label = "GSA Service", description = "This service is used to configure the GSA URLs.")
@Service
@Properties(value={
        @Property(name= GsaServiceImpl.GSA_DOMAIN_PARAM_NAME, label = "GSA URLs", description = "The GSA domains where the GSA feed has to be pushed to.", value={}, cardinality= Integer.MAX_VALUE),
        @Property(name= GsaServiceImpl.GSA_URL_MAPPING_PARAM_NAME, label = "Expressions", description = "Usage <actual_path>::<new_path>::<domain> (Note: domain has been deprecated.)", value={}, cardinality= Integer.MAX_VALUE),
        @Property(name= GsaServiceImpl.GSA_HTML_PARAM_NAME, label = "Add .html", boolValue=true, description = "Check to add '.html' at the end of the url.")

})
public class GsaServiceImpl implements GsaService {

    private static final Logger LOG = LoggerFactory.getLogger(GsaServiceImpl.class);

    static final String GSA_DOMAIN_PARAM_NAME = "gsa.domains";
    static final String[] GSA_DOMAIN = {};

    static final String GSA_URL_MAPPING_PARAM_NAME = "gsa.expressions";
    static final String[] GSA_URL_MAPPING_DEFAULT = {};

    static final String GSA_HTML_PARAM_NAME = "gsa.html";

    private static final int MAX_CARDINALITY = 1000;

    private Hashtable<String, UrlMapping> urlMapping = new Hashtable<String, UrlMapping>();
    private List<String> gsaDomains = new LinkedList<String>();
    private boolean addExtension;

    private ResourceResolver resolver;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;
    @Reference
    private Externalizer externalizer;
    @Reference
    private NoteManagerService service;

    protected void activate(final ComponentContext context) throws LoginException {
        resolver = resourceResolverFactory.getAdministrativeResourceResolver(null);

        Dictionary<?, ?> properties = context.getProperties();

        String[] gsaDomainsArg = PropertiesUtil.toStringArray(properties.get(GSA_DOMAIN_PARAM_NAME), GSA_DOMAIN);
        String[] urlMappingsArg = PropertiesUtil.toStringArray(properties.get(GSA_URL_MAPPING_PARAM_NAME), GSA_URL_MAPPING_DEFAULT);
        this.addExtension = PropertiesUtil.toBoolean(properties.get(GSA_HTML_PARAM_NAME),false);
        LOG.info("Activating GSA Service");

        for( int i = 0; i < gsaDomainsArg.length; i++ ) {
            LOG.info( GSA_DOMAIN_PARAM_NAME + " [ " + i + " ] " + " = " + gsaDomainsArg[ i ] );

            gsaDomains.add( gsaDomainsArg[ i ] );
        }

        for( int i = 0; i < urlMappingsArg.length; i++ ) {
            LOG.info( GSA_URL_MAPPING_PARAM_NAME + " [ " + i + " ] " + " = " + urlMappingsArg[ i ] );

            String[] urlMappingArray = urlMappingsArg[ i ].split("::");

            if( urlMappingArray.length < 3 ) {
                LOG.info( urlMappingsArg[ i ] + " doesn't follow the convention. Usage <actual_path>::<new_path>::<domain>" );
                continue;
            }

            UrlMapping urlMappingObj = new UrlMapping();
            urlMappingObj.setPath( urlMappingArray[0] );
            urlMappingObj.setVanityPath( urlMappingArray[1] );
            urlMappingObj.setDomain( urlMappingArray[2] );

            LOG.info( "urlMappingArray[0] = " + urlMappingArray[0] );
            LOG.info( "urlMappingArray[1] = " + urlMappingArray[1] );
            LOG.info( "urlMappingArray[2] = " + urlMappingArray[2] );

            urlMapping.put( urlMappingArray[0] , urlMappingObj );
        }

    }

    protected void deactivate(final ComponentContext context) {
        //log.info("Stopping OsgiParameters Service");
        this.resolver.adaptTo(Session.class).logout();
    }

    public void pushXML( Page[] pages, String action) {

        if( pages.length > 0 ) {
            LinkedList<Note> notesList = new LinkedList<Note>();
            for (Page page : pages) {
                if (service != null && page != null) {
                    Note note = service.getNote(page.getPath());
                    if (note.isArticle() || note.isPhoto() || note.isVideo()) {
                        notesList.add(note);
                    }
                } else {
                    LOG.info("Error adding note, service: " + service + ", page: " + page);
                }
            }
            while( notesList.size() > 0 ) {
                int length = Math.min(notesList.size(), MAX_CARDINALITY);
                Note[] notes = new Note[ length ];

                for(int i = 0; i < length; i++) {
                    notes[ i ] = notesList.pop();
                }

                LOG.info("Starting GSA feed xml");
                GsaUtilities.setGsaService(this);
                String xml;
                xml = GsaUtilities.getXMLGSAFeed(notes, action);

                LOG.info("xml: " + xml);

                for (String gsaDomain : this.gsaDomains) {
                    try {
                        LOG.info("Performing POST");
                        GsaUtilities.postRequest(gsaDomain, xml, notes[0].getCategory());
                    } catch (IOException e) {
                        LOG.error(e.getMessage());
                    }
                }

            }
        }

    }

    public String buildUrl( String path, String domainType ) {
        boolean containsKey = false;
        String key = "";
        String extension = addExtension && domainType.equals(Externalizer.HTML_DOMAIN)? ".html" : "";
        path = path.replaceAll("&", "%26"); //escaping xml illegal character

        for (Enumeration<String> e = urlMapping.keys(); e.hasMoreElements();) {
            key = e.nextElement();
            if( path.contains( key ) ) {
                containsKey = true;
                break;
            }
        }
        if( containsKey ) {
            UrlMapping urlMappingObj = urlMapping.get( key );
            String vanityPath = path.replaceAll( urlMappingObj.getPath() , urlMappingObj.getVanityPath() );
            return this.externalizer.getDomain(domainType) + vanityPath + extension;
        }
        if("/content/televisa/noticieros".equals(path)){
            return this.externalizer.getDomain(domainType);
        }
        return this.externalizer.getDomain(domainType) + path + extension;
    }

}

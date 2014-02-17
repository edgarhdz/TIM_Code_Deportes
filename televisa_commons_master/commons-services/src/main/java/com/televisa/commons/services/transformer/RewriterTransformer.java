
package com.televisa.commons.services.transformer;

import com.televisa.commons.services.services.Externalizer;
import org.apache.cocoon.xml.sax.AbstractSAXPipe;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.rewriter.ProcessingComponentConfiguration;
import org.apache.sling.rewriter.ProcessingContext;
import org.apache.sling.rewriter.Transformer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.IOException;
import java.util.Map;


public class RewriterTransformer extends AbstractSAXPipe implements Transformer {

    //private static final Logger log = LoggerFactory.getLogger(RewriterTransformer.class);

    private SlingHttpServletRequest httpRequest;
    private Externalizer urlExternalizer;

    private Map<String,String> elementAttributeList;
    private String[] filter;

    public RewriterTransformer () {
        /* empty constructor */
    }

    public RewriterTransformer (Externalizer externalizer, Map<String,String> elements, String[] filter){
        this.elementAttributeList = elements;
        this.filter = filter;
        this.urlExternalizer = externalizer;
    }

    public void dispose() {
        /* empty method */
    }

    public void init(ProcessingContext context, ProcessingComponentConfiguration config) throws IOException {
        this.httpRequest = context.getRequest();
    }

    @Override
    public void startElement (String nsUri, String localname, String qname, Attributes atts) throws SAXException {
        AttributesImpl elementAtts = new AttributesImpl(atts);
        String path;
        String resultPath;
        boolean approved;
        int counter;

        if (urlExternalizer != null && this.elementAttributeList.size() > 0){
            for (String elementName : this.elementAttributeList.keySet()){
                if (elementName.equalsIgnoreCase(localname)){
                    for (int i=0; i < elementAtts.getLength(); i++) {
                        if (this.elementAttributeList.get(elementName).equalsIgnoreCase(elementAtts.getLocalName(i))) {

                            // the current attribute's path
                            path = elementAtts.getValue(i);

                            // can we modify this attribute?
                            counter = 0;
                            approved = this.filter.length == 0;
                            while(!approved && counter < this.filter.length){
                                approved = this.filter[counter] != null && this.filter[counter].trim().length() > 0 ? path.toLowerCase().startsWith(this.filter[counter].toLowerCase()) : false;
                                counter++;
                            }

                            if (approved){

                                // externalize the URL using the static domain
                                resultPath = urlExternalizer.externalizeURL(Externalizer.STATIC_DOMAIN, path, httpRequest);

                                // update the attribute value
                                elementAtts.setValue(i, resultPath);
                            }
                        }
                    }
                }
            }
        }

        // return updated attributes to super and continue with the transformer chain
        super.startElement(nsUri, localname, qname, elementAtts);
    }
}

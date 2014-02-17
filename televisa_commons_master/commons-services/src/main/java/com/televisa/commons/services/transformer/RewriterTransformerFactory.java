
package com.televisa.commons.services.transformer;

import com.televisa.commons.services.services.Externalizer;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.apache.sling.rewriter.Transformer;
import org.apache.sling.rewriter.TransformerFactory;
import org.osgi.service.component.ComponentContext;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

@Component(immediate=true, metatype = true, label = "Televisa Rewriter Transformer Factory")
@Service
public class RewriterTransformerFactory implements TransformerFactory {

    /* Define the alias */
    @Property(value="global")
    static final String PIPELINE_MODE ="pipeline.mode";
    @Property(intValue=1)
    static final String SERVICE_RANKING ="service.ranking";
    @Property(label = "Element & Attribute", description = "Define an element and the attribute within that element to be modified by the rewriter, following the syntaxis: element=<element_name>; attribute=<attribute_name>", cardinality = Integer.MAX_VALUE)
    static final String ELEMENT_ATTRIBUTE = "elementAttributeList";
    @Property(label = "Filter", description="Modify only those attributes whose content starts with the specified value; if empty all attributes of the defined elements will be modified.", cardinality = Integer.MAX_VALUE)
    static final String FILTER = "filter";

    @Reference
    private Externalizer externalizer;

    private String[] filterFactory;
    private Map<String, String> elementAttributeListFactory;

    protected void activate(ComponentContext componentContext){
        config(componentContext.getProperties());
    }

    private void config(Dictionary<?, ?> properties) {
        String elementPart;
        String attributePart;
        String element;
        String attribute;
        String[] elementAttribute;
        String[] values = PropertiesUtil.toStringArray(properties.get(ELEMENT_ATTRIBUTE), new String[0]);

        String[] filter = PropertiesUtil.toStringArray(properties.get(FILTER), new String[0]);
        Map<String, String> elementAttributeList = new HashMap<String,String>();

        // parse the element & attribute list
        // following the form: element=element_name; attribute=attribute_name
        for (String val : values) {

            // check for the semicolon delimeter
            if (val != null && val.contains(";")) {
                elementAttribute = val.split(";");

                // separate the element part and the attribute part
                elementPart = elementAttribute[0];
                attributePart = elementAttribute[1];

                // both element and attribute parts must have an equal sign
                if (elementPart.contains("=") && attributePart.contains("=")) {

                    // get the actual element and attribute values
                    element = elementPart.split("=")[1].trim();
                    attribute = attributePart.split("=")[1].trim();

                    // as long as it's not an empty string it'll do just fine
                    if (element.length() > 0){
                        elementAttributeList.put(element, attribute);
                    }
                }
            }
        }
        this.elementAttributeListFactory = elementAttributeList;
        this.filterFactory = filter;
    }

    public Transformer createTransformer() {
        if (elementAttributeListFactory == null || filterFactory == null) {
            return new RewriterTransformer(externalizer, new HashMap<String, String>(), new String[0]);
        } else {
            return new RewriterTransformer(externalizer, elementAttributeListFactory, filterFactory);
        }
    }
}

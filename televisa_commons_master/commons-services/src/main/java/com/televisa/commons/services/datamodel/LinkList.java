/**
 *  DESCRIPTION
 * -----------------------------------------------------------------------------
 *  interface for LinkListImpl
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date             | Developer       | Changes
 * 1.0     | Jan 31, 2013     | jdiaz         | Initial Creation
 * -----------------------------------------------------------------------------
 */
package com.televisa.commons.services.datamodel;
import javax.jcr.Value;

/**
 * interface for LinkListImpl
 * @author jdiaz
 *
 */
public interface LinkList {

    Value[] getItems();

    void setItems(Value[] items);

    Value[] getText();

    void setText(Value[] text);

    Value[] getTarget();

    void setTarget(Value[] target);

    Value[] getLink();

    void setLink(Value[] link);

    Value[] getPath();

    void setPath(Value[] path);

    Value[] getTitle();

    void setTitle(Value[] title);

    Value[] getChannel();

    void setChannel(Value[] title);

    Value[] getConfigurationPath();

    void setConfigurationPath(Value[] path);

    Value[] getContentPage();

    void setContentPage(Value[] page);


    Value[] getProperty(String key);

    Value[] putProperty(String key, Value[] value);

    Value[] getExternalPath();

    void setExternalPath(Value[] path);

}

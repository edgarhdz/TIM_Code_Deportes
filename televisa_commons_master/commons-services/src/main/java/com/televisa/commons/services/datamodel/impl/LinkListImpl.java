/**
 *  DESCRIPTION
 * -----------------------------------------------------------------------------
 *  Class use to retrieve data from the linklist component from a provided node
 * -----------------------------------------------------------------------------
 *
 * CHANGE HISTORY
 * -----------------------------------------------------------------------------
 * Version | Date        	| Developer      	 | Changes
 * 1.0     | Jan 31, 2013   | jdiaz@xumak.com            | Initial Creation
 * 1.0     | Feb 06, 2013   | jurizar-gt@xumak.com       | Added Channel property
 * -----------------------------------------------------------------------------
 */
package com.televisa.commons.services.datamodel.impl;

import com.televisa.commons.services.datamodel.LinkList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Class use to retrieve data from the linklist component from a provided node
 *
 * @author jdiaz
 *
 */
public class LinkListImpl implements LinkList {

    private static final String ITEMS_PROPERTY = "numberofitems";
    private static final String TEXT_PROPERTY = "text";
    private static final String TARGET_PROPERTY = "target";
    private static final String LINK_TYPE_PROPERTY = "linkType";
    private static final String PATH_PROPERTY = "path";
    private static final String PATH_EXTERNAL = "externalLink";
    private static final String TITLE_PROPERTY = "title";
    private static final String CHANNEL_PROPERTY = "channelType";
    private static final String CONFIGURATION_PATH = "confpath";
    private static final String CONTENT_PAGE = "contentPage";

    private static final Logger LOG = LoggerFactory
            .getLogger(LinkListImpl.class);

    private Node mainNode;

    private String nodeName;

    private Map<String, Value[]> map = new HashMap<String, Value[]>();

    /**
     * Class constructor, sets page and nodeName
     *
     * @param mainNode
     * @param nodeName
     */
    public LinkListImpl(Node mainNode, String nodeName) {
        this.mainNode = mainNode;
        this.nodeName = nodeName;
        try {
            if (null != mainNode) {
                //LOG.info( mainNode.toString() );
                //LOG.info("NODE_NAME : " + nodeName );
                readProperties();
            }
        } catch (ValueFormatException e) {
            LOG.error(e.getMessage(), e);
        } catch (PathNotFoundException e) {
            LOG.error(e.getMessage(), e);
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Method that reads all properties from a specific node
     *
     * @throws javax.jcr.ValueFormatException
     * @throws javax.jcr.PathNotFoundException
     * @throws javax.jcr.RepositoryException
     */
    private void readProperties() throws ValueFormatException,
            PathNotFoundException, RepositoryException {
        //LOG.info(".----READ PROPERTIES------ " + nodeName );
        Long numberOfItems = 0l;
        Node node = null;
        Property property = null;

        try {
            if (nodeName.contains("jcr:content")) {
                //LOG.info("---- setting " + nodeName + " as main node" );
                mainNode = (Node) mainNode.getAncestor(0);
                nodeName = nodeName.substring(1);
                node = mainNode.getNode(nodeName );
                LOG.info( node.toString() );
            } else
            if (nodeName.contains("/content")) {
                mainNode = (Node) mainNode.getAncestor(0);
                nodeName = nodeName.substring(1);
                node = mainNode.getNode(nodeName +"/jcr:content/menulist");
            } else {
                mainNode = mainNode.getParent().getParent();
                node = mainNode.getNode(nodeName);
            }

        } catch (PathNotFoundException e) {
            putProperty(ITEMS_PROPERTY, new Value[] {});
            LOG.error("error: ", e);
        }

        //LOG.info("node: " + node);
        if (null != node) {

            if(node.hasProperty(ITEMS_PROPERTY)){

                property = node.getProperty(ITEMS_PROPERTY);

                if (property != null) {

                    numberOfItems = node.getProperty(ITEMS_PROPERTY).getValue().getLong();
                    putProperty(ITEMS_PROPERTY, new Value[] { node.getProperty(ITEMS_PROPERTY) .getValue() });
					
					/*validation to get property  TEXT_PROPERTY*/
                    if(node.hasProperty(TEXT_PROPERTY)){
                        property = node.getProperty(TEXT_PROPERTY);
                        if (property != null) {
                            if (numberOfItems > 1) {
                                putProperty(TEXT_PROPERTY, node.getProperty(TEXT_PROPERTY).getValues());
                            } else if (numberOfItems == 1) {
                                putProperty(TEXT_PROPERTY, new Value[] { node.getProperty(TEXT_PROPERTY).getValue() });
                            }
                        }
                    }
	
					/*validation to get property  TARGET_PROPERTY*/
                    if(node.hasProperty(TARGET_PROPERTY)){
                        property = node.getProperty(TARGET_PROPERTY);
                        if (property != null) {
                            if (numberOfItems > 1) {
                                putProperty(TARGET_PROPERTY, node.getProperty(TARGET_PROPERTY).getValues());
                            } else if (numberOfItems == 1) {
                                putProperty(TARGET_PROPERTY, new Value[] { node.getProperty(TARGET_PROPERTY).getValue() });
                            }
                        }
                    }
					
					
					/* validation to get property  LINK_TYPE_PROPERTY */
                    if(node.hasProperty(LINK_TYPE_PROPERTY)){
                        property = node.getProperty(LINK_TYPE_PROPERTY);
                        if (property != null) {
                            if (numberOfItems > 1) {
                                putProperty(LINK_TYPE_PROPERTY, node.getProperty(LINK_TYPE_PROPERTY).getValues());
                            } else if (numberOfItems == 1) {
                                putProperty(LINK_TYPE_PROPERTY, new Value[] { node.getProperty(LINK_TYPE_PROPERTY).getValue() });
                            }
                        }
                    }
					
					
					/* validation to get property  PATH_PROPERTY */
                    if(node.hasProperty(PATH_PROPERTY)){
                        property = node.getProperty(PATH_PROPERTY);
                        if (property != null) {
                            if (numberOfItems > 1) {
                                putProperty(PATH_PROPERTY, node.getProperty(PATH_PROPERTY).getValues());
                            } else if (numberOfItems == 1) {
                                putProperty(PATH_PROPERTY, new Value[] { node.getProperty(PATH_PROPERTY).getValue() });
                            }
                        }
                    }
					
					/* adding new code block to get PATH_EXTERNAL */
                    if(node.hasProperty(PATH_EXTERNAL)){
                        property = node.getProperty(PATH_EXTERNAL);
                        if (property != null) {
                            if (numberOfItems > 1) {
                                putProperty(PATH_EXTERNAL, node.getProperty(PATH_EXTERNAL).getValues());
                            } else if (numberOfItems == 1) {
                                putProperty(PATH_EXTERNAL, new Value[] { node.getProperty(PATH_EXTERNAL).getValue() });
                            }
                        }
                    }
	
					
					/* validation to get property  TITLE_PROPERTY */
                    if(node.hasProperty(TITLE_PROPERTY)){
                        property = node.getProperty(TITLE_PROPERTY);
                        if (property != null) {
                            if (numberOfItems > 1) {
                                putProperty(TITLE_PROPERTY, node.getProperty(TITLE_PROPERTY).getValues());
                            } else if (numberOfItems == 1) {
                                putProperty(TITLE_PROPERTY, new Value[] { node.getProperty(TITLE_PROPERTY).getValue() });
                            }
                        }
                    }
					
					/* validation to get property  CHANNEL_PROPERTY */
                    if( node.hasProperty( CHANNEL_PROPERTY )){
                        property = node.getProperty( CHANNEL_PROPERTY );
                        if (property != null) {
                            if (numberOfItems > 1) {
                                putProperty( CHANNEL_PROPERTY, node.getProperty( CHANNEL_PROPERTY ).getValues());
                            } else if (numberOfItems == 1) {
                                putProperty(CHANNEL_PROPERTY, new Value[] { node.getProperty(CHANNEL_PROPERTY).getValue() });
                            }
                        }
                    }
	
					/* validation to get property  CONFIGURATION_PATH */
                    if( node.hasProperty( CONFIGURATION_PATH )){
                        property = node.getProperty( CONFIGURATION_PATH );
                        if (property != null) {
                            if (numberOfItems > 1) {
                                putProperty( CONFIGURATION_PATH, node.getProperty( CONFIGURATION_PATH ).getValues());
                            } else if (numberOfItems == 1) {
                                putProperty(CONFIGURATION_PATH, new Value[] { node.getProperty(CONFIGURATION_PATH).getValue() });
                            }
                        }
                    }
					
					/* validation to get property  CONTENT_PAGE */
                    if( node.hasProperty( CONTENT_PAGE )){
                        property = node.getProperty( CONTENT_PAGE );
                        if (property != null) {
                            if (numberOfItems > 1) {
                                putProperty( CONTENT_PAGE, node.getProperty( CONTENT_PAGE ).getValues());
                            } else if (numberOfItems == 1) {
                                putProperty( CONTENT_PAGE, new Value[] { node.getProperty(CONTENT_PAGE).getValue() });
                            }
                        }
                    }
                }

            }

        }

    }

    public Node getMainNode() {
        return mainNode;
    }

    public void setMainNode(Node mainNode) {
        this.mainNode = mainNode;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Override
    public Value[] getItems() {
        return getProperty(ITEMS_PROPERTY);
    }

    @Override
    public void setItems(Value[] items) {
        putProperty(ITEMS_PROPERTY, items);

    }

    @Override
    public Value[] getText() {
        return getProperty(TEXT_PROPERTY);
    }

    @Override
    public void setText(Value[] text) {
        putProperty(TEXT_PROPERTY, text);
    }

    @Override
    public Value[] getTarget() {
        return getProperty(TARGET_PROPERTY);
    }

    @Override
    public void setTarget(Value[] target) {
        putProperty(TARGET_PROPERTY, target);
    }

    @Override
    public Value[] getLink() {
        return getProperty(LINK_TYPE_PROPERTY);
    }

    @Override
    public void setLink(Value[] link) {
        putProperty(LINK_TYPE_PROPERTY, link);
    }

    @Override
    public Value[] getPath() {
        return getProperty(PATH_PROPERTY);
    }

    @Override
    public void setPath(Value[] path) {
        putProperty(PATH_PROPERTY, path);
    }



    @Override
    public Value[] getExternalPath() {
        return getProperty(PATH_EXTERNAL);
    }

    @Override
    public void setExternalPath(Value[] path) {
        putProperty(PATH_EXTERNAL, path);
    }



    @Override
    public Value[] getTitle() {
        return getProperty(TITLE_PROPERTY);
    }

    @Override
    public void setTitle(Value[] title) {
        putProperty(TITLE_PROPERTY, title);
    }

    @Override
    public Value[] getProperty(String key) {
        return map.get(key);
    }

    @Override
    public Value[] putProperty(String key, Value[] value) {
        return map.put(key, value);
    }
    public Value[] getChannel(){
        return getProperty( CHANNEL_PROPERTY );
    }
    public void setChannel( Value[] channel ){
        putProperty( CHANNEL_PROPERTY, channel );
    }


    public Value[] getConfigurationPath(){
        return getProperty( CONFIGURATION_PATH );
    }

    public void setConfigurationPath( Value[] confpath ){
        putProperty( CONFIGURATION_PATH, confpath );
    }

    public Value[] getContentPage(){
        return getProperty( CONTENT_PAGE );
    }

    public void setContentPage( Value[] page ){
        putProperty( CONTENT_PAGE, page );
    }

    public String toString(){
        return mainNode.toString();

    }

}

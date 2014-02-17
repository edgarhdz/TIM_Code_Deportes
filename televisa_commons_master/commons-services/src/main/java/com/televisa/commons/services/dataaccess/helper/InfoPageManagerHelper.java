/*
 * InfoPageManagerHelper.java
 *
 * The component and service to perform the information of a pages and the respective pagination management.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.services.dataaccess.helper;

import com.televisa.commons.services.datamodel.objects.FilterGalleryHistory;
import com.televisa.commons.services.datamodel.objects.FilterIndexByTags;
import com.televisa.commons.services.datamodel.objects.FilterVideoCarousel;
import com.televisa.commons.services.datamodel.objects.InfoPage;
import com.televisa.commons.services.datamodel.objects.impl.InfoPageImpl;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Information Page Management Service
 *
 * The component and service to perform the information of a pages and the respective pagination management.
 *
 * Changes History:
 *
 *         2013-01-22 Initial Development
 *
 * @author lsztul@xumak.com
 * @version 1.0
 */
public class InfoPageManagerHelper {

    private static final Logger LOG = LoggerFactory.getLogger(InfoPageManagerHelper.class);
    private static final String MESSAGE = "Error while searching for content tagged with: ";

    protected ResourceResolver resourceResolver;

    public InfoPageManagerHelper(ResourceResolver resourceResolver) {
        super();
        this.resourceResolver = resourceResolver;
    }

    /**
     * Get a list of notes from a path and year of the date of publish.
     * @param filter
     * @return
     */

    public InfoPage findNodesByYear(FilterGalleryHistory filter) {
        try {
            if (filter == null ) {
                return null;
            }
            if (filter.getYear() == null || filter.getYear().length() <= 0) {
                return null;
            }
            if(filter.getTypeOfNote() == null || filter.getTypeOfNote().length() <= 0) {
                return null;
            }
            if (filter.getPath() == null || filter.getPath().trim().length() == 0 || "#".equalsIgnoreCase(filter.getPath().trim())) {
                return null;
            }
            if (filter.getPage() <= 0) {
                return null;
            }

            //Example 12 items per page * 15 page = 180
            Long limit = (long)filter.getItemsPerPage() * filter.getTotalPages();
            //(Number of page * items per page) - items per page: Example = ((2 * 12) => 24 - 12 => 12 is where start to recover the number of nodes)
            long offset =  (filter.getPage() * filter.getItemsPerPage()) - filter.getItemsPerPage();
            String strQuery = StatementHelper.nodesByYear(filter.getPath(), filter.getYear(), filter.getTypeOfNote());

            //Recover the Items per page
            QueryResult pageNode = queryExecution((long)filter.getItemsPerPage(), offset, strQuery);
            //Recover all the items for the number of pages
            QueryResult totalNodes = queryExecution(limit, Long.valueOf(0), strQuery);
            NodeIterator nodes = totalNodes.getNodes();

            //Is include the 3 digital after the point for the method ceil take in count the decimal for made tha aproximation
            double itemsPerPage = Double.valueOf(filter.getItemsPerPage() + 0.000);
            double totalOfNodes = 0.000;
            if (totalNodes != null && nodes != null && nodes.getSize() > 0) {
                totalOfNodes = (int)nodes.getSize();
            }
            int maxTotalPages = (int) Math.ceil(totalOfNodes / itemsPerPage);

            return new InfoPageImpl(pageNode.getNodes(), filter.getPage(), maxTotalPages);
        } catch (RepositoryException e) {
            LOG.error(MESSAGE + filter.getPath(), e.getMessage());
            return null;
        } catch (Exception e) {
            LOG.error(MESSAGE + filter.getPath(), e.getMessage());
            return null;
        }
    }

    /**
     * Get a list of notes from a path and list of tags.
     * @param filter
     * @return
     */
    public InfoPage findNodesByTags(FilterIndexByTags filter) {
        try {
            if (filter == null ) {
                return null;
            }
            if (filter.getTags() == null || filter.getTags().length == 0) {
                return null;
            }
            if (filter.getPath() == null || filter.getPath().trim().length() == 0 || "#".equalsIgnoreCase(filter.getPath().trim())) {
                return null;
            }
            if (filter.getPage() <= 0) {
                return null;
            }
            if (!filter.getArticles() && !filter.getVideos() && !filter.getPhotoGalleries() && !filter.getPartidos()) {
                return null;
            }

            //Example 15 items per page * 15 page = 225
            Long limit = (long)filter.getItemsPerPage() * filter.getTotalPages();
            //(Number of page * items per page) - items per page: Example = ((2 * 15) => 30 - 15 => 15 is where start to recover the number of nodes)
            long offset =  (filter.getPage() * filter.getItemsPerPage()) - filter.getItemsPerPage();
            String strQuery = StatementHelper.nodesByTags(filter.getPath(), filter.getTags(), filter.getArticles(), filter.getVideos(), filter.getPhotoGalleries(), filter.getPartidos());

            //Recover the Items per page
            QueryResult pageNode = queryExecution((long)filter.getItemsPerPage(), offset, strQuery);
            //Recover all the items for the number of pages
            QueryResult totalNodes = queryExecution(limit, Long.valueOf(0), strQuery);
            NodeIterator nodes = totalNodes.getNodes();
         
            //Is include the 3 digital after the point for the method ceil take in count the decimal for made tha aproximation
            double itemsPerPage = Double.valueOf(filter.getItemsPerPage() + 0.000);
            double totalOfNodes = 0.000;
            if (totalNodes != null && nodes != null && nodes.getSize() > 0) {
                totalOfNodes = (int)nodes.getSize();
            }
            int maxTotalPages = (int) Math.ceil(totalOfNodes / itemsPerPage);
            
            return new InfoPageImpl(pageNode.getNodes(), filter.getPage(), maxTotalPages);
        } catch (RepositoryException e) {
            LOG.error(MESSAGE + filter.getPath(), e.getMessage());
            return null;
        } catch (Exception e) {
            LOG.error(MESSAGE + filter.getPath(), e.getMessage());
            return null;
        }
    }

    /**
     * Get a list of notes from a path and list of tags.
     * @param filter
     * @return
     */
    public InfoPage findVideoNodesByPathAndTags(FilterVideoCarousel filter) {
        try {
            if (filter == null ) {
                return null;
            }
            if (filter.getTags() == null || filter.getTags().length == 0) {
                return null;
            }
            if (filter.getPath() == null || filter.getPath().trim().length() == 0 || "#".equalsIgnoreCase(filter.getPath().trim())) {
                return null;
            }
            if (filter.getPage() <= 0) {
                return null;
            }

            //Example 15 items per page * 15 page = 225
            Long limit = (long)filter.getItemsPerPage() * filter.getTotalPages();
            //(Number of page * items per page) - items per page: Example = ((2 * 15) => 30 - 15 => 15 is where start to recover the number of nodes)
            long offset =  (filter.getPage() * filter.getItemsPerPage()) - filter.getItemsPerPage();
            String strQuery = StatementHelper.findVideoNodesByPathAndTags(filter.getPath(), filter.getTags(), filter.getTitle(), filter.getDescription());

            //Recover the Items per page
            QueryResult pageNode = queryExecution((long)filter.getItemsPerPage(), offset, strQuery);
            //Recover all the items for the number of pages
            QueryResult totalNodes = queryExecution(limit, Long.valueOf(0), strQuery);
            NodeIterator nodes = totalNodes.getNodes();

            //Is include the 3 digital after the point for the method ceil take in count the decimal for made tha aproximation
            double itemsPerPage = Double.valueOf(filter.getItemsPerPage() + 0.000);
            double totalOfNodes = 0.000;
            if (totalNodes != null && nodes != null && nodes.getSize() > 0) {
                totalOfNodes = (int)nodes.getSize();
            }
            int maxTotalPages = (int) Math.ceil(totalOfNodes / itemsPerPage);

            return new InfoPageImpl(pageNode.getNodes(), filter.getPage(), maxTotalPages);
        } catch (RepositoryException e) {
            LOG.error(MESSAGE + filter.getPath(), e.getMessage());
            return null;
        } catch (Exception e) {
            LOG.error(MESSAGE + filter.getPath(), e.getMessage());
            return null;
        }
    }

    /**
     * Get a list of notes from a path and list of tags.
     * @param filter
     * @return
     */
    public InfoPage findVideoNodesByFilter(FilterVideoCarousel filter) {
        try {
            if (filter == null ) {
                return null;
            }
            if (filter.getTags() == null || filter.getTags().length == 0) {
                return null;
            }
            if (filter.getPath() == null || filter.getPath().trim().length() == 0 || "#".equalsIgnoreCase(filter.getPath().trim())) {
                return null;
            }
            if (filter.getPage() <= 0) {
                return null;
            }
            //Example 15 items per page * 15 page = 225
            Long limit = (long)filter.getItemsPerPage() * filter.getTotalPages();
            //(Number of page * items per page) - items per page: Example = ((2 * 15) => 30 - 15 => 15 is where start to recover the number of nodes)
            long offset =  (filter.getPage() * filter.getItemsPerPage()) - filter.getItemsPerPage();
            String strQuery = StatementHelper.findVideoNodesQueryBuilder(filter.getPath(), filter.getTags(), filter.getTitle(), filter.getDescription());

            //Recover the Items per page
            QueryResult pageNode = queryExecution((long)filter.getItemsPerPage(), offset, strQuery);
            //Recover all the items for the number of pages
            QueryResult totalNodes = queryExecution(limit, Long.valueOf(0), strQuery);
            NodeIterator nodes = totalNodes.getNodes();

            //Is include the 3 digital after the point for the method ceil take in count the decimal for made tha aproximation
            double itemsPerPage = Double.valueOf(filter.getItemsPerPage() + 0.000);
            double totalOfNodes = 0.000;
            if (totalNodes != null && nodes != null && nodes.getSize() > 0) {
                totalOfNodes = (int)nodes.getSize();
            }
            int maxTotalPages = (int) Math.ceil(totalOfNodes / itemsPerPage);


            return new InfoPageImpl(pageNode.getNodes(), filter.getPage(), maxTotalPages);
        } catch (RepositoryException e) {
            LOG.error(MESSAGE + filter.getPath(), e.getMessage());
            return null;
        } catch (Exception e) {
            LOG.error(MESSAGE + filter.getPath(), e.getMessage());
            return null;
        }
    }

    
    /**
     * Get a list of notes from a path (tags are optional)
     * @param filter
     * @return
     */
    public InfoPage findNodesByPath(FilterIndexByTags filter) {
        try {
            if (filter == null ) {
                return null;
            }
            if (filter.getPath() == null || filter.getPath().trim().length() == 0 || "#".equalsIgnoreCase(filter.getPath().trim())) {
                return null;
            }
            if (filter.getPage() <= 0) {
                return null;
            }
            if (!filter.getArticles() && !filter.getVideos() && !filter.getPhotoGalleries() && !filter.getPartidos()) {
                return null;
            }

            //Example 15 items per page * 15 page = 225
            Long limit = (long)filter.getItemsPerPage() * filter.getTotalPages();
            //(Number of page * items per page) - items per page: Example = ((2 * 15) => 30 - 15 => 15 is where start to recover the number of nodes)
            long offset =  (filter.getPage() * filter.getItemsPerPage()) - filter.getItemsPerPage();
            String strQuery = StatementHelper.nodesByTags(filter.getPath(), filter.getTags(), filter.getArticles(), filter.getVideos(), filter.getPhotoGalleries(), filter.getPartidos());
            //Recover the Items per page
            QueryResult pageNode = queryExecution((long)filter.getItemsPerPage(), offset, strQuery);
            //Recover all the items for the number of pages
            QueryResult totalNodes = queryExecution(limit, Long.valueOf(0), strQuery);
            NodeIterator nodes = totalNodes.getNodes();

            //Is include the 3 digital after the point for the method ceil take in count the decimal for made tha aproximation
            double itemsPerPage = Double.valueOf(filter.getItemsPerPage() + 0.000);
            double totalOfNodes = 0.000;
            if (totalNodes != null && nodes != null && nodes.getSize() > 0) {
                totalOfNodes = (int)nodes.getSize();
            }
            int maxTotalPages = (int) Math.ceil(totalOfNodes / itemsPerPage);

            return new InfoPageImpl(pageNode.getNodes(), filter.getPage(), maxTotalPages);
        } catch (RepositoryException e) {
            LOG.error(MESSAGE + filter.getPath(), e.getMessage());
            return null;
        } catch (Exception e) {
            LOG.error(MESSAGE + filter.getPath(), e.getMessage());
            return null;
        }
    }


    /**
     *
     * @param limit
     * @param offset
     * @param strQuery
     * @return
     */

    private QueryResult queryExecutionSQL(Long limit, Long offset, String strQuery){
        try{
            Session session = ((Session)resourceResolver.adaptTo(Session.class));
            Query query = session.getWorkspace().getQueryManager().createQuery(strQuery, Query.JCR_SQL2);
            query.setLimit(limit);
            query.setOffset(offset);
            return query.execute();
        } catch (RepositoryException e) {
            LOG.error(MESSAGE + strQuery, e.getMessage());
            return null;
        }
    }

    /**
     *
     * @param limit
     * @param offset
     * @param strQuery
     * @return
     */
    private QueryResult queryExecution(Long limit, Long offset, String strQuery){
        try{
            Session session = (resourceResolver.adaptTo(Session.class));
            Query query = session.getWorkspace().getQueryManager().createQuery(strQuery, Query.XPATH);
            query.setLimit(limit);
            query.setOffset(offset);
            return query.execute();
        } catch (RepositoryException e) {
            LOG.error("Error while searching for content tagged with: " + strQuery + "'", e.getMessage());
            return null;
        }
    }

}

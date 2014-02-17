package com.televisa.commons.services.dataaccess.helper;

import com.televisa.commons.services.utilities.Constants;
import com.televisa.commons.services.utilities.Utilities;
import org.apache.jackrabbit.util.ISO9075;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Changes History:
 *
 *         2013-03-18 gescobar Replaced CQ_LAST_REPLICATED by CQ_FIRST_REPLICATED, it will be created by the
 *                             ArticlePublishDateUpdateWorkflowProcess workflow process after the first publish.
 *                             @see com.televisa.commons.services.workflow.ArticlePublishDateUpdateWorkflowProcess
 *
 */
public final class StatementHelper {

    private static final String CQ_PAGE_CONTEXT = "cq:PageContent";
    private static final String CQ_TEMPLATE = "cq:template";
    private static final String CQ_FIRST_REPLICATED = "cq:firstReplicated";

    private static final String JCR_TITLE = "jcr:title";
    private static final String JCR_DESCRIPTION = "jcr:description";

    private static final String ARTICLE_TEMPLATE = "article/genericNote";
    private static final String VIDEO_TEMPLATE = "video";
    private static final String GALLERY_TEMPLATE = "gallery";
    private static final String CQ_LAST_REPL_ACTION = "cq:lastReplicationAction";
    private static final String ACTIVATE_ACTION = "Activate";

    private static final Logger LOG = LoggerFactory.getLogger(StatementHelper.class);

    private StatementHelper() {
    }

    /**
     *
     * @param path
     * @param articles
     * @param videos
     * @param photoGalleries
     * @return
     */
    public static String nodesByTagsSQL(String path, String[] tags, boolean articles, boolean videos, boolean photoGalleries) {
        StringBuilder queryStr = new StringBuilder("");

        queryStr.append("SELECT * FROM [" + CQ_PAGE_CONTEXT + "] where isdescendantnode(['");
        queryStr.append(path);
        queryStr.append("']) ");

        if (tags.length > 0) {
            queryStr.append("and ( ");
            for (int i = 0; i < tags.length; i++) {
                String tagID = tags[i];
                if (tagID != null && !tagID.trim().isEmpty()) {
                    tagID = tagID.toLowerCase();
                    if (i > 0) {
                        queryStr.append(" or ");
                    }
                    queryStr.append(" ( [cq:tags]").append(" like '%").append(tagID).append("%') ");
                }
            }
            queryStr.append(" ) ");
        }

        if (articles || videos || photoGalleries) queryStr.append(" and ( ");

        if (articles) queryStr.append(" contains([" + CQ_TEMPLATE + "], '%/" + ARTICLE_TEMPLATE + "')  ");
        if (videos) {
            if(articles) queryStr.append(" or ");
            queryStr.append(" contains([" + CQ_TEMPLATE + "], '%/" + VIDEO_TEMPLATE + "')  ");
        }
        if (photoGalleries) {
            if(videos || articles) queryStr.append(" or ");
            queryStr.append(" contains([" + CQ_TEMPLATE + "], '%/" + GALLERY_TEMPLATE + "')  ");
        }
        if (articles || videos || photoGalleries) queryStr.append(" ) ");
        queryStr.append(" order by [" + CQ_FIRST_REPLICATED + "] desc ");
        return queryStr.toString();
    }
    
    public static String nodesByTags(String path, String[] tags, boolean articles, boolean videos, boolean photoGalleries, boolean partidos){
        StringBuilder queryStr = new StringBuilder("");
        StringBuilder paramsStr = new StringBuilder();

        queryStr.append("/jcr:root").append(transformPathDigitsISO9075(path));
        queryStr.append("//element(*," + CQ_PAGE_CONTEXT +")");
        paramsStr.append(" @").append(CQ_FIRST_REPLICATED).append(" not null");

        if(tags != null && tags.length > 0){
            paramsStr.append(" and (");
            for (int i = 0; i < tags.length; i++) {
                String tagID = tags[i];
                if(tagID != null && tagID.trim().length() >0){
                    if(i > 0){
                        paramsStr.append(" or ");
                    }
                    paramsStr.append("jcr:like(@cq:tags").append(" , '%").append(tagID).append("')");
                }
            }
            paramsStr.append(")");
        }

        if(articles || videos || photoGalleries || partidos){
            paramsStr.append(" and ( ");
        }
        if(articles){
            paramsStr.append("@" + CQ_TEMPLATE + "= '" + Constants.ARTICLE_TEMPLATE + "'");
        }
        if(videos){
            if(articles){
                paramsStr.append(" or ");
            }
            paramsStr.append("@" + CQ_TEMPLATE + "= '" + Constants.VIDEO_TEMPLATE + "'");
        }
        if(photoGalleries){
            if(videos || articles){
                paramsStr.append(" or ");
            }
            paramsStr.append("@" + CQ_TEMPLATE + "= '" + Constants.GALLERY_TEMPLATE + "'");
        }
        if(partidos){
            if(videos || articles || photoGalleries){
                paramsStr.append(" or ");
            }
            paramsStr.append("@" + CQ_TEMPLATE + "= '" + Constants.PARTIDO_TEMPLATE + "'");
        }
        if(articles || videos || photoGalleries || partidos){
            paramsStr.append(" ) ");
        }
        if(paramsStr.toString().length() > 0){
            queryStr.append("[").append(paramsStr.toString()).append("]");
        }
        queryStr.append(" order by @" + CQ_FIRST_REPLICATED + " descending ");
        LOG.info("	query2 ::: " + queryStr.toString());
        return queryStr.toString();
    }



    public static String nodesByYear(String path, String year, String typeOfNote) {
        StringBuilder queryStr = new StringBuilder("");

        if (Utilities.isInteger(year)) {
            Integer intYear = Integer.parseInt(year);
            Integer nextYear = intYear  + 1;

            queryStr.append("/jcr:root").append(transformPathDigitsISO9075(path));
            queryStr.append("//element(*," + CQ_PAGE_CONTEXT +")");
            queryStr.append("[(@" + CQ_FIRST_REPLICATED + " >= xs:dateTime('" + intYear + "-01-01T00:00:00.000Z')");
            queryStr.append(" and @" + CQ_FIRST_REPLICATED + "<= xs:dateTime('" + nextYear + "-01-01T00:00:00.000Z'))");
            queryStr.append(" and jcr:like(@" + CQ_TEMPLATE + ", '%/" + typeOfNote + "%')");

            if (VIDEO_TEMPLATE.equals(typeOfNote)) {
                queryStr.append(" and not(jcr:like(@" + CQ_TEMPLATE + ", '%videoHome%'))");
            }

            queryStr.append(" and @" + CQ_LAST_REPL_ACTION + "= '" + ACTIVATE_ACTION + "' ]");
            queryStr.append(" order by @" + CQ_FIRST_REPLICATED +" descending");

            LOG.info("query");
            LOG.info(queryStr.toString());

        }
        LOG.debug(queryStr.toString());
        return queryStr.toString();

    }

    public static String nodesByYearSQL(String path, String year, String typeOfNote) {
        StringBuilder queryStr = new StringBuilder("");

        boolean isNumber = false;
        try {
            Integer.parseInt(year);
            isNumber = true;
        } catch (NumberFormatException e) {
            isNumber = false;
        }

        if (isNumber) {
            Integer intYear = Integer.parseInt(year);
            Integer nextYear = intYear  + 1;

            queryStr.append("SELECT * FROM [" + CQ_PAGE_CONTEXT + "] where isdescendantnode(['");
            queryStr.append(path);
            queryStr.append("']) ");
            queryStr.append(" and [" + CQ_FIRST_REPLICATED + "] >= CAST('" + intYear + "-01-01T00:00:00.000-00:00' AS DATE) ");
            queryStr.append(" and [" + CQ_FIRST_REPLICATED + "] < CAST('" + nextYear + "-01-01T00:00:00.000-00:00' AS DATE) ");

            if (typeOfNote.equals(ARTICLE_TEMPLATE)) {
                queryStr.append(" and contains([" + CQ_TEMPLATE + "], '%/" + ARTICLE_TEMPLATE + "')  ");
            } else if(typeOfNote.equals(VIDEO_TEMPLATE)){
                queryStr.append(" and contains([" + CQ_TEMPLATE + "], '%/" + VIDEO_TEMPLATE + "') and not contains([cq:template],'%videoMain%') ");
            } else if(typeOfNote.equals(GALLERY_TEMPLATE)){
                queryStr.append(" and contains([" + CQ_TEMPLATE + "], '%/" + GALLERY_TEMPLATE + "')  ");
            }

            queryStr.append(" order by [" + CQ_FIRST_REPLICATED + "] desc ");
            return queryStr.toString();
        } else {
            return queryStr.toString();
        }
    }


    public static String findVideoNodesByPathAndTags(String path, String[] tags, String title, String description) {
        StringBuilder queryStr = new StringBuilder("");
        queryStr.append("/jcr:root").append(transformPathDigitsISO9075(path));
        queryStr.append("//element(*," + CQ_PAGE_CONTEXT +")");
        StringBuilder paramsStr = new StringBuilder();

        paramsStr.append("@" + CQ_LAST_REPL_ACTION + "= '" + ACTIVATE_ACTION + "'");
        paramsStr.append(" and jcr:like(@" + CQ_TEMPLATE + ", '%/" + VIDEO_TEMPLATE + "%') ");

        if (title != null && !title.trim().isEmpty()) {
            paramsStr.append(" and jcr:like(@" + JCR_TITLE + ", '%").append(title).append("%')");
        }

        if (description != null && !description.trim().isEmpty()) {
            paramsStr.append(" and jcr:like(" + JCR_DESCRIPTION + ", '%").append(description).append("%')");
        }

        if (tags != null && tags.length > 0) {
            paramsStr.append(" and (");
            for (int i = 0; i < tags.length; i++) {
                String tagID = tags[i];
                if (tagID != null && !tagID.trim().isEmpty()) {
                    if (i > 0) {
                        paramsStr.append(" or ");
                    }
                    paramsStr.append("(jcr:like(@cq:tags").append(",'%").append(tagID).append("%'))");
                }
            }
            paramsStr.append(")");
        }

        if (paramsStr.toString().length() > 0) {
            queryStr.append("[").append(paramsStr.toString()).append("]");
        }

        queryStr.append(" order by @" + CQ_FIRST_REPLICATED + " descending");
        return queryStr.toString();
    }

    public static String findVideoNodesByPathAndTagsSQL(String path, String[] tags, String title, String description) {

        StringBuilder queryStr = new StringBuilder("");
        queryStr.append("SELECT * FROM [" + CQ_PAGE_CONTEXT + "] where isdescendantnode(['");
        queryStr.append(path);
        queryStr.append("']) ");

        queryStr.append(" and ( contains ([" + CQ_TEMPLATE + "], '%/" + VIDEO_TEMPLATE + "') ) ");

        if(title != null && !title.trim().isEmpty()){
            queryStr.append("and ([" + JCR_TITLE + "] like '%").append(title).append("%') ");
        }

        if (description != null && !description.trim().isEmpty()) {
            queryStr.append("and ([" + JCR_DESCRIPTION + "] like '%").append(description).append("%') ");
        }

        if (tags.length > 0) {
            queryStr.append("and ( ");
            for (int i = 0; i < tags.length; i++) {
                String tagID = tags[i];
                if (tagID != null && !tagID.trim().isEmpty()) {
                    tagID = tagID.toLowerCase();
                    if(i > 0){
                        queryStr.append(" or ");
                    }
                    queryStr.append(" ( [cq:tags]").append(" like '%").append(tagID).append("%') ");
                }
            }
            queryStr.append(" ) ");
        }

        queryStr.append(" order by [" + CQ_FIRST_REPLICATED + "] desc ");
        LOG.info(queryStr.toString());
        return queryStr.toString();
    }

    public static String findVideoNodesQueryBuilder(String path, String[] tags, String title, String description) {
        StringBuilder queryStr = new StringBuilder("");
        queryStr.append("/jcr:root").append(transformPathDigitsISO9075(path));
        queryStr.append("//element(*," + CQ_PAGE_CONTEXT +")");
        StringBuilder paramsStr = new StringBuilder();

        paramsStr.append("@" + CQ_LAST_REPL_ACTION + "= '" + ACTIVATE_ACTION + "'");
        paramsStr.append(" and jcr:like(@" + CQ_TEMPLATE + ", '%/" + VIDEO_TEMPLATE + "%') ");

        if (title != null && !title.trim().isEmpty()) {
            paramsStr.append(" and jcr:like(@" + JCR_TITLE + ", '%").append(title).append("%')");
        }

        if (description != null && !description.trim().isEmpty()) {
            paramsStr.append(" and jcr:like(" + JCR_DESCRIPTION + ", '%").append(description).append("%')");
        }

        if (tags != null && tags.length > 0) {
            paramsStr.append(" and (");
            for (int i = 0; i < tags.length; i++) {
                String tagID = tags[i];
                if(tagID != null && !tagID.trim().isEmpty()) {
                    if(i > 0) {
                        paramsStr.append(" and ");
                    }
                    paramsStr.append("(jcr:like(@cq:tags").append(",'%").append(tagID).append("%'))");
                }
            }
            paramsStr.append(")");
        }

        if (paramsStr.toString().length() > 0) {
            queryStr.append("[").append(paramsStr.toString()).append("]");
        }

        queryStr.append(" order by @" + CQ_FIRST_REPLICATED + " descending");
        return queryStr.toString();
    }

    public static String findVideoNodesQueryBuilderSQL(String path, String[] tags, String title, String description) {

        StringBuilder queryStr = new StringBuilder("");
        queryStr.append("SELECT * FROM [" + CQ_PAGE_CONTEXT + "] where isdescendantnode(['");
        queryStr.append(path);
        queryStr.append("']) ");

        queryStr.append(" and ( contains ([" + CQ_TEMPLATE + "], '%/" + VIDEO_TEMPLATE + "') ) ");
        queryStr.append(" and ( contains ([" + CQ_LAST_REPL_ACTION + "], '" + ACTIVATE_ACTION + "') ) ");
        if (title != null && !title.trim().isEmpty()) {
            queryStr.append("and ([" + JCR_TITLE + "] like '%").append(title).append("%') ");
        }

        if (description != null && !description.trim().isEmpty()) {
            queryStr.append("and ([" + JCR_DESCRIPTION + "] like '%").append(description).append("%') ");
        }

        if (tags.length > 0) {
            queryStr.append("and ( ");
            for (int i = 0; i < tags.length; i++) {
                String tagID = tags[i];
                if (tagID != null && !tagID.trim().isEmpty()) {
                    tagID = tagID.toLowerCase();
                    if (i > 0) {
                        queryStr.append(" and ");
                    }
                    queryStr.append(" ( [cq:tags]").append(" like '%").append(tagID).append("%') ");
                }
            }
            queryStr.append(" ) ");
        }

        queryStr.append(" order by [" + CQ_FIRST_REPLICATED + "] desc ");
        LOG.info(queryStr.toString());
        return queryStr.toString();
    }

    private static String transformPathDigitsISO9075(String path) {
        String[] split = path.split("/");
        StringBuilder transformedPath = new StringBuilder();
        for (String directory : split) {
            if (directory.matches("\\d+(\\.\\d+)?")) {
                transformedPath.append("/").append(ISO9075.encode(directory));
            } else {
                transformedPath.append("/").append(directory);
            }
        }
        return transformedPath.toString();
    }
}

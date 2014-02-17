package com.televisa.commons.services.query;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import org.slf4j.Logger;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class QueryHelper {

    private Session session;
    private final QueryBuilder queryBuilder;

    public QueryHelper(Session session, QueryBuilder queryBuilder) {
        this.session = session;
        this.queryBuilder = queryBuilder;
    }

    public String queryHelper(String path, String propertyName, String propertyValue,  String limit)throws RepositoryException{
        String pathNodeFonded = "";

        Map<String, String> map = new HashMap<String, String>();

        map.put("path", path );
        map.put("1_property", propertyName );
        map.put("1_property.value", propertyValue );

        map.put("type", "nt:unstructured");
        map.put("p.offset", "0");
        map.put("p.limit", limit);

        Query query = queryBuilder.createQuery(PredicateGroup.create(map), session);
        SearchResult result = query.getResult();

        Iterator<Node> queryResult = result.getNodes();

        while(queryResult.hasNext()){
            Node nodeResult = queryResult.next();
            pathNodeFonded = nodeResult.getParent().getPath();
        }

        return pathNodeFonded;

    }


}

package com.local.deportes.services.servlet;

import com.day.cq.wcm.api.WCMException;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import javax.jcr.Node;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.jcr.Session;
import com.day.cq.wcm.api.PageManager;


/**
 * This servlet is used for get the meeting rooms availability by day.
 * 
 * Author: jbarrera
 * Date:   30.07.2013
 * @scr.component
 * @scr.service
 * @scr.property name="sling.servlet.methods" value="GET"
 * @scr.property name="sling.servlet.paths" value="/bin/import/partidos"
 */

public class ImportPartidosContentServlet extends SlingAllMethodsServlet {

    /** @scr.reference */
    SlingRepository repository;

    /** @scr.reference */
    ResourceResolverFactory resourceResolverFactory;

    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    private final String ROOT_PATH = "/content/televisa/deportes/games";
    private final String TEMPLATE_INDEX = "/apps/deportes/local/components/page/articleIndex";
    private final String TEMPLATE_PARTIDO = "/apps/deportes/local/templates/partidoNote";
    private final String RESOURCE_TYPE_ARTICLE = "deportes/generics/components/content/article/article";
    private final String RESOURCE_TYPE_BULLET = "deportes/generics/components/content/article/artBullet01";
    private final String JCR_CONTENT = "jcr:content";
    private final String NT_UNSTRUCTURED = "nt:unstructured";
    private final String SLING_RESOURCE_TYPE = "sling:resourceType";
    private final String PROPERTY_TAGS = "cq:tags";
    private final String PROPERTY_TITLE = "title";
    private final String PROPERTY_AUTHOR = "author";
    private final String PROPERTY_BULLET = "description";
    private final String PROPERTY_TOPIC = "topic";
    private final String PROPERTY_EXTERNAL_URL = "externalUrl";

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        //get parameters
        String data = request.getParameter("data") != null ? request.getParameter("data") : "";
        String pageParentId = getDateCode();
        String parentPath = ROOT_PATH+"/"+pageParentId;
        String content = "The following content was created: ";

        try {
            JSONObject jsonObject = new JSONObject(data);
            if(jsonObject != null){
                Iterator<String> keys = jsonObject.keys();
                Session session = repository.loginAdministrative(null);
                Map authInfo = new HashMap();
                authInfo.put(JcrResourceConstants.AUTHENTICATION_INFO_SESSION,session);
                ResourceResolver resourceResolver = resourceResolverFactory.getResourceResolver(authInfo);
                // get page manager
                PageManager pageManager =  resourceResolver.adaptTo(PageManager.class);

                while(keys.hasNext()){
                    String key = keys.next();
                    String title ="";
                    JSONObject value = jsonObject.getJSONObject(key);
                    //replace white space in the key, that will be the page name
                    key = key.replaceAll(" +","-");
                    String pagePath = parentPath+"/"+key;

                    //validate property necesary to create the page
                    if(value.has("url")){
                        //validate if the page is already created
                        modifyOrCreatePage(pagePath, pageManager);
                        boolean validateParentPage = createParentPageId(parentPath,pageParentId,pageManager);

                        //create partidos note
                        if(validateParentPage){
                            // get title
                            if(value.has(PROPERTY_TITLE)){
                                title = value.getString(PROPERTY_TITLE);
                            }else{ title = key; }
                            //create page partido note
                            Page page = pageManager.create(parentPath,key,TEMPLATE_PARTIDO,title);
                            Node pageNode = session.getNode(pagePath+"/"+JCR_CONTENT);

                            if(page != null){
                                content = content + "</br>"+ page.getPath();
                            }
                            //set url property
                            pageNode.setProperty(PROPERTY_EXTERNAL_URL,value.getString("url"));

                            //tags property
                            if(value.has("tags")){
                                JSONArray tagsTemp = value.getJSONArray("tags");
                                String[] tags = multipleProperty(tagsTemp);
                                pageNode.setProperty(PROPERTY_TAGS,tags);
                            }
                            // article properties
                            pageNode.addNode("article",NT_UNSTRUCTURED);
                            if(pageNode.hasNode("article")){
                                Node articleNode = pageNode.getNode("article");
                                articleNode.setProperty(SLING_RESOURCE_TYPE,RESOURCE_TYPE_ARTICLE);

                                // topic property
                                if(value.has("topic")){
                                    JSONArray topicTemp = value.getJSONArray("topic");
                                    String[] topic = multipleProperty(topicTemp);
                                    articleNode.setProperty(PROPERTY_TOPIC,topic);
                                }
                                // bullet properties
                                if(value.has("bullet")){
                                    articleNode.addNode("description",NT_UNSTRUCTURED);
                                    Node descriptionNode = articleNode.getNode("description");
                                    descriptionNode.setProperty(SLING_RESOURCE_TYPE,RESOURCE_TYPE_BULLET);
                                    descriptionNode.setProperty(PROPERTY_BULLET,value.getString("bullet"));
                                }
                                // title/author properties
                                if(value.has("author")){
                                    articleNode.addNode("title",NT_UNSTRUCTURED);
                                    Node titleNode = articleNode.getNode("title");
                                    titleNode.setProperty(PROPERTY_AUTHOR,value.getString("author"));
                                }
                            }
                            session.save();
                        }
                    }
                }
                session.logout();
                response.getWriter().write(content);
            }
        } catch (JSONException e) {
            LOG.info(e.toString());
            response.getWriter().write("Sintax error");
        } catch (RepositoryException e) {
            LOG.info(e.toString());
            response.getWriter().write("Repository error");
        } catch (LoginException e) {
            LOG.info(e.toString());
            response.getWriter().write("Login error");
        } catch (WCMException e) {
            LOG.info(e.toString());
            response.getWriter().write("WCM error");
        }
    }

    public String[] multipleProperty(JSONArray jsonArray) throws JSONException {
        String[] property = new String[jsonArray.length()];
        if(jsonArray.length() >= 0){
            for(int i=0; i < jsonArray.length(); i++){
                String val = jsonArray.getString(i);
                property[i] = val.toString();
            }
        }
        return property;
    }

    public void modifyOrCreatePage(String path, PageManager pageManager)  {
        Page test = pageManager.getPage(path);
        try {
            if(test != null){
                Node node = test.adaptTo(Node.class);
                node.remove();
                LOG.info("The page was removed to be replaced");
            }
        }catch (RepositoryException e){
            LOG.info(e.toString());
        }
    }

    private String getDateCode(){
        Calendar currentCalendar = Calendar.getInstance();
        String year = String.valueOf(currentCalendar.get(Calendar.YEAR)).substring(2);
        String month = String.valueOf(currentCalendar.get(Calendar.MONTH) + 1);
        month = Integer.parseInt(month) < 10 ? '0' + month : month;
        return year + month;
    }

    private Boolean createParentPageId(String parentPath, String pageParentId, PageManager pageManager) throws WCMException {
        //validate if the page exist
        Page page = pageManager.getPage(parentPath);
        if(page != null){
            return true;
        }else {
            pageManager.create(ROOT_PATH, pageParentId, TEMPLATE_INDEX, pageParentId);
            //set properties
            try {
                Session session = repository.loginAdministrative(null);
                Page pageIndex = pageManager.getPage(ROOT_PATH+"/"+pageParentId);
                Node pageNode = session.getNode(ROOT_PATH+"/"+pageParentId+"/"+JCR_CONTENT);

                pageNode.setProperty("redirectTarget", pageIndex.getParent(2).getPath());
                pageNode.setProperty("sling:redirect",Boolean.TRUE);
                pageNode.setProperty("sling:redirectStatus", "302" );
                pageNode.setProperty(SLING_RESOURCE_TYPE, "foundation/components/redirect" );
                session.save();
                session.logout();
            }catch (RepositoryException e){
                LOG.info(e.toString());
            }
            return true;
        }
    }
}

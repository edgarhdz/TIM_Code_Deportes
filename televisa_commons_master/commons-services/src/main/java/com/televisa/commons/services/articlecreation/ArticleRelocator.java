package com.televisa.commons.services.articlecreation;

import java.util.Calendar;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;



/**
 * Created by IntelliJ IDEA.
 * User: palecio
 * Date: 4/2/13
 * Time: 16:40
 *
 *
 * Updated by: jurizar
 * Date: 2013.03.20
 *
 */
public class ArticleRelocator {

	private static final Logger LOG = LoggerFactory.getLogger(ArticleRelocator.class);

	private final PageManager pageManager;
	private final Page page;
	private final Session session;

	public ArticleRelocator(String pagePath, PageManager pageManager, Session session ) {
		this.pageManager = pageManager;
		this.page = pageManager.getPage(pagePath);
		this.session = session;
	}

	/**
	 * Based on the page's creation date, it builds the path it should be under.
	 * e.g. creation date is 2013/05/12, this method will return 1305
	 * @return the last two numbers of the year + the month
	 */
	public String getPageId() {
		ValueMap properties = this.page.getProperties();
		Calendar calendar = properties.get("jcr:created", Calendar.class);
		String year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2);
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (Integer.parseInt(month) < 10 ) {
			month = new StringBuffer("0").append(month).toString();
		}
		return year + month;
	}

	/**
	 * Gets the day when the page was created.
	 * @return the day when the page was created.
	 */
	public String getPageIdDay() {
		ValueMap properties = this.page.getProperties();
		Calendar calendar = properties.get("jcr:created", Calendar.class);
		String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		if (Integer.parseInt(day) < 10){
			day = new StringBuffer("0").append(day).toString();
		}
		return day;
	}

	/**
	 * based on a given path, it looks if the page has been relocated
	 * @param oldPath the original path of the page
	 * @return the new path of the page or an empty string if the page wasn't found
	 */
	public String findNewLocation(String oldPath) {
		String newLocation = "";
		int lastIndex = oldPath.lastIndexOf("/");
		String parentPath = oldPath.substring(0,lastIndex);
		String pageName = oldPath.substring(lastIndex+1);
		Page parentPage = this.pageManager.getPage(parentPath);
		if (parentPage != null) {
			Iterator<Page> pageIterator = parentPage.listChildren();
			while (pageIterator.hasNext()) {
				Page pageItem = pageIterator.next();
				if (pageItem.hasChild(pageName)) {
					newLocation = pageItem.getPath() + "/" + pageName;
					break;
				}
			}
		}
		return newLocation;
	}

	/**
	 * Adds the pageId to the page path, to create a new path
	 * @param currentPage the current page
	 * @return the path where it should be moved to.
	 */
	public String getNewLocation(Page currentPage) {
		return currentPage.getParent().getPath() + "/" + this.getPageId() + "/" + currentPage.getName();
	}

	/**
	 * Moves an article, video or photogallery page to a new location. if the page path is /parent-path/pagename it will
	 * move it to /parent-path/{pageID}/pagename where pageID is formed by the last two digits of the year + the month when the page
	 * was created.
	 * @throws com.day.cq.wcm.api.WCMException
	 */
	public void relocatePage() throws WCMException {

		Page parent = this.page.getParent();
		String pageId = this.getPageId();
		if ( this.page.getTemplate().getPath().indexOf( "/photo/" ) > 0 ||
				this.page.getTemplate().getPath().indexOf( "/video/" ) > 0 ||
				this.page.getTemplate().getPath().indexOf( "/article/" ) > 0
				) {
			try {
				String[] templateSplitted = this.page.getTemplate().getPath().split( "/" );
				String channelNode = "default";
				if ( templateSplitted.length >= 5 ) {
					channelNode = templateSplitted[ 4 ];
				}
				Node rootNode = this.session.getNode("/content/dam" );
				Node televisaNode   = getOrCreateNode( "televisa", rootNode );
				Node noticierosNode = getOrCreateNode( channelNode, televisaNode );
				Node typeNode       = getOrCreateNode( "fotos", noticierosNode );
				Node pageIdNode     = getOrCreateNode( pageId, typeNode );
				getOrCreateNode( this.getPageIdDay(), pageIdNode );
			} catch( RepositoryException e ) {
				LOG.error( "error when creating dam folders", e.getMessage() );
			}
		}
		if(
				!(
						pageId.equals(this.page.getName()   ) ||
						pageId.equals( parent.getName() ) ) &&
						(
								this.page.getTemplate().getPath().indexOf( "/photo/" ) > 0 ||
								this.page.getTemplate().getPath().indexOf( "/video/" ) > 0 ||
								this.page.getTemplate().getPath().indexOf( "/article/" ) > 0
								)
				){
			if (!parent.hasChild(pageId) && (!((String)parent.getProperties().get("cq:template", "")).contains("/apps/deportes/local/templates/articleIndex"))) {
				LOG.info("Creating a page with path {}/{}.", this.page.getParent().getPath(),pageId + "/" + this.page.getName());
				Page dateDir = this.pageManager.create(parent.getPath(), pageId, "/apps/deportes/local/templates/articleIndex", pageId);
				Node dateDirNode = dateDir.adaptTo( Node.class );
				try{
					Node jcr_content = dateDirNode.getNode("jcr:content");
					jcr_content.setProperty("redirectTarget", this.page.getParent().getPath());
					jcr_content.setProperty("sling:redirect", Boolean.TRUE );
					jcr_content.setProperty("sling:redirectStatus", "302" );
					jcr_content.setProperty("sling:resourceType", "foundation/components/redirect");
				} catch( RepositoryException e ) {
					LOG.error( "error when setting redirect properties", e.getMessage() );
				}
			}
			LOG.info(">>>> Preparing to move the page");
			this.pageManager.move(this.page, getNewLocation(this.page), null, false, true, null);
			LOG.info(">>>> Finished moving the page");
			try {
				this.session.save();
			} catch (RepositoryException e) {
				LOG.error("RepositoryException trying to save the session -> " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public Node getOrCreateNode (String name, Node parentNode) {
		Node node = null;
		if (parentNode != null) {
			try {
				if ( parentNode.hasNode(name)) {
					node = parentNode.getNode(name);
				} else {
					node = parentNode.addNode(name);
				}
			} catch(RepositoryException e) {
				LOG.error( "creating node", e.getMessage() );
			}
		}
		return node;
	}
}

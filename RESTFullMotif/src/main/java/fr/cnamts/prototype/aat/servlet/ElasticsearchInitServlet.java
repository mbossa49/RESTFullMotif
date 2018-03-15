package fr.cnamts.prototype.aat.servlet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import fr.cnamts.prototype.aat.motif.combo.view.TypeEmploiView;
import fr.cnamts.prototype.aat.motif.combo.xml.NewXMLUtil;
import fr.cnamts.prototype.aat.service.ElasticSearchService;
import fr.cnamts.prototype.aat.service.impl.ElasticSearchServiceImpl;
import fr.cnamts.prototype.aat.util.ConfUtils;
/**
 * Cette classe se charge d'initier le processus d'indexation des données de MongoDB vers Elasticsearch.
 * 
 * TODO à terme une solution avec le plugin river est envisagé.
 * 
 * @author ONDONGO-09929
 *
 */
public class ElasticsearchInitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Le logger.
	 */
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private ElasticSearchService elsService = new ElasticSearchServiceImpl();
	
	private static CacheManager cm = CacheManager.getInstance();
	
	public void init() throws ServletException {
		logger.info("Debut init() ...");
		
		/**
		 * Mise en cache new xml
		 */
//		Map<String, List<TypeEmploiView>> cacheMap = new HashMap<String, List<TypeEmploiView>>();
//		JaxbTest.checkConform(cacheMap);
//		Cache cache = cm.getCache("cacheComboMotif");
//		
//		Set<Entry<String, List<TypeEmploiView>>> entrySet = cacheMap.entrySet();
//		Iterator<Entry<String, List<TypeEmploiView>>> iterator = entrySet.iterator();
//		while (iterator.hasNext()) {
//			Entry<String, List<TypeEmploiView>> next = iterator.next();
//			cache.put(new Element(next.getKey(), next.getValue()));
//		}
		
		/**
		 * Indexation dans elasticsearch.
		 */
//		String indexName = ConfUtils.getPropertie("elasticsearch.index");
//		boolean existIndex = elsService.checkIndex(indexName);
//		boolean checkSetting = elsService.checkSetting(indexName);
//		if (existIndex && !checkSetting) {
//			
//			elsService.updateSetting(indexName);
//		}
		
		logger.info("Fin init().");
	}
	
}

package fr.cnamts.prototype.aat.servlet;

import java.io.IOException;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import fr.cnamts.prototype.aat.motif.combo.view.TypeEmploiView;
import fr.cnamts.prototype.aat.motif.combo.xml.NewXMLUtil;
import fr.cnamts.prototype.aat.service.MongoDBService;
import fr.cnamts.prototype.aat.service.impl.MongoDBServiceImpl;
/**
 * Cette classe se charge d'initier le processus de peuple dans mongoDB.
 * TODO à terme un batch sera mis en place.
 * 
 * @author ONDONGO-09929
 *
 */
public class MongoDBInitServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Logger
	 */
	private Logger logger = Logger.getLogger(this.getClass());

	private MongoDBService mongoService = new MongoDBServiceImpl();
	
	@Override
	public void init() throws ServletException {
		
//		CacheManager cm = CacheManager.getInstance();
//		Cache cache = cm.getCache("cacheComboMotif");
//		Map<String, List<TypeEmploiView>> cacheMap = new HashMap<String, List<TypeEmploiView>>();
//		NewXMLUtil.chargerNewXMLInMap(cacheMap);
//		Set<Entry<String, List<TypeEmploiView>>> entrySet = cacheMap.entrySet();
//		 Iterator<Entry<String, List<TypeEmploiView>>> iterator = entrySet.iterator();
//		 while (iterator.hasNext()) {
//			Map.Entry<java.lang.String, java.util.List<fr.cnamts.prototype.aat.motif.combo.view.TypeEmploiView>> 
//			entry = (Map.Entry<java.lang.String, java.util.List<fr.cnamts.prototype.aat.motif.combo.view.TypeEmploiView>>) iterator
//					.next();
//			logger.info("Chargement en cache de fichier nouveau modèle XML du motif ayant pour code =" +entry.getKey());
//			cache.put(new Element(entry.getKey(), entry.getValue()));			
//		}		
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String statutBatch = "Chargement des données avec succès";
		try {
			mongoService.chargerReferentielAAT();
		} catch (Exception e) {
			statutBatch = "Le chargement des données a échoué. <br> Cause : "+e.getMessage() + "<br> Consulter les log tomcat pour plus d'information";
		}
		req.setAttribute("statutBatch", statutBatch);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/mongodb.jsp" ).forward( req, resp);
	}
	
	public static void main(String[] args) {
		String s = "Chargement des données avec succès";
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    System.out.println(s);
	}
}

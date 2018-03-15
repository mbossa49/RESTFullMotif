package fr.cnamts.prototype.aat;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;

import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import fr.cnamts.prototype.aat.entity.Motif;
import fr.cnamts.prototype.aat.service.MongoDBService;
import fr.cnamts.prototype.aat.service.impl.MongoDBServiceImpl;
import fr.cnamts.prototype.aat.util.ConfUtils;
import fr.cnamts.prototype.aat.util.FileUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class Launcher {

	protected static final ESLogger logger = ESLoggerFactory.getLogger(Launcher.class.getName());
	public static void main(String[] args) throws IOException {
		MongoDBService mongo = new MongoDBServiceImpl();
//		ElasticSearchService elsService = new ElasticSearchServiceImpl();
		
//		
//		List<Motif> listeTrieeMotif = mongo.getListeTrieeMotif();
//		SortedMap<String, List<MotifView>> listMotifToSortedMap = MotifMapper.listMotifToSortedMap(listeTrieeMotif);
//	
//		System.out.println(gson.toJson(listMotifToSortedMap));
		
//		TransportClient elasticSearchClient = createClient();
		
//		settingIndex(elasticSearchClient, "ref_aat");

//		search(elasticSearchClient,"far");
		
//		elasticSearchClient.close();
		
		byte[] a = null;
		// Motif findMotifByCode = mongo.findMotifByCode("00020002");
		System.out.println(Arrays.equals(a, null));
	}

	private static void search(TransportClient elasticSearchClient, String stg) {
		String pattern = "libelle:*"+stg+"* OR synonymes:*" + stg+"*";
		SearchResponse actionGet = elasticSearchClient.prepareSearch(ConfUtils.getPropertie("elasticsearch.index"))
			.setTypes(ConfUtils.getPropertie("elasticsearch.type"))
			.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
			.setQuery(
					QueryBuilders.queryString(pattern).analyzer("french")
        		)
			.execute()
			.actionGet();
		SearchHit[] hits = actionGet.getHits().getHits();
		logger.info("Le nombre de motif qui correspondent est {} ", actionGet.getHits().getTotalHits());
	}

	private static void settingIndex(final TransportClient elasticSearchClient, final String indiceName) throws IOException {
		
		File settingFile = new File(ConfUtils.getPropertie("elasticsearch.setting"));
		
		/**
		 * Close index [ref_aat]
		 */	
		elasticSearchClient.admin().indices().prepareClose(indiceName).execute();
		UpdateSettingsRequestBuilder prepareUpdateSettings = elasticSearchClient.admin().indices().prepareUpdateSettings(indiceName);
		prepareUpdateSettings.setSettings(FileUtils.FileToString(settingFile)).execute();
		elasticSearchClient.admin().indices().prepareOpen(indiceName).execute();
	}

	/**
	 * 
	 * @return
	 */
	private static TransportClient createClient() {
		TransportClient elasticSearchClient = new TransportClient(); 
		elasticSearchClient.addTransportAddress(
				new InetSocketTransportAddress(new InetSocketAddress(
						ConfUtils.getPropertie("elasticsearch.host"), 
						Integer.valueOf(ConfUtils.getPropertie("elasticsearch.port")))));
		return elasticSearchClient;
	}
}

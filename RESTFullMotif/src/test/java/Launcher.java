

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import fr.cnamts.prototype.aat.entity.Motif;
import fr.cnamts.prototype.aat.service.MongoDBService;
import fr.cnamts.prototype.aat.service.impl.ElasticSearchServiceImpl;
import fr.cnamts.prototype.aat.service.impl.MongoDBServiceImpl;
import fr.cnamts.prototype.aat.util.ConfUtils;
import fr.cnamts.prototype.aat.util.Constante;

public class Launcher {
	
	protected static final ESLogger logger = ESLoggerFactory.getLogger(Launcher.class.getName());
	/**
	 * Permet de donner une priorite au libelles par rapport aux synonymes
	 */
	static int LIB_BOOST_VALUE = 10;
	static ElasticSearchServiceImpl elasticSearchServiceImpl = new ElasticSearchServiceImpl();
	static  MongoDBService mongoDBServiceImpl = new  MongoDBServiceImpl();
	
	public static void main(String[] args) throws Exception {

//		loadMongoDB();	
//		indexerELS();
		search("Lésionz");
	}

	private static void search(String libPattern){
		TransportClient client = getClientTransport();
		logger.info("Search term =====> [{}]", libPattern);

		SearchResponse actionGet = client.prepareSearch(ConfUtils.getPropertie("elasticsearch.index"))
				.setTypes(ConfUtils.getPropertie("elasticsearch.type"))
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(

        		QueryBuilders.boolQuery()
        		
//    			.should(QueryBuilders.fuzzyQuery("libelle", libPattern)
//    					.fuzziness(Fuzziness.TWO)
//    					.prefixLength(1)
//    					.boost(LIB_BOOST_VALUE))
    			
    			.should(QueryBuilders.matchPhrasePrefixQuery("libelle", libPattern)
    					.analyzer("custom_analyzer")
    					.operator(Operator.AND)
    					.boost(LIB_BOOST_VALUE))
//    			.should(QueryBuilders.queryString("synonymes:"+libPattern)
//    					.defaultOperator(org.elasticsearch.index.query.QueryStringQueryBuilder.Operator.AND))
        		)
        .execute()
        .actionGet();
		client.close();
		
		SearchHit[] hits = actionGet.getHits().getHits();
		logger.info("Le nombre de motif qui correspondent est {} ", actionGet.getHits().getTotalHits());
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			logger.info("le libéllé est =====> {} ===> score {}" , (String)source.get("libelle"), searchHit.score());
		}
	}
	
	private static void loadMongoDB() throws Exception {
		//Chargement référentiel
		mongoDBServiceImpl.chargerReferentielAAT();
	}

	private static void indexerELS() {
		//Indexation
		ElasticSearchServiceImpl elasticSearchServiceImpl = new ElasticSearchServiceImpl();
		List<Motif> listeTrieeMotif = mongoDBServiceImpl.getListeTrieeMotif();
//		List<Motif> listeTrieeMotif = new ArrayList<Motif>();
//		Motif m1 = new Motif();
//		m1.setLibelle("ondongo");
//		m1.setSynonymes(null);
//		listeTrieeMotif.add(m1);
		for (Motif motif : listeTrieeMotif) {
			elasticSearchServiceImpl.indexerDocument("ref_aat", "motif", motif);
		}
	}
	
	private static TransportClient getClientTransport(){
		TransportClient elasticSearchClient = new TransportClient(); 
		elasticSearchClient.addTransportAddress(
				new InetSocketTransportAddress(new InetSocketAddress(
						ConfUtils.getPropertie("elasticsearch.host"), 
						Integer.valueOf(ConfUtils.getPropertie("elasticsearch.port")))));
		return elasticSearchClient;
	}
	
	
	private static void lireCM10(){		
		String csvFile =  ConfUtils.getPropertie("commun.cim10.directory") + "CIM10.csv";
        BufferedReader br = null;
        String line =  "";
        try {
				br = new BufferedReader(new FileReader(csvFile));		
				while ((line = br.readLine()) != null) {  
					String[] fields = line.split(Constante.CSV_SERAPTOR);
					logger.info("la cim10 => {} ", fields[1]);
					
				}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

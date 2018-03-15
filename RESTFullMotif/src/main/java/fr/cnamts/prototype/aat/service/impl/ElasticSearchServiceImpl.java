package fr.cnamts.prototype.aat.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.Base64;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import fr.cnamts.prototype.aat.entity.Motif;
import fr.cnamts.prototype.aat.service.ElasticSearchService;
import fr.cnamts.prototype.aat.util.ConfUtils;
import fr.cnamts.prototype.aat.util.Constante;
import fr.cnamts.prototype.aat.util.FileUtils;

public class ElasticSearchServiceImpl implements ElasticSearchService {

	/**
	 * Logger
	 */
	protected static final ESLogger logger = ESLoggerFactory.getLogger(ElasticSearchServiceImpl.class.getName());
	/**
	 * Permet de donner une priorite au libelles par rapport aux synonymes
	 */
	static int LIB_BOOST_VALUE = 10;
	public void creerIndex(String index) {
		// TODO Auto-generated method stub
	}

	public void supprimerIndex(String index) {
		// TODO Auto-generated method stub
	}

	public void indexerDocument(String index, String type, String id) {
		// TODO Auto-generated method stub
	}

	public void indexerDocument(String index, String type, Motif motif) {
		TransportClient client = getClientTransport();
		
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("code", motif.getCode());
		json.put("libelle", motif.getLibelle());
		json.put("codification", motif.getCodification());
		json.put("synonymes", motif.getSynonymes());

		json.put("htlm", motif.getDureeHTML());
		json.put("xml", motif.getDureeXML());
		json.put("pdf", motif.getDureePDF());
		

		IndexResponse indexResponse = client.prepareIndex(index, type, motif.getCode())
		.setSource(json)
		.get();
		System.out.println(indexResponse.getHeaders());
		client.close();
	}

	private TransportClient getClientTransport(){
		TransportClient elasticSearchClient = new TransportClient(); 
		elasticSearchClient.addTransportAddress(
				new InetSocketTransportAddress(new InetSocketAddress(
						ConfUtils.getPropertie("elasticsearch.host"), 
						Integer.valueOf(ConfUtils.getPropertie("elasticsearch.port")))));
		return elasticSearchClient;
	}

	public void supprimerDocument(String index, String type, Motif motif) {
		// TODO Auto-generated method stub
		
	}

	public void modifierDocument(String index, String type, Motif motif) {
		// TODO Auto-generated method stub
		
	}

	public Motif lireDocument(String index, String code) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.cnamts.prototype.aat.service.ElasticSearchService#listDocument(java.lang.String)
	 */
	public List<Motif> listDocument(String libPattern) {
		TransportClient client = getClientTransport();
		
		List<Motif> resultat = new ArrayList<Motif>();
		SearchResponse actionGet = client.prepareSearch(ConfUtils.getPropertie("elasticsearch.index"))
		.setTypes(ConfUtils.getPropertie("elasticsearch.type"))
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(
        		QueryBuilders.boolQuery()
    			.should(QueryBuilders.matchPhrasePrefixQuery("libelle", libPattern)
    					.operator(Operator.AND)
    					.boost(LIB_BOOST_VALUE))
    			.should(QueryBuilders.queryString("synonymes:"+libPattern).defaultOperator(org.elasticsearch.index.query.QueryStringQueryBuilder.Operator.AND))
        		)
        .execute()
        .actionGet();
		client.close();
		
		SearchHit[] hits = actionGet.getHits().getHits();
		logger.info("Le nombre de motif qui correspondent est {} ", actionGet.getHits().getTotalHits());
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			Motif motif = new Motif();
			motif.setCode((String)source.get("code"));
			motif.setLibelle((String)source.get("libelle"));
			motif.setCodification((String)source.get("codification"));
//			motif.setSynonymes((List<String>) source.get("synonymes"));

			try {
				if (null!=source.get("html")) {
					byte[] res = Base64.decode((String) source.get("html"));
					motif.setDureeHTML(res);
				}
				if (null!=source.get("xml")) {
					byte[] res = Base64.decode((String) source.get("xml"));
					motif.setDureeXML(res);
				}
				if (null!=source.get("pdf")) {
					byte[] res = Base64.decode((String) source.get("pdf"));
					motif.setDureePDF(res);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			resultat.add(motif);
		}
		return resultat;
	}

	public List<String> listLibelleMotif(String libPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.cnamts.prototype.aat.service.ElasticSearchService#checkIndex(java.lang.String)
	 */
	public boolean checkIndex(String index) {
		boolean exists = getClientTransport().admin().indices()
			    .prepareExists(index)
			    .execute().actionGet().isExists();
		return exists;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.cnamts.prototype.aat.service.ElasticSearchService#checkSetting(java.lang.String)
	 */
	public boolean checkSetting(String index){
		GetSettingsResponse actionGet = getClientTransport().admin().indices().prepareGetSettings(index).execute().actionGet();
		boolean containsKey = actionGet.getIndexToSettings().containsKey(index);
		return containsKey;
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.cnamts.prototype.aat.service.ElasticSearchService#updateSetting(java.lang.String)
	 */
	public void updateSetting(String index){
		getClientTransport().admin().indices().prepareClose(index).execute();
		UpdateSettingsRequestBuilder prepareUpdateSettings = getClientTransport().admin().indices().prepareUpdateSettings(index);
		prepareUpdateSettings.setSettings(FileUtils.FileToString(new File(ConfUtils.getPropertie("elasticsearch.setting")))).execute();
		getClientTransport().admin().indices().prepareOpen(index).execute();
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.cnamts.prototype.aat.service.ElasticSearchService#getDureeMotifByCode(java.lang.String)
	 */
	public byte[] getDureeMotifByCode(String codeMotif) {

		TransportClient client = getClientTransport();		
		SearchResponse actionGet = client.prepareSearch(ConfUtils.getPropertie("elasticsearch.index"))
		.setTypes(ConfUtils.getPropertie("elasticsearch.type"))
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(QueryBuilders.termQuery("code", codeMotif))

        .execute()
        .actionGet();
		client.close();
		
		byte[] dureeMotif = null;
		SearchHit[] hits = actionGet.getHits().getHits();
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			try {
				if (null!=source.get(Constante.CHAMP_HTML)) {
					dureeMotif = Base64.decode((String) source.get(Constante.CHAMP_HTML));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dureeMotif;
	}

	public byte[] getDureeXMLMotifByCode(String codeMotif) {
		TransportClient client = getClientTransport();		
		SearchResponse actionGet = client.prepareSearch(ConfUtils.getPropertie("elasticsearch.index"))
		.setTypes(ConfUtils.getPropertie("elasticsearch.type"))
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(QueryBuilders.termQuery("code", codeMotif))
        .execute()
        .actionGet();
		client.close();
		
		byte[] dureeMotif = null;
		SearchHit[] hits = actionGet.getHits().getHits();
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			try {
				if (null!=source.get(Constante.CHAMP_XML)) {
					dureeMotif = Base64.decode((String) source.get(Constante.CHAMP_XML));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dureeMotif;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.cnamts.prototype.aat.service.ElasticSearchService#getPDFMotifByCode(java.lang.String)
	 */
	public byte[] getPDFMotifByCode(String codeMotif) {
		TransportClient client = getClientTransport();		
		SearchResponse actionGet = client.prepareSearch(ConfUtils.getPropertie("elasticsearch.index"))
		.setTypes(ConfUtils.getPropertie("elasticsearch.type"))
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(QueryBuilders.termQuery("code", codeMotif))
        .execute()
        .actionGet();
		client.close();
		
		byte[] dureeMotif = null;
		SearchHit[] hits = actionGet.getHits().getHits();
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			try {
				if (null != source.get(Constante.CHAMP_PDF)) {
					dureeMotif = Base64.decode((String) source.get(Constante.CHAMP_PDF));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dureeMotif;
	}
	
	public static void main(String[] args) {
		TransportClient client = new ElasticSearchServiceImpl().getClientTransport();
		String libPattern ="prothèse";
		String pattern = "libelle:".concat(libPattern).concat("*").concat(" OR synonymes:").concat(libPattern);
		SearchResponse actionGet = client.prepareSearch(ConfUtils.getPropertie("elasticsearch.index"))
		.setTypes(ConfUtils.getPropertie("elasticsearch.type"))
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(
        		QueryBuilders.queryString(pattern) // KO accent
//        		QueryBuilders.boolQuery().should(QueryBuilders.queryString("libelle:"+libPattern+"*")).should(QueryBuilders.matchQuery("synonymes", libPattern))
        	)
        .execute()
        .actionGet();
		client.close();
		
		SearchHit[] hits = actionGet.getHits().getHits();
		logger.info("Le nombre de motif qui correspondent est {} ", actionGet.getHits().getTotalHits());
		
	}
}

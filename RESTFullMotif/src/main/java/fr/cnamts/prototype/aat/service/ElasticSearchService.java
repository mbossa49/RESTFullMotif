package fr.cnamts.prototype.aat.service;

import java.util.List;

import fr.cnamts.prototype.aat.entity.Motif;

/**
 * 
 * @author ONDONGO-09929
 * Interface offrant les services qui permettent d'indexer les données d'une source
 * vers le moteur Elasticsearch.
 */
public interface ElasticSearchService {

	/**
	 * 
	 * @param index
	 */
	public void creerIndex(final String index);
	
	/**
	 * 
	 * @param index
	 */
	public void supprimerIndex(final String index);
	
	/**
	 * 
	 * @param index
	 * @param type
	 * @param id
	 */
	public void indexerDocument(final String index, final String type, String id);
	
	/**
	 * 
	 * @param index
	 * @param type
	 * @param id
	 */
	public void indexerDocument(final String index, final String type, final Motif motif);
	
	/**
	 * 
	 * @param index
	 * @param type
	 * @param motif
	 */
	public void supprimerDocument(final String index, final String type, final Motif motif);
	
	/**
	 * 
	 * @param index
	 * @param type
	 * @param motif
	 */
	public void modifierDocument(final String index, final String type, final Motif motif);
	
	/**
	 * 
	 * @param index
	 * @param code
	 * @return
	 */
	public Motif lireDocument(final String index, final String code);
	
	/**
	 * Retourne la liste de motif qui, dans les libellés ou les synonymes, contiennent le terme passé en paramètre.
	 * @param libPattern : critère de recherche.
	 * @return
	 */
	public List<Motif> listDocument(final String libPattern);
	
	/**
	 * Retourne la liste de motif.libelle qui, dans les libellés ou les synonymes, contiennent le terme passé en paramètre.
	 * @param libPattern : critèrecde recherche.
	 * @return
	 */
	public List<String> listLibelleMotif(final String libPattern);
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	boolean checkIndex(final String index);
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public boolean checkSetting(String index);
	
	/**
	 * 
	 * @param index
	 */
	public void updateSetting(String index);
	
	/**
	 * 
	 * @param codeMotif
	 * @return
	 */
	public byte[] getDureeMotifByCode(String codeMotif);

	/**
	 * 
	 * @param codeMotif
	 * @return
	 */
	public byte[] getDureeXMLMotifByCode(String codeMotif);

	/**
	 * 
	 * @param codeMotif
	 * @return
	 */
	public byte[] getPDFMotifByCode(String codeMotif);
}

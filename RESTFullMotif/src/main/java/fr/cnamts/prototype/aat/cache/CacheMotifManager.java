package fr.cnamts.prototype.aat.cache;

import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import fr.cnamts.prototype.aat.dto.MotifView;
import fr.cnamts.prototype.aat.entity.Motif;
import fr.cnamts.prototype.aat.util.ConfUtils;

public class CacheMotifManager {
	
	/**
	 * Logger.
	 */
	private Logger logger = Logger.getLogger(this.getClass());
	
	private CacheMotif cache;
	private SortedMap<String, List<MotifView>> mapTri; 
	private long dateChargement;
	private long dureeCache;
	
	private static CacheMotifManager instance; 
	
	private CacheMotifManager(){
		
	}
	
	/**
	 * 
	 */
	public static CacheMotifManager getInstance() {
		
		 if(instance == null){ 
			instance = new CacheMotifManager();
			instance.setCache(new CacheMotif());
			instance.setDateChargement(new Date().getTime());
			instance.setDureeCache(Long.valueOf(ConfUtils.getPropertie("commun.duree.cache")));
			instance.setMapTri(new TreeMap<String, List<MotifView>>());
		 }
		 return instance;
	}

	public CacheMotif getCache() {
		return cache;
	}

	public void setCache(CacheMotif cache) {
		this.cache = cache;
	}
	/**
	 * @return the dateChargement
	 */
	public long getDateChargement() {
		return dateChargement;
	}

	/**
	 * @param dateChargement the dateChargement to set
	 */
	public void setDateChargement(long dateChargement) {
		this.dateChargement = dateChargement;
	}

	/**
	 * @return the dureeCache
	 */
	public long getDureeCache() {
		return dureeCache;
	}

	/**
	 * @param dureeCache the dureeCache to set
	 */
	public void setDureeCache(long dureeCache) {
		this.dureeCache = dureeCache;
	}

	
	/**
	 * Vide le cache en cas de durée de vie expirée.
	 */
	public void vide(){
		logger.info("Début du processus de vidage du cache ...");

		this.cache.clear();
		this.dateChargement = new Date().getTime();
		this.mapTri = new TreeMap<String, List<MotifView>>();
		
		logger.info("Fin du processus de vidage du cache ...");
	}
	
	/**
	 * 
	 * @param codeMotif
	 * @return
	 */
	public Motif getMotif(final String codeMotif){
		
		return this.cache.get(codeMotif);
	}
	
	/**
	 * 
	 * @param motif
	 */
	public void setMotif(final Motif motif){
		this.cache.put(motif.getCode(), motif);
	}
	
	/**
	 * 
	 * @param codeMotif
	 * @return
	 */
	public Motif chargerMotifDuCache(final String codeMotif){
		Motif motif = null;
		//long systemDate = new Date().getTime();
		long dateExpiration = dateChargement + dureeCache;
		long systemDate = new Date().getTime();
		
		if(dateExpiration < systemDate){
			logger.info("Vidage du cache pour cause de durée de vie expirée");
			vide();
		}else {
			motif = cache.get(codeMotif);
		}
		return motif;
	}

	/**
	 * @return the mapTri
	 */
	public SortedMap<String, List<MotifView>> getMapTri() {
		return mapTri;
	}

	/**
	 * @param mapTri the mapTri to set
	 */
	public void setMapTri(SortedMap<String, List<MotifView>> mapTri) {
		this.mapTri = mapTri;
	}
}

package fr.cnamts.prototype.aat.motif.combo.view;

import java.io.Serializable;
import java.util.List;

public class ComplementView implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Code = 0 si aucun complement.
	 */
	private String code;
	
	private String libelle;
	
	private int duree;
	
	private List<PathologieView> pathologies;

	/**
	 * @return the duree
	 */
	public int getDuree() {
		return duree;
	}

	/**
	 * @param duree the duree to set
	 */
	public void setDuree(int duree) {
		this.duree = duree;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * @return the pathologies
	 */
	public List<PathologieView> getPathologies() {
		return pathologies;
	}

	/**
	 * @param pathologies the pathologies to set
	 */
	public void setPathologies(List<PathologieView> pathologies) {
		this.pathologies = pathologies;
	}
}

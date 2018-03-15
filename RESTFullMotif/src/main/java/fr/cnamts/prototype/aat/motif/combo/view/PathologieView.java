package fr.cnamts.prototype.aat.motif.combo.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PathologieView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	private String libelle;
	
	private int duree;
	
	private List<ComplementView> listeComplementViews;
	
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the listeComplementViews
	 */
	public List<ComplementView> getListeComplementViews() {
		return listeComplementViews;
	}

	/**
	 * @param listeComplementViews the listeComplementViews to set
	 */
	public void setListeComplementViews(List<ComplementView> listeComplementViews) {
		this.listeComplementViews = listeComplementViews;
	}
	
	public void addComplement(ComplementView complView){
		
		if(null == this.getListeComplementViews()){
			this.setListeComplementViews(new ArrayList<ComplementView>());
		}
		this.getListeComplementViews().add(complView);
	}
}

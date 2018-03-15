package fr.cnamts.prototype.aat.motif.combo.view;

import java.io.Serializable;
import java.util.List;

public class TypeEmploiView  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	private String libelle;
	
	private List<ComplementView> listeComplementViews;
	
	private List<PathologieView> listePathologie;

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
	 * @return the listePathologie
	 */
	public List<PathologieView> getListePathologie() {
		return listePathologie;
	}

	/**
	 * @param listePathologie the listePathologie to set
	 */
	public void setListePathologie(List<PathologieView> listePathologie) {
		this.listePathologie = listePathologie;
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
}

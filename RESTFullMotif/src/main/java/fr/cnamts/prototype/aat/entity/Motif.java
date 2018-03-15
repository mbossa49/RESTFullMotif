package fr.cnamts.prototype.aat.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author ONDONGO-09929
 *
 */
public class Motif implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;
	
	private String libelle;
	
	private String codification;
	
	private List<String> synonymes;
	
	private byte[] dureeHTML;

	private byte[] dureeXML;
	
	private byte[] dureePDF;
	
	private byte[] dureeNewXML;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getCodification() {
		return codification;
	}

	public void setCodification(String codification) {
		this.codification = codification;
	}

	public List<String> getSynonymes() {
		return synonymes;
	}

	public void setSynonymes(List<String> synonymes) {
		this.synonymes = synonymes;
	}

	public byte[] getDureeHTML() {
		return dureeHTML;
	}

	public void setDureeHTML(byte[] dureeHTML) {
		this.dureeHTML = dureeHTML;
	}

	public byte[] getDureeXML() {
		return dureeXML;
	}

	public void setDureeXML(byte[] dureeXML) {
		this.dureeXML = dureeXML;
	}

	public byte[] getDureePDF() {
		return dureePDF;
	}

	public void setDureePDF(byte[] dureePDF) {
		this.dureePDF = dureePDF;
	}

	/**
	 * @return the dureeNewXML
	 */
	public byte[] getDureeNewXML() {
		return dureeNewXML;
	}

	/**
	 * @param dureeNewXML the dureeNewXML to set
	 */
	public void setDureeNewXML(byte[] dureeNewXML) {
		this.dureeNewXML = dureeNewXML;
	}
}

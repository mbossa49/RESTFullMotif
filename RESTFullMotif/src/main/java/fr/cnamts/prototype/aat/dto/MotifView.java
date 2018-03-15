package fr.cnamts.prototype.aat.dto;
/**
 * 
 * @author ONDONGO-09929
 *
 */
public class MotifView {
	
	/**
	 * La valeur affichable correspondant au libellé de motif.
	 */
	private String label;
	
	/**
	 * La valeur non affichable correspondant au code unique de motif.
	 */
	private String value;
	
	
	private String propDuree;
	
	private byte[] dureeHTML;

	private byte[] dureeXML;
	
	private byte[] dureePDF;
	
	/**
	 * Constructeur par défaut.
	 */
	public MotifView() {
		super();
	}
	
	/**
	 * 
	 * @param label
	 * @param value
	 */
	public MotifView(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public String getPropDuree() {
		return propDuree;
	}

	public void setPropDuree(String propDuree) {
		this.propDuree = propDuree;
	}

	/**
	 * @return the dureeHTML
	 */
	public byte[] getDureeHTML() {
		return dureeHTML;
	}

	/**
	 * @param dureeHTML the dureeHTML to set
	 */
	public void setDureeHTML(byte[] dureeHTML) {
		this.dureeHTML = dureeHTML;
	}

	/**
	 * @return the dureeXML
	 */
	public byte[] getDureeXML() {
		return dureeXML;
	}

	/**
	 * @param dureeXML the dureeXML to set
	 */
	public void setDureeXML(byte[] dureeXML) {
		this.dureeXML = dureeXML;
	}

	/**
	 * @return the dureePDF
	 */
	public byte[] getDureePDF() {
		return dureePDF;
	}

	/**
	 * @param dureePDF the dureePDF to set
	 */
	public void setDureePDF(byte[] dureePDF) {
		this.dureePDF = dureePDF;
	}
	
}

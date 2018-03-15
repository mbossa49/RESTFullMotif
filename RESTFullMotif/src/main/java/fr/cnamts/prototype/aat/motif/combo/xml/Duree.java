package fr.cnamts.prototype.aat.motif.combo.xml;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Duree {
	
	private String unite;
	
	private int valeur;
	
	/**
	 * @return the unite
	 */
	public String getUnite() {
		return unite;
	}
	/**
	 * @param unite the unite to set
	 */
	public void setUnite(String unite) {
		this.unite = unite;
	}
	/**
	 * @return the valeur
	 */
	public int getValeur() {
		return valeur;
	}
	/**
	 * @param valeur the valeur to set
	 */
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	
	
}

package fr.cnamts.prototype.aat.motif.combo.xml;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TypeEmploi {
	
	private String libEmploi;
	
	private List<String> complement;
	
	/**
	 * @return the libEmploi
	 */
	public String getLibEmploi() {
		return libEmploi;
	}
	/**
	 * @param libEmploi the libEmploi to set
	 */
	public void setLibEmploi(String libEmploi) {
		this.libEmploi = libEmploi;
	}
	/**
	 * @return the complement
	 */
	public List<String> getComplement() {
		return complement;
	}
	/**
	 * @param complement the complement to set
	 */
	public void setComplement(List<String> complement) {
		this.complement = complement;
	}
	
	
}

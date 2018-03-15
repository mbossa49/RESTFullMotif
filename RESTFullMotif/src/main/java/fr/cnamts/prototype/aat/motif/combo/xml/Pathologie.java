package fr.cnamts.prototype.aat.motif.combo.xml;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Pathologie {

	private String libPathologie;
	
	private Duree duree;

	/**
	 * @return the libPathologie
	 */
	public String getLibPathologie() {
		return libPathologie;
	}

	/**
	 * @param libPathologie the libPathologie to set
	 */
	public void setLibPathologie(String libPathologie) {
		this.libPathologie = libPathologie;
	}

	/**
	 * @return the duree
	 */
	public Duree getDuree() {
		return duree;
	}

	/**
	 * @param duree the duree to set
	 */
	public void setDuree(Duree duree) {
		this.duree = duree;
	}	
}

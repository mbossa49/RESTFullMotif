package fr.cnamts.prototype.aat.motif.combo.xml;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Motifs {
	
	List<Critere> critere;

	/**
	 * @return the critere
	 */
	public List<Critere> getCritere() {
		return critere;
	}

	/**
	 * @param critere the critere to set
	 */
	public void setCritere(List<Critere> critere) {
		this.critere = critere;
	}

	public boolean isMotifConforme(){
		
		Critere pivo = null;
		int i = 0;
		int taille = critere.size();
		
		if (taille > 1) {
			for (i = 0; i < taille - 1; i++) {

				pivo = critere.get(i);
				Critere autre = critere.get(i + 1);
				if (!pivo.isConformPatho(autre)) {
					return false;
				}
			}
		}
		return true;
	}
	
}

package fr.cnamts.prototype.aat.motif.combo.xml;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Critere {

	private List<TypeEmploi> typeEmploi;
	
	private List<Pathologie> pathologie;

	/**
	 * @return the typeEmploi
	 */
	public List<TypeEmploi> getTypeEmploi() {
		return typeEmploi;
	}

	/**
	 * @param typeEmploi the typeEmploi to set
	 */
	public void setTypeEmploi(List<TypeEmploi> typeEmploi) {
		this.typeEmploi = typeEmploi;
	}

	/**
	 * @return the pathologie
	 */
	public List<Pathologie> getPathologie() {
		return pathologie;
	}

	/**
	 * @param pathologie the pathologie to set
	 */
	public void setPathologie(List<Pathologie> pathologie) {
		this.pathologie = pathologie;
	}
	
	public boolean isConformPatho(Critere autre){
		List<Pathologie> pathologies = this.getPathologie();
		List<Pathologie> pathologiesAutre = autre.getPathologie();
		if(pathologies.size() != pathologiesAutre.size()){
			return false;
		}else{
			for(int i=0; i< pathologies.size(); i++){
				Pathologie patho = pathologies.get(i);
				Pathologie patho2 = pathologiesAutre.get(i);
				if(!patho.getLibPathologie().equals(patho2.getLibPathologie())){
					return false;
				}
			}
		}
		return true;
	}
}

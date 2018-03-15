package fr.cnamts.prototype.aat.motif.combo.xml;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import fr.cnamts.prototype.aat.motif.combo.view.ComplementView;
import fr.cnamts.prototype.aat.motif.combo.view.PathologieView;
import fr.cnamts.prototype.aat.motif.combo.view.TypeEmploiView;

public class NewXMLUtil {

	public static void main(String[] args) {
		Map<String, List<TypeEmploiView>> map = new HashMap<String, List<TypeEmploiView>>();
		chargerNewXMLInMap(map);
		System.out.println();
	}
			
	/**
	 * 
	 * @param critere
	 * @return
	 */
	private List<PathologieView> getListPathologieViews2(Critere critere){
		
		List<PathologieView> liste = new ArrayList<PathologieView>();
		List<Pathologie> pathologies = critere.getPathologie();
		List<TypeEmploi> typeEmploi = critere.getTypeEmploi();
		int size = typeEmploi.size();
		
		if (size > 1) {
			System.out.println("################ Il y'a plus d'un type Emploi dans ce critère ########################### ");
		} else {
			
			for (Pathologie pathologie : pathologies) {
				PathologieView patoV = new PathologieView();
				patoV.setLibelle(pathologie.getLibPathologie());
				patoV.setDuree(pathologie.getDuree().getValeur());				
				liste.add(patoV);
			}

		}		
		return liste;
	}
	
	
	private void remplir2(List<TypeEmploiView> listTEmploiViews, Motifs motifs){
		
		List<Critere> criteres = motifs.getCritere();
		for (Critere critere : criteres) {
			String codeTypeEmploi = getCodeTypeEmploi(critere);
			TypeEmploiView existTypeEmploi = existTypeEmploi(listTEmploiViews, codeTypeEmploi);
			if (null != existTypeEmploi) {
				modifierTypeEmploi2(existTypeEmploi, critere);
			} else {
				ajouterTypeEmploi2(listTEmploiViews, critere, codeTypeEmploi);
			}
		}
	}
	
	
	/**
	 * Ajouter un nouveau type dans la liste.
	 * @param listTEmploiViews
	 * @param critere
	 */
	private void ajouterTypeEmploi2(List<TypeEmploiView> listTEmploiViews,
			Critere critere, String codeTypeEmploi) {
		
		TypeEmploiView tev = new TypeEmploiView();
		
		tev.setCode(codeTypeEmploi);
		int codeTypeEmploiInt = Integer.parseInt(codeTypeEmploi);
		switch (codeTypeEmploiInt) {
			case 1:
				tev.setLibelle("Travail sédentaire");
				break;
			case 2:
				tev.setLibelle("Travail physique léger");
				break;
			case 3:
				tev.setLibelle("Travail physique modéré");
				break;
			case 4:
				tev.setLibelle("Travail physique lourd");
				break;
			default:
				tev.setLibelle("Tout type d’emploi");
				break;
		}
		
		tev.setListePathologie(getListPathologieViews2(critere));
		List<TypeEmploi> typeEmploi = critere.getTypeEmploi();
		List<String> complement = typeEmploi.get(0).getComplement();
		
		if(complement != null && complement.size() > 0){
			
			if (complement.get(0).length() > 0) {
				ComplementView complView = new ComplementView();
				complView.setLibelle(complement.get(0));
				complView.setPathologies(getListPathologieViews2(critere));
				tev.setListeComplementViews(new ArrayList<ComplementView>());
				tev.getListeComplementViews().add(complView);
			}
		}
		listTEmploiViews.add(tev);
	}
	


	
	/**
	 * Modification d'un type emploi existant - Le cas où c'est le complément qui determine la durée de l'arrêt.
	 * 
	 * 
	 * @param existTypeEmploi
	 * @param critere
	 */
	private void modifierTypeEmploi2(TypeEmploiView existTypeEmploi,
			Critere critere) {
		System.out.println("Cas de modification d'un typeEmploiView : ");
		List<String> complements = critere.getTypeEmploi().get(0).getComplement();
		List<PathologieView> listePathologieView = getListPathologieViews2(critere);
		
//		for (int i = 0; i < pathologies.size(); i++) {
//			PathologieView pathologieView = listePathologieView.get(i);
			//TODO remove duration from pathologie ????
			ComplementView complView = new ComplementView();
			complView.setLibelle(complements.get(0));
			complView.setPathologies(listePathologieView);
			existTypeEmploi.getListeComplementViews().add(complView);
//		}
	}
	
	
	/**
	 * Retourne le {@link TypeEmploiView} existant en fonction de codeTypeEmploi en cours de traitement.
	 * @param listTEmploiViews
	 * @param codeTypeEmploi
	 * @return
	 */
	private TypeEmploiView existTypeEmploi(List<TypeEmploiView> listTEmploiViews,
			String codeTypeEmploi) {
		TypeEmploiView tev = null;
		for (TypeEmploiView typeEmploiView : listTEmploiViews) {
			if (typeEmploiView.getCode().equalsIgnoreCase(codeTypeEmploi)) {
				tev = typeEmploiView;
				break;
			}
		}
		return tev;
	}


	private String getCodeTypeEmploi(Critere critere) {
		String libEmploi = critere.getTypeEmploi().get(0).getLibEmploi();
		String codeType = "";
		
		if ("Travail sédentaire".equalsIgnoreCase(libEmploi)) {
			codeType = "1";
		} else if ("Travail physique léger".equalsIgnoreCase(libEmploi)) {
			codeType = "2";

		}else if ("Travail physique modéré".equalsIgnoreCase(libEmploi)) {
			codeType = "3";

		}else if ("Travail physique lourd".equalsIgnoreCase(libEmploi)) {
			
			codeType = "4";
		}else {
			codeType = "5";
		}
		
		return codeType;
	}

	


	
	
	public static void chargerNewXMLInMap(Map<String, List<TypeEmploiView>>  cacheMap) {
		
		File directory = new File("D:\\new_xml");
		File[] listFiles = directory.listFiles();
		for (int i = 0; i < listFiles.length; i++) {
			File file = listFiles[i];
			System.out.println("************* Traitement du fichier[" + file.getName() +"] *************");
			try {

					JAXBContext jaxbContext = JAXBContext
							.newInstance(Motifs.class);
					Unmarshaller jaxbUnmarshaller = jaxbContext
							.createUnmarshaller();
					Motifs motif = (Motifs) jaxbUnmarshaller.unmarshal(file);
					List<TypeEmploiView> listTEmploiViews = new ArrayList<TypeEmploiView>();
					new NewXMLUtil().remplir2(listTEmploiViews , motif);
					//TODO la cle égale au code unique
					cacheMap.put(file.getName().replace("Motif-", "").replace(".xml", ""), listTEmploiViews);
					if (!motif.isMotifConforme()) {
						System.out.println("Le fichier :[" + file.getName()
								+ "] contient une erreur");
					}

			} catch (JAXBException e) {
				System.out.println("Le fichier :[" + file.getName() +"] contient une erreur");
				e.printStackTrace();
			}
			System.out.println();
		}
		System.out.println("checkConform  FIN");
	}
	
	public static List<TypeEmploiView> getListeTypeEmploi(final File newXMLFile){
		List<TypeEmploiView> listTEmploiViews = null;
		try {

			JAXBContext jaxbContext = JAXBContext
					.newInstance(Motifs.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext
					.createUnmarshaller();
			Motifs motif = (Motifs) jaxbUnmarshaller.unmarshal(newXMLFile);
			listTEmploiViews = new ArrayList<TypeEmploiView>();
			new NewXMLUtil().remplir2(listTEmploiViews , motif);
			//TODO la cle égale au code unique
			//cacheMap.put(file.getName().replace("Motif-", "").replace(".xml", ""), listTEmploiViews);
			if (!motif.isMotifConforme()) {
				System.out.println("Le fichier :[" + newXMLFile.getName()
						+ "] contient une erreur");
			}

		} catch (JAXBException e) {
			System.out.println("Le fichier :[" + newXMLFile.getName() +"] contient une erreur");
		}
		return listTEmploiViews;
	}
}

package fr.cnamts.prototype.aat.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import fr.cnamts.prototype.aat.dto.MotifView;
import fr.cnamts.prototype.aat.entity.Motif;

/**
 * 
 * @author ONDONGO-09929
 *
 */
public final class MotifMapper {

	/**
	 * Constructeur privé.
	 */
	private MotifMapper(){
		
	}
	
	/**
	 * 
	 * @param motif
	 * @return
	 */
	public static MotifView convertMotifToMotifView(final Motif motif){
		MotifView motifView = new MotifView();
		if(null != motif){
			motifView.setLabel(motif.getCode());
			motifView.setValue(motif.getLibelle());
			motifView.setDureeHTML(motif.getDureeHTML());
			motifView.setDureePDF(motif.getDureePDF());
			motifView.setDureeXML(motif.getDureeXML());
			
		}
		return motifView;
	}
	/**
	 * 
	 * @param listMotifView
	 * @return
	 */
	public static SortedMap<String, List<MotifView>> listMotifViewToSortedMap(final List<MotifView> listMotifView){
		List<String> alphabet = Arrays
				.asList("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z");
		SortedMap<String, List<MotifView>> myTriMap = new TreeMap<String, List<MotifView>>();
		
		for (String string : alphabet) {
			//je cree une liste
			List<MotifView> list = new ArrayList<MotifView>();
			for(int i= 0; i < listMotifView.size(); i++){
				MotifView motifView = listMotifView.get(i);
				String libMotif = motifView.getValue().toLowerCase();
				if (libMotif.startsWith(string.toLowerCase())) {
					list.add(motifView);
				}
			}
			myTriMap.put(string, list);
		}
		return myTriMap;
	}
	
	/**
	 * 
	 * @param listMotif
	 * @return
	 */
	public static SortedMap<String, List<MotifView>> listMotifToSortedMap(final List<Motif> listMotif){
		List<String> alphabet = Arrays
				.asList("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z");
		SortedMap<String, List<MotifView>> myTriMap = new TreeMap<String, List<MotifView>>();
		
		for (String string : alphabet) {
			//je cree une liste
			List<MotifView> list = new ArrayList<MotifView>();
			for(int i= 0; i < listMotif.size(); i++){
				MotifView motifView = convertMotifToMotifView(listMotif.get(i));
				String libMotif = motifView.getValue().toLowerCase();
				if (libMotif.startsWith(string.toLowerCase())) {
					list.add(motifView);
				}
			}
			myTriMap.put(string, list);
		}
		return myTriMap;
	}
	/**
	 * 
	 * @param motifView
	 * @return
	 */
	public static Motif convertMotifViewToMotif(final MotifView motifView) {
		Motif motif = new Motif();
		if (null != motifView) {
			motif.setLibelle(motifView.getValue());
			motif.setCode(motifView.getLabel());
		}
		return motif;
	}
}

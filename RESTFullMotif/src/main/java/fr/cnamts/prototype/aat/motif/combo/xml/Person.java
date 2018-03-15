package fr.cnamts.prototype.aat.motif.combo.xml;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {
 
	 private String nom;
	 private int age;
	 private List<String> prenom;
	 
	 public Person() {
	 }

	public String getNom() {
		return nom;
	}
	
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public List<String> getPrenom() {
		return prenom;
	}
	
	public void setPrenom(List<String> prenom) {
		this.prenom = prenom;
	}
 
}

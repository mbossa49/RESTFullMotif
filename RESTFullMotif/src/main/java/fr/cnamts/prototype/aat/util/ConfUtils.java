package fr.cnamts.prototype.aat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * 
 * Classe utilitaire pour la lecture de la conf.
 * @author ONDONGO-09929
 *
 */
public class ConfUtils {

	
	private static final String MONGODB_FILE ="mongodb.";
	private static final String ELS_FILE ="elasticsearch.";
	private static final String COMMON_FILE ="commun.";
	
	
	public static String getPropertie(final String propName){
		Properties prop  = new Properties();
		InputStream inputStream = null;
		String propValue = "";
		
		// Determiner le fichier properties à lire.
		String configFile = null;
		if(propName.contains(MONGODB_FILE)){
			configFile = "mongodb.properties";
		}else if (propName.contains(ELS_FILE)) {
			configFile = "elasticsearch.properties";
		}else if (propName.contains(COMMON_FILE)) {
			configFile = "commun.properties";
		}else {
			System.out.println("ERROR : le property " + propName + "n'existe pas dans les fichiers de configuration");
			return propValue;
		}
		
		inputStream = ConfUtils.class.getClassLoader().getResourceAsStream(configFile);
		if(inputStream == null){
			System.out.println("ERROR : le fichier " + configFile + " n'existe pas dans le classpath");
			return propValue;
		}
		
		try {
			prop.load(inputStream);
			propValue =  prop.getProperty(propName);
		} catch (IOException e) {

			e.printStackTrace();
		}finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return propValue;
	}
}

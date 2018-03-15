package fr.cnamts.prototype.aat.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

	/**
	 * Convertir un fichier en tableau de byte
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] fileToByteArray(File file) {

		FileInputStream fileInputStream = null;
		byte[] bFile = new byte[(int) file.length()];

		try {
			// convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bFile;
	}
	
	public static String FileToString(File file){
		
		String sCurrentLine;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
			}
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {

				}
			}
		}

		return sb.toString();
	}
}

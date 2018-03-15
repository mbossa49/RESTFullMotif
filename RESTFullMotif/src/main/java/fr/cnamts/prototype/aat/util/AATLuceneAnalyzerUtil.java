package fr.cnamts.prototype.aat.util;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author ONDONGO-09929
 *
 */
public final class AATLuceneAnalyzerUtil {

	/**
	 * Liste des termes ignores à l'indexation et pour la recherche dans lucene.
	 * Elle permet d'etendre la liste de base FrenchAnalyzer.getDefaultStopSet()
	 */
	public static final String[] STOP_WORD = new String[] { "chez", "-", "droite", "droit", "gauche", "drt", "drte",
			"gche", "gches", "gch", "gauches", "droites", "dte", "bilatérale", "bilater", "bilat" };

	/**
	 * Constructeur privÃ©.
	 */
	private AATLuceneAnalyzerUtil() {
		super();
	}

	public static Directory getDirectory() throws IOException{		
		FSDirectory fileDirectory = FSDirectory.open(new File("/tmp/testIndex"));
		return fileDirectory;
	}
	/**
	 *
	 * @return {@link Analyzer}
	 * @throws IOException
	 */
	public static Analyzer getAnalyzer() {

		Analyzer customAnalyzer = new Analyzer() {

			@Override
			protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
				Tokenizer aatTokenizer = new WhitespaceTokenizer(reader);
				TokenStream filter = new StandardFilter(aatTokenizer);

				CharArraySet defaultStopSet = FrenchAnalyzer.getDefaultStopSet();
				defaultStopSet.addAll(Arrays.asList(STOP_WORD));
//				filter = new StopFilter(filter, etendreFrenchStopWordSet2());
				filter = new LowerCaseFilter(filter);
				filter = new ASCIIFoldingFilter(filter, true);
				try {
//					filter.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return new TokenStreamComponents(aatTokenizer);
			}
		};
		return customAnalyzer;
	}

	/**
	 *
	 * @return {@link Analyzer}
	 * @throws IOException
	 */
	public static Analyzer getSynonymeAnalyzer() {

		Analyzer customAnalyzer = new Analyzer() {
			
			@Override
			protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
				Tokenizer aatTokenizer = new StandardTokenizer(reader);
				TokenStream filter = new StandardFilter(aatTokenizer);
//				filter = new StopFilter(filter, etendreFrenchStopWordSet2());
				filter = new LowerCaseFilter(filter);
				filter = new ASCIIFoldingFilter(filter, true);
				try {
//					filter.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return new TokenStreamComponents(aatTokenizer);
			}
		};
		return customAnalyzer;
	}

//	/**
//	 * Cette methode permet d'etendre la liste, par defaut, des mots français,
//	 * insignifiants pour etre indexés. Ex : le, la une etc..
//	 * 
//	 * @return la liste résultante.
//	 */
//	public static Set<String> etendreFrenchStopWordSet() {
//
//		final Set<?> frenchStopWords = FrenchAnalyzer.getDefaultStopSet();
//		final Set<String> stopWordEtendu = new HashSet<String>();
//		for (Object string : frenchStopWords) {
//			char[] mot = (char[]) string;
//			final String string2 = new String(mot);
//			stopWordEtendu.add(string2);
//
//		}
//
//		// Extension de la liste par defaut.
//		stopWordEtendu.addAll(Arrays.asList(STOP_WORD));
//
//		// Suppression des mots une lettre.
//		final Set<String> stopWordEtendu2 = new HashSet<String>();
//		for (String mot : stopWordEtendu) {
//			if (mot.length() > 1) {
//				stopWordEtendu2.add(mot);
//			}
//		}
//		return stopWordEtendu2;
//	}

	/**
	 * Cette methode permet d'etendre la liste, par defaut, des mots français,
	 * insignifiants pour etre indexés. Ex : le, la une etc..
	 * 
	 * @return la liste résultante.
	 */
	public static CharArraySet etendreFrenchStopWordSet() {

		final CharArraySet frenchStopWords = FrenchAnalyzer.getDefaultStopSet();
		// Extension de la liste par defaut.
		frenchStopWords.addAll(Arrays.asList(STOP_WORD));
		final CharArraySet stopWordCustom = new CharArraySet(0, true);

		// Suppression des mots une lettre.
		for (Object object : frenchStopWords) {
			char[] mot = (char[]) object;
			if (mot.length > 1) {
				stopWordCustom.add(mot);
			}
		}
		return stopWordCustom;
	}

	public static void main(String[] args) {
		final CharArraySet frenchStopWords = FrenchAnalyzer.getDefaultStopSet();
		// Extension de la liste par defaut.
		frenchStopWords.addAll(Arrays.asList(STOP_WORD));
		final CharArraySet stopWordCustom = new CharArraySet(0, true);

		for (Object object : frenchStopWords) {
			char[] mot = (char[]) object;
			if (mot.length > 1) {
				stopWordCustom.add(mot);
				String motStr = new String(mot);
				System.out.println(motStr);
			}
		}
		System.out.println("AATLuceneAnalyzerUtil.main() " + stopWordCustom.size());
		
	}
	
}

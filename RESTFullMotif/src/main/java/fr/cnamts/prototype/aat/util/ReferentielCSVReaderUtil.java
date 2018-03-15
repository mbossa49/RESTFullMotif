package fr.cnamts.prototype.aat.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.cnamts.prototype.aat.entity.Motif;


/**
 * Classe utilitaire qui permet de lire le référentiel(CSV) de motif AAT.
 *
 * @author ONDONGO-09929
 *
 */
public final class ReferentielCSVReaderUtil {

    /**
     * Espace.
     */
    public static final String ESPACE = " ";

    /**
     * Chaine vide.
     */
    public static final String VIDE = "";

    /**
     * Separateur des donnÃ©es.
     */
    public static final String CSV_SERAPTOR = ";";
    /**
     * SÃ©parateur des synonymes.
     */
    public static final String SYNONYME_SERAPTOR = ",";
    /**
     * Position de la valeur de l'attribut code.
     */
    public static final int CSV_CODE_INDEX = 0;
    /**
     * Position de la valeur de l'attribut libelle.
     */
    public static final int CSV_LIBELLE_INDEX = 1;
    /**
     * Position de la valeur de l'attribut codification.
     */
    public static final int CSV_CODIFICATION_INDEX = 2;
    /**
     * Position de la valeur de l'attribut synonymes.
     */
    public static final int CSV_SYNONYME_INDEX = 3;

    /**
     * Constructeur.
     */
    private ReferentielCSVReaderUtil() {
        super();
    }

    /**
     * Méthode qui permet de lire une ligne spécifique du fichier CSV.
     * @param pLigne : la ligne en cours de lecture
     * @return : une instance de {@link Motif}
     * qui encapsule les infos de la ligne
     */
    public static Motif lireLigne(final String pLigne) {
        final String[] fields = pLigne.split(CSV_SERAPTOR);
        Motif motif = new Motif();
        
        if (fields.length >= CSV_SYNONYME_INDEX) {
        	
            motif.setCode(fields[CSV_CODE_INDEX]);
            motif.setLibelle(fields[CSV_LIBELLE_INDEX]);
            motif.setCodification(fields[CSV_CODIFICATION_INDEX]);

            String syn = "";
            if (fields.length > CSV_SYNONYME_INDEX) {
                String synonymesString = fields[CSV_SYNONYME_INDEX];
                String[] tableDeSynonyme = synonymesString
                .split(SYNONYME_SERAPTOR);
                for (String synonyme : tableDeSynonyme) {
                    syn = syn + ESPACE + synonyme;
                }
                motif.setSynonymes(Arrays.asList(tableDeSynonyme));
            }
        }
        return motif;
    }

    /**
     * Méthode qui permet de lire toute
     * les lignes du fichier (référentiel) CSV.
     * @param pReferentielCSV : le fichier référentiel.
     * @return : liste d'objet de la classe {@link Motif}.
     * @throws IOException : Notification d'une erreur technique
     * lors de la lecture du fichier.
     */
    public static List < Motif > lireFichier(final File pReferentielCSV)
    throws IOException {

        List < Motif > liste = new ArrayList < Motif >();

        Motif motifEncours = null;
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(pReferentielCSV));

        while ((line = br.readLine()) != null) {

            String[] fields = line.split(CSV_SERAPTOR);
            motifEncours = new Motif();

            if (fields.length >= CSV_SYNONYME_INDEX) {

                motifEncours.setCode(fields[CSV_CODE_INDEX]);
                motifEncours.setLibelle(fields[CSV_LIBELLE_INDEX]);
                motifEncours.setCodification(fields[CSV_CODIFICATION_INDEX]);

                if (fields.length > CSV_SYNONYME_INDEX) {
                    String synonymesString = fields[CSV_SYNONYME_INDEX];
                    String[] tableDeSynonyme = synonymesString
                        .split(SYNONYME_SERAPTOR);
//                    String syn = motifEncours.getSynonymes();
//
//                    if (null == syn) {
//                        syn = "";
//                    }
//                    for (String synonyme : tableDeSynonyme) {
//                        syn = syn + ESPACE + synonyme;
//                    }
//                    motifEncours.setSynonymes(syn);
                    motifEncours.setSynonymes(Arrays.asList(tableDeSynonyme));
                }
            }
            liste.add(motifEncours);
        }
        br.close();
        return liste;
    }
    
    
}

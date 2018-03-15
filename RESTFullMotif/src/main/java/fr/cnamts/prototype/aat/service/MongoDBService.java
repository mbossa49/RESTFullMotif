package fr.cnamts.prototype.aat.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;

import fr.cnamts.prototype.aat.dto.MotifView;
import fr.cnamts.prototype.aat.entity.Motif;


public interface MongoDBService {

	  /**
     * Chargement complet du référentiel ( Un batch est prévu pour ça )
     */
    public void chargerReferentielAAT() throws Exception;
    /**
     * 
     * @param htmlFile
     * @param motifCode
     * @return
     * @throws IOException
     */
    public ObjectId insertHTMLfiche(final File htmlFile ,final String motifCode) throws IOException;
    
    /**
     * 
     * @param pPDFFile
     * @param pMotifCode
     * @return
     * @throws IOException
     */
    public ObjectId insertPDFfiche(final File pPDFFile, final String pMotifCode) throws IOException;
    
    /**
     * 
     * @param pXmlFile
     * @param pMotifCode
     * @return
     * @throws IOException
     */
    public ObjectId insertXMLFiche(final File pXmlFile, final String pMotifCode) throws IOException;
    
    /**
     * Insertion du nouveau modèle xml dans la base de données.
     * @param pNewXmlFile : le fichier 
     * @param pMotifCode : le code unique du motif AAT
     * @return
     * @throws IOException
     */
    public ObjectId insertNewXMLFiche(final File pNewXmlFile, final String pMotifCode) throws IOException;
    
    /**
     * 
     * @param pCollectionName
     * @param pCode
     * @param pMotifLibelle
     * @param pCodification
     * @param synonymes
     * @param pPdfFile
     * @param phtmlFile
     * @param pXmlFile TODO
     * @throws IOException
     */
    public void insertMotif(final String pCollectionName, 
            final String pCode,
            final String pMotifLibelle, 
            final String pCodification,
            final List<String> synonymes,
            final File pPdfFile, 
            final File phtmlFile, 
            final File pXmlFile) throws IOException;
    
    /**
     * 
     * @param pCollectionName
     * @param motifDTO
     * @throws IOException
     */
    public void insertMotif(final String pCollectionName, 
            final Motif pMotif,
            final File pPDFFile, 
            final File pHTMLFile, 
            final File pXMLFile,
            final File pNewXmlFile) throws IOException;
    
    /**
     * 
     * @param codeMotif
     * @return
     */
    public Motif findMotifByCode(final String pCodeMotif);
    
    /**
     * 
     * @return
     */
    public List<Motif> findMotifs();
    
    /**
     * 
     * @param motifPDF_ID
     * @return TODO
     * @throws IOException
     */
    public byte[] downloadMotifPDF(ObjectId motifPDF_ID) throws IOException;
    
    /**
     * 
     * @param motifHTML_ID
     * @return TODO
     * @throws IOException
     */
    public byte[] downloadMotifHTML(ObjectId motifHTML_ID) throws IOException;
    
    /**
     * 
     * @param motifXML_ID
     * @return TODO
     * @throws IOException
     */
    public byte[] downloadMotifXML(ObjectId motifXML_ID) throws IOException;
    
    /**
     * Lecture du ficheir XML (nouveau modèle)
     * @param motifNewXML_ID
     * @return
     * @throws IOException
     */
    public byte[] downloadMotifNewXML(ObjectId motifNewXML_ID) throws IOException;
    
    /**
     * Retourne la liste de motif triée par ordre alphabetique du libellé.
     * @return
     */
    public List<Motif> getListeTrieeMotif();
    
    /**
     * Retourne la liste de motif triée par ordre alphabetique du libellé.
     * @return
     */
    public List<MotifView> getListeTrieeMotifView();
}

package fr.cnamts.prototype.aat.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.model.Filters;
import com.sun.jersey.core.util.Base64;

import fr.cnamts.prototype.aat.dto.MotifView;
import fr.cnamts.prototype.aat.entity.Motif;
import fr.cnamts.prototype.aat.service.MongoDBService;
import fr.cnamts.prototype.aat.util.ConfUtils;
import fr.cnamts.prototype.aat.util.Constante;
import fr.cnamts.prototype.aat.util.FileUtils;
import fr.cnamts.prototype.aat.util.MotifMapper;

public class MongoDBServiceImpl implements MongoDBService {

	final Logger logger = Logger.getLogger(this.getClass());
    static MongoClient mongoClient = new MongoClient(ConfUtils.getPropertie("mongodb.host"));
     
    static MongoDatabase db = mongoClient.getDatabase(ConfUtils.getPropertie("mongodb.base"));
    static final String FICH_MOTIF = "FichierReferentielMotifsAAT.csv";

    static final String DB_MOTIF_COLLECTION = "mongodb.motif.collection";
    static final String DB_PDF_COLLECTION = "mongodb.pdf.collection";
    static final String DB_HTML_COLLECTION = "mongodb.html.collection";
    static final String DB_XML_COLLECTION = "mongodb.xml.collection";
    static final String DB_NEW_XML_COLLECTION = "mongodb.new.xml.collection";
    
	public void chargerReferentielAAT() throws Exception {
		String csvFile =  ConfUtils.getPropertie("mongodb.motif.directory") + FICH_MOTIF;
        BufferedReader br = null;
        String line =  "";
        try {
            
            br = new BufferedReader(new FileReader(csvFile));
            
            //Drop de la base
            db.drop();
            //db.getCollection(ConfUtils.getPropertie(DB_MOTIF_COLLECTION)).drop();
            
            while ((line = br.readLine()) != null) {
                
                String[] fields = line.split(Constante.CSV_SERAPTOR);
                Motif motifEncours = new Motif();
              
                if(fields.length >= Constante.CSV_SYNONYME_INDEX){
                    
                    motifEncours.setCode(fields[Constante.CSV_CODE_INDEX]);
                    motifEncours.setLibelle(fields[Constante.CSV_LIBELLE_INDEX]);
                    motifEncours.setCodification(fields[Constante.CSV_CODIFICATION_INDEX]);
                    
                    if(fields.length > Constante.CSV_SYNONYME_INDEX){
                        String synonymesString = fields[Constante.CSV_SYNONYME_INDEX];
                        String[] tableDeSynonyme = synonymesString.split(Constante.SYNONYME_SERAPTOR);
                        motifEncours.setSynonymes(new ArrayList<String>());
                        for (String synonyme : tableDeSynonyme) {
                            motifEncours.getSynonymes().add(synonyme);
                        }
                    }
                    //logger.info(gson.toJson(motifEncours));
                    
                    File pXMLFile = new File(ConfUtils.getPropertie("mongodb.xml.directory") + String.format(Constante.FICH_XML_NAME_PATTERN, motifEncours.getCode()));
                    File pNewXMLFile = new File(ConfUtils.getPropertie("mongodb.new.xml.directory") + String.format(Constante.FICH_XML_NAME_PATTERN, motifEncours.getCode()));
                    File pPDFFile = new File(ConfUtils.getPropertie("mongodb.pdf.directory") + String.format(Constante.FICH_PDF_NAME_PATTERN, motifEncours.getCode()));
                    File pHMTLFile = new File(ConfUtils.getPropertie("mongodb.html.directory") + String.format(Constante.FICH_HTML_NAME_PATTERN, motifEncours.getCode()));
                    
                    
                    if(!pHMTLFile.exists()){
                        logger.info("Le fichier " + ConfUtils.getPropertie("mongodb.html.directory") + String.format(Constante.FICH_HTML_NAME_PATTERN, motifEncours.getCode()) + " n'existe pas");
                    }
                    
                    if(!pPDFFile.exists()){
                        logger.info("Le fichier " + ConfUtils.getPropertie("mongodb.pdf.directory") + String.format(Constante.FICH_PDF_NAME_PATTERN, motifEncours.getCode()) + " n'existe pas");
                    }
                    if(!pXMLFile.exists()){
                        logger.info("Le fichier " + ConfUtils.getPropertie("mongodb.xml.directory") + String.format(Constante.FICH_XML_NAME_PATTERN, motifEncours.getCode()) + " n'existe pas");
                    }
                    if(!pNewXMLFile.exists()){
                        logger.info("Le fichier nouveau modèle" + ConfUtils.getPropertie("mongodb.new.xml.directory") + String.format(Constante.FICH_XML_NAME_PATTERN, motifEncours.getCode()) + " n'existe pas");
                        pNewXMLFile = null;
                    }
                    insertMotif(ConfUtils.getPropertie(DB_MOTIF_COLLECTION), motifEncours, pPDFFile, pHMTLFile, pXMLFile, pNewXMLFile);

                }   
            }
        } catch (FileNotFoundException e) {
        	logger.error(e);
        	// TODO créer une classe specifique.
        	throw new Exception("Exception lors du chargement du référenciel", e);
        } catch (IOException e) {
        	logger.error(e);
        	// TODO créer une classe specifique.
        	throw new Exception("Exception lors du chargement du référenciel", e);
        } finally {
            if( br != null){
                try {
                    br.close();
                } catch (IOException e) {
                	logger.error(e);
                }
            }
        }

	}

	/**
	 * {@inheritDoc}
	 */
	public ObjectId insertHTMLfiche(File pHtmlFile, String pMotifCode) throws IOException {
		if (null != pHtmlFile && pHtmlFile.exists()) {
            GridFSBucket gridFsBucket = GridFSBuckets.create(db, ConfUtils.getPropertie(DB_HTML_COLLECTION));
            // Get the input stream
            InputStream streamToUploadFrom = new FileInputStream(pHtmlFile);
            // Create some custom options
            GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(1024).metadata(
                    new Document("type", "presentation"));
            ObjectId fileId = gridFsBucket.uploadFromStream(pMotifCode, streamToUploadFrom, options);
            logger.info(fileId);
            return fileId;
        }else{
            return null;
        }
	}

	
	/**
	 * {@inheritDoc}
	 */
	public ObjectId insertPDFfiche(File pPDFFile, String pMotifCode) throws IOException {
		if (null != pPDFFile && pPDFFile.exists()) {
            GridFSBucket gridFsBucket = GridFSBuckets.create(db, ConfUtils.getPropertie(DB_PDF_COLLECTION));
            // Get the input stream
            InputStream streamToUploadFrom = new FileInputStream(pPDFFile);
            // Create some custom options
            GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(1024).metadata(
                    new Document("type", "presentation"));
            ObjectId fileId = gridFsBucket.uploadFromStream(pMotifCode, streamToUploadFrom, options);
            logger.info(fileId);
            return fileId;
        }else {
            return null;
        }
	}

	/**
	 * {@inheritDoc}
	 */
	public ObjectId insertXMLFiche(File pXmlFile, String pMotifCode) throws IOException {
		if (null != pXmlFile && pXmlFile.exists() ) {
            GridFSBucket gridFsBucket = GridFSBuckets.create(db, ConfUtils.getPropertie(DB_XML_COLLECTION));
            // Get the input stream
            InputStream streamToUploadFrom = new FileInputStream(pXmlFile);
            // Create some custom options
            GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(1024).metadata(
                    new Document("type", "presentation"));
            ObjectId fileId = gridFsBucket.uploadFromStream(pMotifCode, streamToUploadFrom, options);
            logger.info(fileId);
            return fileId;
        }else {
            return null;
        }
	}

	/**
	 * {@inheritDoc}
	 */
	public ObjectId insertNewXMLFiche(File pNewXmlFile, String pMotifCode) throws IOException {
		
		if (null != pNewXmlFile && pNewXmlFile.exists() ) {
            GridFSBucket gridFsBucket = GridFSBuckets.create(db, ConfUtils.getPropertie(DB_NEW_XML_COLLECTION));
            // Get the input stream
            InputStream streamToUploadFrom = new FileInputStream(pNewXmlFile);
            // Create some custom options
            GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(1024).metadata(
                    new Document("type", "presentation"));
            ObjectId fileId = gridFsBucket.uploadFromStream(pMotifCode, streamToUploadFrom, options);
            logger.info(fileId);
            return fileId;
        }else {
            return null;
        }
	}

	/**
	 * {@inheritDoc}
	 */
	public void insertMotif(String pCollectionName, String pCode, String pMotifLibelle, String pCodification,
			List<String> synonymes, File pPdfFile, File phtmlFile, File pXmlFile) throws IOException {
        db.getCollection(pCollectionName).insertOne(
                new Document()
                .append("code", pCode)
                .append("libelle", pMotifLibelle)
                .append("codification", pCodification)
                .append("synonymes", synonymes)
                .append("pdf", insertPDFfiche(pPdfFile , pCode))
                .append("html", insertHTMLfiche(phtmlFile, pCode))
        ); 

	}

	/**
	 * 
	 * @param doc
	 * @param motif
	 * @return
	 * @throws IOException 
	 */
	private boolean isUpDateDocument(Document doc, Motif motif) throws IOException{
		boolean reponse = false;
		String code = doc.getString("code");
		String libelle = doc.getString("libelle");
		String codification = doc.getString("codification");
		@SuppressWarnings("unchecked")
		List<String> synonymes = (List<String>) doc.get("synonymes");
		
		ObjectId pdfId = doc.getObjectId("pdf");
		ObjectId htmlId = doc.getObjectId("html");
		ObjectId xmlId = doc.getObjectId("xml");
		byte[] dureePDF = null;
		byte[] dureeHTML = null;
		byte[] dureeXML = null;
		
		reponse = code.equals(motif.getCode()) && libelle.equals(motif.getLibelle()) 
				&& codification.equals(motif.getCodification()) 
				&& (null != synonymes && synonymes.equals(motif.getSynonymes())
				);
		if (reponse) {
			if (null != pdfId) {
				dureePDF = downloadMotifPDF(pdfId);
			}
			if (null != htmlId) {
				dureeHTML = downloadMotifHTML(htmlId);
			}
			if (null != pdfId) {
				dureeXML = downloadMotifXML(xmlId);
			} 
			reponse = reponse 
					&& Arrays.equals(dureeHTML, motif.getDureeHTML())
					&& Arrays.equals(dureePDF, motif.getDureePDF())
					&& Arrays.equals(dureeXML, motif.getDureeXML());
		}
		return !reponse;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void insertMotif(String pCollectionName, Motif pMotif, File pPDFFile, File pHTMLFile, File pXMLFile, File pNewXmlFile)
			throws IOException {
        if (null != pMotif){
//        	FindIterable<Document> find = db.getCollection(pCollectionName).find(Filters.eq("code", pMotif.getCode()));
//        	FindIterable<Document> find = db.getCollection(pCollectionName).find();
//            Document first = find.first();
            
            db.getCollection(pCollectionName).insertOne(
	                new Document()
	                .append("_id", new ObjectId(Base64.encode(pMotif.getCode())))
	                .append("code", pMotif.getCode())
	                .append("libelle", pMotif.getLibelle())
	                .append("codification", pMotif.getCodification())
	                .append("synonymes", pMotif.getSynonymes())
	                .append("pdf", insertPDFfiche(pPDFFile , pMotif.getCode()))
	                .append("html", insertHTMLfiche(pHTMLFile, pMotif.getCode()))
	                .append("xml", insertXMLFiche(pXMLFile, pMotif.getCode()))
	                .append("newxml", insertNewXMLFiche(pNewXmlFile, pMotif.getCode())));

//            if (null != first) {
//            	
//				if (isUpDateDocument(first, pMotif)) {
//					db.getCollection(pCollectionName).updateOne(new Document("code", pMotif.getCode()), new Document(
//							"$set",
//							new Document().append("code", pMotif.getCode()).append("libelle", pMotif.getLibelle())
//									.append("codification", pMotif.getCodification())
//									.append("synonymes", pMotif.getSynonymes())
//									.append("pdf", insertPDFfiche(pPDFFile, pMotif.getCode()))
//									.append("html", insertHTMLfiche(pHTMLFile, pMotif.getCode()))
//									.append("xml", insertXMLFiche(pXMLFile, pMotif.getCode()))));
//				}
//			} else {
//				db.getCollection(pCollectionName).insertOne(
//	                new Document()
//	                .append("_id", new ObjectId(Base64.encode(pMotif.getCode())))
//	                .append("code", pMotif.getCode())
//	                .append("libelle", pMotif.getLibelle())
//	                .append("codification", pMotif.getCodification())
//	                .append("synonymes", pMotif.getSynonymes())
//	                .append("pdf", insertPDFfiche(pPDFFile , pMotif.getCode()))
//	                .append("html", insertHTMLfiche(pHTMLFile, pMotif.getCode()))
//	                .append("xml", insertXMLFiche(pXMLFile, pMotif.getCode())));
//			}
        }

	}

	/**
	 * {@inheritDoc}
	 */
	public Motif findMotifByCode(String pCodeMotif) {
        logger.info("BEGIN findMotifByCode ....");
        Gson gson = new Gson();
        
        FindIterable<Document> find = db.getCollection(ConfUtils.getPropertie(DB_MOTIF_COLLECTION)).find(Filters.eq("code", pCodeMotif));
        Document first = find.first();

        byte [] pdf_file = null;
		byte [] xml_file = null;
		byte [] html_file = null;
		byte [] new_xml_file = null;
        try {
			pdf_file = downloadMotifPDF(first.getObjectId("pdf"));
			html_file = downloadMotifHTML(first.getObjectId("html"));
			xml_file = downloadMotifXML(first.getObjectId("xml"));
			new_xml_file = downloadMotifNewXML(first.getObjectId("newxml"));
		} catch (IOException e) {
			logger.error(e);
		}
        String json = first.toJson();
        Motif motifDTO = gson.fromJson(json, Motif.class);
        motifDTO.setDureePDF(pdf_file);
        motifDTO.setDureeHTML(html_file);
        motifDTO.setDureeXML(xml_file);
        motifDTO.setDureeNewXML(new_xml_file);
        logger.info(first);
        logger.info("END findMotifByCode.");
        return motifDTO;
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.cnamts.prototype.aat.service.MongoDBService#findMotifs()
	 */
	public List<Motif> findMotifs() {
        logger.info("BEGIN findMotifs ....");
        
        Gson gson = new Gson();
        List<Motif> resultat = new ArrayList<Motif>();
        
        FindIterable<org.bson.Document> find = db.getCollection(ConfUtils.getPropertie(DB_MOTIF_COLLECTION)).find();
        MongoCursor<Document> iter = find.iterator();
        while (iter.hasNext()) {
			Document doc = iter.next();
			 byte [] pdf_file = null;
			 byte [] xml_file = null;
			 byte [] html_file = null;
		        try {
					pdf_file = downloadMotifPDF(doc.getObjectId("pdf"));
					html_file = downloadMotifHTML(doc.getObjectId("html"));
					xml_file = downloadMotifHTML(doc.getObjectId("xml"));
				} catch (IOException e) {
					logger.error(e);
				}
			String json = doc.toJson();
			Motif motif = gson.fromJson(json, Motif.class);
			motif.setDureePDF(pdf_file);
			motif.setDureeHTML(html_file);
			motif.setDureeXML(xml_file);
			resultat.add(motif);
		}

        logger.info("END findMotifs.");

		return resultat;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.cnamts.prototype.aat.service.MongoDBService#downloadMotifPDF(org.bson.types.ObjectId)
	 */
	public byte[] downloadMotifPDF(ObjectId motifPDF_ID) throws IOException {
		logger.info("BEGIN DOWNLOADIND PDF ....");
		byte[] fileToByteArray = null;
		if (null != motifPDF_ID) {
			File tmpFile = File.createTempFile("temp", "tem2");
			FileOutputStream streamToDownloadTo = new FileOutputStream(tmpFile);
			GridFSBucket gridFSBucket = GridFSBuckets.create(db, ConfUtils.getPropertie(DB_PDF_COLLECTION));
			gridFSBucket.downloadToStream(motifPDF_ID, streamToDownloadTo);
			fileToByteArray = FileUtils.fileToByteArray(tmpFile);
			streamToDownloadTo.close();
		}
		logger.info("END DOWNLOADIND PDF.");
        return fileToByteArray;
	}

	public byte[] downloadMotifHTML(ObjectId motifHTML_ID) throws IOException {
		logger.info("BEGIN DOWNLOADIND HTML ....");
		byte[] fileToByteArray = null;
		if (null != motifHTML_ID) {
			File tmpFile = File.createTempFile("temp3", "tem4");
			FileOutputStream streamToDownloadTo = new FileOutputStream(tmpFile);
			GridFSBucket gridFSBucket = GridFSBuckets.create(db, ConfUtils.getPropertie(DB_HTML_COLLECTION));
			gridFSBucket.downloadToStream(motifHTML_ID, streamToDownloadTo);
			fileToByteArray = FileUtils.fileToByteArray(tmpFile);
			streamToDownloadTo.close();
		}
		logger.info("END DOWNLOADIND HTML.");
        return fileToByteArray;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.cnamts.prototype.aat.service.MongoDBService#downloadMotifXML(org.bson.types.ObjectId)
	 */
	public byte[] downloadMotifXML(ObjectId motifXML_ID) throws IOException {
		logger.info("BEGIN DOWNLOADIND XML ....");
		byte[] fileToByteArray = null;
		if (null != motifXML_ID) {
			File tmpFile = File.createTempFile("temp5", "tem6");
			FileOutputStream streamToDownloadTo = new FileOutputStream(tmpFile);
			GridFSBucket gridFSBucket = GridFSBuckets.create(db, ConfUtils.getPropertie(DB_XML_COLLECTION));
			gridFSBucket.downloadToStream(motifXML_ID, streamToDownloadTo);
			fileToByteArray = FileUtils.fileToByteArray(tmpFile);
			streamToDownloadTo.close();
		}
		logger.info("END DOWNLOADIND XML.");
        return fileToByteArray;
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.cnamts.prototype.aat.service.MongoDBService#downloadMotifXML(org.bson.types.ObjectId)
	 */
	public byte[] downloadMotifNewXML(ObjectId motifNewXML_ID) throws IOException {
		logger.info("BEGIN DOWNLOADIND New_XML ....");
		byte[] fileToByteArray = null;
		if (null != motifNewXML_ID) {
			File tmpFile = File.createTempFile("temp5", "tem6");
			FileOutputStream streamToDownloadTo = new FileOutputStream(tmpFile);
			GridFSBucket gridFSBucket = GridFSBuckets.create(db, ConfUtils.getPropertie(DB_NEW_XML_COLLECTION));
			gridFSBucket.downloadToStream(motifNewXML_ID, streamToDownloadTo);
			fileToByteArray = FileUtils.fileToByteArray(tmpFile);
			streamToDownloadTo.close();
		}
		logger.info("END DOWNLOADIND New_XML.");
        return fileToByteArray;
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.cnamts.prototype.aat.service.MongoDBService#getListeTrieeMotif()
	 */
	public List<Motif> getListeTrieeMotif() {
		logger.info("BEGIN getListeTrieeMotif() ....");
        
        Gson gson = new Gson();
        List<Motif> resultat = new ArrayList<Motif>();
        
        FindIterable<org.bson.Document> find = db.getCollection(ConfUtils.getPropertie(DB_MOTIF_COLLECTION))
        		.find().sort(new Document("libelle", 1));
        MongoCursor<Document> iter = find.iterator();
        while (iter.hasNext()) {
			Document doc = iter.next();
			String json = doc.toJson();
			Motif motif = gson.fromJson(json, Motif.class);
			resultat.add(motif);
		}

        logger.info("END getListeTrieeMotif().");

		return resultat;
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.cnamts.prototype.aat.service.MongoDBService#getListeTrieeMotifView()
	 */
	public List<MotifView> getListeTrieeMotifView() {
		logger.info("BEGIN getListeTrieeMotifView() ....");
        
        Gson gson = new Gson();
        List<MotifView> resultat = new ArrayList<MotifView>();
        
        FindIterable<org.bson.Document> find = db.getCollection(ConfUtils.getPropertie(DB_MOTIF_COLLECTION))
        		.find().sort(new Document("libelle", 1));
        MongoCursor<Document> iter = find.iterator();
        while (iter.hasNext()) {
			Document doc = iter.next();
			String json = doc.toJson();
			Motif motif = gson.fromJson(json, Motif.class);
			resultat.add(MotifMapper.convertMotifToMotifView(motif));
		}

        logger.info("END getListeTrieeMotifView().");

		return resultat;
	}

}

package fr.cnamts.prototype.aat.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import fr.cnamts.prototype.aat.dto.MotifView;
import fr.cnamts.prototype.aat.entity.Motif;
import fr.cnamts.prototype.aat.motif.combo.view.TypeEmploiView;
import fr.cnamts.prototype.aat.motif.combo.xml.NewXMLUtil;
import fr.cnamts.prototype.aat.service.ElasticSearchService;
import fr.cnamts.prototype.aat.service.LuceneIndexRecherche;
import fr.cnamts.prototype.aat.service.MongoDBService;
import fr.cnamts.prototype.aat.service.impl.ElasticSearchServiceImpl;
import fr.cnamts.prototype.aat.service.impl.LuceneIndexRechercheImpl;
import fr.cnamts.prototype.aat.service.impl.MongoDBServiceImpl;
import fr.cnamts.prototype.aat.util.ConfUtils;
import fr.cnamts.prototype.aat.util.Constante;
import fr.cnamts.prototype.aat.util.MotifMapper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Service REST
 * @author ONDONGO-09929
 *
 */
@Path("/motif")
public class MotifRESTController {
	
//	private ElasticSearchService elsService = new ElasticSearchServiceImpl();
	LuceneIndexRecherche luceneService = new LuceneIndexRechercheImpl();

//	private MongoDBService mongoService = new MongoDBServiceImpl();
	private Logger logger = Logger.getLogger(this.getClass());
	private Gson gson = new Gson();
//	private static CacheMotifManager cacheMotifManager = CacheMotifManager.getInstance();
//	private static CacheManager cm = CacheManager.getInstance();
	
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
 
		String output = "MotifRESTController say hello : " + msg;
		return Response.status(200).entity(output).build();
 
	}
	

	@GET
	@Path("/listeTypeEmploi/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListeTypeEmploi(@PathParam("param") String codeMotif) {
		String output = null;
//		Cache cache = cm.getCache("cacheComboMotif");
//		Element element = cache.get(codeMotif);
//		List<TypeEmploiView> listTypeEmploi = new ArrayList<TypeEmploiView>();
//		if(null == element){
//			Motif motif = 
//					//mongoService.findMotifByCode(codeMotif);
//			byte[] dureeNewXML = motif.getDureeNewXML();
//			
//			//convert array of bytes into file
//		    try {
//		    	if (null!= dureeNewXML) {
//					File newXmlTp = File.createTempFile("newxml1", "newxml12");
//					FileOutputStream fileOuputStream = new FileOutputStream(
//							newXmlTp);
//					fileOuputStream.write(dureeNewXML);
//					listTypeEmploi = NewXMLUtil.getListeTypeEmploi(newXmlTp);
//					cache.put(new Element(codeMotif, listTypeEmploi));
//					output = gson.toJson(listTypeEmploi);
//					fileOuputStream.close();
//				}
//			} catch (FileNotFoundException e) {
//				logger.error("Une erreur s'est produite lors de la récupération u modèle XML", e);
//				return Response.status(500).entity(gson.toJson(listTypeEmploi)).build();
//			} catch (IOException e) {
//				logger.error("Une erreur s'est produite lors de la récupération u modèle XML", e);
//				return Response.status(500).entity(gson.toJson(listTypeEmploi)).build();
//			}
//
//			
//		}else{
//			listTypeEmploi = (List<TypeEmploiView>) element.getObjectValue();
//			output = gson.toJson(listTypeEmploi);
//		}
		return Response.status(200).entity(output).build();
	}
	
	
	@GET
	@Path("/htm/{param}")
	@Produces(MediaType.TEXT_HTML)
	public Response getTableDuree(@PathParam("param") String codeMotif) {
 
		logger.info("Begin getTableDuree...");
		logger.info("Code motif :=" + codeMotif);
		byte[] output = null;
		Motif motif = null;

//		Cache cache = cm.getCache("cacheMotif");
//		Element element = cache.get(codeMotif);
//		// On appelle la source de donnée physique que si les données ne sont plus en cache.
//		if(null == element){
//			motif = mongoService.findMotifByCode(codeMotif);
//			cache.put(new Element(codeMotif, motif));
//			output = motif.getDureeHTML();
//		}else{
//			motif = (Motif) element.getObjectValue();
//			output = motif.getDureeHTML();
//		}
		
		logger.info("End getTableDuree.");
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/pdf/{param}")
	@Produces("application/pdf")
	public Response getPDF(@PathParam("param") String codeMotif) {
 
		logger.info("Begin getPDF...");
		logger.info("Code motif :=" + codeMotif);
		byte[] output = null;
		Motif motif = null;
//		Cache cache = cm.getCache("cacheMotif");
//		Element element = cache.get(codeMotif);
//		// On appelle la source de donnée physique que si les données ne sont plus en cache.
//		if(null == element){
//			motif = mongoService.findMotifByCode(codeMotif);
//			cache.put(new Element(codeMotif, motif));
//			output = motif.getDureePDF();
//		} else {
//			motif = (Motif) element.getObjectValue();
//			output = motif.getDureePDF();
//		}
		
		logger.info("Byte tableau :  " + output.length);
		logger.info("End getPDF.");
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/xml/{param}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getDureeXML(@PathParam("param") String codeMotif) {
 
		logger.info("Begin getDureeXML...");
		logger.info("Code motif :=" + codeMotif);
		byte[] output = null;
		Motif motif = null;
//		Cache cache = cm.getCache("cacheMotif");
//		Element element = cache.get(codeMotif);
//		
//		if(null == element){
//			motif = mongoService.findMotifByCode(codeMotif);
//			cache.put(new Element(codeMotif, motif));
//			output = motif.getDureeXML();
//		} else {
//			motif = (Motif) element.getObjectValue();
//			output = motif.getDureeXML();
//		}
		logger.info("End getDureeXML.");
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/motif/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMotif(@PathParam("param") String msg) {
		logger.info("Le motif saisie par l'utilisateur est :" + msg);
		List<Motif> output = luceneService.rechercher(msg);
		List<MotifView> listMotifView = new ArrayList<MotifView>();
		if (null != output) {
			for (Motif motif : output) {
				listMotifView.add(MotifMapper.convertMotifToMotifView(motif));
			}
		}
		return gson.toJson(listMotifView);
	}
	

	@SuppressWarnings("resource")
	@GET
	@Path("/cim10")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListOrdonneCIM10() {		
		/**
		 * Motifs triés par lette
		 */
		List<String> listeCIM10 = new ArrayList<String>();		
		String csvFile =  ConfUtils.getPropertie("commun.cim10.directory") + "CIM10.csv";
        BufferedReader br = null;
        String line =  "";
        try {
			br = new BufferedReader(new FileReader(csvFile));		
			while ((line = br.readLine()) != null) {  
				String[] fields = line.split(Constante.CSV_SERAPTOR);
				listeCIM10.add(fields[1]);
			}
		} catch (FileNotFoundException e1) {
			logger.error("Une erreur s'est produite lors de la récupération des CIM10", e1);
			return Response.status(500).entity(gson.toJson(listeCIM10)).build();
		} catch (IOException e) {
			logger.error("Une erreur s'est produite lors de la récupération des CIM10", e);
			return Response.status(500).entity(gson.toJson(listeCIM10)).build();
		} finally {
			
		}
		
		logger.info(gson.toJson(listeCIM10));
		return Response.status(200).entity(gson.toJson(listeCIM10)).build();
	}
	

	@GET
	@Path("/liste/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getListOrdonneMotif() {		
		/**
		 * Motifs triés par lette
		 */
		SortedMap<String, List<MotifView>> mapMotif = null;
		List<Motif> listeTrieeMotif = null;
//		Cache cache = cm.getCache("cacheMotif");
//		String keyList = "liste";
//		Element element = cache.get(keyList);
//		if (null == element) {
//			mapMotif = new TreeMap<String, List<MotifView>>();
//			listeTrieeMotif = mongoService.getListeTrieeMotif();
//			mapMotif = MotifMapper.listMotifToSortedMap(listeTrieeMotif);
//			cache.put(new Element(keyList, mapMotif));
//		} else {
//			mapMotif = (SortedMap<String, List<MotifView>>) element.getObjectValue();
//		}
		logger.info(gson.toJson(mapMotif));
		return gson.toJson(mapMotif);
	}

	@GET
	@Path("/pdf1/{param}")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response getPDF1(@PathParam("param") String codeMotif) {
 
		logger.info("Begin getPDF1...");
		logger.info("Code motif :=" + codeMotif);
		byte[] output = null;
		Motif motif = null;
//		Cache cache = cm.getCache("cacheMotif");
//		Element element = cache.get(codeMotif);
//		// On appelle la source de donnée physique que si les données ne sont plus en cache.
//		if(null == element){
//			motif = mongoService.findMotifByCode(codeMotif);
//			cache.put(new Element(codeMotif, motif));
//			output = motif.getDureePDF();
//		} else {
//			motif = (Motif) element.getObjectValue();
//			output = motif.getDureePDF();
//		}
//		
//		logger.info("Byte tableau :  " + output.length);
//		logger.info("End getPDF1.");
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/pdf2/{param}")
	@Produces("application/pdf")
	public Response getPDF2(@PathParam("param") String codeMotif) {
 
		logger.info("Begin getPDF2...");
		logger.info("Code motif :=" + codeMotif);
		StringBuilder response = new StringBuilder();
		
//		try {
//			URL url = new URL("http://localhost:8080/RESTFullMotif/rest/motif/pdf1/" + codeMotif);
//	          
//            final HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
//            connexion.setRequestMethod("GET");
//            final BufferedReader in = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//logger.info("Byte tableau :  " + b.length  + " String : " + b.toString());
		//logger.info("Byte tableau :  " + response.toString().getBytes().length  + " String : " + gson.toJson(response.toString().getBytes()).toString());
		logger.info("End getPDF2.");
		return Response.status(200).entity(response.toString().getBytes()).build();
	}
}

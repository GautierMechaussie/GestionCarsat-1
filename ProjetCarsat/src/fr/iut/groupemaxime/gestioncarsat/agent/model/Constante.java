package fr.iut.groupemaxime.gestioncarsat.agent.model;

import java.text.SimpleDateFormat;

public class Constante {
	public static String CHEMIN_PDF = "target/PDF/";
	public static String EXTENSION_XML = ".xml";
	public static String EXTENSION_PDF = ".pdf";
	public static String CHEMIN_PDF_VIDE = "target/PDF/OM_vide.pdf";
	public static String CHEMIN_FICHIERS_DEFAUT = "target/OM/";
	public static String EXTENSION_JSON = ".json";


	public static String CHEMIN_OPTIONS = "target/options.json";
	public static String CHEMIN_IMAGES = "ressources/images/";
	
	public static SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd-MM-yyyy");
	
	public static int TAILLE_NUMCAPSSA = 8;
	public static int TAILLE_CODE_ANALYTIQUE = 10;
	
	public static final int TAILLE_SIGNATURE = 60;
	public static final int SIGNATURE_AGENT_Y = 230;
	public static final int SIGNATURE_AGENT_X = 330;


}

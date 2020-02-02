package fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.iut.groupemaxime.gestioncarsat.agent.interfaces.DocJson;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.EtatMission;

public class HoraireTravail implements DocJson<HoraireTravail> {

	private String adresseFichier;
	private String dateDebutMission;
	private String dateFinMission;
	private HashMap<String, HoraireJournalier> horaireJournalier;
	private EtatMission etat;
	private boolean signe;

	public HoraireTravail(String adresseFichier, String dateDebutMission, String dateFinMission,
			HashMap<String, HoraireJournalier> horaireTravail) {
		this.adresseFichier = adresseFichier;
		this.dateDebutMission = dateDebutMission;
		this.dateFinMission = dateFinMission;
		this.horaireJournalier = horaireTravail;
		this.etat = EtatMission.NON_REMPLI;
		this.signe = false;
	}

	public HoraireTravail(String adresseFichier) {
		this(adresseFichier, null, null, new HashMap<String, HoraireJournalier>());
	}

	@Override
	public void sauvegarderJson(String adresseFichier) {
		this.setEtat(EtatMission.NON_SIGNE);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String s = gson.toJson(this);
		FileWriter f;
		try {
			f = new FileWriter(new File(adresseFichier));
			f.write(s);
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setEtat(EtatMission etat) {
		this.etat = etat;
	}

	@Override
	public HoraireTravail chargerJson(String adresseFichier) {
		Gson g = new Gson();
		HoraireTravail horaireTravail = new HoraireTravail(adresseFichier);
		InputStream is;
		try {
			is = new FileInputStream(new File(adresseFichier));
			// Creation du JsonReader depuis Json.
			JsonReader reader = Json.createReader(is);
			// Recuperer la structure JsonObject depuis le JsonReader.
			JsonObject objetJson = reader.readObject();
			reader.close();
			horaireTravail = g.fromJson(objetJson.toString(), HoraireTravail.class);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		horaireTravail.trierHoraireJournalier();
		return horaireTravail;
	}

	public void trierHoraireJournalier() { // pb dans la sauvegarde
		this.horaireJournalier = HoraireTravail.triAvecValeur(this.horaireJournalier);
	}

	public static HashMap<String, HoraireJournalier> triAvecValeur(HashMap<String, HoraireJournalier> map) {
		LinkedList<Map.Entry<String, HoraireJournalier>> list = new LinkedList<Map.Entry<String, HoraireJournalier>>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, HoraireJournalier>>() {
			@Override
			public int compare(Map.Entry<String, HoraireJournalier> o1, Map.Entry<String, HoraireJournalier> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		HashMap<String, HoraireJournalier> map_apres = new LinkedHashMap<String, HoraireJournalier>();
		for (Map.Entry<String, HoraireJournalier> entry : list)
			map_apres.put(entry.getKey(), entry.getValue());
		return map_apres;
	}

	public void ajouterJournee(HoraireJournalier horaireJournalier) {
		if (null != this.horaireJournalier.get(horaireJournalier)) {
			this.horaireJournalier.replace(horaireJournalier.getDate(), horaireJournalier);
		} else {
			this.horaireJournalier.put(horaireJournalier.getDate(), horaireJournalier);
		}
	}

	// Remplir le fichier excel
	public void remplirExcelHT() {
		try {
			FileInputStream excelFile = new FileInputStream(new File(Constante.CHEMIN_EXCEL_VIDE));
			Workbook workbook = new HSSFWorkbook(excelFile);
			Sheet dataSheet = workbook.getSheetAt(0);
			int ligne = Constante.DEBUT_LIGNE_EXCEL;
			OrdreMission om = new OrdreMission(null, null, null);
			om = om.chargerJson(this.adresseFichier.replace("HT_", "OM_"));

			CellStyle style = workbook.createCellStyle();
			// Ajout bordure
			style.setBorderBottom((short) 1.0);
			style.setBorderLeft((short) 1.0);
			style.setBorderRight((short) 1.0);
			style.setBorderTop((short) 1.0);
			for (HoraireJournalier hj : this.getHoraireTravail().values()) {
				for (PlageHoraire ph : hj.getPlageHoraire()) {
					Row row;
					style = workbook.createCellStyle();
					// Ajout bordure
					style.setBorderBottom((short) 1.0);
					style.setBorderLeft((short) 1.0);
					style.setBorderRight((short) 1.0);
					style.setBorderTop((short) 1.0);
					dataSheet.shiftRows(ligne, ligne + 20, 1);
					row = dataSheet.createRow(ligne);

					for (int i = 1; i < 9; i++) {
						row.createCell(i).setCellStyle(style);
					}

					row = dataSheet.getRow(ligne);
					// Ajout format heure
					CreationHelper createHelper = workbook.getCreationHelper();
					style.setDataFormat(createHelper.createDataFormat().getFormat("HH:mm"));
					row.getCell(3).setCellStyle(style);
					row.getCell(4).setCellStyle(style);
					row.getCell(7).setCellStyle(style);

					row.getCell(2).setCellValue(hj.getDate());
					row.getCell(3)
							.setCellValue(String.valueOf(ph.getHeureDeb()) + ':' + String.valueOf(ph.getMinDeb()));
					row.getCell(4)
							.setCellValue(String.valueOf(ph.getHeureFin()) + ':' + String.valueOf(ph.getMinFin()));
					// TODO
					row.getCell(5)
							.setCellFormula("IF(IF(EXACT(G" + (ligne + 1) + ",\"\"),E" + (ligne + 1) + "-D"
									+ (ligne + 1) + ",\"\")=0,\"\",IF(EXACT(G" + (ligne + 1) + ",\"\"),E" + (ligne + 1)
									+ "-D" + (ligne + 1) + ",\"\"))");

					if (ph.getIsTransport())
						row.getCell(6).setCellValue(hj.getTransportUtiliseSurPlace());

					row.getCell(7).setCellFormula(
							"IF(NOT(EXACT(G" + (ligne + 1) + ",\"\")),E" + (ligne + 1) + "-D" + (ligne + 1) + ",\"\")");

					// TODO
					row.getCell(8).setCellValue(hj.getObservation());
					ligne++;
				}
			}
			dataSheet.getRow(8).getCell(2).setCellValue(om.getAgent().getNumCAPSSA());
			// TODO
			dataSheet.getRow(8).getCell(4).setCellValue(2);
			dataSheet.getRow(8).getCell(7).setCellValue(om.getAgent().getUniteTravail());
			dataSheet.getRow(10).getCell(2).setCellValue(om.getAgent().getNom());
			dataSheet.getRow(10).getCell(7).setCellValue(om.getAgent().getPrenom());

			dataSheet.getRow(ligne).getCell(2).setCellStyle(style);
			dataSheet.getRow(ligne).getCell(2).setCellFormula("SUM(F" + (Constante.DEBUT_LIGNE_EXCEL + 1) + ":F" + ligne
					+ ")+SUM(H" + (Constante.DEBUT_LIGNE_EXCEL + 1) + ":H" + ligne + ")*0.75");

			// TODO Avoir la formule horaire journalier
//			dataSheet.getRow(ligne).getCell(4).setCellStyle(style);
//			dataSheet.getRow(ligne).getCell(4).setCellFormula("");

			workbook.setForceFormulaRecalculation(true);

			// Sauvegarder le fichier
			FileOutputStream out = new FileOutputStream(
					new File(this.adresseFichier.replace(Constante.EXTENSION_JSON, Constante.EXTENSION_XLS)));
			workbook.write(out);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, HoraireJournalier> getHoraireTravail() {
		return horaireJournalier;
	}

	public String getAdresseFichier() {
		return this.adresseFichier;
	}

	public String getDateDebutMission() {
		return dateDebutMission;
	}

	public void setDateDebutMission(String dateDebutMission) {
		this.dateDebutMission = dateDebutMission;
	}

	public String getDateFinMission() {
		return dateFinMission;
	}

	public void setDateFinMission(String dateFinMission) {
		this.dateFinMission = dateFinMission;
	}

	public EtatMission getEtat() {
		return this.etat;
	}

	public boolean estSigne() {
		return this.signe;
	}

}
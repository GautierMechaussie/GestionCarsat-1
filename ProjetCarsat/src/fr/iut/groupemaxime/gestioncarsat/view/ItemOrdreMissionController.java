package fr.iut.groupemaxime.gestioncarsat.view;

import javafx.fxml.FXML;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import fr.iut.groupemaxime.gestioncarsat.MainApp;
import fr.iut.groupemaxime.gestioncarsat.form.PDF;
import fr.iut.groupemaxime.gestioncarsat.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.model.OrdreMission;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ItemOrdreMissionController {
	@FXML
	private Label lieuLabel;
	@FXML
	private Label datesLabel;
	@FXML
	private VBox boite;

	private OrdreMission om;
	
	private MainApp mainApp;

	@FXML
	private void initialize() {

	}

	// Event Listener on Button.onAction
	@FXML
	public void afficherOrdreMissionPDF(ActionEvent event) {
		PDF pdf;
		try {
			pdf = new PDF(new File("target/PDF/OM_vide.pdf"));
			pdf.remplirPDF(om);
			pdf.sauvegarderPDF();
			pdf.fermerPDF();
			try {
				Desktop.getDesktop().browse(
						new URI("file:///C:/Users/mylan/git/GestionCarsat/ProjetCarsat/target/PDF/Doc1modif.pdf"));
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	// Event Listener on Button.onAction
	@FXML
	public void modifierOM(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on Button.onAction
	@FXML
	public void supprimerOM(ActionEvent event) {
		this.mainApp.enleverOm(om);
	}

	public void chargerOM(OrdreMission om) {
		this.om = om;
		this.lieuLabel.setText(((MissionTemporaire) om.getMission()).getLieuDeplacement());
		this.datesLabel.setText("Du " + ((MissionTemporaire) om.getMission()).getDateDebut() + " au "
				+ ((MissionTemporaire) om.getMission()).getDateFin());

	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp=mainApp;
	}
}

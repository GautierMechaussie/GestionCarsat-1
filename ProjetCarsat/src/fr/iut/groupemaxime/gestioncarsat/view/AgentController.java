package fr.iut.groupemaxime.gestioncarsat.view;

import java.awt.font.NumericShaper;

import fr.iut.groupemaxime.gestioncarsat.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class AgentController {

	@FXML
	private GridPane grilleTop;

	@FXML
	private TextField nomTextField;

	@FXML
	private TextField prenomTextField;

	@FXML
	private TextField fonctionTextField;

	@FXML
	private TextField residenceAdminTextField;

	@FXML
	private TextField uniteTavailTextField;

	@FXML
	private TextField codeAnalytiqueTextField;

	@FXML
	private TextField numCAPSSATextField;

	@FXML
	private TextField coefficientTextField;

	@FXML
	private Button suivantBtn;

	private MainApp mainApp;

	@FXML
	private void initialize() {
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void passerATypeOM() {
		if (tousLesChampsValides())
			this.mainApp.afficherFormTypeOM();
	}

	private boolean tousLesChampsValides() {
		String erreur = "";
		if (nomTextField.getText() == null || nomTextField.getText().length() == 0)
			erreur += "Le champ nom est vide !\n";
		if (prenomTextField.getText() == null || prenomTextField.getText().length() == 0)
			erreur += "Le champ pr�nom est vide !\n";
		if (fonctionTextField.getText() == null || fonctionTextField.getText().length() == 0)
			erreur += "Le champ fonction est vide !\n";
		if (uniteTavailTextField.getText() == null || uniteTavailTextField.getText().length() == 0)
			erreur += "Le champ unit� de travail est vide !\n";
		if (residenceAdminTextField.getText() == null || residenceAdminTextField.getText().length() == 0)
			erreur += "Le champ r�sidence administrative est vide !\n";
		if (numCAPSSATextField.getText() == null || numCAPSSATextField.getText().length() == 0)
			erreur += "Le champ num�ro CAPSSA est vide !\n";
		else {
			try {
				Integer.parseInt(numCAPSSATextField.getText());
			} catch (NumberFormatException e) {
				erreur += "Le champ num�ro CAPSSA est invalide (entrez un nombre entier)!\n";
			}
		}
		if (coefficientTextField.getText() == null || coefficientTextField.getText().length() == 0)
			erreur += "Le champ coefficient est vide !\n";
		else {
			try {
				Integer.parseInt(coefficientTextField.getText());
			} catch (NumberFormatException e) {
				erreur += "Le champ coefficient est invalide (entrez un nombre entier)!\n";
			}
		}
		if (codeAnalytiqueTextField.getText() == null || codeAnalytiqueTextField.getText().length() == 0)
			erreur += "Le champ code analytique est vide !\n";
		else {
			try {
				Integer.parseInt(codeAnalytiqueTextField.getText());
			} catch (NumberFormatException e) {
				erreur += "Le champ code analytique est invalide (entrez un nombre entier)!\n";
			}
		}
		
		if (erreur.length() > 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Champs non Valides");
			alert.setHeaderText("Des champs ne sont pas valides");
			alert.setContentText(erreur);

			alert.showAndWait();
			return false;
		} else {
			return true;
		}

	}

}
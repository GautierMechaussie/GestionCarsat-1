package fr.iut.groupemaxime.gestioncarsat.agent.view;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Constante;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class RootLayoutController {
	private AgentApp mainApp;

	@FXML
	private ImageView banniereCarsat;

	@FXML
	private ImageView imageParametre;

	@FXML
	private ImageView imageOM;

	@FXML
	private ImageView imageFM;

	@FXML
	private ImageView imageHT;

	@FXML
	private VBox boxOM;

	@FXML
	private VBox boxFM;

	@FXML
	private VBox boxHT;

	@FXML
	private VBox boxParam;

	@FXML
	private VBox boxMenu;

	@FXML
	private void initialize() {
		this.banniereCarsat.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "banniereCarsat.png"));
		this.imageParametre.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "parametres.png"));
		this.imageHT.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "horaires.png"));
		this.imageFM.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "frais.png"));
		this.imageOM.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "ordre.png"));

	}

	// Event Listener sur Btn Parametres
	@FXML
	public void modifierOptions() {
		mainApp.modifierOptions();
	}

	@FXML
	public void afficherFraisMission() {
		mainApp.afficherFraisMission();
	}

	@FXML
	public void afficherOrdresMission() {
		mainApp.afficherOrdresMission();
	}

	@FXML
	public void afficherHorairesTravail() {
		mainApp.afficherHorairesTravail();
	}

	public void setMainApp(AgentApp mainApp) {
		this.mainApp = mainApp;
	}
}

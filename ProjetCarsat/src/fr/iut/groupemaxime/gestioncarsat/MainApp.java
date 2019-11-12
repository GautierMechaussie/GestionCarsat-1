package fr.iut.groupemaxime.gestioncarsat;

import java.io.IOException;
import fr.iut.groupemaxime.gestioncarsat.view.AgentController;
import fr.iut.groupemaxime.gestioncarsat.view.TransportController;
import fr.iut.groupemaxime.gestioncarsat.view.MissionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private AnchorPane pageAgent;
	private AnchorPane pageMission;
	private AnchorPane pageTransport;
	private AgentController controllerAgent;
	private MissionController controllerMission;
	private TransportController controllerTransport;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("OM");
		// this.primaryStage.isResizable(); --> propri�t� redimensionnement de la
		// fen�tre
		initialiseRootLayout();
		afficherListOm();
		afficherFormInfoPerso();

	}

	private void afficherListOm() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MenuAgent.fxml"));
			AnchorPane page = loader.load();

			rootLayout.setLeft(page);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void initialiseRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void afficherFormInfoPerso() {
		if (this.pageAgent != null) {
			rootLayout.setCenter(this.pageAgent);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("view/Agent.fxml"));
				pageAgent = loader.load();

				rootLayout.setCenter(pageAgent);

				controllerAgent = loader.getController();
				controllerAgent.setMainApp(this);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public BorderPane getRootLayout() {
		return rootLayout;
	}

	public void afficherFormTypeOM() {
		if (this.pageMission != null) {
			rootLayout.setCenter(this.pageMission);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("view/Mission.fxml"));
				pageMission = loader.load();

				rootLayout.setCenter(pageMission);
				controllerMission = loader.getController();
				controllerMission.setMainApp(this);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void afficherFormMoyenTransport() {
		if (this.pageTransport != null) {
			rootLayout.setCenter(this.pageTransport);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("view/Transport.fxml"));
				pageTransport = loader.load();

				rootLayout.setCenter(pageTransport);

				controllerTransport = loader.getController();
				controllerTransport.setMainApp(this);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void validerOrdreMission() {
		//TODO
	}
}

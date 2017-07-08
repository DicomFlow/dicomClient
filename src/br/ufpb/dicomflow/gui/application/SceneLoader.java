package br.ufpb.dicomflow.gui.application;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneLoader {

	private static SceneLoader sceneLoader =  new SceneLoader();


	private SceneLoader(){

	}


	public static SceneLoader getSceneLoader(){
		return sceneLoader;
	}

	public void loadMainScene() throws IOException {

		this.loadScene("DicomFlow Client", getClass().getResource("Main.fxml"));

	}

	public void loadLoginScene() throws IOException {

		this.loadScene("DicomFlow Client", getClass().getResource("Login.fxml"));

	}

	public void loadConfigScene() throws IOException {

		this.loadScene("DicomFlow Client", getClass().getResource("Configuration.fxml"));

	}

	public Stage loadScene(String title, URL resource) throws IOException {

		Pane myPane = FXMLLoader.load(resource);
		Scene scene = new Scene(myPane);

		Stage stage = Main.getpStage();
		stage.setTitle(title);
		stage.setScene(scene);
		//stage.hide();
		stage.show();

		return stage;

	}

}

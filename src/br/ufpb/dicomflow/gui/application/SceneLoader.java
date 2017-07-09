package br.ufpb.dicomflow.gui.application;

import java.io.IOException;
import java.lang.reflect.Field;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneLoader {

	public static final String MAIN_SCENE = "Main.fxml";
	public static final String LOGIN_SCENE = "Login.fxml";
	public static final String CONFIG_SCENE = "Configuration.fxml";
	public static final String CONFIG_UPDATE_SCENE = "ConfigurationUpdate.fxml";
	public static final String RECEIVED_MESSAGES_SCENE = "ReceivedMessages.fxml";

	private static SceneLoader sceneLoader =  new SceneLoader();


	private SceneLoader(){

	}


	public static SceneLoader getSceneLoader(){
		return sceneLoader;
	}

	public void loadMainScene() throws IOException {

		this.loadScene("DicomFlow Client", MAIN_SCENE);

	}

	public void loadLoginScene() throws IOException {

		this.loadScene("DicomFlow Client", LOGIN_SCENE);

	}

	public void loadConfigScene() throws IOException {

		this.loadScene("DicomFlow Client", CONFIG_SCENE);

	}

	public void loadReceivedMessagesScene() throws IOException {

		this.loadScene("DicomFlow Client", RECEIVED_MESSAGES_SCENE);

	}

	public Node getNode(String fxml){
        Node node = null;
        try {
            node = FXMLLoader.load(getClass().getResource(fxml));
        } catch (Exception e) {
        }
        return node;

    }

	public Stage loadScene(String title, String fxml) throws IOException {

		Pane myPane = FXMLLoader.load(getClass().getResource(fxml));
		Scene scene = new Scene(myPane);

		Stage stage = Main.getpStage();
		stage.setTitle(title);
		stage.setScene(scene);
		//stage.hide();
		stage.show();

		return stage;

	}


	/**
	 * ***********FORCE TOOL TIP TO BE DISPLAYED FASTER************
	 */
	public void hackTooltipStartTiming(Tooltip tooltip) {
		try {
			Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
			fieldBehavior.setAccessible(true);
			Object objBehavior = fieldBehavior.get(tooltip);

			Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
			fieldTimer.setAccessible(true);
			Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

			objTimer.getKeyFrames().clear();
			objTimer.getKeyFrames().add(new KeyFrame(new Duration(5)));
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			System.out.println(e);
		}
	}

}

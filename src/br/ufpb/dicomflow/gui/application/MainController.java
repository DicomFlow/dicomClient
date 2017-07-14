package br.ufpb.dicomflow.gui.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.ufpb.dicomflow.gui.business.AuthenticationProcessor;
import br.ufpb.dicomflow.gui.business.MessageProcessor;
import br.ufpb.dicomflow.gui.exception.MessageException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable {
//	private final String DATE_ORDER = "Data";
//	private final String FROM_ORDER = "De";
//	private final String TO_ORDER = "Para";
//	private final String SUBJECT_ORDER = "Assunto";

	@FXML
	private ComboBox<String> orderByComboBox;
	@FXML
	private TextField searchTextField;

	@FXML
	private Label receivedLabel;

	@FXML
	private Button loadButton;

	@FXML
	private Button showButton;

	@FXML
	private AnchorPane anchorPane;

	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {

		//orderByComboBox.getItems().addAll(DATE_ORDER,FROM_ORDER, TO_ORDER, SUBJECT_ORDER);

		anchorPane.getChildren().clear();

		try {
			URL url = getClass().getResource("ReceivedMessages.fxml");
			if(url == null){
				url = getClass().getClassLoader().getResource("ReceivedMessages.fxml");
			}
			Parent root = FXMLLoader.load(url);
			anchorPane.getChildren().add(root);//SceneLoader.getInstance().getNode(SceneLoader.RECEIVED_MESSAGES_SCENE));

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	@FXML
	public void logoutAction(ActionEvent event){
		AuthenticationProcessor.getInstance().logout();

		try {
			SceneLoader.getInstance().loadLoginScene();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void exitAction(ActionEvent event){
		Platform.exit();
		System.exit(0);
	}

	@FXML
	public void configAction(ActionEvent event){
		anchorPane.getChildren().clear();


		try {
			URL url = getClass().getResource("ConfigurationUpdate.fxml");
			if(url == null){
				url = getClass().getClassLoader().getResource("ConfigurationUpdate.fxml");
			}
			Parent root = FXMLLoader.load(url);
			anchorPane.getChildren().add(root);//SceneLoader.getInstance().getNode(SceneLoader.RECEIVED_MESSAGES_SCENE));

		} catch (IOException e) {
			e.printStackTrace();
		}



	}

	@FXML
	public void loadReceivedAction(ActionEvent event){
		Main.getpStage().getScene().setCursor(Cursor.WAIT);
		try {

			MessageProcessor.getInstance().receiveMessages();

		} catch (MessageException e) {
			Main.getpStage().getScene().setCursor(Cursor.DEFAULT);
			e.printStackTrace();
		}

		showReceivedAction(event);


	}

	@FXML
	public void showReceivedAction(ActionEvent event){
		anchorPane.getChildren().clear();

		try {
			URL url = getClass().getResource("ReceivedMessages.fxml");
			if(url == null){
				url = getClass().getClassLoader().getResource("ReceivedMessages.fxml");
			}
			Parent root = FXMLLoader.load(url);
			anchorPane.getChildren().add(root);//SceneLoader.getInstance().getNode(SceneLoader.RECEIVED_MESSAGES_SCENE));

		} catch (IOException e) {
			e.printStackTrace();
		}
		Main.getpStage().getScene().setCursor(Cursor.DEFAULT);
	}

	@FXML
	public void loadSentAction(MouseEvent event){

	}

	@FXML
	public void loadImportantAction(MouseEvent event){

	}

	@FXML
	public void loadArchivedAction(MouseEvent event){

	}




}

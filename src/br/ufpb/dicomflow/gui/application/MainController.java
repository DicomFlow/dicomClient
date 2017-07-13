package br.ufpb.dicomflow.gui.application;

import java.io.IOException;
import java.util.ResourceBundle;

import br.ufpb.dicomflow.gui.business.AuthenticationProcessor;
import br.ufpb.dicomflow.gui.business.MessageProcessor;
import br.ufpb.dicomflow.gui.exception.MessageException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable {
	private final String DATE_ORDER = "Data";
	private final String FROM_ORDER = "De";
	private final String TO_ORDER = "Para";
	private final String SUBJECT_ORDER = "Assunto";

	@FXML
	private ComboBox<String> orderByComboBox;
	@FXML
	private TextField searchTextField;

	@FXML
	private AnchorPane anchorPane;

	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		orderByComboBox.getItems().addAll(DATE_ORDER,FROM_ORDER, TO_ORDER, SUBJECT_ORDER);

		anchorPane.getChildren().clear();
		anchorPane.getChildren().add(SceneLoader.getInstance().getNode(SceneLoader.RECEIVED_MESSAGES_SCENE));


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
		anchorPane.getChildren().add(SceneLoader.getInstance().getNode(SceneLoader.CONFIG_UPDATE_SCENE));

	}

	@FXML
	public void loadReceivedAction(MouseEvent event){

		try {

			MessageProcessor.getInstance().receiveMessages();

		} catch (MessageException e) {
			e.printStackTrace();
		}

		anchorPane.getChildren().clear();
		anchorPane.getChildren().add(SceneLoader.getInstance().getNode(SceneLoader.RECEIVED_MESSAGES_SCENE));
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

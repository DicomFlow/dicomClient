package br.ufpb.dicomflow.gui.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MainAppController implements Initializable  {
	
	@FXML
	private TextField pesquisarTextField;

	@FXML
	private Label contatosLabel;
	
	@FXML
	private Label inboxLabel;
	
	@FXML
	private Label favoritosLabel;
	
	@FXML
	private Label configuracoesLabel;
	
	@FXML
	ImageView contatosIcon;
	
	@FXML
	private ImageView inboxIcon;
	
	@FXML
	private ImageView favoritosIcon;
	
	@FXML
	private ImageView configuracoesIcon;
	
	@FXML
	private Pane centerPane;
	
	@FXML
	private Pane mailsPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setIcons();

		mailsPane.getChildren().clear();
		for (int i = 0; i <= 5; i++) {
			loadMailItem();	
		}		
	}

	private void loadMailItem() {
		try {
			URL url = getClass().getResource("mail_item.fxml");
			if (url == null) {
				url = getClass().getClassLoader().getResource("mail_item.fxml");
			}
			Parent root = FXMLLoader.load(url);
			mailsPane.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void logoutAction(ActionEvent event){		
		try {			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void exitAction(ActionEvent event){
		Platform.exit();
		System.exit(0);
	}	
	
	private void setIcons() {
		contatosIcon.setImage(new Image(getClass().getResourceAsStream("img/employee.png")));
		inboxIcon.setImage(new Image(getClass().getResourceAsStream("img/download.png")));
		favoritosIcon.setImage(new Image(getClass().getResourceAsStream("img/star.png")));
		configuracoesIcon.setImage(new Image(getClass().getResourceAsStream("img/cogwheel.png")));
	}

}

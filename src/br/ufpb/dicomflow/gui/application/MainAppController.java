package br.ufpb.dicomflow.gui.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.ufpb.dicomflow.gui.business.AuthenticationProcessor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MainAppController implements Initializable  {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
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


}

package br.ufpb.dicomflow.gui.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.ufpb.dicomflow.gui.application.validation.ValidationFields;
import br.ufpb.dicomflow.gui.dao.GenericDao;
import br.ufpb.dicomflow.gui.dao.bean.AuthenticationBean;
import br.ufpb.dicomflow.utils.CryptographyUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController implements Initializable{

	@FXML
    private Text loginErrors;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    private Button loginButton;

    @FXML
    private Button configButton;

    @FXML
    protected void loginAction(ActionEvent event) {

    	if(!validate()){
    		return;
    	}

    	AuthenticationBean authDB = (AuthenticationBean) GenericDao.select(AuthenticationBean.class, "mail", loginField.getText());
    	if(authDB == null){
    		loginErrors.setText("E-mail não configurado ou senha inválida.");
    		return;
    	}else{
    		String encryptedPassword = CryptographyUtil.encriptSHA256(passwordField.getText());
        	if(encryptedPassword == null){
        		loginErrors.setText("Não foi possível salvar as configurações.");
        		return;
        	}

        	if(!authDB.getPassword().equals(encryptedPassword)){
        		loginErrors.setText("E-mail não configurado ou senha inválida.");
        		return;
        	}
    	}

    	try {
    		SceneLoader.getSceneLoader().loadMainScene();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    private boolean validate() {

    	if(!ValidationFields.checkEmptyFields(loginField, passwordField)){
			return false;
		}

    	if(!ValidationFields.checkMailFormat(loginField)){
    		return false;
    	}


    	return true;

	}


    @FXML
    protected void configAction(ActionEvent event) {

    	try {
    		SceneLoader.getSceneLoader().loadConfigScene();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}

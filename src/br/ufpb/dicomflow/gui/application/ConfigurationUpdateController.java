package br.ufpb.dicomflow.gui.application;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.ufpb.dicomflow.gui.application.validation.ValidationFields;
import br.ufpb.dicomflow.gui.business.AuthenticationProcessor;
import br.ufpb.dicomflow.gui.business.ConfigurationProcessor;
import br.ufpb.dicomflow.gui.dao.bean.AuthenticationBean;
import br.ufpb.dicomflow.gui.exception.ConfigurationException;
import br.ufpb.dicomflow.gui.exception.LoginException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

public class ConfigurationUpdateController implements Initializable{


	@FXML
    private Text configAlerts;

	@FXML
    ComboBox<String> typeField;

	@FXML
    private TextField mailField;


    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField rePasswordField;

    @FXML
    private Label aliasLabel;

    @FXML
    private TextField aliasField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField departamentField;

    @FXML
    private TextField organizationField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField stateField;

    @FXML
    private Label countryLabel;

    @FXML
    private TextField countryField;

    @FXML
    private Label certFolderLabel;

    @FXML
    private TextField certFolderField;

    @FXML
    private Label folderLabel;

    @FXML
    private Label newPasswordLabel;

    @FXML
    private TextField folderField;

    @FXML
    protected void updateAction(ActionEvent event) {

    	if(!validate()){
    		return;
    	}

    	//no login, no update
    	try {
			AuthenticationProcessor.getInstance().login(mailField.getText(), passwordField.getText());
		} catch (LoginException e) {
			configAlerts.setText("A senha atual está incorreta.");
			return;
		}

    	try {

    		String password = !ValidationFields.checkEmptyFields(newPasswordField) ?  newPasswordField.getText() : passwordField.getText();
			ConfigurationProcessor.getInstance().updateConfiguration(mailField.getText(), password, typeField.getValue(), folderField.getText(), nameField.getText(), departamentField.getText(), organizationField.getText(), locationField.getText(), stateField.getText(), countryField.getText());

			AuthenticationProcessor.getInstance().login(mailField.getText(), password);

    	} catch (ConfigurationException e) {
			configAlerts.setText(e.getMessage());
			return;
		} catch (LoginException e) {
			configAlerts.setText("Não foi possível completar a atualização");
			return;
		}

    	configAlerts.setText("Configurações atualizadas com sucesso.");

    }

    private boolean validate() {

    	if(!ValidationFields.checkRqueridFields(passwordField, folderField, nameField, departamentField, organizationField, locationField, stateField, countryField)){
			return false;
		}

    	if(!ValidationFields.checkCountryFormat(countryField)){
    		return false;
    	}

    	if(!ValidationFields.checkEmptyFields(newPasswordField)){
	    	if(!ValidationFields.checkPasswordEquals(newPasswordField, rePasswordField)){
	    		return false;
	    	}
    	}

    	return true;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		loadConfiguration();

		//initialize comboBox
		List<String> configurationTitles = ConfigurationProcessor.getInstance().getConfigurationTitles();
		typeField.getItems().addAll(configurationTitles);

		SceneLoader.getInstance().installTooltip(countryLabel, "Informe a sigla do país. Ex.: Brasil = BR");
		SceneLoader.getInstance().installInfoGraphic(countryLabel);

		SceneLoader.getInstance().installTooltip(newPasswordLabel, "Informe para alterar a senha.");
		SceneLoader.getInstance().installInfoGraphic(newPasswordLabel);


		SceneLoader.getInstance().installTooltip(folderLabel, "Informe o diretório onde serão armazenados os exames.");
		SceneLoader.getInstance().installInfoGraphic(folderLabel);

		folderField.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory =
                        directoryChooser.showDialog(Main.getpStage());

                if(selectedDirectory != null){
                	folderField.setText(selectedDirectory.getAbsolutePath());
                }
            }

		});

	}

	private void loadConfiguration() {
		AuthenticationBean loggedAuthentication = ApplicationSession.getInstance().getLoggedUser();
		if(loggedAuthentication!= null){
			typeField.setValue(loggedAuthentication.getConfiguration().getTitle());
			mailField.setText(loggedAuthentication.getMail());
			folderField.setText(loggedAuthentication.getFolder());
			nameField.setText(loggedAuthentication.getName());
			departamentField.setText(loggedAuthentication.getDepartament());
			organizationField.setText(loggedAuthentication.getOrganization());
			locationField.setText(loggedAuthentication.getLocation());
			stateField.setText(loggedAuthentication.getState());
			countryField.setText(loggedAuthentication.getCountry());
			aliasField.setText(loggedAuthentication.getAlias());
			certFolderField.setText(loggedAuthentication.getCertFolder());
		}
	}



}

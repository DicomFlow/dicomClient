package br.ufpb.dicomflow.gui.application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.ufpb.dicomflow.gui.application.validation.ValidationFields;
import br.ufpb.dicomflow.gui.business.CertificateProcessor;
import br.ufpb.dicomflow.gui.business.ConfigurationProcessor;
import br.ufpb.dicomflow.gui.exception.CertificateException;
import br.ufpb.dicomflow.gui.exception.ConfigurationException;
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

public class ConfigurationController implements Initializable{


	@FXML
    private Text configAlerts;

	@FXML
    ComboBox<String> typeField;

	@FXML
    private TextField mailField;


    @FXML
    private PasswordField passwordField;

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
    private TextField folderField;

    @FXML
    protected void saveAction(ActionEvent event) {

    	if(!validate()){
    		return;
    	}

    	try {
			CertificateProcessor.getInstance().createCertificate(aliasField.getText(), passwordField.getText(), nameField.getText(), departamentField.getText(), organizationField.getText(), locationField.getText(), stateField.getText(), countryField.getText(), certFolderField.getText());
		} catch (CertificateException e) {
			configAlerts.setText(e.getMessage());
			return;
		}

    	try {
			ConfigurationProcessor.getInstance().saveConfiguration(mailField.getText(), passwordField.getText(), typeField.getValue(), folderField.getText(), aliasField.getText(), nameField.getText(), departamentField.getText(), organizationField.getText(), locationField.getText(), stateField.getText(), countryField.getText(), certFolderField.getText());
		} catch (ConfigurationException e) {
			configAlerts.setText(e.getMessage());
			return;
		}

    	configAlerts.setText("Configurações salvas com sucesso.");

    }

    private boolean validate() {

    	if(!ValidationFields.checkRqueridFields(typeField, mailField, passwordField, rePasswordField, aliasField, nameField, departamentField, organizationField, locationField, stateField, countryField, certFolderField, folderField)){
			return false;
		}

    	if(!ValidationFields.checkMailFormat(mailField)){
    		return false;
    	}

    	if(!ValidationFields.checkAliasFormat(aliasField)){
    		return false;
    	}

    	if(!ValidationFields.checkCountryFormat(countryField)){
    		return false;
    	}

    	if(!ValidationFields.checkPasswordEquals(passwordField, rePasswordField)){
    		return false;
    	}

    	return true;

	}

	@FXML
    protected void cancelAction(ActionEvent event) {
    	try {
    		SceneLoader.getInstance().loadLoginScene();
		} catch (IOException e) {
			e.printStackTrace();
		}


    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		List<String> configurationTitles = ConfigurationProcessor.getInstance().getConfigurationTitles();
		typeField.getItems().addAll(configurationTitles);

		SceneLoader.getInstance().installTooltip(aliasLabel, "O identificador do certificado digital que será criado e associado ao seu e-mail. Use apenas letras e números");

		SceneLoader.getInstance().installTooltip(countryLabel, "Informe a sigla do país. Ex.: Brasil = BR");

		SceneLoader.getInstance().installTooltip(certFolderLabel, "Informe o diretório onde sera armazenado o certificado digital.");

		SceneLoader.getInstance().installTooltip(folderLabel, "Informe o diretório onde serão armazenados os exames.");

		certFolderField.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory =
                        directoryChooser.showDialog(Main.getpStage());

                if(selectedDirectory != null){
                	certFolderField.setText(selectedDirectory.getAbsolutePath());
                }
            }

		});

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



}

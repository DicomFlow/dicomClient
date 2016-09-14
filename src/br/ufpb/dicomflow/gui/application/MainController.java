package br.ufpb.dicomflow.gui.application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.ufpb.dicomflow.gui.business.ProcessadorAutenticacao;
import br.ufpb.dicomflow.gui.exception.LoginException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.AccordionBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainController implements Initializable {
	
	@FXML
    private Text loginErrors;    
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private TextField loginField;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Button novosExamesButton;
    
    @FXML
    private BorderPane mainPane;
    
    @FXML
    private Accordion menuLateral;
    
    @FXML
    private Accordion studiesAccordion;
            
    @FXML 
    protected void handleEntrarButtonAction(ActionEvent event) {        
        try {
        	ProcessadorAutenticacao.validate(loginField.getText(), passwordField.getText());
			showMainScreen(event);
		} catch (LoginException e) {
			loginErrors.setText(e.getMessage());
		} catch (IOException e) {
			loginErrors.setText("Não foi possível carregar a aplicação");
			e.printStackTrace();
		}
    }
    
    
    public void showMainScreen(ActionEvent event) throws IOException {
        Stage stage = Main.getpStage();
        stage.setTitle("DicomFlow Client");
        BorderPane myPane = null;
        myPane = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(myPane);
        stage.setScene(scene);        
        
        stage.show();
     }
    
    public void showStudies(ActionEvent event) throws IOException {
    	Scene scene = novosExamesButton.getScene();
        Stage stage = (Stage) scene.getWindow();
        

        BorderPane myPane = null;
        myPane = FXMLLoader.load(getClass().getResource("main.fxml"));        
        
        studiesAccordion = new Accordion();               
        studiesAccordion.getPanes().addAll(getStudies());                                     
        myPane.setCenter(studiesAccordion);
        
        scene.setRoot(myPane);        
        stage.show();
     }
    
    public List<TitledPane> getStudies() {  
    	ArrayList<TitledPane> result = new ArrayList<TitledPane>();
    	
    	TitledPane study1 = new TitledPane();
    	study1.setText("(12345) João da Silva - Data de Nascimento: 03/10/1960");
    	
    	TitledPane t1 = new TitledPane("Estudos de: Hospital Universitário Lauro Wanderley", study1);
    	result.add(t1);
    	
    	return result;
    }

    
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub		
	}
	
	
	
    
}

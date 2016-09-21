package br.ufpb.dicomflow.gui.application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.ufpb.dicomflow.gui.business.ProcessadorAutenticacao;
import br.ufpb.dicomflow.gui.business.ProcessadorBuscaMensagens;
import br.ufpb.dicomflow.gui.exception.LoginException;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;
import br.ufpb.dicomflow.integrationAPI.message.xml.SharingPut;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
    private Accordion newMessagesAccordion;
    
    @FXML ComboBox<String> ordenarPor;
    
    @FXML
    private ImageView iconeNovosExames;
    
    @FXML
    private ImageView iconeExames;
    
    @FXML
    private ImageView iconeLixeira;
    
    @FXML 
    private Hyperlink novosExames;
            
    @FXML 
    protected void handleEntrarButtonAction(ActionEvent event) {        
        try {
        	ProcessadorAutenticacao.validate(loginField.getText(), passwordField.getText());
        	initFields();
			showMainScreen(event);
		} catch (LoginException e) {
			loginErrors.setText(e.getMessage());
		} catch (IOException e) {
			loginErrors.setText("Não foi possível carregar a aplicação");
			e.printStackTrace();
		}
    }
    
    private void initFields() {    	
    	ordenarPor = new ComboBox<String>();
    	ordenarPor.getItems().addAll("Data", "Nome");    	
    	
    }
    
    
    public void showMainScreen(ActionEvent event) throws IOException {
        Stage stage = Main.getpStage();
        stage.setTitle("DicomFlow Client");
        BorderPane myPane = null;
        myPane = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(myPane);
        stage.setScene(scene);        
        
        initFields();
        stage.show();
     }
    
    public void showStudies(ActionEvent event) throws IOException {
    	Scene scene = Main.getpStage().getScene();
        Stage stage = (Stage) scene.getWindow();
        

        BorderPane myPane = null;
        myPane = FXMLLoader.load(getClass().getResource("main.fxml"));        
        
        studiesAccordion = getStudiesList();       
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
    
    public Accordion getStudiesList() {
    	Accordion root = new Accordion();
    	
    	Accordion listaPacientes1 = new Accordion ();
    	TitledPane paciente1 = new TitledPane();
    	paciente1.setText("Paciente: João da Silva");
    	listaPacientes1.getPanes().add(paciente1);
    	
    	Accordion listaPacientes2 = new Accordion();
    	TitledPane paciente2 = new TitledPane();
    	paciente2.setText("Paciente: Maria Andrade de Souza");
    	listaPacientes2.getPanes().add(paciente2);
    	TitledPane paciente3 = new TitledPane();
    	paciente3.setText("Paciente: Neto Lucena");
    	listaPacientes2.getPanes().add(paciente3);
    	
    	Accordion listaPacientes3 = new Accordion();
    	TitledPane paciente4 = new TitledPane();
    	paciente4.setText("Paciente: José Antônio Medeiros");
    	listaPacientes3.getPanes().add(paciente4);
    	
    	TitledPane hospital1 = new TitledPane("Estudos do Hospital Universitário Lauro Wanderley (HULW)", listaPacientes1);
    	TitledPane hospital2 = new TitledPane("Estudos do Hospital Napoleão Laureano", listaPacientes2);
    	TitledPane hospital3 = new TitledPane("Estudos do Hospital da UNIMED", listaPacientes3);
    	
    	root.getPanes().add(hospital1);
    	root.getPanes().add(hospital2);
    	root.getPanes().add(hospital3);
    	return root;
    	          
    }
    
    public void showNewMessages(ActionEvent event) throws Exception {
    	Scene scene = Main.getpStage().getScene();
        Stage stage = (Stage) scene.getWindow();
        

        BorderPane myPane = null;
        myPane = FXMLLoader.load(getClass().getResource("main.fxml"));        
        
        List<SharingPut> newMessages = (List<SharingPut>) ProcessadorBuscaMensagens.receberMensagens();                
        newMessagesAccordion = getNewMessagesList(newMessages);        
        
        myPane.setCenter(newMessagesAccordion);
        
        scene.setRoot(myPane);        
        stage.show();
     }
    
    public Accordion getNewMessagesList(List<SharingPut> newMessages) {
    	Accordion root = new Accordion();
    	
    	for (SharingPut message: newMessages) {
    		Accordion item = new Accordion();    		
    		for (br.ufpb.dicomflow.integrationAPI.message.xml.URL url: message.getUrls()) {
    			TitledPane tp = new TitledPane();
    	    	tp.setText("URL: " +  url.getValue());
    	    	item.getPanes().add(tp);
        	}
    		TitledPane messageTP = new TitledPane("Mensagem ID: " + message.getMessageID(), item);
    		root.getPanes().add(messageTP);
    	}    	    
    	
    	return root;
    }

    
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub		
	}
	
	
	
    
}

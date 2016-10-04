package br.ufpb.dicomflow.gui.application;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import br.ufpb.dicomflow.gui.business.ProcessadorAutenticacao;
import br.ufpb.dicomflow.gui.business.ProcessadorBuscaMensagens;
import br.ufpb.dicomflow.gui.exception.LoginException;
import br.ufpb.dicomflow.integrationAPI.message.xml.Patient;
import br.ufpb.dicomflow.integrationAPI.message.xml.RequestPut;
import br.ufpb.dicomflow.integrationAPI.message.xml.Serie;
import br.ufpb.dicomflow.integrationAPI.message.xml.Study;
import br.ufpb.dicomflow.integrationAPI.message.xml.URL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    TreeView<String> studiesTreeView;
    
    @FXML
    TreeView<String> newStudiesTreeView;
        
    @FXML ComboBox<String> ordenarPor;
    
    @FXML
    private ImageView iconeNovosExames;
    
    @FXML
    private ImageView iconeExames;
    
    @FXML
    private ImageView iconeLixeira;
       
    @FXML 
    MenuItem atualizarExames;
	
    private Node rootIcon = new ImageView(new Image(getClass().getResourceAsStream("reading_16.png")));		
            
    
    @FXML 
    protected void handleEntrarButtonAction(ActionEvent event) {        
        try {
        	SessaoAplicacao.getInstance().loadProperties();
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
        
        stage.hide();
        initFields();
        stage.show();
                
     }
    
    public void AtualizarExames(ActionEvent event) throws Exception {	
    	SessaoAplicacao.getInstance().setNewMessages(ProcessadorBuscaMensagens.receberMensagens());
    	showNewMessages(event);    	
     }
    
    
    public void showStudies(ActionEvent event) throws IOException {
    	Scene scene = Main.getpStage().getScene();
        Stage stage = (Stage) scene.getWindow();        

        BorderPane myPane = null;
        myPane = FXMLLoader.load(getClass().getResource("main.fxml"));                
        
        studiesTreeView = getStudiesTreeList();
        myPane.setCenter(studiesTreeView);
        
        scene.setRoot(myPane);        
        stage.show();
     }    
    
    
    public Accordion getStudiesList() {
    	Accordion root = new Accordion();
    	
    	Accordion listaPacientes1 = new Accordion();
    	
    	TitledPane exame1 = new TitledPane();
    	exame1.setText("Exame: Estudo de CT");    	
    	TitledPane paciente1 = new TitledPane("Paciente: João da Silva", exame1);
    	ContextMenu ctxMenu = getStudyContextMenu(exame1);
		exame1.setContextMenu(ctxMenu);
    	
		listaPacientes1.getPanes().add(paciente1);    	 
    			
    	
    	Accordion listaPacientes2 = new Accordion();
    	TitledPane paciente2 = new TitledPane();
    	paciente2.setText("Paciente: Maria Andrade de Souza");
    	ContextMenu ctxMenu2 = getStudyContextMenu(paciente2);
		paciente2.setContextMenu(ctxMenu2);
		
    	listaPacientes2.getPanes().add(paciente2);
    	TitledPane paciente3 = new TitledPane();
    	paciente3.setText("Paciente: Neto Lucena");
    	ContextMenu ctxMenu3 = getStudyContextMenu(paciente3);
		paciente3.setContextMenu(ctxMenu3);
    	listaPacientes2.getPanes().add(paciente3);
    	
    	
    	Accordion listaPacientes3 = new Accordion();
    	TitledPane paciente4 = new TitledPane();
    	paciente4.setText("Paciente: José Antônio Medeiros");
    	ContextMenu ctxMenu4 = getStudyContextMenu(paciente4);
		paciente4.setContextMenu(ctxMenu4);
    	listaPacientes3.getPanes().add(paciente4);
    	
    	
    	TitledPane hospital1 = new TitledPane("Estudos do Hospital Universitário Lauro Wanderley (HULW)", listaPacientes1);
    	TitledPane hospital2 = new TitledPane("Estudos do Hospital Napoleão Laureano", listaPacientes2);
    	TitledPane hospital3 = new TitledPane("Estudos do Hospital da UNIMED", listaPacientes3);
    	
    	root.getPanes().add(hospital1);
    	root.getPanes().add(hospital2);
    	root.getPanes().add(hospital3);
    	return root;
    	          
    }
    
    public  TreeView<String> getStudiesTreeList() {        	
    	 TreeItem<String> rootItem = new TreeItem<> ("Inbox", rootIcon);
         rootItem.setExpanded(true);
         for (int i = 1; i < 6; i++) {
             TreeItem<String> item = new TreeItem<> ("Message" + i);            
             rootItem.getChildren().add(item);
         }        
         TreeView<String> tree = new TreeView<> (rootItem);
         return tree;
    	          
    }
    
    public ContextMenu getStudyContextMenu(Node node) {
    	ContextMenu contextMenu = new ContextMenu();
		contextMenu.setOnShowing(new EventHandler<WindowEvent>() {
		    public void handle(WindowEvent e) {
		        System.out.println("showing");
		    }
		});
		MenuItem item1 = new MenuItem("Abrir Exame");
		item1.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        System.out.println("Abrir Exame");
		    }
		});
		MenuItem item2 = new MenuItem("Emitir Laudo");
		item2.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        System.out.println("Emitir Laudo");
		    }
		});
		contextMenu.getItems().addAll(item1, item2);
		
		return contextMenu;
    }
    
    
    public ContextMenu getNewStudiesContextMenu(Node node) {
    	ContextMenu contextMenu = new ContextMenu();
		contextMenu.setOnShowing(new EventHandler<WindowEvent>() {
		    public void handle(WindowEvent e) {
		        System.out.println("showing");
		    }
		});
		MenuItem item1 = new MenuItem("Download");
		item1.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        System.out.println("Abrir Exame");
		    }
		});		
		contextMenu.getItems().addAll(item1);
		
		return contextMenu;
    }
    
    
    public void showNewMessages(ActionEvent event) throws Exception {
    	Scene scene = Main.getpStage().getScene();
        Stage stage = (Stage) scene.getWindow();
        

        BorderPane myPane = null;
        myPane = FXMLLoader.load(getClass().getResource("main.fxml"));        
        
        List<RequestPut> newMessages = SessaoAplicacao.getInstance().getNewMessages();              
        newStudiesTreeView = getNewMessagesTreeList(newMessages);        
        
        myPane.setCenter(newStudiesTreeView);
        
        scene.setRoot(myPane);        
        stage.show();
     }
    
    public TreeView<String> getNewMessagesTreeList(List<RequestPut> newMessages) {
    	TreeItem<String> root = new TreeItem<> ("Novas Mensagens", rootIcon);
        root.setExpanded(true);             
                
        for (RequestPut requestPut: newMessages) {
        	TreeItem<String> requestPutList = new TreeItem<> ("Mensagem: " + requestPut.getMessageID(), new ImageView(new Image(getClass().getResourceAsStream("mail_16.png"))));        	
        	URL url = requestPut.getUrl();
        	
        	for (Patient patient: url.getPatient()) {
        		TreeItem<String> patientList = new TreeItem<> ("Paciente: " + patient.getName(), new ImageView(new Image(getClass().getResourceAsStream("avatar_16.png"))));
        		for (Study study: patient.getStudy()) {
        			TreeItem<String> studyList = new TreeItem<> ("Estudo: " + study.getDescription(), new ImageView(new Image(getClass().getResourceAsStream("test_16.png"))));
        			for (Serie serie: study.getSerie()) {
        				TreeItem<String> serieList = new TreeItem<> ("Série: " + serie.getDescription(), new ImageView(new Image(getClass().getResourceAsStream("tornado_16.png"))));
        				
        				studyList.getChildren().add(serieList);
        			}
        			patientList.getChildren().add(studyList);
        		}
        		requestPutList.getChildren().add(patientList);
        	}
        	root.getChildren().add(requestPutList);
        }
        
        TreeView<String> tree = new TreeView<>(root);       
        return tree;              	    	    		   
    }

    
	public void initialize(java.net.URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub		
	}

			    
}

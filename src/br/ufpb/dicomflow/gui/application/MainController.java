package br.ufpb.dicomflow.gui.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;

import br.ufpb.dicomflow.gui.business.MessageProcessor;
import br.ufpb.dicomflow.gui.business.DownloadProcessor;
import br.ufpb.dicomflow.gui.components.ArquivosExame;
import br.ufpb.dicomflow.gui.components.CustomTreeItem;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainController implements Initializable {

    @FXML
    private Button novosExamesButton;

    @FXML
    private BorderPane mainPane;

    @FXML
    private Accordion menuLateral;

    @FXML
    TreeView<CustomTreeItem> localStudiesTreeView;

    @FXML
    TreeView<CustomTreeItem> newStudiesTreeView;

    @FXML
    ComboBox<String> ordenarPor;

    @FXML
    private ImageView iconeNovosExames;

    @FXML
    private ImageView iconeExames;

    @FXML
    private ImageView iconeLixeira;

    @FXML
    MenuItem atualizarExames;

    private Node rootIcon = new ImageView(new Image(getClass().getResourceAsStream("img/reading_16.png")));

    final FileChooser fileChooser = new FileChooser();

    public void atualizarExames(ActionEvent event) throws Exception {
    	SessaoAplicacao.getInstance().setNewMessages(MessageProcessor.receberMensagens());
    	showNewStudies(event);
     }


    public void showStudies(ActionEvent event) throws IOException {
    	Scene scene = Main.getpStage().getScene();
        Stage stage = (Stage) scene.getWindow();

        BorderPane myPane = null;
        myPane = FXMLLoader.load(getClass().getResource("main.fxml"));

        List<RequestPut> localMessages = SessaoAplicacao.getInstance().getLocalMessages();
        localStudiesTreeView = getLocalMessagesTreeList(localMessages);

        myPane.setCenter(localStudiesTreeView);

        scene.setRoot(myPane);
        stage.show();
     }


    public void showNewStudies(ActionEvent event) throws Exception {
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


    public TreeView<CustomTreeItem> getNewMessagesTreeList(List<RequestPut> messages) {
    	TreeItem<CustomTreeItem> root = new TreeItem<CustomTreeItem>(new CustomTreeItem( new Label("Novas Mensagens"), rootIcon ));
        root.setExpanded(true);

        for (RequestPut requestPut: messages) {
        	Button messageButton = new Button();
        	messageButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/cloud-computing.png"))));
        	messageButton.setTooltip(new Tooltip("Download"));
        	messageButton.setOnAction(new EventHandler<ActionEvent>() {
        	    @Override public void handle(ActionEvent e) {
        	       try {
					String filePath = DownloadProcessor.downloadExames(requestPut);
					HashMap<String, ArquivosExame> arquivosExamesMap = SessaoAplicacao.getInstance().getArquivosExameMap();
					ArquivosExame arquivosExame = arquivosExamesMap.get(requestPut.getMessageID());
					if (arquivosExame == null) {
						arquivosExame = new ArquivosExame();
					}
					arquivosExame.setExamesPath(filePath);
					arquivosExamesMap.put(requestPut.getMessageID(), arquivosExame);
					SessaoAplicacao.getInstance().getLocalMessages().add(requestPut);
				} catch (LoginException e1) {
					e1.printStackTrace();
				}
        	    }
        	});
        	TreeItem<CustomTreeItem> requestPutList = new TreeItem<CustomTreeItem>(new CustomTreeItem( new Label("Mensagem: " + requestPut.getMessageID()), messageButton, new ImageView(new Image(getClass().getResourceAsStream("img/mail_16.png")))) );
        	URL url = requestPut.getUrl();

        	for (Patient patient: url.getPatient()) {
        		TreeItem<CustomTreeItem> patientList = new TreeItem<CustomTreeItem>(new CustomTreeItem( new Label( "Paciente: " + patient.getName() ), new ImageView(new Image(getClass().getResourceAsStream("img/avatar_16.png")))) );
        		for (Study study: patient.getStudy()) {
        			TreeItem<CustomTreeItem> studyList = new TreeItem<CustomTreeItem>(new CustomTreeItem( new Label( "Estudo: " + study.getDescription() ), new ImageView(new Image(getClass().getResourceAsStream("img/test_16.png")))) );
        			for (Serie serie: study.getSerie()) {
        				TreeItem<CustomTreeItem> serieList = new TreeItem<CustomTreeItem>(new CustomTreeItem( new Label( "S�rie: " + serie.getDescription() ), new ImageView(new Image(getClass().getResourceAsStream("img/tornado_16.png")))) );
        				studyList.getChildren().add(serieList);
        			}
        			patientList.getChildren().add(studyList);
        		}
        		requestPutList.getChildren().add(patientList);
        	}
        	root.getChildren().add(requestPutList);
        }

        TreeView<CustomTreeItem> tree = new TreeView<CustomTreeItem>();
        tree.setRoot(root);
        return tree;
    }


    public TreeView<CustomTreeItem> getLocalMessagesTreeList(List<RequestPut> messages) {
    	TreeItem<CustomTreeItem> root = new TreeItem<CustomTreeItem>(new CustomTreeItem( new Label("Mensagens Locais"), rootIcon ));
        root.setExpanded(true);

        for (RequestPut requestPut: messages) {
        	Button laudoButton = new Button();
        	laudoButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/attachment.png"))));
        	laudoButton.setTooltip(new Tooltip("Anexar Laudo"));
        	laudoButton.setOnAction(new EventHandler<ActionEvent>() {
        	    @Override public void handle(ActionEvent e) {
        	       try {
        	    	   Stage stage = Main.getpStage();
        	    	   File laudo = fileChooser.showOpenDialog(stage);
                           if (laudo != null) {
                        	   HashMap<String, ArquivosExame> arquivosExamesMap = SessaoAplicacao.getInstance().getArquivosExameMap();
           						ArquivosExame arquivosExame = arquivosExamesMap.get(requestPut.getMessageID());
           						if (arquivosExame == null) {
           							arquivosExame = new ArquivosExame();
           						}
           						arquivosExame.setLaudo(laudo.getAbsolutePath());
           						arquivosExamesMap.put(requestPut.getMessageID(), arquivosExame);
                           }
        	       } catch (Exception e1) {
        	    	   e1.printStackTrace();
        	       }
        	    }
        	});
        	Button envioRespostaButton = new Button();
        	envioRespostaButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("img/cloud-computing-1.png"))));
        	envioRespostaButton.setTooltip(new Tooltip("Enviar Resposta"));
        	envioRespostaButton.setOnAction(new EventHandler<ActionEvent>() {
        	    @Override public void handle(ActionEvent e) {
        	       try {
        	    	   HashMap<String, ArquivosExame> arquivosExamesMap = SessaoAplicacao.getInstance().getArquivosExameMap();
   						ArquivosExame arquivosExame = arquivosExamesMap.get(requestPut.getMessageID());
   						byte[] laudo = null;
   						if (arquivosExame != null) {
   							if (arquivosExame.getLaudo() != null) {
   								File file = new File(arquivosExame.getLaudo());
   								laudo = IOUtils.toByteArray(new FileInputStream(file));
   							}
   						}
   						MessageProcessor.enviarExamesLaudos(requestPut, requestPut.getMessageID() + ".zip", laudo);
        	       } catch (Exception e1) {
        	    	   e1.printStackTrace();
        	       }
        	    }
        	});
        	TreeItem<CustomTreeItem> requestPutList = new TreeItem<CustomTreeItem>(new CustomTreeItem( new Label("Mensagem: " + requestPut.getMessageID()), laudoButton, envioRespostaButton, new ImageView(new Image(getClass().getResourceAsStream("img/mail_16.png")))) );
        	URL url = requestPut.getUrl();

        	for (Patient patient: url.getPatient()) {
        		TreeItem<CustomTreeItem> patientList = new TreeItem<CustomTreeItem>(new CustomTreeItem( new Label( "Paciente: " + patient.getName() ), new ImageView(new Image(getClass().getResourceAsStream("img/avatar_16.png")))) );
        		for (Study study: patient.getStudy()) {
        			TreeItem<CustomTreeItem> studyList = new TreeItem<CustomTreeItem>(new CustomTreeItem( new Label( "Estudo: " + study.getDescription() ), new ImageView(new Image(getClass().getResourceAsStream("img/test_16.png")))) );
        			for (Serie serie: study.getSerie()) {
        				TreeItem<CustomTreeItem> serieList = new TreeItem<CustomTreeItem>(new CustomTreeItem( new Label( "S�rie: " + serie.getDescription() ), new ImageView(new Image(getClass().getResourceAsStream("img/tornado_16.png")))) );
        				studyList.getChildren().add(serieList);
        			}
        			patientList.getChildren().add(studyList);
        		}
        		requestPutList.getChildren().add(patientList);
        	}
        	root.getChildren().add(requestPutList);
        }

        TreeView<CustomTreeItem> tree = new TreeView<CustomTreeItem>();
        tree.setRoot(root);
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

	public void initialize(java.net.URL location, ResourceBundle resources) {
    	ordenarPor.getItems().addAll("Data", "Nome");
	}


}

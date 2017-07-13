package br.ufpb.dicomflow.gui.application;

import br.ufpb.dicomflow.gui.business.AuthenticationProcessor;
import br.ufpb.dicomflow.gui.business.ConfigurationProcessor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private static Stage pStage;

    @Override
    public void start(Stage stage) throws Exception {

    	ConfigurationProcessor.getInstance().init();

    	Scene scene = null;

    	if(AuthenticationProcessor.getInstance().loadLoggedUser()){

    		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());

    	}else{

	        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
	        scene = new Scene(root, 300, 275);
	        scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());

    	}


    	stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        setpStage(stage);
        stage.setTitle("DicomFlow Client");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

	public static Stage getpStage() {
		return pStage;
	}

	public static void setpStage(Stage pStage) {
		Main.pStage = pStage;
	}

}

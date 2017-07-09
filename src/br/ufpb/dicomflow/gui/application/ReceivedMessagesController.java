package br.ufpb.dicomflow.gui.application;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import br.ufpb.dicomflow.gui.business.ConfigurationProcessor;
import br.ufpb.dicomflow.gui.business.MessageProcessor;
import br.ufpb.dicomflow.gui.components.MessageTreeItem;
import br.ufpb.dicomflow.gui.dao.bean.AuthenticationBean;
import br.ufpb.dicomflow.gui.dao.bean.MessageBean;
import br.ufpb.dicomflow.gui.exception.LoginException;
import br.ufpb.dicomflow.integrationAPI.mail.MessageIF;
import br.ufpb.dicomflow.integrationAPI.message.xml.Patient;
import br.ufpb.dicomflow.integrationAPI.message.xml.RequestPut;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;
import br.ufpb.dicomflow.integrationAPI.message.xml.Study;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ReceivedMessagesController implements Initializable {

	private static final double SPACING = 5;
	@FXML
	private VBox messageVBox;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		messageVBox.setSpacing(SPACING);

		try {

			List<MessageBean> messages = receiveMessages();

			for (MessageBean messageBean: messages) {

				TreeView<MessageTreeItem> treeView = new TreeView<MessageTreeItem>();

				TreeItem<MessageTreeItem> messageTreeItem = createMessageTreeItem(messageBean);

				MessageIF message  = messageBean.getValues();

				ServiceIF service = message.getService();
				if(service instanceof RequestPut){

					TreeItem<MessageTreeItem> requestPutTreeItem = requestPutTreeItem(service);
		        	messageTreeItem.getChildren().add(requestPutTreeItem);
				}
				treeView.setRoot(messageTreeItem);
				messageVBox.getChildren().add(treeView);

	        }


		} catch (LoginException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}



	}

	public TreeItem<MessageTreeItem> requestPutTreeItem(ServiceIF service) {
		RequestPut requestPut = (RequestPut) service;


		ImageView downloadIcon = new ImageView(new Image(getClass().getResourceAsStream("img/cloud-computing.png")));
		Tooltip download = new Tooltip("Download");
		SceneLoader.getSceneLoader().hackTooltipStartTiming(download);
		Tooltip.install(downloadIcon,download);

		ImageView replyIcon = new ImageView(new Image(getClass().getResourceAsStream("img/cloud-computing-1.png")));
		Tooltip reply = new Tooltip("Enviar Laudo");
		SceneLoader.getSceneLoader().hackTooltipStartTiming(reply);
		Tooltip.install(replyIcon,reply);

		TreeItem<MessageTreeItem> requestPutTreeItem = new TreeItem<MessageTreeItem>(new MessageTreeItem( new Label("Request-Put: " + requestPut.getMessageID()), downloadIcon, replyIcon));

		br.ufpb.dicomflow.integrationAPI.message.xml.URL url = requestPut.getUrl();

		for (Patient patient: url.getPatient()) {
			TreeItem<MessageTreeItem> patientList = new TreeItem<MessageTreeItem>(new MessageTreeItem( new Label("Paciente: " + patient.getName()), new Label( "Nascicmento: " + patient.getBirthdate() ), new Label( "Gênero: " + patient.getGender() ),new ImageView(new Image(getClass().getResourceAsStream("img/avatar_16.png")))) );
			for (Study study: patient.getStudy()) {
				TreeItem<MessageTreeItem> studyList = new TreeItem<MessageTreeItem>(new MessageTreeItem( new Label( "Estudo: " + study.getDescription() ), new ImageView(new Image(getClass().getResourceAsStream("img/test_16.png")))) );
				patientList.getChildren().add(studyList);
			}
			requestPutTreeItem.getChildren().add(patientList);
		}
		return requestPutTreeItem;
	}

	public TreeItem<MessageTreeItem> createMessageTreeItem(MessageBean messageBean) {


		String image = messageBean.getStatus() == MessageBean.UNREAD ? "img/mail_16.png" : "img/reading_16.png";
		String tooltipText = messageBean.getStatus() == MessageBean.UNREAD ? "Não Lida" : "Lida";
		ImageView statusIcon = new ImageView(new Image(getClass().getResourceAsStream(image)));
		Tooltip status = new Tooltip(tooltipText);
		SceneLoader.getSceneLoader().hackTooltipStartTiming(status);
		Tooltip.install(statusIcon,status);


		TreeItem<MessageTreeItem> messageTreeItem  = new TreeItem<MessageTreeItem>(new MessageTreeItem( new Label(messageBean.getAFrom()), new Label(messageBean.getSubject()),statusIcon));
		return messageTreeItem;
	}

	public List<MessageBean> receiveMessages() throws LoginException {

		AuthenticationBean loggedUser = ApplicationSession.getInstance().getLoggedUser();
		Properties properties = ConfigurationProcessor.getProcessadorConfiguracao().getProperties(loggedUser.getConfiguration());

		List<MessageBean> messages = MessageProcessor.receiveMessages(loggedUser, properties);
		return messages;

	}

}

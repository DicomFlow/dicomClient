package br.ufpb.dicomflow.gui.application;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import br.ufpb.dicomflow.integrationAPI.message.xml.RequestPut;

public class SessaoAplicacao {

	private static SessaoAplicacao instance = null;		
	
//	private Usuario usuarioLogado;	
//	private Log log = LogFactory.getLog("RestauranteUniversitarioDesktop");	
	List<RequestPut> newMessages = new ArrayList<RequestPut>();

	//Arquivo .properties
	private Properties properties;
	public static final String CONFIG_FILE_PATH = "conf/dicomflow.properties";
	
	protected SessaoAplicacao() {		
	}

	public static SessaoAplicacao getInstance() {
		if (instance == null) {
			instance = new SessaoAplicacao();
		}
		return instance;
	}
	
	public void loadProperties(){
		String path = CONFIG_FILE_PATH;
		try {			
			properties = new Properties();
			properties.load(new FileInputStream(path));
		} catch (Exception e) {
			//TODO - Log exception
			e.printStackTrace();
		}
	}
	
	
	public String getProperty(String property) throws Exception {
		if (properties.getProperty(property) == null || properties.getProperty(property).equals("")) {
			throw new Exception(property + " not found.");
		}
		return properties.getProperty(property);
	}

	public List<RequestPut> getNewMessages() {
		return newMessages;
	}

	public void setNewMessages(List<RequestPut> newMessages) {
		this.newMessages = newMessages;
	}	
	
	

}

package br.ufpb.dicomflow.gui.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufpb.dicomflow.gui.components.ArquivosExame;
import br.ufpb.dicomflow.gui.dao.bean.AuthenticationBean;
import br.ufpb.dicomflow.integrationAPI.message.xml.RequestPut;

public class ApplicationSession {

	private static ApplicationSession instance = null;

//	private Usuario usuarioLogado;
//	private Log log = LogFactory.getLog("RestauranteUniversitarioDesktop");

	private AuthenticationBean loggedUser;

	List<RequestPut> localMessages = new ArrayList<RequestPut>();
	List<RequestPut> newMessages = new ArrayList<RequestPut>();
	HashMap<String, ArquivosExame> arquivosExameMap = new HashMap<String, ArquivosExame>();

	protected ApplicationSession() {
	}

	public static ApplicationSession getInstance() {
		if (instance == null) {
			instance = new ApplicationSession();
		}
		return instance;
	}


	public AuthenticationBean getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(AuthenticationBean loggedUser) {
		this.loggedUser = loggedUser;
	}





}

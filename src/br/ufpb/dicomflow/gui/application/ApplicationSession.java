package br.ufpb.dicomflow.gui.application;

import br.ufpb.dicomflow.gui.dao.bean.AuthenticationBean;

public class ApplicationSession {

	private static ApplicationSession instance = null;

//	private Usuario usuarioLogado;
//	private Log log = LogFactory.getLog("RestauranteUniversitarioDesktop");

	private AuthenticationBean loggedUser;

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

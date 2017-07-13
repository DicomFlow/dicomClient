package br.ufpb.dicomflow.gui.business;

import br.ufpb.dicomflow.gui.application.ApplicationSession;
import br.ufpb.dicomflow.gui.dao.GenericDao;
import br.ufpb.dicomflow.gui.dao.bean.AuthenticationBean;
import br.ufpb.dicomflow.gui.exception.LoginException;
import br.ufpb.dicomflow.utils.CryptographyUtil;

public class AuthenticationProcessor {

	private static AuthenticationProcessor authenticationProcessor = new AuthenticationProcessor();


	private AuthenticationProcessor(){

	}

	public static AuthenticationProcessor getInstance() {
		return authenticationProcessor;
	}

	public void login(String login, String password, boolean connected) throws LoginException {

		this.login(login, password);

		if(connected){
			keepConnected(connected);
		}



	}

	public boolean loadLoggedUser(){
		AuthenticationBean loggedUser = (AuthenticationBean) GenericDao.select(AuthenticationBean.class, "connected", true);
		if(loggedUser != null) {
			ApplicationSession.getInstance().setLoggedUser(loggedUser);
			return true;
		}
		return false;
	}



	public void login(String login, String password) throws LoginException {

		AuthenticationBean authDB = (AuthenticationBean) GenericDao.select(AuthenticationBean.class, "mail", login);
		if(authDB == null){
			throw new LoginException("E-mail não configurado ou senha incorreta.");
		}else{
			String encryptedPassword = CryptographyUtil.encryptPBEWithMD5AndDES(password);
			if(encryptedPassword == null){
				throw new LoginException("Não foi possível efeutar o login.");
			}

			if(!authDB.getPassword().equals(encryptedPassword)){
				throw new LoginException("E-mail não configurado ou senha incorreta.");
			}
		}

		ApplicationSession.getInstance().setLoggedUser(authDB);

	}

	public void logout(){

		keepConnected(false);
		ApplicationSession.getInstance().setLoggedUser(null);
	}


	public void keepConnected(boolean connected) {
		AuthenticationBean loggedUser = ApplicationSession.getInstance().getLoggedUser();
		loggedUser.setConnected(connected);
		GenericDao.update(loggedUser);
	}

}

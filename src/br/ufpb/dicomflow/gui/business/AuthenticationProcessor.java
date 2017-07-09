package br.ufpb.dicomflow.gui.business;

import br.ufpb.dicomflow.gui.application.ApplicationSession;
import br.ufpb.dicomflow.gui.dao.GenericDao;
import br.ufpb.dicomflow.gui.dao.bean.AuthenticationBean;
import br.ufpb.dicomflow.gui.exception.LoginException;
import br.ufpb.dicomflow.utils.CryptographyUtil;

public class AuthenticationProcessor {

	private static AuthenticationProcessor processadorAutenticacao = new AuthenticationProcessor();


	private AuthenticationProcessor(){

	}

	public static AuthenticationProcessor getProcessadorAutenticacao() {
		return processadorAutenticacao;
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
		ApplicationSession.getInstance().setLoggedUser(null);
	}


}

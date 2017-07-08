package br.ufpb.dicomflow.gui.business;

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

	public void validate(String login, String password) throws LoginException {

	AuthenticationBean authDB = (AuthenticationBean) GenericDao.select(AuthenticationBean.class, "mail", login);
	if(authDB == null){
		throw new LoginException("E-mail n�o configurado ou senha incorreta.");
	}else{
		String encryptedPassword = CryptographyUtil.encriptSHA256(password);
    	if(encryptedPassword == null){
    		throw new LoginException("N�o foi poss�vel efeutar o login.");
    	}

    	if(!authDB.getPassword().equals(encryptedPassword)){
    		throw new LoginException("E-mail n�o configurado ou senha incorreta.");
    	}
	}


}


}

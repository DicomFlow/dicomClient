package br.ufpb.dicomflow.gui.business;

import java.io.File;

import br.ufpb.dicomflow.gui.dao.GenericDao;
import br.ufpb.dicomflow.gui.dao.bean.AuthenticationBean;
import br.ufpb.dicomflow.gui.exception.CertificateException;
import br.ufpb.dicomflow.gui.exception.DicomFlowException;
import br.ufpb.dicomflow.utils.CertificateUtil;

public class CertificateProcessor {

	private final String VALIDITY = "3650"; //ten years

	private static CertificateProcessor certificateProcessor = new CertificateProcessor();


	private CertificateProcessor(){

	}

	public static CertificateProcessor getInstance() {
		return certificateProcessor;
	}

	public void createCertificate(String alias, String password, String name, String departament, String organization, String location, String state, String country, String certFolder) throws CertificateException{

		AuthenticationBean authDB = (AuthenticationBean) GenericDao.select(AuthenticationBean.class, "alias", alias);
    	if(authDB != null){
    		throw new CertificateException("apelido já utilizado.");
    	}

		try {
			CertificateUtil.generateSelfCert(alias, password, certFolder+File.separator+alias, password, name, departament, organization, location, state, country, VALIDITY);
		} catch (DicomFlowException e) {
			e.printStackTrace();
			throw new CertificateException(e.getMessage());
		}


	}

}

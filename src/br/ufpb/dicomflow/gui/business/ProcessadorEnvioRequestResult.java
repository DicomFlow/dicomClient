package br.ufpb.dicomflow.gui.business;

import java.util.Properties;

import br.ufpb.dicomflow.gui.exception.LoginException;
import br.ufpb.dicomflow.integrationAPI.exceptions.ServiceCreationException;
import br.ufpb.dicomflow.integrationAPI.mail.MailAuthenticatorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailContentBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailHeadBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.impl.MailContentBuilderFactory;
import br.ufpb.dicomflow.integrationAPI.mail.impl.MailHeadBuilderFactory;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPAuthenticator;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPSender;
import br.ufpb.dicomflow.integrationAPI.main.ServiceFactory;
import br.ufpb.dicomflow.integrationAPI.main.ServiceProcessor;
import br.ufpb.dicomflow.integrationAPI.message.xml.Data;
import br.ufpb.dicomflow.integrationAPI.message.xml.RequestPut;
import br.ufpb.dicomflow.integrationAPI.message.xml.RequestResult;
import br.ufpb.dicomflow.integrationAPI.message.xml.Result;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public class ProcessadorEnvioRequestResult {
	
	public static void enviarExamesLaudos(RequestPut requestPut, String fileName, byte[] bytes) throws LoginException {
		
		RequestResult requestResult = (RequestResult) ServiceFactory.createService(ServiceIF.REQUEST_RESULT);
		
		Data data = new Data();
		data.setFilename(fileName);
		//byte[] b = fileName.getBytes(); 
		data.setBytes(bytes);
		
		Result result = new Result();		
		result.setData(data);
		result.setOriginalMessageID(requestPut.getMessageID());
		
		requestResult.addResult(result);
						
		try {
			//TODO - parametrizar propriedades
			Properties props = new Properties();
			props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.imap.socketFactory.fallback", "false");
			props.put("mail.store.protocol", "imaps");
			props.put("mail.debug", "false");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.socketFactory.port", "25");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "25");
			props.put("authentication.login", "protocolointegracao@gmail.com");
			props.put("authentication.password", "pr0t0c0l0ap1d1c0m");
			props.put("domain", "gmail.com");
			
			MailAuthenticatorIF smtpAuthenticatorStrategy =  new SMTPAuthenticator("protocolointegracao@gmail.com", "pr0t0c0l0ap1d1c0m");				
			MailHeadBuilderIF mailHeadBuilder =  MailHeadBuilderFactory.createHeadStrategy(MailHeadBuilderIF.SMTP_HEAD_STRATEGY);
			mailHeadBuilder.setFrom("protocolointegracao@gmail.com");
			mailHeadBuilder.setTo("protocolointegracao@gmail.com");
			MailContentBuilderIF mailContentBuilder = MailContentBuilderFactory.createContentStrategy(MailContentBuilderIF.SMTP_SIMPLE_CONTENT_STRATEGY);
			
			ServiceProcessor.sendMessage(requestResult, "protocolointegracao@gmail.com", props, smtpAuthenticatorStrategy, mailHeadBuilder, mailContentBuilder);
		} catch (ServiceCreationException e) {
			e.printStackTrace();
		}
				
	}


}

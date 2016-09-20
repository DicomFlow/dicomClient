package br.ufpb.dicomflow.tests;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import br.ufpb.dicomflow.integrationAPI.conf.IntegrationAPIProperties;
import br.ufpb.dicomflow.integrationAPI.mail.FilterIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailAuthenticatorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailMessageReaderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailServiceExtractorIF;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPAuthenticator;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPFilter;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPMessageReader;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPReceiver;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPServiceExtractor;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public class MailTestCase {
	
	@Test
	public void testReceive() {	
		
//		Properties pop3Props = new Properties();
//		 
//		pop3Props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		pop3Props.setProperty("mail.pop3.socketFactory.fallback", "false");
//		pop3Props.setProperty("mail.pop3.port",  "995");
//		pop3Props.setProperty("mail.pop3.socketFactory.port", "995");

		Properties props = new Properties();// System.getProperties();
		props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.imap.socketFactory.fallback", "false");
		props.put("mail.store.protocol", "imaps");
		
		MailAuthenticatorIF smtpAuthenticatorStrategy =  new SMTPAuthenticator("protocolointegracao@gmail.com", "pr0t0c0l0ap1");
		MailMessageReaderIF smtpMesssaStrategy = new SMTPMessageReader("imap.googlemail.com", "INBOX");
		MailServiceExtractorIF serviceExtractor = new SMTPServiceExtractor();
		SMTPReceiver receiver = new SMTPReceiver();
		receiver.setProperties(props);
		receiver.setAuthenticatorBuilder(smtpAuthenticatorStrategy);
		receiver.setMessageReader(smtpMesssaStrategy);
		receiver.setServiceExtractor(serviceExtractor);
		
		List<ServiceIF> services = receiver.receive(new SMTPFilter());
		Iterator<ServiceIF> iterator = services.iterator();
		while (iterator.hasNext()) {
			ServiceIF serviceIF = (ServiceIF) iterator.next();
			//System.out.println("MessageID:" +serviceIF.getMessageID() + "Name: " + serviceIF.getName() + "Action: " +serviceIF.getAction());
		}
	}
	
	public void testReceive2() {
		try {

			Properties props = new Properties();
			IntegrationAPIProperties.getInstance().load(IntegrationAPIProperties.CONFIG_FILE_PATH);
			props = IntegrationAPIProperties.getInstance().getReceiveProperties();

			MailAuthenticatorIF mailAuthenticator = new SMTPAuthenticator(
					props.getProperty(IntegrationAPIProperties.AUTHENTICATION_LOGIN),
					props.getProperty(IntegrationAPIProperties.AUTHENTICATION_PASSWORD));
			MailServiceExtractorIF mailServiceExtractor = new SMTPServiceExtractor();
			MailMessageReaderIF mailMessageReader = new SMTPMessageReader(
					props.getProperty(IntegrationAPIProperties.PROVIDER_HOST),
					props.getProperty(IntegrationAPIProperties.PROVIDER_FOLDER));

			SMTPReceiver receiver = new SMTPReceiver();
			receiver.setProperties(props);
			receiver.setAuthenticatorBuilder(mailAuthenticator);
			receiver.setMessageReader(mailMessageReader);
			receiver.setServiceExtractor(mailServiceExtractor);

			FilterIF filter = new SMTPFilter();
			
			List<ServiceIF> services = receiver.receive(filter);
			
			System.out.println(services.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

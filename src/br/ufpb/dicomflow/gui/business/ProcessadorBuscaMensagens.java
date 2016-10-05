package br.ufpb.dicomflow.gui.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import br.ufpb.dicomflow.gui.exception.LoginException;
import br.ufpb.dicomflow.integrationAPI.mail.MailAuthenticatorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailMessageReaderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailServiceExtractorIF;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPAuthenticator;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPFilter;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPMessageReader;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPReceiver;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPServiceExtractor;
import br.ufpb.dicomflow.integrationAPI.message.xml.RequestPut;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;
public class ProcessadorBuscaMensagens {
	
	public static List<RequestPut> receberMensagens() throws LoginException {
		//TODO - parametrizar propriedades
		Properties props = new Properties();
		props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.imap.socketFactory.fallback", "false");
		props.put("mail.store.protocol", "imaps");
		
		MailAuthenticatorIF smtpAuthenticatorStrategy =  new SMTPAuthenticator("protocolointegracao@gmail.com", "pr0t0c0l0ap1d1c0m");
		MailMessageReaderIF smtpMesssaStrategy = new SMTPMessageReader("imap.googlemail.com", "INBOX");
		MailServiceExtractorIF serviceExtractor = new SMTPServiceExtractor();
		SMTPReceiver receiver = new SMTPReceiver();
		receiver.setProperties(props);
		receiver.setAuthenticatorBuilder(smtpAuthenticatorStrategy);
		receiver.setMessageReader(smtpMesssaStrategy);
		receiver.setServiceExtractor(serviceExtractor);
		
		SMTPFilter filter = new SMTPFilter();
		filter.setServiceType(ServiceIF.REQUEST_PUT);
		
		List<ServiceIF> services = receiver.receive(filter);
		
		List<RequestPut> requestPutList = new ArrayList<RequestPut>();
		for (ServiceIF service: services) {
			if (service instanceof RequestPut) {
				requestPutList.add((RequestPut)service);
			}
		}		
		
		return requestPutList;
	}
	
}

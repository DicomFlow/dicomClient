package br.ufpb.dicomflow.gui.business;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import br.ufpb.dicomflow.gui.dao.GenericDao;
import br.ufpb.dicomflow.gui.dao.bean.Persistent;
import br.ufpb.dicomflow.gui.dao.bean.RequestPutBean;
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

		MailAuthenticatorIF smtpAuthenticatorStrategy =  new SMTPAuthenticator("dicomflow@gmail.com", "pr0t0c0l0ap1d1c0m");
		MailMessageReaderIF smtpMesssaStrategy = new SMTPMessageReader("imap.googlemail.com", "INBOX");
		MailServiceExtractorIF serviceExtractor = new SMTPServiceExtractor();
		SMTPReceiver receiver = new SMTPReceiver();
		receiver.setProperties(props);
		receiver.setAuthenticatorBuilder(smtpAuthenticatorStrategy);
		receiver.setMessageReader(smtpMesssaStrategy);
		receiver.setServiceExtractor(serviceExtractor);

		SMTPFilter filter = new SMTPFilter();
		filter.setServiceType(ServiceIF.REQUEST_PUT);
		filter.setUnreadOnly(true);

		List<ServiceIF> services = receiver.receive(filter);


		for (ServiceIF service: services) {
			if (service instanceof RequestPut) {
				salvar((RequestPut) service);

			}
		}

		return loadRequests();


	}

	private static List<RequestPut> loadRequests() {

		List<RequestPut> requestPuts = new ArrayList<>();

		List<Persistent> requestPutBeans = GenericDao.selectAll(RequestPutBean.class);

		Iterator<Persistent> iterator = requestPutBeans.iterator();
		while (iterator.hasNext()) {
			RequestPutBean requestPutBean = (RequestPutBean) iterator.next();

			try {

				requestPuts.add(requestPutBean.getValues());

			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}

			System.out.println(requestPutBean.getMessageID());
		}


		return requestPuts;

	}

	private static void salvar(RequestPut service) {
		try {

			RequestPutBean bean = new RequestPutBean();
			bean.setValues(service);

			GenericDao.save(bean);

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

}

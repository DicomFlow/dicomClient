package br.ufpb.dicomflow.gui.business;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import br.ufpb.dicomflow.gui.dao.GenericDao;
import br.ufpb.dicomflow.gui.dao.bean.AuthenticationBean;
import br.ufpb.dicomflow.gui.dao.bean.MessageBean;
import br.ufpb.dicomflow.gui.dao.bean.Persistent;
import br.ufpb.dicomflow.gui.exception.LoginException;
import br.ufpb.dicomflow.integrationAPI.exceptions.ServiceCreationException;
import br.ufpb.dicomflow.integrationAPI.mail.MailAuthenticatorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailContentBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailHeadBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailMessageReaderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailServiceExtractorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MessageIF;
import br.ufpb.dicomflow.integrationAPI.mail.impl.MailContentBuilderFactory;
import br.ufpb.dicomflow.integrationAPI.mail.impl.MailHeadBuilderFactory;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPAuthenticator;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPFilter;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPMessageReader;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPReceiver;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPServiceExtractor;
import br.ufpb.dicomflow.integrationAPI.main.ServiceFactory;
import br.ufpb.dicomflow.integrationAPI.main.ServiceProcessor;
import br.ufpb.dicomflow.integrationAPI.message.xml.Data;
import br.ufpb.dicomflow.integrationAPI.message.xml.RequestPut;
import br.ufpb.dicomflow.integrationAPI.message.xml.RequestResult;
import br.ufpb.dicomflow.integrationAPI.message.xml.Result;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;
import br.ufpb.dicomflow.utils.CryptographyUtil;
public class MessageProcessor {

	//SMTP Properties
	public static final String DOMAIN = "domain";
	public static final String MAIL_DEBUG = "mail.debug";
	public static final String MAIL_SMTP_HOST = "mail.smtp.host";
	public static final String MAIL_SMTP_AUTH ="mail.smtp.auth";
	public static final String MAIL_SMTP_SOCKET_FACTORY_PORT ="mail.smtp.socketFactory.port";
	public static final String MAIL_SMTP_SOCKET_FACTORY_CLASS ="mail.smtp.socketFactory.class";
	public static final String MAIL_SMTP_SOCKET_FACTORY_FALLBACK ="mail.smtp.socketFactory.fallback";
	public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	public static final String MAIL_SMTP_PORT ="mail.smtp.port";

	//IMAP Properties
	public static final String MAIL_IMAP_SOCKET_FACTORY_CLASS = "mail.imap.socketFactory.class";
	public static final String MAIL_IMAP_SOCKET_FACTORY_FALLBACK = "mail.imap.socketFactory.fallback";
	public static final String MAIL_STORE_PROTOCOL = "mail.store.protocol";
	public static final String PROVIDER_HOST = "provider.host";
	public static final String PROVIDER_FOLDER = "provider.folder";

	public static final String LOG_MIN_SEVERITY ="log.min.severity";
	public static final String MAIL_IMAP_PORT ="mail.imap.port";
	public static final String MAIL_IMAP_SOCKET_FACTORY_PORT ="mail.imap.socketFactory.port";


	private static final int FIRST_PAGE = 1;
	private static final int DEFAULT_MAX = 20;


	public static List<MessageBean> receiveMessages(AuthenticationBean authenticationBean, Properties properties) throws LoginException {

		Properties receiveProperties = new Properties();
		receiveProperties.put(MAIL_IMAP_SOCKET_FACTORY_CLASS, properties.getProperty(MAIL_IMAP_SOCKET_FACTORY_CLASS));
		receiveProperties.put(MAIL_IMAP_SOCKET_FACTORY_FALLBACK, properties.getProperty(MAIL_IMAP_SOCKET_FACTORY_FALLBACK));
		receiveProperties.put(MAIL_STORE_PROTOCOL, properties.getProperty(MAIL_STORE_PROTOCOL));

		MailAuthenticatorIF smtpAuthenticatorStrategy =  new SMTPAuthenticator(authenticationBean.getMail(), CryptographyUtil.decryptPBEWithMD5AndDES(authenticationBean.getPassword()));
		MailMessageReaderIF smtpMesssaStrategy = new SMTPMessageReader( properties.getProperty(PROVIDER_HOST), properties.getProperty(PROVIDER_FOLDER));
		MailServiceExtractorIF serviceExtractor = new SMTPServiceExtractor();
		SMTPReceiver receiver = new SMTPReceiver();
		receiver.setProperties(receiveProperties);
		receiver.setAuthenticatorBuilder(smtpAuthenticatorStrategy);
		receiver.setMessageReader(smtpMesssaStrategy);
		receiver.setServiceExtractor(serviceExtractor);

		SMTPFilter filter = new SMTPFilter();
		filter.setServiceType(ServiceIF.REQUEST_PUT);
		filter.setUnreadOnly(true);

		List<MessageIF> messages = receiver.receiveMessages(filter);


		for (MessageIF message: messages) {

			salvar(authenticationBean, message);

		}

		//load fisrt page
		return loadReceivedMessages(FIRST_PAGE, DEFAULT_MAX);

//		return loadRequests();


	}

	private static void salvar(AuthenticationBean authenticationBean, MessageIF message) {
		try {

			MessageBean messageBean = new MessageBean();
			messageBean.setValues(message);

			messageBean.setAuthentication(authenticationBean);
			messageBean.setType(MessageBean.RECEIVED);
			messageBean.setStatus(MessageBean.UNREAD);

			GenericDao.save(messageBean);

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	public static List<MessageBean> loadReceivedMessages(int page, int max) {

		List<MessageBean> messages = new ArrayList<>();

		List<Persistent> messageBeans = GenericDao.selectAll(MessageBean.class, "type", MessageBean.RECEIVED, GenericDao.DESC, "idMessage", page, max);

		Iterator<Persistent> iterator = messageBeans.iterator();
		while (iterator.hasNext()) {

			MessageBean messageBean = (MessageBean) iterator.next();

			messages.add(messageBean);

		}

		return messages;

	}

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

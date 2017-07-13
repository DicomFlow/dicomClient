package br.ufpb.dicomflow.gui.business;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import br.ufpb.dicomflow.gui.application.ApplicationSession;
import br.ufpb.dicomflow.gui.dao.GenericDao;
import br.ufpb.dicomflow.gui.dao.bean.AuthenticationBean;
import br.ufpb.dicomflow.gui.dao.bean.MessageBean;
import br.ufpb.dicomflow.gui.dao.bean.Persistent;
import br.ufpb.dicomflow.gui.exception.MessageException;
import br.ufpb.dicomflow.integrationAPI.exceptions.ServiceCreationException;
import br.ufpb.dicomflow.integrationAPI.mail.MailAuthenticatorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailContentBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailHeadBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailMessageReaderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailServiceExtractorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MessageIF;
import br.ufpb.dicomflow.integrationAPI.mail.impl.MailContentBuilderFactory;
import br.ufpb.dicomflow.integrationAPI.mail.impl.MailHeadBuilderFactory;
import br.ufpb.dicomflow.integrationAPI.mail.impl.MailXTags;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPAuthenticator;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPFilter;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPMessage;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPMessageReader;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPServiceExtractor;
import br.ufpb.dicomflow.integrationAPI.main.ServiceFactory;
import br.ufpb.dicomflow.integrationAPI.main.ServiceProcessor;
import br.ufpb.dicomflow.integrationAPI.message.xml.Completed;
import br.ufpb.dicomflow.integrationAPI.message.xml.Data;
import br.ufpb.dicomflow.integrationAPI.message.xml.RequestPut;
import br.ufpb.dicomflow.integrationAPI.message.xml.RequestResult;
import br.ufpb.dicomflow.integrationAPI.message.xml.Result;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;
import br.ufpb.dicomflow.utils.CryptographyUtil;
import br.ufpb.dicomflow.utils.DateUtil;
import br.ufpb.dicomflow.utils.FileUtil;
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


	public static final int FIRST_PAGE = 0;
	public static final int DEFAULT_MAX = 20;

	private static MessageProcessor messageProcessor = new MessageProcessor();


	private MessageProcessor(){

	}

	public static MessageProcessor getInstance() {
		return messageProcessor;
	}


	public List<MessageBean> receiveMessages() throws MessageException {

		try {

			AuthenticationBean authenticationBean = ApplicationSession.getInstance().getLoggedUser();
			Properties properties = ConfigurationProcessor.getInstance().getProperties(authenticationBean.getConfiguration());

			Properties receiveProperties = new Properties();
			receiveProperties.put(MAIL_IMAP_SOCKET_FACTORY_CLASS, properties.getProperty(MAIL_IMAP_SOCKET_FACTORY_CLASS));
			receiveProperties.put(MAIL_IMAP_SOCKET_FACTORY_FALLBACK, properties.getProperty(MAIL_IMAP_SOCKET_FACTORY_FALLBACK));
			receiveProperties.put(MAIL_STORE_PROTOCOL, properties.getProperty(MAIL_STORE_PROTOCOL));

			MailAuthenticatorIF smtpAuthenticatorStrategy =  new SMTPAuthenticator(authenticationBean.getMail(), CryptographyUtil.decryptPBEWithMD5AndDES(authenticationBean.getPassword()));
			MailMessageReaderIF smtpMesssaStrategy = new SMTPMessageReader( properties.getProperty(PROVIDER_HOST), properties.getProperty(PROVIDER_FOLDER));
			MailServiceExtractorIF serviceExtractor = new SMTPServiceExtractor();


			SMTPFilter filter = new SMTPFilter();
			filter.setServiceType(ServiceIF.REQUEST_PUT);
			filter.setUnreadOnly(true);

			List<MessageIF> messages = ServiceProcessor.receiveMessages(receiveProperties, smtpAuthenticatorStrategy, serviceExtractor, smtpMesssaStrategy, filter); // receiver.receiveMessages(filter);

			for (MessageIF message: messages) {

				save(authenticationBean, message);

			}

		} catch (ServiceCreationException e) {
			e.printStackTrace();
		}


		//load first page
		return loadReceivedMessages(ApplicationSession.getInstance().getLoggedUser(),FIRST_PAGE, DEFAULT_MAX);



	}

	private void save(AuthenticationBean authenticationBean, MessageIF message) {
		try {

			MessageBean messageBean = new MessageBean();
			messageBean.setValues(message);

			messageBean.setAuthentication(authenticationBean);
			messageBean.setTypee(MessageBean.RECEIVED);
			messageBean.setStatuss(MessageBean.UNREAD);

			GenericDao.save(messageBean);

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	public void changeMessageStatus(Integer id, String status){

		MessageBean message = (MessageBean) GenericDao.select(MessageBean.class, id);
		message.setStatuss(status);
		GenericDao.update(message);

	}

	public List<MessageBean> loadReceivedMessages(AuthenticationBean authetication, int page, int max) {

		List<MessageBean> messages = new ArrayList<>();

		List<Persistent> messageBeans = GenericDao.selectAll(MessageBean.class, new String[]{"typee", "authentication"},new Object[]{MessageBean.RECEIVED, authetication}, GenericDao.DESC, "idMessage", page, max);

		Iterator<Persistent> iterator = messageBeans.iterator();
		while (iterator.hasNext()) {

			MessageBean messageBean = (MessageBean) iterator.next();

			messages.add(messageBean);

		}

		return messages;

	}

	public void sendReport(MessageBean messageBean, RequestPut requestPut, File filePath) throws MessageException {

		if(!filePath.canExecute()){
			throw new MessageException("arquivo de laudo não pode ser lido");
		}

		RequestResult requestResult = (RequestResult) ServiceFactory.createService(ServiceIF.REQUEST_RESULT);

		Data data = new Data();
		data.setFilename(filePath.getName());
		data.setBytes(FileUtil.getBytes(filePath));

		Completed completed = new Completed();
		completed.setStatus(Completed.SUCCESS);
		completed.setCompletedMessage(Completed.SUCCESS);

		Result result = new Result();
		result.setData(data);
		result.setOriginalMessageID(requestPut.getMessageID());
		result.setTimestamp(DateUtil.dateToTimestamp(new Date()));
		result.setCompleted(completed);

		requestResult.addResult(result);

		try {

			AuthenticationBean authenticationBean = ApplicationSession.getInstance().getLoggedUser();
			Properties properties = ConfigurationProcessor.getInstance().getProperties(authenticationBean.getConfiguration());

			Properties sendProperties = new Properties();
			sendProperties.put(DOMAIN, properties.getProperty(DOMAIN));
			sendProperties.put(MAIL_DEBUG, properties.getProperty(MAIL_DEBUG));
			sendProperties.put(MAIL_SMTP_HOST, properties.getProperty(MAIL_SMTP_HOST));
			sendProperties.put(MAIL_SMTP_AUTH, properties.getProperty(MAIL_SMTP_AUTH));
			sendProperties.put(MAIL_SMTP_SOCKET_FACTORY_PORT, properties.getProperty(MAIL_SMTP_SOCKET_FACTORY_PORT));
			sendProperties.put(MAIL_SMTP_SOCKET_FACTORY_CLASS, properties.getProperty(MAIL_SMTP_SOCKET_FACTORY_CLASS));
			sendProperties.put(MAIL_SMTP_SOCKET_FACTORY_FALLBACK, properties.getProperty(MAIL_SMTP_SOCKET_FACTORY_FALLBACK));
			sendProperties.put(MAIL_SMTP_STARTTLS_ENABLE, properties.getProperty(MAIL_SMTP_STARTTLS_ENABLE));
			sendProperties.put(MAIL_SMTP_PORT, properties.getProperty(MAIL_SMTP_PORT));

			MailAuthenticatorIF smtpAuthenticatorStrategy =  new SMTPAuthenticator(authenticationBean.getMail(), CryptographyUtil.decryptPBEWithMD5AndDES(authenticationBean.getPassword()));
			MailHeadBuilderIF mailHeadBuilder =  MailHeadBuilderFactory.createHeadStrategy(MailHeadBuilderIF.SMTP_HEAD_STRATEGY);
			mailHeadBuilder.setDomain(properties.getProperty(DOMAIN));

			//it's a reply!
			mailHeadBuilder.setFrom(authenticationBean.getMail());
			mailHeadBuilder.setTo(messageBean.getFromm());

			MailContentBuilderIF mailContentBuilder = MailContentBuilderFactory.createContentStrategy(MailContentBuilderIF.SMTP_SIMPLE_CONTENT_STRATEGY);

			String messageID = ServiceProcessor.sendMessage(requestResult, messageBean.getFromm(), sendProperties, smtpAuthenticatorStrategy, mailHeadBuilder, mailContentBuilder);
			requestResult.setMessageID(messageID);

			//Saving RequestResult message
			SMTPMessage message = new SMTPMessage();
			message.addMailXTag(MailXTags.DATE, DateUtil.dateToMailDateString(new Date()));
			message.addMailXTag(MailXTags.FROM, authenticationBean.getMail());
			message.addMailXTag(MailXTags.TO, messageBean.getFromm());
			message.addMailXTag(MailXTags.SUBJECT, "Request-Result");
			message.setService(requestResult);

			MessageBean newMessageBean = new MessageBean();
			newMessageBean.setValues(message);
			newMessageBean.setAuthentication(authenticationBean);
			newMessageBean.setTypee(MessageBean.SENT);
			newMessageBean.setStatuss(MessageBean.UNREAD);

			GenericDao.save(newMessageBean);

		} catch (ServiceCreationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			throw new MessageException(e.getMessage());
		}

	}

}

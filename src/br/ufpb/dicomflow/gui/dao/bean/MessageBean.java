package br.ufpb.dicomflow.gui.dao.bean;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.ufpb.dicomflow.gui.dao.GenericDao;
import br.ufpb.dicomflow.integrationAPI.mail.MessageIF;
import br.ufpb.dicomflow.integrationAPI.mail.impl.MailXTags;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPMessage;
import br.ufpb.dicomflow.integrationAPI.message.xml.RequestPut;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

@Entity
@Table(name="message")
public class MessageBean implements Persistent {


	public static final String UNREAD = "UNREAD";
	public static final String READ = "READ";
	public static final String IMPORTANT = "IMPORTANT";
	public static final String ARCHIVED = "ARCHIVED";

	public static final String SENT = "SENT";
	public static final String RECEIVED = "RECEIVED";


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_message")
	private Integer idMessage;

	@Column
	private String from;

	@Column
	private String to;

	@Column
	private String subject;


	private String date;

	@Column
	private String status;

	private String type;

	@OneToOne(mappedBy = "message", cascade = CascadeType.ALL)
	private ServiceBean service;


	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_authentication")
	private AuthenticationBean authentication;

	public Integer getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(Integer idMessage) {
		this.idMessage = idMessage;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public ServiceBean getService() {
		return service;
	}

	public void setService(ServiceBean service) {
		this.service = service;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AuthenticationBean getAuthentication() {
		return authentication;
	}

	public void setAuthentication(AuthenticationBean authentication) {
		this.authentication = authentication;
	}

	@Override
	public Integer getIdentifierValue() {
		return this.getIdMessage();
	}

	@Override
	public String getIndetifierName() {
		return "idMessage";
	}

	public void setValues(MessageIF message) throws IllegalAccessException, InvocationTargetException{
		this.setDate((String) message.getMailTag(MailXTags.DATE));
		this.setFrom((String) message.getMailTag(MailXTags.FROM));
		this.setTo((String) message.getMailTag(MailXTags.TO));
		this.setSubject((String) message.getMailTag(MailXTags.SUBJECT));

		ServiceIF service = message.getService();


		if (service instanceof RequestPut) {

			RequestPutBean RequestPutbean = new RequestPutBean();
			RequestPutbean.setValues((RequestPut) service);

			GenericDao.save(RequestPutbean);

			ServiceBean serviceBean = new ServiceBean();
			serviceBean.setMessage(this);
			serviceBean.setPersistentClass(RequestPutBean.class.getName());
			serviceBean.setPersitentId(RequestPutbean.getIdentifierValue());

			this.setService(serviceBean);

		}
	}

	public MessageIF getValues() throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException{
		MessageIF message = new SMTPMessage();
		Map<String,Object> mailXTags = new HashMap<>();

		mailXTags.put(MailXTags.DATE, this.getDate());
		mailXTags.put(MailXTags.FROM, this.getFrom());
		mailXTags.put(MailXTags.TO, this.getTo());
		mailXTags.put(MailXTags.SUBJECT, this.getSubject());

		message.setMailXTags(mailXTags);

		ServiceBean serviceBean = this.getService();

		Persistent persistent = (Persistent) Class.forName(serviceBean.getPersistentClass()).newInstance();

		if (persistent instanceof RequestPutBean) {

			RequestPutBean requestPutBean = (RequestPutBean) GenericDao.select(persistent.getClass(), service.getIdentifierValue());
			RequestPut requestPut = ((RequestPutBean) requestPutBean).getValues();

			message.setService(requestPut);
		}


		return message;
	}

}

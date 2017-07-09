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
	private String fromm;

	@Column
	private String too;

	@Column
	private String subjectt;


	@Column
	private String datee;

	@Column
	private String statuss;

	@Column
	private String typee;

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

	public String getFromm() {
		return fromm;
	}

	public void setFromm(String fromm) {
		this.fromm = fromm;
	}

	public String getToo() {
		return too;
	}

	public void setToo(String too) {
		this.too = too;
	}

	public String getDatee() {
		return datee;
	}

	public void setDatee(String datee) {
		this.datee = datee;
	}


	public String getSubjectt() {
		return subjectt;
	}

	public void setSubjectt(String subjectt) {
		this.subjectt = subjectt;
	}

	public String getStatuss() {
		return statuss;
	}

	public void setStatuss(String statuss) {
		this.statuss = statuss;
	}

	public String getTypee() {
		return typee;
	}

	public void setTypee(String typee) {
		this.typee = typee;
	}

	public ServiceBean getService() {
		return service;
	}

	public void setService(ServiceBean service) {
		this.service = service;
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
		this.setDatee((String) message.getMailTag(MailXTags.DATE));
		this.setFromm((String) message.getMailTag(MailXTags.FROM));
		this.setToo((String) message.getMailTag(MailXTags.TO));
		this.setSubjectt((String) message.getMailTag(MailXTags.SUBJECT));

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

		mailXTags.put(MailXTags.DATE, this.getDatee());
		mailXTags.put(MailXTags.FROM, this.getFromm());
		mailXTags.put(MailXTags.TO, this.getToo());
		mailXTags.put(MailXTags.SUBJECT, this.getSubjectt());

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

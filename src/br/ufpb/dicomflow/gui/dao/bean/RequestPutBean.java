package br.ufpb.dicomflow.gui.dao.bean;

import java.lang.reflect.InvocationTargetException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtils;

import br.ufpb.dicomflow.integrationAPI.message.xml.RequestPut;

@Entity
@Table(name="request_put")
public class RequestPutBean implements Persistent{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_request_put")
	private Integer idRequestPut;

	@Column
	private String version;

	@Column
	private String name;

	@Column
	private String action;

	@Column
	private int type;

	@Column
	private String messageID;

	@Column
	private String timestamp;

	@Column
	private String timeout;

	@Column
	private String requestType;

	@OneToOne(mappedBy = "requestPutBean", cascade = CascadeType.ALL)
	private URLBean urlBean;

	public Integer getIdRequestPut() {
		return idRequestPut;
	}

	public void setIdRequestPut(Integer idRequestPut) {
		this.idRequestPut = idRequestPut;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public URLBean getUrlBean() {
		return urlBean;
	}

	public void setUrlBean(URLBean urlBean) {
		this.urlBean = urlBean;
	}


	public void setValues(RequestPut requestPut) throws IllegalAccessException, InvocationTargetException{
		BeanUtils.copyProperties(this, requestPut);

		if(requestPut.getUrl() != null){
			urlBean = new URLBean();
			urlBean.setValues(requestPut.getUrl());
			urlBean.setRequestPutBean(this);
		}

	}

	public RequestPut getValues() throws IllegalAccessException, InvocationTargetException{

		RequestPut requestPut = new RequestPut();
		BeanUtils.copyProperties(requestPut, this);

		if(this.getUrlBean() != null){
			requestPut.setUrl(this.getUrlBean().getValues());
		}

		return requestPut;
	}

	@Override
	public Integer getIdentifierValue() {
		return this.getIdRequestPut();
	}

	@Override
	public String getIndetifierName() {
		return "idRequestPut";
	}


}

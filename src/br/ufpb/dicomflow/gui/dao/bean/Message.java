package br.ufpb.dicomflow.gui.dao.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="message")
public class Message implements Persistent {


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

	@OneToOne(mappedBy = "message", cascade = CascadeType.ALL)
	private Service service;

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

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	@Override
	public Integer getIdentifier() {
		return this.getIdMessage();
	}

}

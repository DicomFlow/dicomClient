package br.ufpb.dicomflow.gui.dao.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="service")
public class Service implements Persistent {


	@Id
	@GeneratedValue(generator="myGenerator")
	@GenericGenerator(name="myGenerator", strategy = "foreign", parameters = @Parameter(name = "property", value = "message"))
	@Column(name="id_service")
	private Integer idService;

	@Column
	private String persistentClass;

	@Column
	private String persitentId;

	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Message message;


	public Integer getIdService() {
		return idService;
	}

	public void setIdService(Integer idService) {
		this.idService = idService;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getPersistentClass() {
		return persistentClass;
	}

	public void setPersistentClass(String persistentClass) {
		this.persistentClass = persistentClass;
	}

	public String getPersitentId() {
		return persitentId;
	}

	public void setPersitentId(String persitentId) {
		this.persitentId = persitentId;
	}

	@Override
	public Integer getIdentifier() {
		return this.getIdService();
	}

}

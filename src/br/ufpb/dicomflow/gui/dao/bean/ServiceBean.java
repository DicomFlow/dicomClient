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
public class ServiceBean implements Persistent {


	@Id
	@GeneratedValue(generator="myGenerator")
	@GenericGenerator(name="myGenerator", strategy = "foreign", parameters = @Parameter(name = "property", value = "message"))
	@Column(name="id_service")
	private Integer idService;

	@Column
	private String persistentClass;

	@Column
	private Integer persitentId;

	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private MessageBean message;


	public Integer getIdService() {
		return idService;
	}

	public void setIdService(Integer idService) {
		this.idService = idService;
	}

	public MessageBean getMessage() {
		return message;
	}

	public void setMessage(MessageBean message) {
		this.message = message;
	}

	public String getPersistentClass() {
		return persistentClass;
	}

	public void setPersistentClass(String persistentClass) {
		this.persistentClass = persistentClass;
	}

	public Integer getPersitentId() {
		return persitentId;
	}

	public void setPersitentId(Integer persitentId) {
		this.persitentId = persitentId;
	}

	@Override
	public Integer getIdentifierValue() {
		return this.getIdService();
	}

	@Override
	public String getIndetifierName() {
		return "idService";
	}


}

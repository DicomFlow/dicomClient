package br.ufpb.dicomflow.gui.dao.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="configuration")
public class ConfigurationBean implements Persistent {

	public static final String GMAIL = "GMAIL";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_configuration")
	private Integer id;

	@Column
	private String title;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="id_configuration")
	private List<PropertyBean> properties;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="id_configuration")
	private List<AuthenticationBean> authentications;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<PropertyBean> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyBean> properties) {
		this.properties = properties;
	}

	public List<AuthenticationBean> getAuthentications() {
		return authentications;
	}

	public void setAuthentications(List<AuthenticationBean> authentications) {
		this.authentications = authentications;
	}

	@Override
	public Integer getIdentifierValue() {
		return this.getId();
	}

	@Override
	public String getIndetifierName() {
		return "id";
	}


}

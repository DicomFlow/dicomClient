package br.ufpb.dicomflow.gui.dao.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="property")
public class PropertyBean implements Persistent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_property")
	private Integer id;

	@Column
	private String property;

	@Column
	private String value;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_configuration")
	private ConfigurationBean configuration;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ConfigurationBean getConfiguration() {
		return configuration;
	}

	public void setConfiguration(ConfigurationBean configuration) {
		this.configuration = configuration;
	}

	@Override
	public Integer getIdentifierValue() {
		return getId();
	}


	@Override
	public String getIndetifierName() {
		return "id";
	}

}

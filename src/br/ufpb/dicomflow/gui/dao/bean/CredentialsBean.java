package br.ufpb.dicomflow.gui.dao.bean;

import java.lang.reflect.InvocationTargetException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import br.ufpb.dicomflow.integrationAPI.message.xml.Credentials;

@Entity
@Table(name="credentials")
public class CredentialsBean implements Persistent {

	@Id
	@GeneratedValue(generator="myGenerator")
	@GenericGenerator(name="myGenerator", strategy = "foreign", parameters = @Parameter(name = "property", value = "urlBean"))
	@Column(name = "id_credentials")
	private Integer idCredentials;

	@Column
	private String value;

	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private URLBean urlBean;


	public Integer getIdCredentials() {
		return idCredentials;
	}

	public void setIdCredentials(Integer idCredentials) {
		this.idCredentials = idCredentials;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public URLBean getUrlBean() {
		return urlBean;
	}

	public void setUrlBean(URLBean urlBean) {
		this.urlBean = urlBean;
	}

	public void setValues(Credentials credentials) throws IllegalAccessException, InvocationTargetException{
		BeanUtils.copyProperties(this, credentials);
	}

	public Credentials getValues() throws IllegalAccessException, InvocationTargetException{

		Credentials credentials = new Credentials();
		BeanUtils.copyProperties(credentials, this);
		return credentials;
	}

	@Override
	public Integer getIdentifierValue() {
		return this.getIdCredentials();
	}

	@Override
	public String getIndetifierName() {
		return "idCredentials";
	}


}

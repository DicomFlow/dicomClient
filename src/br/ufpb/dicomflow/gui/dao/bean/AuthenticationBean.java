package br.ufpb.dicomflow.gui.dao.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="autentication")
public class AuthenticationBean implements Persistent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_authentication")
	private Integer id;

	@Column
	private String mail;

	@Column
	private String password;

	@Column(nullable=false)
	private boolean connected;

	@Column
	private String folder;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_configuration")
	private ConfigurationBean configuration;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="id_authentication")
	private List<MessageBean> messages;




	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public ConfigurationBean getConfiguration() {
		return configuration;
	}

	public void setConfiguration(ConfigurationBean configuration) {
		this.configuration = configuration;
	}

	public List<MessageBean> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageBean> messages) {
		this.messages = messages;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
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

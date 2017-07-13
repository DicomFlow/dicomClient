package br.ufpb.dicomflow.gui.dao.bean;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;

import br.ufpb.dicomflow.integrationAPI.message.xml.Patient;
import br.ufpb.dicomflow.integrationAPI.message.xml.URL;

@Entity
@Table(name="url")
public class URLBean implements Persistent{

	@Id
	@GeneratedValue(generator="myGenerator")
	@GenericGenerator(name="myGenerator", strategy = "foreign", parameters = @Parameter(name = "property", value = "requestPutBean"))
	@Column(name="id_url")
	private Integer idURL;

	@Column
	private String value;

	@OneToOne(mappedBy = "urlBean", cascade = CascadeType.ALL)
	private CredentialsBean credentialsBean;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade=CascadeType.ALL )
	@JoinColumn(name="id_url")
	private List<PatientBean> patientsBean;

	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private RequestPutBean requestPutBean;

	public Integer getIdURL() {
		return idURL;
	}

	public void setIdURL(Integer idURL) {
		this.idURL = idURL;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CredentialsBean getCredentialsBean() {
		return credentialsBean;
	}

	public void setCredentialsBean(CredentialsBean credentialsBean) {
		this.credentialsBean = credentialsBean;
	}

	public List<PatientBean> getPatientsBean() {
		return patientsBean;
	}

	public void setPatientsBean(List<PatientBean> patientsBean) {
		this.patientsBean = patientsBean;
	}


	public RequestPutBean getRequestPutBean() {
		return requestPutBean;
	}

	public void setRequestPutBean(RequestPutBean requestPutBean) {
		this.requestPutBean = requestPutBean;
	}

	public void setValues(URL url) throws IllegalAccessException, InvocationTargetException{
		BeanUtils.copyProperties(this, url);

		if(url.getCredentials() != null){
			credentialsBean = new CredentialsBean();
			credentialsBean.setValues(url.getCredentials());
			credentialsBean.setUrlBean(this);
		}

		if(url.getPatient() != null){

			patientsBean  =  new ArrayList<>();

			Iterator<Patient> it = url.getPatient().iterator();
			while (it.hasNext()) {

				Patient patient = (Patient) it.next();
				PatientBean patientBean = new PatientBean();
				patientBean.setValues(patient);

				patientsBean.add(patientBean);

			}
		}
	}

	public URL getValues() throws IllegalAccessException, InvocationTargetException{

		URL url = new URL();
		BeanUtils.copyProperties(url, this);

		if(this.getCredentialsBean() != null){
			url.setCredentials(this.getCredentialsBean().getValues());
		}

		if(this.getPatientsBean() != null){

			List<Patient> patients = new ArrayList<>();

			Iterator<PatientBean> it =  this.getPatientsBean().iterator();
			while (it.hasNext()) {

				PatientBean patientBean = (PatientBean) it.next();

				patients.add(patientBean.getValues());

			}

			url.setPatient(patients);

		}

		return url;
	}

	@Override
	public Integer getIdentifierValue() {
		return this.getIdURL();
	}

	@Override
	public String getIndetifierName() {
		return "idUrl";
	}


}

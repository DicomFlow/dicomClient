package br.ufpb.dicomflow.gui.dao.bean;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
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

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.ufpb.dicomflow.integrationAPI.message.xml.Patient;
import br.ufpb.dicomflow.integrationAPI.message.xml.Study;

@Entity
@Table(name="patient")
public class PatientBean implements Persistent{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_patient")
	private Integer idPatient;

	@Column
	private String id;

	@Column
	private String name;

	@Column
	private String gender;

	@Column
	private String birthdate;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="id_patient")
	private List<StudyBean> studiesBean;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_url")
	private URLBean urlBean;

	public Integer getIdPatient() {
		return idPatient;
	}
	public void setIdPatient(Integer idPatient) {
		this.idPatient = idPatient;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public List<StudyBean> getStudiesBean() {
		return studiesBean;
	}
	public void setStudiesBean(List<StudyBean> studiesBean) {
		this.studiesBean = studiesBean;
	}

	public URLBean getUrlBean() {
		return urlBean;
	}
	public void setUrlBean(URLBean urlBean) {
		this.urlBean = urlBean;
	}

	public void setValues(Patient patient) throws IllegalAccessException, InvocationTargetException{
		BeanUtils.copyProperties(this, patient);

		if(patient.getStudy() != null){

			studiesBean  =  new ArrayList<>();

			Iterator<Study> it = patient.getStudy().iterator();
			while (it.hasNext()) {

				Study study = (Study) it.next();
				StudyBean studyBean = new StudyBean();
				studyBean.setValues(study);

				studiesBean.add(studyBean);

			}
		}
	}

	public Patient getValues() throws IllegalAccessException, InvocationTargetException{

		Patient patient = new Patient();
		BeanUtils.copyProperties(patient, this);

		if(this.getStudiesBean() != null){

			List<Study> studies = new ArrayList<>();

			Iterator<StudyBean> it =  this.getStudiesBean().iterator();
			while (it.hasNext()) {

				StudyBean studyBean = (StudyBean) it.next();

				studies.add(studyBean.getValues());

			}

			patient.setStudy(studies);

		}

		return patient;
	}

	@Override
	public Integer getIdentifierValue() {
		return this.getIdPatient();
	}

	@Override
	public String getIndetifierName() {
		return "idPatient";
	}


}

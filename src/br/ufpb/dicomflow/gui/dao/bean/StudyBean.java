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

import br.ufpb.dicomflow.integrationAPI.message.xml.Serie;
import br.ufpb.dicomflow.integrationAPI.message.xml.Study;

@Entity
@Table(name="study")
public class StudyBean implements Persistent{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_study")
	private Integer idStudy;

	@Column
	private String id;

	@Column
	private String type;

	@Column
	private String description;

	@Column
	private String datetime;

	@Column
	private long size;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="id_study")
	private List<SerieBean> seriesBean;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_patient")
	private PatientBean patientBean;


	public Integer getIdStudy() {
		return idStudy;
	}
	public void setIdStudy(Integer idStudy) {
		this.idStudy = idStudy;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public List<SerieBean> getSeriesBean() {
		return seriesBean;
	}
	public void setSeriesBean(List<SerieBean> seriesBean) {
		this.seriesBean = seriesBean;
	}

	public PatientBean getPatientBean() {
		return patientBean;
	}
	public void setPatientBean(PatientBean patientBean) {
		this.patientBean = patientBean;
	}

	public void setValues(Study study) throws IllegalAccessException, InvocationTargetException{
		BeanUtils.copyProperties(this, study);

		if(study.getSerie() != null){

			seriesBean  =  new ArrayList<>();

			Iterator<Serie> it = study.getSerie().iterator();
			while (it.hasNext()) {

				Serie serie = (Serie) it.next();
				SerieBean serieBean = new SerieBean();
				serieBean.setValues(serie);

				seriesBean.add(serieBean);

			}
		}
	}

	public Study getValues() throws IllegalAccessException, InvocationTargetException{

		Study study = new Study();
		BeanUtils.copyProperties(study, this);

		if(this.getSeriesBean() != null){

			List<Serie> series = new ArrayList<>();

			Iterator<SerieBean> it =  this.getSeriesBean().iterator();
			while (it.hasNext()) {

				SerieBean serieBean = (SerieBean) it.next();

				series.add(serieBean.getValues());

			}

			study.setSerie(series);

		}

		return study;
	}

	@Override
	public Integer getIdentifierValue() {
		return this.getIdStudy();
	}

	@Override
	public String getIndetifierName() {
		return "idStudy";
	}

}

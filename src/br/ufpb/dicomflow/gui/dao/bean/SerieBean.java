package br.ufpb.dicomflow.gui.dao.bean;

import java.lang.reflect.InvocationTargetException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtils;

import br.ufpb.dicomflow.integrationAPI.message.xml.Serie;

@Entity
@Table(name="serie")
public class SerieBean implements Persistent{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_serie")
	private Integer idSerie;

	@Column
	private String id;

	@Column
	private String bodypart;

	@Column
	private String description;

	@Column
	private int instances;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_study")
	private StudyBean studyBean;


	public Integer getIdSerie() {
		return idSerie;
	}
	public void setIdSerie(Integer idSerie) {
		this.idSerie = idSerie;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBodypart() {
		return bodypart;
	}
	public void setBodypart(String bodypart) {
		this.bodypart = bodypart;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getInstances() {
		return instances;
	}
	public void setInstances(int instances) {
		this.instances = instances;
	}

	public StudyBean getStudyBean() {
		return studyBean;
	}
	public void setStudyBean(StudyBean studyBean) {
		this.studyBean = studyBean;
	}

	public void setValues(Serie serie) throws IllegalAccessException, InvocationTargetException{
		BeanUtils.copyProperties(this, serie);
	}

	public Serie getValues() throws IllegalAccessException, InvocationTargetException{

		Serie serie = new Serie();
		BeanUtils.copyProperties(serie, this);
		return serie;
	}

	@Override
	public Integer getIdentifierValue() {
		return this.getIdSerie();
	}

	@Override
	public String getIndetifierName() {
		return "idSerie";
	}


}

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

import br.ufpb.dicomflow.integrationAPI.message.xml.Completed;


@Entity
@Table(name="completed")
public class CompletedBean implements Persistent {


	@Id
	@GeneratedValue(generator="myGenerator")
	@GenericGenerator(name="myGenerator", strategy = "foreign", parameters = @Parameter(name = "property", value = "resultBean"))
	@Column(name="id_completed")
	private Integer idCompleted;

	@Column
	private String status;

	@Column
	private String completedMessage;

	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private ResultBean resultBean;



	public Integer getIdCompleted() {
		return idCompleted;
	}

	public void setIdCompleted(Integer idCompleted) {
		this.idCompleted = idCompleted;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCompletedMessage() {
		return completedMessage;
	}

	public void setCompletedMessage(String completedMessage) {
		this.completedMessage = completedMessage;
	}

	public ResultBean getResultBean() {
		return resultBean;
	}

	public void setResultBean(ResultBean resultBean) {
		this.resultBean = resultBean;
	}

	@Override
	public Integer getIdentifierValue() {
		return this.getIdCompleted();
	}

	@Override
	public String getIndetifierName() {
		return "idCompleted";
	}


	public void setValues(Completed completed) throws IllegalAccessException, InvocationTargetException{
		BeanUtils.copyProperties(this, completed);


	}

	public Completed getValues() throws IllegalAccessException, InvocationTargetException{

		Completed completed = new Completed();
		BeanUtils.copyProperties(completed, this);

		return completed;
	}

}

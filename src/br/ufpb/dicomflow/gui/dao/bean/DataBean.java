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
import org.hibernate.annotations.Type;

import br.ufpb.dicomflow.integrationAPI.message.xml.Data;

@Entity
@Table(name="data")
public class DataBean implements Persistent{

	@Id
	@GeneratedValue(generator="myGenerator")
	@GenericGenerator(name="myGenerator", strategy = "foreign", parameters = @Parameter(name = "property", value = "resultBean"))
	@Column(name="id_data")
	private Integer idData;

	@Column
	private String filename;

	@Column
	@Type(type = "org.hibernate.type.MaterializedBlobType")
	private byte[] bytes;

	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private ResultBean resultBean;


	public Integer getIdData() {
		return idData;
	}



	public void setIdData(Integer idData) {
		this.idData = idData;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public ResultBean getResultBean() {
		return resultBean;
	}

	public void setResultBean(ResultBean resultBean) {
		this.resultBean = resultBean;
	}

	@Override
	public Integer getIdentifierValue() {
		return this.getIdData();
	}

	@Override
	public String getIndetifierName() {
		return "idData";
	}


	public void setValues(Data data) throws IllegalAccessException, InvocationTargetException{
		BeanUtils.copyProperties(this, data);


	}

	public Data getValues() throws IllegalAccessException, InvocationTargetException{

		Data data = new Data();
		BeanUtils.copyProperties(data, this);

		return data;
	}


}

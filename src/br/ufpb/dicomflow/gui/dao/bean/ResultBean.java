package br.ufpb.dicomflow.gui.dao.bean;

import java.lang.reflect.InvocationTargetException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtils;

import br.ufpb.dicomflow.integrationAPI.message.xml.Result;

@Entity
@Table(name="result")
public class ResultBean implements Persistent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_result")
	private Integer idResult;

	@Column
	private String originalMessageID;

	@Column
	private String timestamp;

	@OneToOne(mappedBy = "resultBean", cascade = CascadeType.ALL)
	private CompletedBean completedBean;

	@OneToOne(mappedBy = "resultBean", cascade = CascadeType.ALL)
	private DataBean dataBean;


	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_request_result")
	private RequestResultBean requestResultBean;

	public Integer getIdResult() {
		return idResult;
	}

	public void setIdResult(Integer idResult) {
		this.idResult = idResult;
	}

	public String getOriginalMessageID() {
		return originalMessageID;
	}

	public void setOriginalMessageID(String originalMessageID) {
		this.originalMessageID = originalMessageID;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public CompletedBean getCompletedBean() {
		return completedBean;
	}

	public void setCompletedBean(CompletedBean completedBean) {
		this.completedBean = completedBean;
	}

	public DataBean getDataBean() {
		return dataBean;
	}

	public void setDataBean(DataBean dataBean) {
		this.dataBean = dataBean;
	}

	public RequestResultBean getRequestResultBean() {
		return requestResultBean;
	}

	public void setRequestResultBean(RequestResultBean requestResultBean) {
		this.requestResultBean = requestResultBean;
	}

	@Override
	public Integer getIdentifierValue() {
		return this.getIdResult();
	}

	@Override
	public String getIndetifierName() {
		return "idResult";
	}

	public void setValues(Result result) throws IllegalAccessException, InvocationTargetException{
		BeanUtils.copyProperties(this, result);

		if(result.getData() != null){
			dataBean = new DataBean();
			dataBean.setValues(result.getData());
			dataBean.setResultBean(this);
		}

		if(result.getCompleted() != null){
			completedBean = new CompletedBean();
			completedBean.setValues(result.getCompleted());
			completedBean.setResultBean(this);
		}


	}

	public Result getValues() throws IllegalAccessException, InvocationTargetException{

		Result result = new Result();
		BeanUtils.copyProperties(result, this);

		if(this.getDataBean() != null){
			result.setData(this.getDataBean().getValues());
		}

		if(this.getCompletedBean() != null){
			result.setCompleted(this.getCompletedBean().getValues());
		}

		return result;
	}

}

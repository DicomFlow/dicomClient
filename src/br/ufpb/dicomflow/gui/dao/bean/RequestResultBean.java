package br.ufpb.dicomflow.gui.dao.bean;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.ufpb.dicomflow.integrationAPI.message.xml.RequestResult;
import br.ufpb.dicomflow.integrationAPI.message.xml.Result;


@Entity
@Table(name="request_result")
public class RequestResultBean implements Persistent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_request_result")
	private Integer idRequestResult;

	@Column
	private String version;

	@Column
	private String name;

	@Column
	private String action;

	@Column
	private int type;

	@Column
	private String messageID;

	@Column
	private String timestamp;

	@Column
	private String timeout;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade=CascadeType.ALL )
	@JoinColumn(name="id_request_result")
	private List<ResultBean> resultsBean;

	public Integer getIdRequestResult() {
		return idRequestResult;
	}

	public void setIdRequestResult(Integer idRequestResult) {
		this.idRequestResult = idRequestResult;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public List<ResultBean> getResultsBean() {
		return resultsBean;
	}

	public void setResultsBean(List<ResultBean> resultsBean) {
		this.resultsBean = resultsBean;
	}

	@Override
	public Integer getIdentifierValue() {
		return getIdRequestResult();
	}

	@Override
	public String getIndetifierName() {
		return "idRequestResult";
	}

	public void setValues(RequestResult requestResult) throws IllegalAccessException, InvocationTargetException{
		BeanUtils.copyProperties(this, requestResult);

		if(requestResult.getResult() != null){

			resultsBean  =  new ArrayList<>();

			Iterator<Result> it = requestResult.getResult().iterator();
			while (it.hasNext()) {

				Result result = (Result) it.next();
				ResultBean resultBean = new ResultBean();
				resultBean.setValues(result);

				resultsBean.add(resultBean);

			}
		}
	}

	public RequestResult getValues() throws IllegalAccessException, InvocationTargetException{

		RequestResult requestResult = new RequestResult();
		BeanUtils.copyProperties(requestResult, this);


		if(this.getResultsBean() != null){

			List<Result> results = new ArrayList<>();

			Iterator<ResultBean> it =  this.getResultsBean().iterator();
			while (it.hasNext()) {

				ResultBean resultBean = (ResultBean) it.next();

				results.add(resultBean.getValues());

			}

			requestResult.setResult(results);

		}

		return requestResult;
	}

}

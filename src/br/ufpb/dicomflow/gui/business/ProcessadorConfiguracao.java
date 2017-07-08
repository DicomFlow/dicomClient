package br.ufpb.dicomflow.gui.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import br.ufpb.dicomflow.gui.dao.GenericDao;
import br.ufpb.dicomflow.gui.dao.bean.ConfigurationBean;
import br.ufpb.dicomflow.gui.dao.bean.Persistent;
import br.ufpb.dicomflow.gui.dao.bean.PropertyBean;

public class ProcessadorConfiguracao {

	private final String GMAIL_PROPERTIES = "gmail.properties";

	private static ProcessadorConfiguracao processadorConfiguracao = new ProcessadorConfiguracao();


	private ProcessadorConfiguracao(){

	}

	public static ProcessadorConfiguracao getProcessadorConfiguracao() {
		return processadorConfiguracao;
	}

	public  void init(){
		ConfigurationBean gmailConfig = (ConfigurationBean) GenericDao.select(ConfigurationBean.class,"title",ConfigurationBean.GMAIL);

		if(gmailConfig == null){
			insertProperties(ConfigurationBean.GMAIL, GMAIL_PROPERTIES);
		}else{
			updateProperties(gmailConfig, GMAIL_PROPERTIES);
		}


	}




	private void insertProperties(String configTitle, String configFile){

			//create new configuration
			ConfigurationBean configurationBean = new ConfigurationBean();
			configurationBean.setTitle(configTitle);

			//insert new properties
			configurationBean.setProperties(getPropertyBeans(configFile));
			GenericDao.save(configurationBean);



	}

	private void updateProperties(ConfigurationBean configurationBean, String configFile) {

		//remove old properties
		List<Persistent> propertyBeans = GenericDao.selectAll(PropertyBean.class, "configuration", configurationBean);
		GenericDao.deleteAll(propertyBeans);

		//insert new properties
		configurationBean.setProperties(getPropertyBeans(configFile));
		GenericDao.update(configurationBean);


	}

	private List<PropertyBean> getPropertyBeans(String configFile) {
		List<PropertyBean> propertyBeans = new ArrayList<>();

		try {
			Properties properties = new Properties();

			FileInputStream is =  new FileInputStream(getPath(configFile));
			properties.load(is);

			Enumeration<?> keys = properties.propertyNames();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();

				PropertyBean property = new PropertyBean();
				property.setProperty(key);
				property.setValue(properties.getProperty(key));

				propertyBeans.add(property);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propertyBeans;
	}

	private String getPath(String configFile) {
		String path = "";
		File dir = new File(ProcessadorConfiguracao.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		path = dir.toString();
		return path + File.separator + configFile;
	}

	public List<String> getConfigurationTitles(){

		List<String> titles = new ArrayList<>();

		List<Persistent> configurations =  GenericDao.selectAll(ConfigurationBean.class);
		for (Iterator<Persistent> iterator = configurations.iterator(); iterator.hasNext();) {
			ConfigurationBean configuration = (ConfigurationBean) iterator.next();
			titles.add(configuration.getTitle());
		}

		return titles;


	}

	public ConfigurationBean  getConfiguration(String title){
		return (ConfigurationBean) GenericDao.select(ConfigurationBean.class, "title", title);
	}

	public Properties getProperties(ConfigurationBean configuration){
		Properties properties = new Properties();

		List<Persistent> propertyBeans = GenericDao.selectAll(PropertyBean.class, "configuration", configuration);

		Iterator<Persistent> it = propertyBeans.iterator();
		while (it.hasNext()) {

			PropertyBean propertyBean = (PropertyBean) it.next();

			properties.put(propertyBean.getProperty(), propertyBean.getValue());

		}

		return properties;

	}



}

package br.ufpb.dicomflow.gui.business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.client.ClientConfig;

import br.ufpb.dicomflow.gui.exception.LoginException;
import br.ufpb.dicomflow.integrationAPI.conf.IntegrationAPIProperties;
import br.ufpb.dicomflow.integrationAPI.message.xml.RequestPut;

public class ProcessadorDownloadExames {
	
	public static void downloadExames(RequestPut requestPut) throws LoginException {
		
		try {						
			ClientConfig clientConfig = new ClientConfig();
			Client client = ClientBuilder.newClient(clientConfig);
			
			WebTarget webTarget = client.target(requestPut.getUrl().getValue());									
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_OCTET_STREAM);	
			
			Response response = invocationBuilder.get();
			InputStream is = (InputStream)response.getEntity();		
			//response.readEntity(String.class);		

			byte[] SWFByteArray = IOUtils.toByteArray(is);  

			//TODO download dir
			FileOutputStream fos = new FileOutputStream(new File("c:/temp/" + requestPut.getMessageID() + ".zip"));
			fos.write(SWFByteArray);			
			fos.flush();
			fos.close();										
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}

//	host=10.0.0.1
//	port=80;
//	context=dicomMove2/rest
//	serviceName=DownloadStudy
//	studyId=2.16.840.1.113669.632.20.1211.10000324479
//	resultPath=temp/dicomflow/
}

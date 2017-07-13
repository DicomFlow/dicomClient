package br.ufpb.dicomflow.gui.business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.client.ClientConfig;

import br.ufpb.dicomflow.gui.application.ApplicationSession;
import br.ufpb.dicomflow.gui.dao.bean.AuthenticationBean;
import br.ufpb.dicomflow.gui.exception.DownloadException;
import br.ufpb.dicomflow.integrationAPI.message.xml.RequestPut;

public class DownloadProcessor {

	private static DownloadProcessor downloadProcessor = new DownloadProcessor();


	private DownloadProcessor(){

	}

	public static DownloadProcessor getInstance() {
		return downloadProcessor;
	}

	public String downloadImages(RequestPut requestPut) throws DownloadException{

		try {
			ClientConfig clientConfig = new ClientConfig();
			Client client = ClientBuilder.newClient(clientConfig);

			System.out.println("URL : " + requestPut.getUrl().getValue());

			WebTarget webTarget = client.target(requestPut.getUrl().getValue());
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_OCTET_STREAM);

			Response response = invocationBuilder.get();
			InputStream is = (InputStream)response.getEntity();

			byte[] SWFByteArray = IOUtils.toByteArray(is);

			AuthenticationBean authenticationBean = ApplicationSession.getInstance().getLoggedUser();

			String fileName = authenticationBean.getFolder() + File.separator + requestPut.getMessageID() + ".zip";

			FileOutputStream fos = new FileOutputStream(new File(fileName));
			fos.write(SWFByteArray);
			fos.flush();
			fos.close();

			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DownloadException(e.getMessage());
		} finally {

		}
	}
}

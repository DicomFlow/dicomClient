package br.ufpb.dicomflow.gui.business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
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

	public String download(RequestPut requestPut) throws DownloadException{
		InputStream in = null;

		AuthenticationBean authenticationBean = ApplicationSession.getInstance().getLoggedUser();
		String fileName = authenticationBean.getFolder() + File.separator + requestPut.getMessageID() + ".zip";

		try {
			URL url = new URL(requestPut.getUrl().getValue());

			if(url.getProtocol().toLowerCase().equals("https")){

					SSLContext ctx = SSLContext.getInstance("TLS");
					ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
			        SSLContext.setDefault(ctx);


			        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

			        con.setHostnameVerifier(new HostnameVerifier() {
			            @Override
			            public boolean verify(String arg0, SSLSession arg1) {
			                return true;
			            }
			        });

			        in = con.getInputStream();


			}else{
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				in = con.getInputStream();
			}

			if(in != null){

				java.io.File file = new java.io.File(fileName);
				FileOutputStream fout = new FileOutputStream(file, false);
				int i = 0;
				byte buffer[] = new byte[8192];


				while( (i = in.read(buffer)) != -1 ) {
					fout.write(buffer, 0, i);
				}
				in.close();
				fout.close();

			}

		} catch (NoSuchAlgorithmException | KeyManagementException| IOException e) {
			e.printStackTrace();
			throw new DownloadException(e.getMessage());
		}
		return fileName;








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


	private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}

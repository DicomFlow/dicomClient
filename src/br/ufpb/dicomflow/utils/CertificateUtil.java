package br.ufpb.dicomflow.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import br.ufpb.dicomflow.gui.exception.DicomFlowException;
import sun.security.tools.keytool.Main;
import sun.misc.BASE64Encoder;

public class CertificateUtil {



	public static void main(String[] args) {

		try {
			Certificate cert = getCertificate("dicomflowClient",  "C:"+File.separator+"Danilo"+File.separator+"dicomflowClient.jks", "changeit");

			System.out.println(cert);

			addCertificate(cert, "dicomflowClient", "C:"+File.separator+"Danilo"+File.separator+"teste.jks", "changeit");

			File certFile = exportCertificate("C:"+File.separator+"Danilo"+File.separator+"teste.cer", "teste", "C:"+File.separator+"Danilo"+File.separator+"teste.jks", "changeit");

			importCertificate(certFile, "teste", "C:"+File.separator+"Danilo"+File.separator+"dicomflowClient.jks", "changeit");

			System.out.println("DONE!!!!");
		} catch (DicomFlowException e) {
			e.printStackTrace();
		}


	}

	public static void generateSelfCert(String alias, String keypass, String keystore, String storepass, String name, String departament, String organization, String location, String state, String country, String validity) throws DicomFlowException{


		try {
            String comandos[] = {"-selfcert", "-alias", alias, "-genkey", "-keyalg", "RSA", "-keypass", keypass, "-keystore", keystore, "-storepass", storepass, "-validity", validity,
            		             "-dname", "CN="+name+", OU="+departament+", O="+organization+", L="+location+", ST="+state+", C="+ country};
            Main.main(comandos);
        } catch (Exception ex) {
        	ex.printStackTrace();
        	throw new DicomFlowException(ex.getMessage());
        }

	}

	public boolean containsCertificate(String alias, String keystore, String storepass) throws DicomFlowException{

		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");
			InputStream is = new FileInputStream(keystore);
			keyStore.load(is, storepass.toCharArray());
			is.close();

			return keyStore.containsAlias(alias);


		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			e.printStackTrace();
			throw new DicomFlowException(e.getMessage());
		}

	}

	public static Certificate getCertificate(String alias, String keystore, String storepass) throws DicomFlowException{


		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");
			InputStream is = new FileInputStream(keystore);
			keyStore.load(is, storepass.toCharArray());
			is.close();

			Certificate cert = keyStore.getCertificate(alias);

			return cert;


		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			e.printStackTrace();
			throw new DicomFlowException(e.getMessage());
		}

	}

	public static void addCertificate(Certificate certificate, String alias, String keystore, String storepass) throws DicomFlowException{
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");
			InputStream is = new FileInputStream(keystore);
			keyStore.load(is, storepass.toCharArray());
			is.close();

			keyStore.setCertificateEntry(alias, certificate);

			//store certificate object
		    OutputStream out = new FileOutputStream(keystore);
		    keyStore.store(out, storepass.toCharArray());
		    out.close();

		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			e.printStackTrace();
			throw new DicomFlowException(e.getMessage());
		}


	}

	public static void importCertificate(File certFile, String alias, String keystore, String storepass) throws DicomFlowException{
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");
			InputStream is = new FileInputStream(keystore);
			keyStore.load(is, storepass.toCharArray());
			is.close();

			//create a certificate object
			InputStream certIn = new FileInputStream(certFile);
			BufferedInputStream bis = new BufferedInputStream(certIn);
		    CertificateFactory cf = CertificateFactory.getInstance("X.509");
		    while (bis.available() > 0) {
		        Certificate cert = cf.generateCertificate(bis);
		        keyStore.setCertificateEntry(alias, cert);
		    }
		    bis.close();

		    //store certificate object
		    OutputStream out = new FileOutputStream(keystore);
		    keyStore.store(out, storepass.toCharArray());
		    out.close();

		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			e.printStackTrace();
			throw new DicomFlowException(e.getMessage());
		}


	}

	public static File exportCertificate(String path, String alias, String keystore, String storepass) throws DicomFlowException{

		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");
			InputStream is = new FileInputStream(keystore);
			keyStore.load(is, storepass.toCharArray());
			is.close();

			File certFile = new File(path);


			//retrieve certificate object
		    Certificate certificate = keyStore.getCertificate(alias);


		    //write certificate object on file
		    byte[] buf = certificate.getEncoded();
		    FileOutputStream os = new FileOutputStream(certFile);
		    os.write(buf);
		    os.close();

		    Writer wr = new OutputStreamWriter(os, Charset.forName("UTF-8"));
		    wr.write(new BASE64Encoder().encode(buf));

		    return certFile;

		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			e.printStackTrace();
			throw new DicomFlowException(e.getMessage());
		}

	}

}

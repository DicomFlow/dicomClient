package br.ufpb.dicomflow.utils;

import sun.security.tools.keytool.*;

public class CertificateUtil {

	public static void main(String args[]) {
        try {
            String comandos[] = {"-genkey", "-alias", "guj", "-keyalg", "RSA", "-keypass", "changeit", "-storepass", "changeit", "-keystore", "C:/Danilo/danilo.jks", "-dname", "CN=Danilo, OU=Alexandre, O=Barbosa, L=Joao Pessoa, ST=Paraiba, C=BR"};
            Main.main(comandos);
        } catch (Exception ex) {

        }
    }

}

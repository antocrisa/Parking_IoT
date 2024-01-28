package com.example.mosquittoclient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.Security;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SslUtil {
	
	@Value("${parkinglot.mqtt.cert.path}")
	private String caCertPath;
	
    //richiama il metodo per la creazione di una socket factory
    public SSLSocketFactory getSSLSocketFactory() {
    	return getSocketFactory(caCertPath);
    }

    //carica il certificato
    private X509CertificateHolder loadCACert(String caCrtFile) throws IOException {
        PEMParser reader =
                new PEMParser(
                        new InputStreamReader(new ByteArrayInputStream(
                                Files.readAllBytes(Paths.get(caCrtFile)))));
        X509CertificateHolder caCert = (X509CertificateHolder) reader.readObject();
        reader.close();

        return caCert;
    }

    //crea socket factory
    private SSLSocketFactory getSocketFactory(final String caCrtFile) {

        try {
            Security.addProvider(new BouncyCastleProvider());

            JcaX509CertificateConverter jcaX509CertificateConverter = new JcaX509CertificateConverter();

            X509CertificateHolder caCertificateHolder = loadCACert(caCrtFile);
            
            // CA certificate is used to authenticate server
            KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
            caKs.load(null, null);
            caKs.setCertificateEntry("ca-certificate", jcaX509CertificateConverter.getCertificate(caCertificateHolder));
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(caKs);

            // finally, create SSL socket factory
            SSLContext context = SSLContext.getInstance("TLSv1.2");

            context.init(null, tmf.getTrustManagers(), null);

            return context.getSocketFactory();
        } catch (Exception ex) {
            return null;
        }
    }
}

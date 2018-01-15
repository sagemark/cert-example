package com.sausage.certexampleclient;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.KeyStore;

@SpringBootApplication
@RestController
public class CertExampleClientApplication {

	private RestTemplate restTemplate;

	public CertExampleClientApplication(@Value("${cert}")Resource  certificate, @Value("${password}")String password) throws Exception{
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(certificate.getInputStream(),password.toCharArray());
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
				new SSLContextBuilder()
						.loadTrustMaterial(null, new TrustSelfSignedStrategy())
						.loadKeyMaterial(keyStore, password.toCharArray())
						.build(),
				NoopHostnameVerifier.INSTANCE);

		HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();

		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		restTemplate = new RestTemplate(requestFactory);
	}

	@GetMapping("/forward-to-cert")
	String forward() throws Exception {


		return restTemplate.getForEntity("https://localhost:8443/need-cert",String.class).getBody();
	}

	public static void main(String[] args) {
		SpringApplication.run(CertExampleClientApplication.class, args);
	}
}

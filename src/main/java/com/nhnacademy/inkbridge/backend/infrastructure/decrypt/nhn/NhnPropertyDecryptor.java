package com.nhnacademy.inkbridge.backend.infrastructure.decrypt.nhn;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.nhnacademy.inkbridge.backend.infrastructure.decrypt.PropertyDecryptor;

public class NhnPropertyDecryptor implements PropertyDecryptor {

	private final NhnKeyProperties properties;

	public NhnPropertyDecryptor(NhnKeyProperties properties) {
		this.properties = properties;
	}

	@Override
	public String decrypt(String encryptedKey) {
		try {
			KeyStore clientStore = KeyStore.getInstance("PKCS12");
			InputStream result = new ClassPathResource("key-store.p12").getInputStream();
			clientStore.load(result, properties.getPassword().toCharArray());

			SSLContext sslContext = SSLContextBuilder.create()
				.setProtocol("TLS")
				.loadKeyMaterial(clientStore, properties.getPassword().toCharArray())
				.loadTrustMaterial(new TrustSelfSignedStrategy())
				.build();

			SSLConnectionSocketFactory sslConnectionSocketFactory =
				new SSLConnectionSocketFactory(sslContext);
			CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(sslConnectionSocketFactory)
				.build();

			HttpComponentsClientHttpRequestFactory requestFactory =
				new HttpComponentsClientHttpRequestFactory(httpClient);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(List.of(MediaType.APPLICATION_JSON));

			RestTemplate restTemplate = new RestTemplate(requestFactory);

			URI uri = UriComponentsBuilder
				.fromUriString(properties.getUrl())
				.path(properties.getPath())
				.encode()
				.build()
				.expand(properties.getAppKey(), encryptedKey)
				.toUri();

			return Objects.requireNonNull(restTemplate.exchange(uri,
						HttpMethod.GET,
						new HttpEntity<>(headers),
						NhnKeyResponse.class)
					.getBody())
				.getBody()
				.getSecret();
		} catch (KeyStoreException | IOException | CertificateException
				 | NoSuchAlgorithmException
				 | UnrecoverableKeyException
				 | KeyManagementException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}

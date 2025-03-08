package com.nhnacademy.inkbridge.backend.infrastructure.decrypt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import com.nhnacademy.inkbridge.backend.infrastructure.decrypt.nhn.NhnKeyProperties;
import com.nhnacademy.inkbridge.backend.infrastructure.decrypt.nhn.NhnPropertyDecryptor;

public class PropertiesDecryptProcessor implements EnvironmentPostProcessor {

	private final EncryptedPropertyDetector propertyDetector = new EncryptedPropertyDetector();
	private PropertyDecryptor propertyDecryptor;

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		Map<String, Object> decryptedProperties = new HashMap<>();

		if (!propertyDetector.existsEncryptedProperty(environment))
			return;

		initPropertyDecryptor(environment);

		for (PropertySource<?> propertySource : environment.getPropertySources()) {
			if (propertySource instanceof EnumerablePropertySource) {
				EnumerablePropertySource<?> enumerablePropertySource = (EnumerablePropertySource<?>)propertySource;
				for (String propertyName : enumerablePropertySource.getPropertyNames()) {
					String propertyValue = environment.getProperty(propertyName);
					if (propertyDetector.isEncrypted(propertyValue)) {
						String unwrapEncryptedValue = propertyDetector.unwrapEncryptedValue(propertyValue);
						String decryptedValue = propertyDecryptor.decrypt(unwrapEncryptedValue);
						decryptedProperties.put(propertyName, decryptedValue);
					}
				}
			}
		}

		environment.getPropertySources().addFirst(new MapPropertySource("decryptedProperties", decryptedProperties));
	}

	private void initPropertyDecryptor(ConfigurableEnvironment environment) {
		Binder binder = Binder.get(environment);
		NhnKeyProperties properties = binder.bind("secure-key-manager", NhnKeyProperties.class)
			.orElseThrow(() -> new IllegalStateException("properties값 매핑 실패"));

		propertyDecryptor = new NhnPropertyDecryptor(properties);
	}
}

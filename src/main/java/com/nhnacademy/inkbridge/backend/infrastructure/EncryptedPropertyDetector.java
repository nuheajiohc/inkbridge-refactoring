package com.nhnacademy.inkbridge.backend.infrastructure;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;

public class EncryptedPropertyDetector {

	private String prefix = "ENC(";
	private String suffix = ")";

	public EncryptedPropertyDetector() {
	}

	public EncryptedPropertyDetector(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}

	public boolean isEncrypted(String property) {
		if (property == null) {
			return false;
		} else {
			String trimmedValue = property.trim();
			return trimmedValue.startsWith(this.prefix) && trimmedValue.endsWith(this.suffix);
		}
	}

	public String unwrapEncryptedValue(String property) {
		return property.substring(this.prefix.length(), property.length() - this.suffix.length());
	}

	public boolean existsEncryptedProperty(ConfigurableEnvironment environment) {
		for (PropertySource<?> propertySource : environment.getPropertySources()) {
			if (propertySource instanceof EnumerablePropertySource) {
				EnumerablePropertySource<?> enumerablePropertySource = (EnumerablePropertySource<?>)propertySource;
				for (String propertyName : enumerablePropertySource.getPropertyNames()) {
					String propertyValue = environment.getProperty(propertyName);
					if (isEncrypted(propertyValue)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}

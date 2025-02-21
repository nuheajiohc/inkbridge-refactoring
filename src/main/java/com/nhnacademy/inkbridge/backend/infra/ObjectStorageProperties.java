package com.nhnacademy.inkbridge.backend.infra;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

/**
 * class: ObjectStorageProperty.
 *
 * @author jeongbyeonghun
 * @version 3/12/24
 */

@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "object-storage")
public class ObjectStorageProperties {

    private final String storageUrl;
    private final String containerName;
    private final String password;
    private final String username;
    private final String tenantId;
    private final String authUrl;

    private final KeyReader keyReader;

    public String getStorageUrl() {
        return keyReader.readKey(storageUrl);
    }

    public String getContainerName() {
        return keyReader.readKey(containerName);
    }

    public String getPassword() {
        return keyReader.readKey(password);
    }

    public String getUsername() {
        return keyReader.readKey(username);
    }

    public String getTenantId() {
        return keyReader.readKey(tenantId);
    }

    public String getAuthUrl() {
        return keyReader.readKey(authUrl);
    }
}

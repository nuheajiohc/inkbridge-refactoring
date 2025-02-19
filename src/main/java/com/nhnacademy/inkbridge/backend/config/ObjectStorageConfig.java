package com.nhnacademy.inkbridge.backend.config;

import com.nhnacademy.inkbridge.backend.property.ObjectStorageProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * class: ObjectStorageConfig.
 *
 * @author jeongbyeonghun
 * @version 3/22/24
 */
@Component
@RequiredArgsConstructor
public class ObjectStorageConfig {

    private final ObjectStorageProperty objectStorageProperty;
    private final KeyConfig keyConfig;

    @Bean
    public String getStorageUrl() {
        return keyConfig.keyStore(objectStorageProperty.getStorageUrl());
    }

    @Bean
    public String getContainerName() {
        return keyConfig.keyStore(objectStorageProperty.getContainerName());
    }

    @Bean
    public String getPassword() {
        return keyConfig.keyStore(objectStorageProperty.getPassword());
    }

    @Bean
    public String getUsername() {
        return keyConfig.keyStore(objectStorageProperty.getUsername());
    }

    @Bean
    public String getTenantId() {
        return keyConfig.keyStore(objectStorageProperty.getTenantId());
    }

    @Bean
    public String getAuthUrl() {
        return keyConfig.keyStore(objectStorageProperty.getAuthUrl());
    }
}

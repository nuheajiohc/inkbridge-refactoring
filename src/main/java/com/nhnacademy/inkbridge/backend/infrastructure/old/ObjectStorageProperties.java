package com.nhnacademy.inkbridge.backend.infrastructure.old;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * class: ObjectStorageProperty.
 *
 * @author jeongbyeonghun
 * @version 3/12/24
 */

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "object-storage")
public class ObjectStorageProperties {

    private final String storageUrl;
    private final String containerName;
    private final String password;
    private final String username;
    private final String tenantId;
    private final String authUrl; //identity 의미?
}

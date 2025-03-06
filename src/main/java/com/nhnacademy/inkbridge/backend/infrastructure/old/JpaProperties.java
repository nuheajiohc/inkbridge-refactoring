package com.nhnacademy.inkbridge.backend.infrastructure.old;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * class: JpaProperty.
 *
 * @author jangjaehun
 * @version 2024/02/23
 */

@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties(prefix = "datasource")
@Getter
public class JpaProperties {

	private String username;

	private String password;

	private String url;

}

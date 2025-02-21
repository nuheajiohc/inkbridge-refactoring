package com.nhnacademy.inkbridge.backend.infra;

import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * class: JpaConfig.
 *
 * @author jangjaehun
 * @version 2024/02/23
 */
@Configuration
@AllArgsConstructor
public class JpaConfig {

    private JpaProperties jpaProperties;
    private KeyReader keyReader;
    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl(keyReader.readKey(jpaProperties.getUrl()));
        dataSource.setUsername(keyReader.readKey(jpaProperties.getUsername()));
        dataSource.setPassword(keyReader.readKey(jpaProperties.getPassword()));

        dataSource.setInitialSize(20);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(20);
        dataSource.setMaxTotal(20);

        dataSource.setMaxWaitMillis(20);

        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setTestWhileIdle(true);

        return dataSource;
    }
}

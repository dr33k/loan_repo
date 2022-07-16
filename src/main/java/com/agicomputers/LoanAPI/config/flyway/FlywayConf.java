package com.agicomputers.LoanAPI.config.flyway;

import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConf implements FlywayConfigurationCustomizer {

    @Value("${app.flyway.licenseKey}")
    String licenseKey;

    @Override
    public void customize(FluentConfiguration configuration) {
        configuration

                .licenseKey(licenseKey)
                .baselineOnMigrate(true)
                .validateMigrationNaming(true)

                ;
    }

    @Bean
    public FlywayConfigurationCustomizer configurationCustomizer(){return this;}
}

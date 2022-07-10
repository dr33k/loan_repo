package com.agicomputers.LoanAPI.config.flyway;

import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;

@FlywayDataSource
public class FlywayConfiguration implements FlywayConfigurationCustomizer {
    @Override
    public void customize(FluentConfiguration configuration) {
        configuration.validateMigrationNaming(true)
                .baselineOnMigrate(true)
                ;
    }
}

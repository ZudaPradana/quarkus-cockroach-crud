package org.zydd;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.sql.DataSource;

@Singleton
public class DatabaseInitialization {

    @Inject
    DataSource dataSource;

    void onStart(@Observes StartupEvent event) {
        try {
            System.out.println("Testing database connection...");
            dataSource.getConnection().close();
            System.out.println("Database connection successful.");
        } catch (Exception e) {
            System.err.println("Error testing database connection: " + e.getMessage());
            // You can log more details or throw an exception here.
        }
    }
}

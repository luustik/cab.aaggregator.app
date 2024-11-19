package cab.aggregator.app.driverservice.integration;

import org.testcontainers.containers.PostgreSQLContainer;

import static cab.aggregator.app.driverservice.utils.DriverConstants.POSTGRESQL_CONTAINER;


public class PostgresContainer extends PostgreSQLContainer<PostgresContainer>{

    private static PostgresContainer container;

    private PostgresContainer() {
        super(POSTGRESQL_CONTAINER);
    }

    public static PostgresContainer getInstance() {
        if (container == null) {
            container = new PostgresContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}

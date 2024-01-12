package fi.sovellustalo.accounts;

import org.testcontainers.containers.PostgreSQLContainer;

public class AccountsPostgresqlContainer extends PostgreSQLContainer<AccountsPostgresqlContainer> {
    private static final String IMAGE_VERSION = "postgres:16.1";
    private static AccountsPostgresqlContainer container;

    private AccountsPostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    public static AccountsPostgresqlContainer instance() {
        if (container == null) {
            container = new AccountsPostgresqlContainer();
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

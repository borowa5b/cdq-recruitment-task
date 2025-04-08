package pl.borowa5b.cdq_recruitment_task.helper;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

class IntegrationTestLifecycleManager extends SpringExtension implements ExtensionContext.Store.CloseableResource {

    private static Boolean IS_POSTGRES_CONTAINER_RUNNING = false;

    private final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("cdq-recruitment-task")
            .withUsername("postgres")
            .withPassword("postgres");

    @Override
    public void beforeAll(final ExtensionContext context) {
        if (!IS_POSTGRES_CONTAINER_RUNNING) {
            IS_POSTGRES_CONTAINER_RUNNING = true;
            postgreSQLContainer.start();
            setPostgreSQLProperties();
        }
    }

    @Override
    public void beforeEach(final ExtensionContext context) {
        ((Database) getApplicationContext(context).getBean("database")).prepare();
    }

    @Override
    public void close() throws Throwable {
        if (IS_POSTGRES_CONTAINER_RUNNING) {
            IS_POSTGRES_CONTAINER_RUNNING = false;
            postgreSQLContainer.stop();
        }
    }

    private void setPostgreSQLProperties() {
        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());

    }
}

package pl.borowa5b.cdq_recruitment_task.helper;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;

class IntegrationTestLifecycleManager extends SpringExtension implements ExtensionContext.Store.CloseableResource {

    private static Boolean IS_MONGO_CONTAINER_RUNNING = false;

    private final MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo");

    @Override
    public void beforeAll(final ExtensionContext context) {
        if (!IS_MONGO_CONTAINER_RUNNING) {
            IS_MONGO_CONTAINER_RUNNING = true;
            mongoDbContainer.start();
            setMongoDbProperties();
        }
    }

    @Override
    public void beforeEach(final ExtensionContext context) {
        ((Database) getApplicationContext(context).getBean("database")).prepare();
    }

    @Override
    public void close() throws Throwable {
        if (IS_MONGO_CONTAINER_RUNNING) {
            IS_MONGO_CONTAINER_RUNNING = false;
            mongoDbContainer.stop();
        }
    }

    private void setMongoDbProperties() {
        System.setProperty("spring.data.mongodb.uri", mongoDbContainer.getReplicaSetUrl());
    }
}

package pl.borowa5b.cdq_recruitment_task.infrastructure.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pl.borowa5b.cdq_recruitment_task.domain.model.Task;
import pl.borowa5b.cdq_recruitment_task.domain.repository.TaskRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.infrastructure.entity.TaskEntity;
import pl.borowa5b.cdq_recruitment_task.infrastructure.entity.TaskResultEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DefaultTaskRepository implements TaskRepository {

    private final SpringMongoTaskRepository repository;
    private final MongoTemplate mongoTemplate;

    @Override
    public Task findBy(final TaskId taskId) {
        return repository.findById(taskId.value()).map(TaskEntity::toDomain).orElse(null);
    }

    @Override
    public List<Task> findAll(final Task probe, final Pageable pageable) {
        final var matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase();
        final var example = Example.of(TaskEntity.fromDomain(probe), matcher);
        final var query = new Query()
                .addCriteria(new Criteria().alike(example))
                .with(pageable)
                .limit(pageable.getPageSize() + 1);
        return mongoTemplate.find(query, TaskEntity.class).stream().map(TaskEntity::toDomain).toList();
    }

    @Override
    public Task save(final Task task) {
        return repository.save(TaskEntity.fromDomain(task)).toDomain();
    }

    @Override
    public void update(final Task task) {
        final var entity = repository.findById(task.getId().value()).orElseThrow(() -> new IllegalStateException("Not found"));
        final var taskStatus = task.getStatus();
        entity.setProgress(taskStatus.getProgress());
        entity.setStage(taskStatus.getStage());
        entity.setResult(task.getResults().stream().map(TaskResultEntity::fromDomain).collect(Collectors.toList()));
        repository.save(entity);
    }
}

@Repository
interface SpringMongoTaskRepository extends MongoRepository<TaskEntity, String> {
}
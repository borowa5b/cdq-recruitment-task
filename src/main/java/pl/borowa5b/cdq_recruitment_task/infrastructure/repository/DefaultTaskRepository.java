package pl.borowa5b.cdq_recruitment_task.infrastructure.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pl.borowa5b.cdq_recruitment_task.domain.model.Task;
import pl.borowa5b.cdq_recruitment_task.domain.repository.TaskRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.infrastructure.entity.TaskEntity;
import pl.borowa5b.cdq_recruitment_task.infrastructure.entity.TaskResultEntity;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DefaultTaskRepository implements TaskRepository {

    private final SpringJpaTaskRepository repository;

    @Override
    public Task findBy(final TaskId taskId) {
        return repository.findById(taskId.value()).map(TaskEntity::toDomain).orElse(null);
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
interface SpringJpaTaskRepository extends JpaRepository<TaskEntity, String> {
}
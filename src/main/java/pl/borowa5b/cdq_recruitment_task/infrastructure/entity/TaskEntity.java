package pl.borowa5b.cdq_recruitment_task.infrastructure.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.borowa5b.cdq_recruitment_task.domain.model.Task;
import pl.borowa5b.cdq_recruitment_task.domain.model.TaskStatus;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Setter
@Getter
@Document(collection = "task")
@NoArgsConstructor(force = true)
public class TaskEntity {

    @Id
    private String id;
    private Stage stage;
    private Integer progress;
    private List<TaskResultEntity> result;
    @CreatedDate
    private OffsetDateTime creationDate;
    @LastModifiedDate
    private OffsetDateTime modificationDate;
    @Version
    private Long entityVersion;

    private TaskEntity(final String id,
                       final Stage stage,
                       final Integer progress,
                       final List<TaskResultEntity> result) {
        this.id = id;
        this.stage = stage;
        this.progress = progress;
        this.result = result;
    }

    public Task toDomain() {
        return new Task(
                new TaskId(id),
                new TaskStatus(stage, progress),
                Objects.requireNonNull(result).stream().map(TaskResultEntity::toDomain).collect(Collectors.toList()),
                creationDate
        );
    }

    public static TaskEntity fromDomain(final Task task) {
        return new TaskEntity(
                task.getId() != null ? task.getId().value() : null,
                task.getStage(),
                task.getProgress(),
                task.getResults() != null
                        ? task.getResults().stream().map(TaskResultEntity::fromDomain).collect(Collectors.toList())
                        : null
        );
    }
}

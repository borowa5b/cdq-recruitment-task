package pl.borowa5b.cdq_recruitment_task.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.borowa5b.cdq_recruitment_task.domain.model.Task;
import pl.borowa5b.cdq_recruitment_task.domain.model.TaskStatus;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Entity(name = "task")
@NoArgsConstructor(force = true)
public class TaskEntity {

    @Id
    private final String id;
    @Enumerated(EnumType.STRING)
    @Setter
    private Stage stage;
    @Setter
    private int progress;
    @Setter
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TaskResultEntity> result;
    private OffsetDateTime creationDate;
    private OffsetDateTime modificationDate;
    @Version
    private Long entityVersion;

    @PrePersist
    public void prePersist() {
        creationDate = OffsetDateTime.now(ZoneOffset.UTC);
        modificationDate = OffsetDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate
    public void preUpdate() {
        modificationDate = OffsetDateTime.now(ZoneOffset.UTC);
    }

    private TaskEntity(final String id,
                       final Stage stage,
                       final int progress,
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
                Objects.requireNonNull(result).stream().map(TaskResultEntity::toDomain).collect(Collectors.toList())
        );
    }

    public static TaskEntity fromDomain(final Task task) {
        return new TaskEntity(
                task.getId().value(),
                task.getStage(),
                task.getProgress(),
                task.getResults() != null
                        ? task.getResults().stream().map(TaskResultEntity::fromDomain).collect(Collectors.toList())
                        : null
        );
    }
}

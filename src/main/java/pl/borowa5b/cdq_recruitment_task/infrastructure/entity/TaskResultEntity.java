package pl.borowa5b.cdq_recruitment_task.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Classification;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskResult;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskResultId;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Getter
@Entity(name = "task_result")
@NoArgsConstructor(force = true)
public class TaskResultEntity {

    @Id
    private final String id;
    private final String taskId;
    private final String valueBefore;
    private final String currentValue;
    private final Double dissimilarity;
    @Enumerated(EnumType.STRING)
    private final Classification classification;
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

    private TaskResultEntity(final String id,
                             final String taskId,
                             final String valueBefore,
                             final String currentValue,
                             final Double dissimilarity,
                             final Classification classification) {
        this.id = id;
        this.taskId = taskId;
        this.valueBefore = valueBefore;
        this.currentValue = currentValue;
        this.dissimilarity = dissimilarity;
        this.classification = classification;
    }

    public TaskResult toDomain() {
        return new TaskResult(
                new TaskResultId(id),
                new TaskId(taskId),
                valueBefore,
                currentValue,
                dissimilarity,
                classification
        );
    }

    public static TaskResultEntity fromDomain(final TaskResult taskResult) {
        if (taskResult == null) {
            return null;
        }

        return new TaskResultEntity(
                taskResult.id().value(),
                taskResult.taskId().value(),
                taskResult.valueBefore(),
                taskResult.currentValue(),
                taskResult.dissimilarity(),
                taskResult.classification()
        );
    }
}

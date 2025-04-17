package pl.borowa5b.cdq_recruitment_task.infrastructure.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Classification;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskResult;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskResultId;

import java.time.OffsetDateTime;

@Setter
@Getter
@NoArgsConstructor(force = true)
@Document(collection = "task_result")
public class TaskResultEntity {

    @Id
    private String id;
    private String taskId;
    private String valueBefore;
    private String currentValue;
    private Double dissimilarity;
    private Classification classification;
    @CreatedDate
    private OffsetDateTime creationDate;
    @LastModifiedDate
    private OffsetDateTime modificationDate;
    @Version
    private Long entityVersion;

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

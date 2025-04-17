package pl.borowa5b.cdq_recruitment_task.application.response;

import pl.borowa5b.cdq_recruitment_task.domain.model.Task;

import java.time.OffsetDateTime;
import java.util.List;

public record TaskResponse(String id,
                           TaskStatusResponse status,
                           OffsetDateTime creationDate,
                           List<TaskResultResponse> results) {

    public static TaskResponse fromDomain(final Task task) {
        return new TaskResponse(
                task.getId().value(),
                TaskStatusResponse.of(task.getStage(), task.getProgress()),
                task.getCreationDate(),
                task.getResults().stream().map(TaskResultResponse::fromDomain).toList()
        );
    }
}

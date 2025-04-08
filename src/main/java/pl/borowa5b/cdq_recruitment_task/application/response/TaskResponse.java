package pl.borowa5b.cdq_recruitment_task.application.response;

import pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks.TaskDetails;

import java.time.OffsetDateTime;
import java.util.List;

public record TaskResponse(String id,
                           TaskStatusResponse status,
                           OffsetDateTime creationDate,
                           List<TaskResultResponse> results) {

    public static TaskResponse fromDetails(final TaskDetails task) {
        return new TaskResponse(
                task.id(),
                TaskStatusResponse.of(task.stage(), task.progress()),
                task.creationDate(),
                task.results().stream().map(TaskResultResponse::fromDetails).toList()
        );
    }
}

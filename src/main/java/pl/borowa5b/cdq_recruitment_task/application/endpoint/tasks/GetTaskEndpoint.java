package pl.borowa5b.cdq_recruitment_task.application.endpoint.tasks;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.borowa5b.cdq_recruitment_task.application.response.TaskResponse;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks.TasksFinder;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

@TasksEndpoint
@AllArgsConstructor
public class GetTaskEndpoint {

    private final TasksFinder finder;

    @Operation(summary = "Get task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task fetched", content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Request validation failed", content = @Content()),
    })
    @GetMapping(value = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    TaskResponse getTask(
            @Parameter(description = "Task identifier", example = "TSK12432532")
            @PathVariable final String taskId
    ) {
        final var taskDetails = finder.findBy(new TaskId(taskId));
        return TaskResponse.fromDomain(taskDetails);
    }
}

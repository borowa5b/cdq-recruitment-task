package pl.borowa5b.cdq_recruitment_task.application.endpoint.tasks;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import pl.borowa5b.cdq_recruitment_task.application.request.filter.GetTasksFilter;
import pl.borowa5b.cdq_recruitment_task.application.request.filter.PageFilter;
import pl.borowa5b.cdq_recruitment_task.application.response.PageResponse;
import pl.borowa5b.cdq_recruitment_task.application.response.TaskResponse;
import pl.borowa5b.cdq_recruitment_task.domain.exception.ValidationException;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.AggregatingValidationExceptionHandler;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks.TasksFinder;

@TasksEndpoint
@AllArgsConstructor
public class GetTasksEndpoint {

    private final TasksFinder finder;

    @Operation(summary = "Get all tasks")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks fetched", content = @Content(schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Request validation failed", content = @Content()),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<TaskResponse> getTasks(final GetTasksFilter filter, final PageFilter pageFilter) {
        validate(filter, pageFilter);
        final var tasksDetails = finder.findBy(filter.toQuery(), pageFilter.toPage());
        final var data = tasksDetails.data().stream().map(TaskResponse::fromDomain).toList();
        return new PageResponse<>(data, tasksDetails.pagination());
    }

    private void validate(final GetTasksFilter filter, final PageFilter pageFilter) {
        final var validationExceptionHandler = new AggregatingValidationExceptionHandler();

        filter.validate(validationExceptionHandler);
        pageFilter.validate(validationExceptionHandler);

        if (validationExceptionHandler.hasErrors()) {
            throw new ValidationException(validationExceptionHandler.getErrors());
        }
    }
}

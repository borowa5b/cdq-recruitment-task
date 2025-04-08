package pl.borowa5b.cdq_recruitment_task.application.request.filter;

import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ValidationExceptionHandler;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.Validator;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks.TaskQuery;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

@ParameterObject
public record GetTasksFilter(

        @Parameter(description = "Task identifier", example = "TSK1214242443242")
        String taskId,

        @Parameter(description = "Task stage", example = "CREATED")
        String stage
) {
    public void validate(final ValidationExceptionHandler exceptionHandler) {
        if (taskId != null) {
            TaskId.validate(taskId, "tasksId", exceptionHandler);
        }

        if (stage != null) {
            Validator.isValidEnumValue(Stage.class, stage, "stage", exceptionHandler);
        }
    }

    public TaskQuery toQuery() {
        return new TaskQuery(
                taskId != null ? new TaskId(taskId) : null,
                stage != null ? Stage.valueOf(stage) : null);
    }
}

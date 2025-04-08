package pl.borowa5b.cdq_recruitment_task.domain.exception;

import org.zalando.problem.Status;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

public class TaskNotFoundException extends DomainException {

    public TaskNotFoundException(final TaskId taskId) {
        super(Status.NOT_FOUND, "Task with id " + taskId.value() + " not found");
    }
}

package pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks;

import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

public record TaskQuery(TaskId taskId, Stage stage) {
}

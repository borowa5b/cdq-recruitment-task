package pl.borowa5b.cdq_recruitment_task.domain.repository;

import pl.borowa5b.cdq_recruitment_task.domain.model.Task;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

public interface TaskRepository {

    Task findBy(final TaskId taskId);

    Task save(final Task task);

    void update(final Task task);
}

package pl.borowa5b.cdq_recruitment_task.domain.repository;

import org.springframework.data.domain.Pageable;
import pl.borowa5b.cdq_recruitment_task.domain.model.Task;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

import java.util.List;

public interface TaskRepository {

    Task findBy(final TaskId taskId);

    List<Task> findAll(final Task probe, final Pageable pageable);

    Task save(final Task task);

    void update(final Task task);
}

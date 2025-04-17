package pl.borowa5b.cdq_recruitment_task.infrastructure.repository.read;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import pl.borowa5b.cdq_recruitment_task.domain.exception.TaskNotFoundException;
import pl.borowa5b.cdq_recruitment_task.domain.model.Task;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.Page;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.Pageable;
import pl.borowa5b.cdq_recruitment_task.domain.repository.TaskRepository;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks.TaskQuery;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks.TasksFinder;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

@Component
@AllArgsConstructor
public class DefaultTasksFinder implements TasksFinder {

    private final TaskRepository repository;

    @Override
    public Pageable<Task> findBy(final TaskQuery query, final Page page) {
        final var pageable = PageRequest.of(
                page.number() - 1,
                page.size(),
                page.order().getDirection(),
                page.order().getProperty()
        );
        final var data = repository.findAll(query.toTask(), pageable);
        return Pageable.of(data, page);
    }

    @Override
    public Task findBy(final TaskId taskId) {
        final var task = repository.findBy(taskId);
        if (task == null) {
            throw new TaskNotFoundException(taskId);
        }
        return task;
    }
}

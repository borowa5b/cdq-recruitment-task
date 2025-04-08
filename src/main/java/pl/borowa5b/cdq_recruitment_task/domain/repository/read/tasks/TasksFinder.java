package pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks;

import pl.borowa5b.cdq_recruitment_task.domain.pagination.Page;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.Pageable;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

public interface TasksFinder {

    Pageable<TaskDetails> findBy(final TaskQuery query, final Page page);

    TaskDetails findBy(final TaskId personId);
}


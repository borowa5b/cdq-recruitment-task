package pl.borowa5b.cdq_recruitment_task.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.borowa5b.cdq_recruitment_task.domain.generator.TaskIdGenerator;
import pl.borowa5b.cdq_recruitment_task.domain.model.Task;
import pl.borowa5b.cdq_recruitment_task.domain.repository.TaskRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

@Component
@AllArgsConstructor
public class TaskCreator {

    private final TaskIdGenerator taskIdGenerator;
    private final TaskRepository taskRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TaskId create() {
        final var taskId = taskIdGenerator.generate();
        final var task = new Task(taskId);
        return taskRepository.save(task).getId();
    }
}

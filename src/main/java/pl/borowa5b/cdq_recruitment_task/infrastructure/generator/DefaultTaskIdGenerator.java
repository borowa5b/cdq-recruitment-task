package pl.borowa5b.cdq_recruitment_task.infrastructure.generator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.borowa5b.cdq_recruitment_task.domain.generator.IdGenerator;
import pl.borowa5b.cdq_recruitment_task.domain.generator.TaskIdGenerator;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

@Component
@AllArgsConstructor
public class DefaultTaskIdGenerator implements TaskIdGenerator {

    private final IdGenerator idGenerator;

    @Override
    public TaskId generate() {
        return new TaskId(idGenerator.generate(TaskId.PREFIX));
    }
}

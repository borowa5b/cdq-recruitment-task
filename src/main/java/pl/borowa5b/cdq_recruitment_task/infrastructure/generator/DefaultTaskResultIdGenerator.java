package pl.borowa5b.cdq_recruitment_task.infrastructure.generator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.borowa5b.cdq_recruitment_task.domain.generator.IdGenerator;
import pl.borowa5b.cdq_recruitment_task.domain.generator.TaskResultIdGenerator;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskResultId;

@Component
@AllArgsConstructor
public class DefaultTaskResultIdGenerator implements TaskResultIdGenerator {

    private final IdGenerator idGenerator;

    @Override
    public TaskResultId generate() {
        return new TaskResultId(idGenerator.generate(TaskResultId.PREFIX));
    }
}

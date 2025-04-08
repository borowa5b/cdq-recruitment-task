package pl.borowa5b.cdq_recruitment_task.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.borowa5b.cdq_recruitment_task.domain.generator.TaskResultIdGenerator;
import pl.borowa5b.cdq_recruitment_task.domain.repository.TaskRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskResult;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TaskClassificationManager {

    private static final int PROGRESS_STEP = 25;

    @Value("${cdq-recruitment-task.slowdown-in-millis}")
    private int slowdownInMillis;

    private final TaskClassificator classificator;
    private final TaskRepository taskRepository;
    private final TaskResultIdGenerator taskResultIdGenerator;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void beginClassification(final TaskId taskId,
                                    final String valueBefore,
                                    final String currentValue) {
        final var task = taskRepository.findBy(taskId);
        if (!Objects.equals(valueBefore, currentValue)) {
            final var classification = classificator.classificate(valueBefore, currentValue);
            final var taskResultId = taskResultIdGenerator.generate();
            final var taskResult = new TaskResult(
                    taskResultId,
                    taskId,
                    valueBefore,
                    currentValue,
                    classification.first(),
                    classification.second()
            );
            task.addResult(taskResult);
        }
        slowdownProcessing();
        task.setProgress(task.getProgress() + PROGRESS_STEP);
        taskRepository.update(task);
    }

    private void slowdownProcessing() {
        try {
            Thread.sleep(slowdownInMillis);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

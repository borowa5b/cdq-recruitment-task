package pl.borowa5b.cdq_recruitment_task.domain.model;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Classification;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskResult;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskResultId;

import static org.assertj.core.api.Assertions.assertThat;

class TaskTest {

    @Test
    void shouldSetTaskProgress() {
        // given
        final var task = new Task(new TaskId("TSK123432432"));

        // when
        task.setProgress(50);

        // then
        assertThat(task.getProgress()).isEqualTo(50);
    }

    @Test
    void shouldAddResult() {
        // given
        final var taskId = new TaskId("TSK123432432");
        final var task = new Task(taskId);
        final var taskResult = new TaskResult(
                new TaskResultId("TKR1232421"),
                taskId,
                "valueBefore",
                "currentValue",
                0.5,
                Classification.MEDIUM
        );

        // when
        task.addResult(taskResult);

        // then
        assertThat(task.getResults()).hasSize(1);
    }
}
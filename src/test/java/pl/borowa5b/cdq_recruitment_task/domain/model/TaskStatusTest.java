package pl.borowa5b.cdq_recruitment_task.domain.model;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;

import static org.assertj.core.api.Assertions.assertThat;

class TaskStatusTest {

    @Test
    void shouldSetProgress() {
        // given
        final var taskStatus = new TaskStatus(Stage.IN_PROGRESS, 0);
        final var newProgress = 50;

        // when
        taskStatus.setProgress(newProgress);

        // then
        assertThat(taskStatus.getProgress()).isEqualTo(newProgress);
    }
}
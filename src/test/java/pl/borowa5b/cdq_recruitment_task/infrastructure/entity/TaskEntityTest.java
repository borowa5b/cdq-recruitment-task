package pl.borowa5b.cdq_recruitment_task.infrastructure.entity;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.model.Task;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

import static org.assertj.core.api.Assertions.assertThat;

class TaskEntityTest {

    @Test
    void shouldConvertFromDomain() {
        // given
        final var task = new Task(new TaskId("TSK123432432"));

        // when
        final var result = TaskEntity.fromDomain(task);

        // then
        assertThat(result).isExactlyInstanceOf(TaskEntity.class);
        assertThat(result.getId()).isEqualTo(task.getId().value());
        assertThat(result.getStage()).isEqualTo(task.getStage());
        assertThat(result.getProgress()).isEqualTo(task.getProgress());
    }

    @Test
    void shouldConvertToDomain() {
        // given
        final var entity = TaskEntity.fromDomain(new Task(new TaskId("TSK123432432")));

        // when
        final var result = entity.toDomain();

        // then
        assertThat(result).isExactlyInstanceOf(Task.class);
        assertThat(result.getId().value()).isEqualTo(entity.getId());
        assertThat(result.getStage()).isEqualTo(entity.getStage());
        assertThat(result.getProgress()).isEqualTo(entity.getProgress());
    }
}
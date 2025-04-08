package pl.borowa5b.cdq_recruitment_task.infrastructure.entity;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Classification;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskResult;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskResultId;

import static org.assertj.core.api.Assertions.assertThat;

class TaskResultEntityTest {

    @Test
    void shouldConvertFromDomain() {
        // given
        final var taskResult = new TaskResult(
                new TaskResultId("TKR1232421"),
                new TaskId("TSK2423432"),
                "valueBefore",
                "currentValue",
                0.5,
                Classification.MEDIUM
        );

        // when
        final var result = TaskResultEntity.fromDomain(taskResult);

        // then
        assertThat(result).isExactlyInstanceOf(TaskResultEntity.class);
        assertThat(result.getId()).isEqualTo(taskResult.id().value());
        assertThat(result.getTaskId()).isEqualTo(taskResult.taskId().value());
        assertThat(result.getValueBefore()).isEqualTo(taskResult.valueBefore());
        assertThat(result.getCurrentValue()).isEqualTo(taskResult.currentValue());
        assertThat(result.getDissimilarity()).isEqualTo(taskResult.dissimilarity());
        assertThat(result.getClassification()).isEqualTo(taskResult.classification());
    }

    @Test
    void shouldConvertToDomain() {
        // given
        final var entity = TaskResultEntity.fromDomain(new TaskResult(
                new TaskResultId("TKR1232421"),
                new TaskId("TSK2423432"),
                "valueBefore",
                "currentValue",
                0.5,
                Classification.MEDIUM
        ));

        // when
        final var result = entity.toDomain();

        // then
        assertThat(result).isExactlyInstanceOf(TaskResult.class);
        assertThat(result.id().value()).isEqualTo(entity.getId());
        assertThat(result.taskId().value()).isEqualTo(entity.getTaskId());
        assertThat(result.valueBefore()).isEqualTo(entity.getValueBefore());
        assertThat(result.currentValue()).isEqualTo(entity.getCurrentValue());
        assertThat(result.dissimilarity()).isEqualTo(entity.getDissimilarity());
        assertThat(result.classification()).isEqualTo(entity.getClassification());
    }
}
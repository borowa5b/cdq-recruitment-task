package pl.borowa5b.cdq_recruitment_task.infrastructure.repository.read.tasks;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks.TaskQuery;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jooq.impl.DSL.field;

class TaskConditionsBuilderTest {

    @Test
    void shouldBuildTaskIdCondition() {
        // given
        final var query = new TaskQuery(new TaskId("TSK234234"), null);
        final var expectedCondition = field(TaskTableDefinition.Column.ID).eq(query.taskId().value());

        // when
        final var result = TaskConditionsBuilder.build(query);

        // then
        assertThat(result).isEqualTo(expectedCondition);
    }

    @Test
    void shouldBuildStageCondition() {
        // given
        final var query = new TaskQuery(null, Stage.IN_PROGRESS);
        final var expectedCondition = field(TaskTableDefinition.Column.STAGE).eq(query.stage().name());

        // when
        final var result = TaskConditionsBuilder.build(query);

        // then
        assertThat(result).isEqualTo(expectedCondition);
    }
}
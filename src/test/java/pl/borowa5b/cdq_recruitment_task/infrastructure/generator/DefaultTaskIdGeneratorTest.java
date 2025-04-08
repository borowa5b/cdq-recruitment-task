package pl.borowa5b.cdq_recruitment_task.infrastructure.generator;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultTaskIdGeneratorTest {

    @Test
    void shouldGenerateTaskId() {
        // given
        final var generator = new DefaultTaskIdGenerator(new DefaultIdGenerator());

        // when
        final var result = generator.generate();

        // then
        assertThat(result.value()).startsWith(TaskId.PREFIX);
    }
}
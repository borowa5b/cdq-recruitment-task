package pl.borowa5b.cdq_recruitment_task.infrastructure.generator;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskResultId;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultTaskResultIdGeneratorTest {

    @Test
    void shouldGenerateTaskResultId() {
        // given
        final var generator = new DefaultTaskResultIdGenerator(new DefaultIdGenerator());

        // when
        final var result = generator.generate();

        // then
        assertThat(result.value()).startsWith(TaskResultId.PREFIX);
    }
}
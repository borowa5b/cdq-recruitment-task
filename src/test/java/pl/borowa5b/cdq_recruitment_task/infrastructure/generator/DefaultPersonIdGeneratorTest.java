package pl.borowa5b.cdq_recruitment_task.infrastructure.generator;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultPersonIdGeneratorTest {

    @Test
    void shouldGeneratePersonId() {
        // given
        final var generator = new DefaultPersonIdGenerator(new DefaultIdGenerator());

        // when
        final var result = generator.generate();

        // then
        assertThat(result.value()).startsWith(PersonId.PREFIX);
    }
}
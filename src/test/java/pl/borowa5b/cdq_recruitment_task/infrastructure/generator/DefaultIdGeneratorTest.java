package pl.borowa5b.cdq_recruitment_task.infrastructure.generator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultIdGeneratorTest {

    @Test
    void shouldGenerateId() {
        // given
        final var generator = new DefaultIdGenerator();
        final var prefix = "prefix";

        // when
        final var result = generator.generate(prefix);

        // then
        assertThat(result).startsWith(prefix);
    }
}
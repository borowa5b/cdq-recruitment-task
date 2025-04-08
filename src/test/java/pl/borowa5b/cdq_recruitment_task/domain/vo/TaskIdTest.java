package pl.borowa5b.cdq_recruitment_task.domain.vo;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.AggregatingValidationExceptionHandler;

import static org.assertj.core.api.Assertions.assertThat;

class TaskIdTest {

    @Test
    void shouldValidateId() {
        // given
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        TaskId.validate("TSK123124", "taskId", exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isFalse();
    }

    @Test
    void shouldNotValidateId() {
        // given
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        TaskId.validate("PPPD242423", "taskId", exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isTrue();
    }
}